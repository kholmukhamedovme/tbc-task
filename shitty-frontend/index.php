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

    <a class="btn btn-block btn-outline-primary btn-lg" href="create.php">CREATE</a>

    <br><br>

    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Photo</th>
            <th scope="col">Name</th>
            <th scope="col">Status</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <?php
        $query = mysqli_query($mysqli, "SELECT * FROM user");

        while ($data = mysqli_fetch_array($query)):
            ?>
            <tr>
                <th scope="row"><?= $data["id"]; ?></th>
                <td>
                    <? if ($data["hasPhoto"]): ?>
                        <img src="http://localhost/photos/<?= $data["id"]; ?>.jpg" height="100"/>
                    <? else: ?>
                        NO PHOTO
                    <? endif; ?>
                </td>
                <td><?= $data["name"]; ?></td>
                <td><?= ($data["status"] ? "ACTIVATED" : "DEACTIVATED"); ?></td>
                <td>
                    <a href="update.php?id=<?= $data["id"]; ?>">UPDATE</a>
                    |
                    <a href="delete.php?id=<?= $data["id"]; ?>">DELETE</a>
                </td>
            </tr>
        <?php
        endwhile;
        ?>
        </tbody>
    </table>

    <footer class="my-5 pt-5 text-muted text-center text-small"></footer>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
