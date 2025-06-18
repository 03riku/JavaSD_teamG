<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>得点管理システム</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 0;
            padding: 0;
            background-color: #ffffff;
        }

        /* ヘッダー */
        .header {
            background: #e0f0ff;
            padding: 20px;
            text-align: center;
        }

        .header h2 {
            margin: 0;
        }

        /* コンテンツ全体 */
        .container {
            width: 60%;
            margin: 40px auto;
        }

        /* ラベル（ログアウト） */
        p.label {
            background-color: #f0f0f0;
            padding: 10px;
            margin-bottom: 5px;
            font-weight: bold;
        }

        /* 緑ボックス中央揃え */
        .message-box {
            background-color: #c0e0c0;
            padding: 10px;
            margin-bottom: 5px;
            text-align: center; /* ← 中央寄せ */
        }

        /* ログインリンク */
        .login-link {
            margin-top: 10px;
            text-align: left;
        }

        .login-link a {
            font-size: 14px;
            color: blue;
            text-decoration: none;
        }

        /* フッター */
        .footer {
            background-color: #eeeeee;
            padding: 15px;
            font-size: 12px;
            text-align: center;
            margin-top: 60px;
        }
    </style>
</head>
<body>

<!-- ヘッダー -->
<div class="header">
    <h2>得点管理システム</h2>
</div>

<!-- コンテンツ -->
<div class="container">
    <p class="label">ログアウト</p>

    <div class="message-box">ログアウトしました</div>

    <div class="login-link">
        <a href="LOGI.jsp">ログイン</a>
    </div>
</div>

<!-- フッター -->
<div class="footer">
    © 2023 TIC<br>大学学園
</div>

</body>
</html>
