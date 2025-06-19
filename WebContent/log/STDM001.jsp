<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>得点管理システム - 学生管理一覧</title>
    <style>
        body {
            margin: 0;
            font-family: sans-serif;
        }
        .layout {
            display: flex;
        }
        .sidebar {
            width: 180px;
            background-color: #f9f9f9;
            height: 100vh;
            padding: 20px;
            box-sizing: border-box;
            border-right: 1px solid #ccc;
        }
        .sidebar a {
            display: block;
            margin: 10px 0;
            color: #333;
            text-decoration: none;
        }
        .content {
            flex: 1;
            padding: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        h2 {
            margin: 0;
        }
        form {
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            gap: 10px;
            margin: 20px 0;
        }
        label {
            margin-right: 5px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            border: 1px solid #999;
            padding: 5px 10px;
            text-align: center;
        }
        .no-data {
            margin-top: 20px;
            font-weight: bold;
            color: red;
        }
    </style>
</head>
<body>

<div class="layout">
    <!-- ⑪：左リンク -->
    <div class="sidebar">
        <a href="STDM001">学生管理</a>  <!-- ⑪ -->
        <a href="TESTM001">成績管理</a> <!-- ⑧ -->
    </div>

    <div class="content">
        <!-- タイトルとログアウト -->
        <div class="header">
            <h2>得点管理システム</h2> <!-- 画面タイトル -->
            <div>大阪 第一高校 教員さん | <a href="logout.jsp">ログアウト</a> <!-- ⑯ --> </div>
        </div>

        <h3>学生管理</h3> <!-- ① -->

        <!-- 検索フォーム（②〜⑨） -->
        <form method="get" action="STDM001">
            <label>入学年度</label> <!-- ② -->
            <select name="entYear">
                <option value="">--</option>
                <option value="2021">2021</option>
                <option value="2022">2022</option>
                <option value="2023">2023</option>
            </select> <!-- ③ -->

            <label>クラス</label> <!-- ④ -->
            <select name="classNum">
                <option value="">--</option>
                <option value="1">1組</option>
                <option value="2">2組</option>
                <option value="3">3組</option>
            </select> <!-- ⑤ -->

            <label>在学中</label> <!-- ⑥ -->
            <input type="checkbox" name="isAttend" value="true" checked> <!-- ⑦ -->

            <button type="submit">絞り込み</button> <!-- ⑨ -->
        </form>

        <!-- テーブル（⑩〜㉓） -->
        <table>
            <thead>
                <tr>
                    <th>入学年度</th>    <!-- ⑫ -->
                    <th>学籍番号</th>    <!-- ⑬ -->
                    <th>氏名</th>        <!-- ⑭ -->
                    <th>高校名</th>      <!-- ⑮ -->
                    <th>学年</th>        <!-- ⑯ -->
                    <th>クラス</th>      <!-- ⑰ -->
                    <th>在学</th>        <!-- ⑱ -->
                    <th>詳細</th>        <!-- ⑲ -->
                </tr>
            </thead>
            <tbody>
                <c:forEach var="s" items="${students}">
                    <tr>
                        <td>${s.entYear}</td>        <!-- ⑳ -->
                        <td>${s.studentNo}</td>      <!-- ㉑ -->
                        <td>${s.studentName}</td>    <!-- ㉒ -->
                        <td>${s.schoolName}</td>     <!-- ㉓ -->
                        <td>${s.grade}</td>
                        <td>${s.classNum}</td>
                        <td><input type="radio" <c:if test="${s.isAttend}">checked</c:if> disabled></td>
                        <td><a href="STDM002?studentId=${s.studentNo}">詳細</a></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty students}">
                    <tr>
                        <td colspan="8">※絞り込み条件に該当する学生情報がありません</td> <!-- ㉔ -->
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
