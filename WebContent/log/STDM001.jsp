<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>得点管理システム - 学生管理一覧</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
        }
        .sidebar {
            width: 200px;
            background-color: #f0f0f0;
            height: 100vh;
            float: left;
            padding: 20px;
            box-sizing: border-box;
        }
        .sidebar a {
            display: block;
            margin-bottom: 15px;
            text-decoration: none;
            color: black;
        }
        .main-content {
            margin-left: 200px;
            padding: 20px;
        }
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 5px 10px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <h3>メニュー</h3>
        <a href="STDM001.jsp">学生管理</a>
        <a href="TESTM001.jsp">成績管理</a>
    </div>

    <div class="main-content">
        <h2>得点管理システム</h2>
        <div>
            <span>ようこそ <strong>○○高校 教員</strong> さん</span> |
            <a href="logout.jsp">ログアウト</a>
        </div>

        <h3>学生管理</h3>

        <form action="STDM001" method="get">
            <label for="entYear">入学年度：</label>
            <select name="entYear" id="entYear">
                <option value="2021">2021</option>
                <option value="2022">2022</option>
                <option value="2023">2023</option>
            </select>

            <label for="class">クラス：</label>
            <select name="class" id="class">
                <option value="">--</option>
                <option value="1">1組</option>
                <option value="2">2組</option>
                <option value="3">3組</option>
            </select>

            <label>在学中：</label>
            <input type="checkbox" name="isAttend" id="isAttend" checked>

            <button type="submit">絞り込み</button>
        </form>

        <br>

        <table>
            <thead>
                <tr>
                    <th>入学年度</th>
                    <th>学籍番号</th>
                    <th>氏名</th>
                    <th>高校名</th>
                    <th>学年</th>
                    <th>クラス</th>
                    <th>在学</th>
                    <th>詳細</th>
                </tr>
            </thead>
            <tbody>
                <!-- JSTLで繰り返し -->
                <tr>
                    <td>2021</td>
                    <td>2123456</td>
                    <td>山田 太郎</td>
                    <td>大阪 第一高校</td>
                    <td>3</td>
                    <td>1</td>
                    <td><input type="radio" checked></td>
                    <td><a href="STDM002?studentId=2123456">詳細</a></td>
                </tr>
                <!-- 条件に該当しない場合 -->
                <!-- <tr><td colspan="8">※絞り込み条件に該当する学生情報がありません</td></tr> -->
            </tbody>
        </table>
    </div>
</body>
</html>
