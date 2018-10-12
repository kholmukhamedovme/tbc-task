<?php

require_once __DIR__ . "/vendor/autoload.php";
require_once __DIR__ . "/models/ErrorModel.php";
require_once __DIR__ . "/models/UserModel.php";

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

$app = new Silex\Application();
$mysqli = new mysqli("db", "tbc_user", "tbc_pass", "tbc_db", 3306);

/**
 * Create user
 *
 * Input:
 * - name [REQUIRED]
 * - status [OPTIONAL]
 * - photo (file) [OPTIONAL]
 */
$app->POST("/users/", function (Application $app, Request $request) use ($mysqli) {
    $user = new UserModel();

    $name = $request->get("name");
    $status = $request->get("status");
    $photo = $request->files->get("photo");

    //region Validation
    if ($name == null) {
        return $app->json(
            new ErrorModel(ErrorModel::REQUIRED_FIELD_NOT_PROVIDED, "Required field NAME is not provided"),
            Response::HTTP_BAD_REQUEST
        );
    } else {
        $user->name = $name;
    }

    if ($photo != null && $photo->getMimeType() != image_type_to_mime_type(IMAGETYPE_JPEG)) {
        return $app->json(
            new ErrorModel(ErrorModel::FORMAT_IS_NOT_SUPPORTED, "Profile picture must be in JPEG"),
            Response::HTTP_BAD_REQUEST
        );
    }
    //endregion

    $queryColumns[] = "name";
    $queryValues[] = "'" . mysqli_escape_string($mysqli, $user->name) . "'";

    if ($status != null) {
        $user->status = filter_var($status, FILTER_VALIDATE_BOOLEAN);
        $queryColumns[] = "status";
        $queryValues[] = intval($user->status);
    }
    if ($photo != null) {
        $user->hasPhoto = true;
        $queryColumns[] = "hasPhoto";
        $queryValues[] = intval($user->hasPhoto);
    }

    $queryStatus = $mysqli->query("INSERT INTO user (" . implode(",", $queryColumns) . ") VALUES (" . implode(",", $queryValues) . ")");
    $user->id = $mysqli->insert_id;

    if ($queryStatus) {
        if ($user->hasPhoto) {
            if (move_uploaded_file($photo->getPathName(), "photos/" . $user->id . ".jpg")) {
                return $app->json($user);
            } else {
                return $app->json(
                    new ErrorModel(ErrorModel::FILE_OPERATION_ERROR, "Problem occurred while uploading profile photo"),
                    Response::HTTP_INTERNAL_SERVER_ERROR
                );
            }
        } else {
            return $app->json($user);
        }
    } else {
        return $app->json(
            new ErrorModel(ErrorModel::DB_OPERATION_ERROR, "Problem occurred while creating user"),
            Response::HTTP_INTERNAL_SERVER_ERROR
        );
    }
});

/**
 * Delete user
 *
 * Input:
 * - id [REQUIRED]
 */
$app->DELETE("/users/", function (Application $app, Request $request) use ($mysqli) {
    $id = $request->get("id");

    //region Validation
    if ($id == null) {
        return $app->json(
            new ErrorModel(ErrorModel::REQUIRED_FIELD_NOT_PROVIDED, "Required field ID is not provided"),
            Response::HTTP_BAD_REQUEST
        );
    } else {
        $id = intval($id);
    }
    //endregion

    $queryStatus = $mysqli->query("DELETE FROM user WHERE id = $id");
    $fileStatus = file_exists("photos/" . $id . ".jpg") ? unlink("photos/" . $id . ".jpg") : true;

    if ($queryStatus && $fileStatus) {
        return $app->json();
    } else {
        return $app->json(
            new ErrorModel(ErrorModel::DB_OPERATION_ERROR, "Problem occurred while deleting user"),
            Response::HTTP_INTERNAL_SERVER_ERROR
        );
    }
});

/**
 * Update user
 *
 * Input:
 * - id [REQUIRED]
 * - name [OPTIONAL]
 * - status [OPTIONAL]
 * - photo (file) [OPTIONAL]
 */
$app->PUT("/users/", function (Application $app, Request $request) use ($mysqli) {
    $id = $request->get("id");
    $name = $request->get("name");
    $status = $request->get("status");

    //region Validation
    if ($id == null) {
        return $app->json(
            new ErrorModel(ErrorModel::REQUIRED_FIELD_NOT_PROVIDED, "Required field ID is not provided"),
            Response::HTTP_BAD_REQUEST
        );
    }
    //endregion

    $query = array();

    if ($name != null) {
        $query[] = "name = '" . mysqli_escape_string($mysqli, $name) . "'";
    }
    if ($status != null) {
        $query[] = "status = " . intval(filter_var($status, FILTER_VALIDATE_BOOLEAN));
    }

    $queryStatus = $mysqli->query("UPDATE user SET " . implode(",", $query) . " WHERE id = $id");

    if ($queryStatus) {
        return $app->json();
    } else {
        return $app->json(
            new ErrorModel(ErrorModel::DB_OPERATION_ERROR, "Problem occurred while updating user"),
            Response::HTTP_INTERNAL_SERVER_ERROR
        );
    }
});

/**
 * Get user(s)
 *
 * Input:
 * - id [OPTIONAL]
 */
$app->GET("/users/", function (Application $app, Request $request) use ($mysqli) {
    $id = $request->get("id");
    $oneUserSelection = false;

    //region Validation
    if ($id != null) {
        $oneUserSelection = true;
        $id = intval($id);
    }
    //endregion

    $queryResult = $mysqli->query("SELECT * FROM user " . ($oneUserSelection ? "WHERE id = $id" : ""));

    if ($queryResult) {
        if ($oneUserSelection) {
            $result = UserModel::fromArray($queryResult->fetch_assoc());
        } else {
            while ($user = $queryResult->fetch_assoc()) {
                $result[] = UserModel::fromArray($user);
            }
        }

        return $app->json($result);
    } else {
        return $app->json(
            new ErrorModel(ErrorModel::DB_OPERATION_ERROR, "Problem occurred while getting user(s)"),
            Response::HTTP_INTERNAL_SERVER_ERROR
        );
    }
});

$app->run();
