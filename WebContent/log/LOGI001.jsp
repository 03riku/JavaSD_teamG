<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>得点管理システム ログイン</title>
</head>
<body>
    <h2>ログイン</h2>
    <form action="LoginServlet" method="post">
        <label for="id">ID:</label>
        <input type="text" id="id" name="id" required>
        <br><br>
        <label for="password">パスワード:</label>
        <input type="password" id="password" name="password" required>
        <br><br>
        <input type="submit" value="ログイン">
    </form>
</body>
</html>