<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生管理</title>
    <style>
        body {
            margin: 0;
            font-family: 'Meiryo', sans-serif;
            font-size: 14px;
        }

        /* 最上部の青いヘッダーバー */
        header {
            background-color: #007bff;
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 20px;
            font-weight: bold;
        }

        header .user-info {
            font-size: 14px;
            font-weight: normal;
        }

        header .user-info a {
            color: white;
            text-decoration: none;
            margin-left: 10px;
        }

        /* 全体を左右に分ける */
        .container {
            display: flex;
            min-height: 100vh;
        }

        /* 左側のメニュー */
        .sidebar {
            background-color: #e6f0ff;
            width: 200px;
            padding: 20px;
        }

        .sidebar h3 {
            font-size: 16px;
            margin-bottom: 10px;
            color: #000;
        }

        .sidebar ul {
            list-style: none;
            padding-left: 0;
        }

        .sidebar li {
            margin-bottom: 10px;
        }

        .sidebar a {
            color: #000;
            text-decoration: none;
        }

        .sidebar a:hover {
            text-decoration: underline;
        }

        /* 右側のメイン */
        .main-content {
            flex-grow: 1;
            padding: 20px;
        }

        .box {
            background-color: #f5f5f5;
            border: 1px solid #ccc;
            padding: 20px;
        }

        h2 {
            font-size: 18px;
            margin-bottom: 20px;
        }

        .form-row {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
            align-items: center;
        }

        select, input[type="checkbox"] {
            padding: 5px;
        }

        .btn {
            background-color: #6c757d;
            color: white;
            border: none;
            padding: 8px 16px;
            cursor: pointer;
        }

        .btn:hover {
            background-color: #5a6268;
        }

        .new-register {
            text-align: right;
            margin-bottom: 10px;
        }

        .new-register a {
            text-decoration: none;
            color: #007bff;
        }

        .new-register a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <header>
        得点管理システム
        <div class="user-info">
            大原 太郎様　
            <a href="Logout.action">ログアウト</a>
        </div>
    </header>

    <div class="container">
        <!-- 左メニュー -->
        <nav class="sidebar">
            <h3>メニュー</h3>
            <ul>
                <li><a href="STDM001.jsp">学生管理</a></li>
                <li><a href="#">成績管理</a></li>
                <li><a href="GRMU001.jsp">成績登録</a></li>
                <li><a href="GRMR001.jsp">成績参照</a></li>
                <li><a href="SBJM002.jsp">科目管理</a></li>
            </ul>
        </nav>

        <!-- メイン -->
        <main class="main-content">
            <div class="box">
                <h2>学生管理</h2>

                <div class="new-register">
                    <a href="STDM002.jsp">新規登録</a>
                </div>

                <form action="StudentList.action" method="post">
                    <div class="form-row">
                        <label for="entYear">入学年度</label>
                        <select name="entYear" id="entYear">
                            <option value="">-----</option>
                            <option value="2021">2021</option>
                            <option value="2022">2022</option>
                        </select>

                        <label for="classNo">クラス</label>
                        <select name="classNo" id="classNo">
                            <option value="">-----</option>
                            <option value="201">201</option>
                            <option value="202">202</option>
                        </select>

                        <label><input type="checkbox" name="isEnrolled" value="true"> 在学中</label>

                        <button class="btn" type="submit">絞込み</button>
                    </div>
                </form>
            </div>
        </main>
    </div>

</body>
</html>
