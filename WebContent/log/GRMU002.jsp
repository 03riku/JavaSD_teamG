<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>得点管理システム - 成績管理</title>
    <style>
        body {
            margin: 0;
            font-family: "Segoe UI", "ヒラギノ角ゴ ProN", Meiryo, sans-serif;
            background-color: #ffffff;
        }

        .header {
            background-color: #e6f0fc;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #ccc;
        }

        .header h1 {
            font-size: 20px;
            margin: 0;
            color: #333;
        }

        .user-info {
            font-size: 14px;
            color: #333;
        }

        .user-info a {
            color: #007bff;
            text-decoration: none;
            margin-left: 10px;
        }

        .container {
            display: flex;
            height: calc(100vh - 41px);
        }

        .menu {
            width: 180px;
            background-color: #fff;
            border-right: 1px solid #ddd;
            padding: 20px 10px;
        }

        .menu a {
            display: block;
            color: #007bff;
            text-decoration: none;
            margin-bottom: 10px;
            font-size: 14px;
        }

        .menu a:hover {
            text-decoration: underline;
        }

        .main-content {
            flex: 1;
            padding: 30px 40px;
        }

        .title-box {
            background-color: #f0f0f0;
            padding: 10px 20px;
            font-size: 18px;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }

        .message-box {
            background-color: #93c9a3;
            color: #000; /* ←黒に変更 */
            padding: 10px 20px;
            font-size: 14px;
            margin-bottom: 20px;
            text-align: center; /* ←中央に */
        }

        .bottom-links {
            margin-top: 30px;
            display: flex;
            gap: 30px;
        }

        .bottom-links a {
            font-size: 14px;
            color: #007bff;
            text-decoration: none;
        }

        .bottom-links a:hover {
            text-decoration: underline;
        }

    </style>
</head>
<body>

    <div class="header">
        <h1>得点管理システム</h1>
        <div class="user-info">
            大原 太郎様 <a href="LOGO001.jsp">ログアウト</a> <%-- LOGO001.jspがlogフォルダにある場合、href="log/LOGO001.jsp" に変更 --%>
        </div>
    </div>

    <div class="container">
        <div class="menu">
            <a href="MMNU001.jsp">メニュー</a> <%-- MMNU001.jspがlogフォルダにある場合、href="log/MMNU001.jsp" に変更 --%>
            <a href="STDM001.jsp">学生管理</a> <%-- STDM001.jspがlogフォルダにある場合、href="log/STDM001.jsp" に変更 --%>
            <div>成績管理</div>
            <a href="log/GRMR001.jsp">成績参照</a> <%-- 修正 --%>
            <a href="log/GRMU001.jsp">成績登録</a> <%-- 修正 --%>
            <a href="SBJM001.jsp">科目管理</a> <%-- SBJM001.jspがlogフォルダにある場合、href="log/SBJM001.jsp" に変更 --%>
        </div>

        <div class="main-content">
            <div class="title-box">成績管理</div>
            <div class="message-box"><c:out value="${message}" /></div> <%-- Controllerからのメッセージ表示に修正 --%>
            <div class="bottom-links">
                <a href="log/GRMU001.jsp">戻る</a> <%-- 修正 --%>
                <a href="log/GRMR001.jsp">成績参照</a> <%-- 修正 --%>
            </div>
        </div>
    </div>

</body>
</html>