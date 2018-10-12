<?php
$mysqli = new mysqli("db", "tbc_user", "tbc_pass", "tbc_db", 3306);
?>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>TBC</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container">
    <div class="py-5 text-center">
        <h2>TBC</h2>
        <p class="lead">Here you can CRUD under user database</p>
    </div>

    <?php
    if (isset($_GET["id"]) || !empty($_GET["id"]) || isset($_POST["id"]) || !empty($_POST["id"])) {
        $query = mysqli_query($mysqli, "SELECT * FROM user WHERE id = " . $_REQUEST["id"]);
        $data = mysqli_fetch_array($query);

        if (isset($_POST["id"])) {
            $queryString = "UPDATE user SET ";

            $queryString .= "name = '" . $_POST["name"] . "', status = " . $_POST["status"] . " WHERE id = " . $_POST["id"];

            if (mysqli_query($mysqli, $queryString)) {
                echo "<div class=\"alert alert-success\" role=\"alert\">User successfully updated!</div>";
            } else {
                echo "<div class=\"alert alert-danger\" role=\"alert\">Problem occurred while updating user!</div>";
            }

        } else {
            ?>

            <form action="update.php" method="post">
                <input type="hidden" name="id" value="<?= $data["id"]; ?>"/>
                <br>
                Name: <input type="text" name="name" value="<?= $data["name"]; ?>"/>
                <br>
                Status: <input type="radio" name="status" <?php if ($data["status"]) echo "checked"; ?> value="true">
                ACTIVE | <input type="radio" name="status" <?php if (!$data["status"]) echo "checked"; ?> value="false">
                INACTIVE
                <br>
                <button type="submit">Apply</button>
            </form>

            <?php
        }
    } else {
        echo "<div class=\"alert alert-warning\" role=\"alert\">User ID is not provided!</div>";
    }
    ?>

    <footer class="my-5 pt-5 text-muted text-center text-small"></footer>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
