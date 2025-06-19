<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>得点管理システム - 学生管理</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
        }
        .sidebar {
            width: 200px;
            height: 100vh;
            background-color: #f0f0f0;
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
        form {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

    <!-- 左メニュー -->
    <div class="sidebar">
        <h3>メニュー</h3>
        <a href="STDM001">学生管理</a>
        <a href="TESTM001">成績管理</a>
    </div>

    <!-- メインコンテンツ -->
    <div class="main-content">
        <h2>得点管理システム</h2>
        <div>
            <span>ようこそ <strong>○○高校 教員</strong> さん</span> |
            <a href="logout.jsp">ログアウト</a>
        </div>

        <h3>学生管理</h3>

        <!-- 絞り込みフォーム -->
        <form action="STDM001" method="get">
            <label for="entYear">入学年度：</label>
            <select name="entYear" id="entYear">
                <option value="">--</option>
                <option value="2021">2021</option>
                <option value="2022">2022</option>
                <option value="2023">2023</option>
            </select>

            <label for="classNum">クラス：</label>
            <select name="classNum" id="classNum">
                <option value="">--</option>
                <option value="1">1組</option>
                <option value="2">2組</option>
                <option value="3">3組</option>
            </select>

            <label>在学中：</label>
            <input type="checkbox" name="isAttend" value="true" checked>

            <button type="submit">絞り込み</button>
        </form>

        <!-- 学生一覧テーブル -->
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
                <c:forEach var="s" items="${students}">
                    <tr>
                        <td>${s.entYear}</td>
                        <td>${s.studentNo}</td>
                        <td>${s.studentName}</td>
                        <td>${s.schoolName}</td>
                        <td>${s.grade}</td>
                        <td>${s.classNum}</td>
                        <td><input type="radio" <c:if test="${s.isAttend}">checked</c:if> disabled></td>
                        <td><a href="STDM002?studentId=${s.studentNo}">詳細</a></td>
                    </tr>
                </c:forEach>

                <!-- 一致する学生がいない場合 -->
                <c:if test="${empty students}">
                    <tr>
                        <td colspan="8">※絞り込み条件に該当する学生情報がありません</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

</body>
</html>
