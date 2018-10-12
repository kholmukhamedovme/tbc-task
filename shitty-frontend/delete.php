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
    if (!isset($_GET["id"]) || empty($_GET["id"])) {
        echo "<div class=\"alert alert-warning\" role=\"alert\">User ID is not provided!</div>";
    } else {
        $query = mysqli_query($mysqli, "DELETE FROM user WHERE id = " . $_GET["id"]);

        if ($query) {
            echo "<div class=\"alert alert-success\" role=\"alert\">User successfully deleted!</div>";
        } else {
            echo "<div class=\"alert alert-danger\" role=\"alert\">Problem occurred while deleting user!</div>";
        }
    }
    ?>

    <footer class="my-5 pt-5 text-muted text-center text-small"></footer>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
