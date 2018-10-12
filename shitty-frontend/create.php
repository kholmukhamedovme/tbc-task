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

    <form action="http://localhost/users/" method="post" enctype="multipart/form-data">
        Photo: <input type="file" name="photo"/>
        <br>
        Name: <input type="text" name="name"/>
        <br>
        Status: <input type="radio" name="status" value="true"> ACTIVE | <input type="radio" name="status"
                                                                                value="false"> INACTIVE
        <br>
        <button type="submit">Apply</button>
    </form>

    <footer class="my-5 pt-5 text-muted text-center text-small"></footer>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
