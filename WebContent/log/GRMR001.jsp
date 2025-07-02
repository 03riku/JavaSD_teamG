<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>得点管理システム - 成績参照</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }

        .header {
            background-color: #e6f0fc;
            padding: 10px 20px;
            border-bottom: 1px solid #c0d9ef;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        }

        .header h1 {
            font-size: 24px;
            margin: 0;
            color: #333;
        }

        .user-info {
            font-size: 14px;
            color: #555;
        }

        .user-info a {
            color: #007bff;
            text-decoration: none;
            margin-left: 10px;
        }

        .user-info a:hover {
            text-decoration: underline;
        }

        .container {
            display: flex;
            min-height: calc(100vh - 41px);
            background-color: #fff;
        }

        .menu {
            width: 200px;
            padding: 20px 0;
            border-right: 1px solid #e0e0e0;
            background-color: #fdfdfd;
            box-shadow: 2px 0 5px rgba(0,0,0,0.03);
        }

        .menu ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .menu ul li {
            margin-bottom: 5px;
        }

        .menu ul li a {
            text-decoration: none;
            color: #007bff;
            display: block;
            padding: 8px 25px;
            font-size: 14px;
            transition: background-color 0.2s ease, color 0.2s ease;
        }

        .menu ul li a:hover {
            background-color: #e0f2ff;
            color: #0056b3;
        }

        .menu ul li a.active {
            background-color: #dbeaff;
            color: #0056b3;
            font-weight: bold;
        }

        .main-content {
            flex-grow: 1;
            padding: 20px 30px;
            background-color: #fff;
        }

        .section {
            background-color: #fff;
            padding: 20px 25px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }

        .section-title {
            font-size: 20px;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
            color: #333;
            font-weight: bold;
        }

        .form-group {
            display: flex;
            align-items: center;
            margin-bottom: 15px;
            gap: 15px;
            flex-wrap: wrap;
        }

        .form-group label {
            width: 80px;
            min-width: 80px;
            text-align: right;
            font-weight: bold;
            color: #555;
            flex-shrink: 0;
        }

        .sub-section-label {
            text-align: left;
            font-weight: bold;
            width: auto;
            flex-grow: 1;
            color: #333;
            font-size: 16px;
            margin-top: 15px;
        }

        .form-group select,
        .form-group input[type="text"] {
            flex-grow: 1;
            max-width: 180px;
            padding: 8px 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }

        .form-group input[type="text"]::placeholder {
            color: #aaa;
        }

        .search-button {
            background-color: #4f4f4f;
            color: white;
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
            transition: background-color 0.2s ease, transform 0.1s ease;
            margin-left: 10px;
            white-space: nowrap;
        }

        .search-button:hover {
            background-color: #3a3a3a;
            transform: translateY(-1px);
        }

        .search-button:active {
            transform: translateY(0);
        }

        .info-message {
            margin-top: 25px;
            padding: 12px;
            background-color: #e0f7fa;
            border: 1px solid #b2ebf2;
            border-radius: 5px;
            color: #00796b;
            font-size: 14px;
            text-align: center;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }

        .form-group.field-row {
            justify-content: flex-start;
            gap: 15px;
        }

        .form-group.field-row > label {
            width: auto;
            text-align: left;
            font-weight: normal;
            color: #333;
        }

        .form-group.student-search {
            align-items: center;
        }

        .form-group.student-search .search-button {
            margin-left: 20px;
        }
    </style>
</head>
<body>

    <div class="header">
        <h1>得点管理システム</h1>
        <div class="user-info">
            大原 太郎様 <a href="LOGO001.jsp">ログアウト</a>
        </div>
    </div>

    <div class="container">
        <div class="menu">
            <ul>
                <li><a href="MMNU001.jsp">メニュー</a></li>
                <li><a href="STDM001.jsp">学生管理</a></li>
                <li><a>成績管理</a></li>
                <li><a href="GRMU001.jsp" class="active">成績参照</a></li>
                <li><a href="GRMR001.jsp">成績登録</a></li>
                <li><a href="SBJM002.jsp">科目管理</a></li>
            </ul>
        </div>

        <div class="main-content">
            <div class="section">
                <div class="section-title">成績参照</div>

                <%-- フォームのactionをTestList.actionに修正 --%>
                <form action="TestList.action" method="get">
                    <div class="form-group">
                        <span class="sub-section-label">科目情報</span>
                    </div>
                    <div class="form-group field-row">
                        <label for="year">入学年度</label>
                        <select id="year" name="year">
                            <option value="">------</option>
                            <%-- Servletから渡される entYears リストをJSTLでループ --%>
                            <c:forEach var="yearOption" items="${entYears}">
                                <option value="${yearOption}" <c:if test="${param.year eq yearOption}">selected</c:if>>${yearOption}</option>
                            </c:forEach>
                        </select>

                        <label for="class">クラス</label>
                        <select id="class" name="class_num"> <%-- name属性をclass_numに変更 --%>
                            <option value="">------</option>
                            <%-- Servletから渡される classNums リストをJSTLでループ --%>
                            <c:forEach var="classOption" items="${classNums}">
                                <option value="${classOption}" <c:if test="${param.class_num eq classOption}">selected</c:if>>${classOption}</option> <%-- param.class_numに修正 --%>
                            </c:forEach>
                        </select>

                        <label for="subject">科目</label>
                        <select id="subject" name="subject">
                            <option value="">------</option>
                            <%-- Servletから渡される subjects リストをJSTLでループ --%>
                            <c:forEach var="subjectOption" items="${subjects}">
                                <option value="${subjectOption.cd}" <c:if test="${param.subject eq subjectOption.cd}">selected</c:if>>${subjectOption.name}</option>
                            </c:forEach>
                        </select>

                        <button type="submit" class="search-button">検索</button>
                    </div>

                    <div class="form-group" style="margin-top: 15px;">
                        <span class="sub-section-label">学生情報</span>
                    </div>
                    <div class="form-group student-search">
                        <label for="studentId">学生番号</label>
                        <input type="text" id="studentId" name="studentId" placeholder="学生番号を入力してください" value="${param.studentId}">
                        <button type="submit" class="search-button">検索</button>
                    </div>
                </form>

                <div class="info-message">
                    <c:choose>
                        <%-- Servletからmessageが渡されていれば表示、そうでなければデフォルトメッセージ --%>
                        <c:when test="${not empty message}">${message}</c:when>
                        <c:otherwise>科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

</body>
</html>