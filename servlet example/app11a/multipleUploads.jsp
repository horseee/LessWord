<!DOCTYPE HTML>
<html>
<body>
<h1>Select a file to upload</h1>
<form action="multipleUploads" enctype="multipart/form-data"
        method="post">
    Author : <input name="author"/><br/>
    First file to upload <input type="file" name="filename"/>
    <br/>
    Second file to upload <input type="file" name="filename"/>
    <br/>
    <input type="submit" value="Upload"/>
</form>
</body>
</html>