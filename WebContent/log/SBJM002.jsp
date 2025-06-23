<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Teacher BeanとSchool Beanがセッションスコープに保存されていることを前提とします --%>
<%-- 例: session.setAttribute("teacher", teacherObject); --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目情報登録</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 90%;
            margin: 20px auto;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            display: flex; /* Use flexbox for menu and main content layout */
            flex-wrap: wrap; /* Allow wrapping for responsiveness */
        }
        .header {
            width: 100%; /* Header takes full width */
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .user-info {
            font-size: 14px;
        }
        .user-info a {
            color: #007bff;
            text-decoration: none;
        }
        .menu {
            flex: 0 0 150px; /* Fixed width for menu */
            padding-right: 20px;
            border-right: 1px solid #eee;
            min-height: 400px; /* Adjust as needed */
        }
        .menu ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .menu ul li {
            margin-bottom: 10px;
        }
        .menu ul li a {
            text-decoration: none;
            color: #333;
            display: block;
            padding: 5px 0;
        }
        .menu ul li a.current {
            color: #007bff;
            font-weight: bold;
        }
        .main-content {
            flex-grow: 1; /* Main content takes remaining space */
            padding-left: 20px;
        }
        .main-content h2 {
            margin-top: 0;
            border-bottom: 2px solid #007bff;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }
        .new-subject-link {
            float: right;
            margin-top: -40px; /* Adjust to align with h2 */
            color: #007bff;
            text-decoration: none;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table th, table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        table th {
            background-color: #f2f2f2;
        }
        table td a {
            color: #007bff;
            text-decoration: none;
            margin-right: 10px;
        }
        .footer {
            clear: both;
            text-align: center;
            padding-top: 20px;
            border-top: 1px solid #eee;
            margin-top: 30px;
            font-size: 12px;
            color: #777;
            width: 100%; /* Footer takes full width */
        }
        .error-message {
            color: red;
            font-weight: bold;
            margin-bottom: 10px;
            font-size: 0.9em;
            margin-top: 5px;
        }
        .success-message {
            color: green;
            font-weight: bold;
            margin-bottom: 10px;
            font-size: 0.9em;
            margin-top: 5px;
        }
        /* Form specific styles for better alignment */
        form label {
            display: inline-block;
            width: 80px; /* Adjust as needed for label alignment */
            text-align: right;
            margin-right: 10px;
            vertical-align: top; /* Align with input */
        }
        form input[type="text"] {
            width: 200px; /* Adjust input width */
            padding: 5px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        form button {
            padding: 8px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 10px;
        }
        form button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>得点管理システム</h1>
            <div class="user-info">
                <c:if test="${not empty teacher}">
                    ${teacher.school.name} ${teacher.name}様&nbsp;
                </c:if>
                <a href="LOGO001.jsp">ログアウト</a>
            </div>
        </div>

        <div class="menu">
            <ul>
                <li><a href="MMNU001.jsp">メニュー</a></li>
                <li><a href="STDM001.jsp">学生管理</a></li>
                <li><label>成績管理</label></li>
                <li><a href="GRMU001.jsp">成績登録</a></li>
                <li><a href="GRMR001.jsp">成績検索</a></li>
                <li><a href="SBJM002.jsp" class="current">科目管理</a></li>
            </ul>
        </div>

        <div class="main-content">
            <h2>科目情報登録</h2>

            <%-- 登録成功メッセージの表示 --%>
            <c:if test="${not empty successMessage}">
                <div class="success-message">${successMessage}</div>
            </c:if>

            <form action="SubjectRegisterServlet" method="post">
                <label for="subjectCd">科目コード</label>
                <input type="text" id="subjectCd" name="cd" value="${subject.cd}" placeholder="科目コードを入力してください" required />
                <c:if test="${not empty errorSubjectCdEmpty}">
                    <div class="error-message">${errorSubjectCdEmpty}</div>
                </c:if>
                <c:if test="${not empty errorSubjectCdLength}">
                    <div class="error-message">${errorSubjectCdLength}</div>
                </c:if>
                <br>

                <label for="subjectName">科目名</label>
                <input type="text" id="subjectName" name="name" value="${subject.name}" placeholder="科目名を入力してください" required />
                <c:if test="${not empty errorSubjectNameEmpty}">
                    <div class="error-message">${errorSubjectNameEmpty}</div>
                </c:if>
                <br><br>

                <button type="submit" name="execute">登録</button>
            </form>

            <p><a href="SBJM001.jsp">戻る</a></p>

            <h2>科目情報一覧</h2>
            <c:choose>
                <%-- 科目リストが空の場合、メッセージを表示 --%>
                <c:when test="${empty subjects}">
                    <p>登録されている科目情報はありません。</p>
                </c:when>
                <%-- 科目リストにデータがある場合、テーブルで表示 --%>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>科目コード</th>
                                <th>科目名</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="subject" items="${subjects}">
                                <tr>
                                    <td><c:out value="${subject.cd}"/></td>
                                    <td><c:out value="${subject.name}"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="footer">
            <p>&copy; 2025 得点管理システム</p>
        </div>
    </div>
</body>
</html>