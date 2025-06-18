<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>得点管理システム</title>
    <style>
        body {
            font-family: sans-serif;
            background-color: #ffffff;
            margin: 0;
            padding: 0;
            text-align: center;
        }
        .header {
            background: #e0f0ff;
            padding: 20px;
            font-size: 24px;
            font-weight: bold;
        }
        .box {
            margin: 30px auto;
            width: 50%;
        }
        .message {
            background-color: #d9eede;
            padding: 10px;
            margin-top: 10px;
        }
        .footer {
            margin-top: 40px;
            padding: 20px;
            background-color: #eeeeee;
            font-size: 12px;
        }
        a {
            color: blue;
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="header">得点管理システム</div>

<div class="box">
    <div>ログアウト</div>
    <div class="message"> ログアウトしました</div>
</div>


<!-- コンテンツ -->
<div class="container">

        <a href="LOGI.jsp">ログイン</a>
    </div>
</div>

<!-- フッター -->
<div class="footer">
    © 2023 TIC<br>大原学園
</div>

</body>
</html>
