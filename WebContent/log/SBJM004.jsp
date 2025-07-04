<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Teacher BeanとSchool Beanがセッションスコープに保存されていることを前提とします --%>
<%-- 例: session.setAttribute("teacher", teacherObject); --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目情報変更</title>
    <style>
        /* ここからCSSの記述 */
        body {
            font-family: sans-serif;
		    margin: 0;
		    display: flex;
		    flex-direction: column;
		    min-height: 100vh;
		    background-color: #f4f4f4; /* 全体の背景色を追加 */
        }

        /* Header Styles - Applied to the table */
        .header {
            background-color: #ffffff; /* 白背景 */
		    padding: 10px 20px;
		    display: flex;
		    justify-content: space-between;
		    align-items: center;
		    border-bottom: 1px solid #ddd; /* 境界線 */
		    box-shadow: 0 2px 4px rgba(0,0,0,0.1); /* 軽い影 */
        }

        .header h1 {
            margin: 0;
            font-size: 24em;
            color: #333;

        }

        .header h1:first-child {
            font-weight: bold;
            font-size: 1.5em;
            color: #333;
        }

        .header a {
            text-decoration: none;
            color: #007bff;
            font-size: 0.9em;

        }

        .header a:hover {
            text-decoration: underline;
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

        /* Main Layout Container for Navigation and Content */
        .main-container {
            display: flex;
            height: calc(100vh - 50px); /* ヘッダーの高さ分を引く */
        }

        /* Left Navigation Bar */
        .nav-sidebar {
            width: 200px;
		    background-color: #ffffff; /* 白背景 */
		    padding: 20px 0; /* 上下左右のパディング調整 */
		    border-right: 1px solid #ddd;
		    box-shadow: 2px 0 4px rgba(0,0,0,0.05); /* 軽い影 */
        }
        .sidebar h3 {
		    margin-top: 0;
		    padding: 0 20px; /* パディング */
		    color: #444;
		    border-bottom: 1px solid #eee;
		    padding-bottom: 10px;
		    margin-bottom: 15px;
		}

        .nav-sidebar ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .nav-sidebar li {
            margin-bottom: 5px;
        }

        .nav-sidebar a{
            text-decoration: none;
		    color: #333;
		    display: block;
		    padding: 10px 20px; /* パディング */
		    transition: background-color 0.3s ease; /* ホバーアニメーション */

        }

        .nav-sidebar a:hover {
            background-color: #e9e9e9;
    		color: #007bff;
        }

        /* Main Content Area */
        .main-content {
            flex-grow: 1;
		    padding: 20px;
		    background-color: #ffffff; /* 白背景 */
		    margin-left: 10px; /* サイドバーとの間に少しスペース */
		    border-radius: 8px; /* 角を丸くする */
		    box-shadow: 0 0 10px rgba(0,0,0,0.05); /* 軽い影 */

        }

        .main-content h2 {
            margin-top: 0;
		    padding-bottom: 15px;
		    border-bottom: 2px solid #007bff; /* 青い下線 */
		    color: #333;
		    margin-bottom: 25px;
		    font-size: 28px;

        }


        .form-group {
            margin-bottom: 20px;
            display: flex; /* Flexbox for label and input */
            align-items: center; /* Vertical alignment */
        }

        .form-group label {
            display: inline-block;
            width: 120px; /* Fixed width for label alignment */
            font-weight: bold;
            color: #555;
            text-align: right; /* Right align labels */
            margin-right: 20px; /* Space between label and input */
        }

        .form-group input[type="text"],
        .form-group select {
            padding: 9px 12px; /* Adjusted padding */
            border: 1px solid #ccc;
            border-radius: 4px; /* Slightly rounded corners */
            width: 250px; /* Adjusted width */
            font-size: 16px;
            box-sizing: border-box; /* Include padding and border in width */
        }

        /* Style for readonly text span */
        .readonly-field {
            display: inline-block;
            padding: 9px 12px;
            border: 1px solid #e9e9e9; /* Lighter border for read-only */
            background-color: #e9e9e9; /* Gray background for read-only */
            color: #777;
            border-radius: 4px;
            width: 250px; /* Match input width */
            box-sizing: border-box;
            margin-bottom: 15px; /* Space below field */
        }

        /* Error and Success Messages */
        .error-message {
            color: #dc3545; /* Red for errors */
            font-size: 0.85em;
            margin-top: -10px; /* Pull error message closer to input */
            margin-left: 140px; /* Adjust based on label width + margin */
            display: block; /* Ensure it's on its own line */
            padding-bottom: 5px;
        }

        .success-message {
            color: #28a745; /* Green for success */
            font-size: 1em;
            margin-bottom: 20px;
            padding: 10px 15px;
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            border-radius: 4px;
        }

        .form-actions {
            margin-top: 30px;
            text-align: center; /* Center buttons */
        }

        .form-actions button {
            padding: 12px 25px;
            margin: 0 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .form-actions button[type="submit"] {
            background-color: #007bff; /* Primary blue button */
            color: white;
        }

        .form-actions button[type="submit"]:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
        }

        .form-actions button[type="button"] { /* For "戻る" button */
            background-color: #6c757d; /* Gray button */
            color: white;
        }

        .form-actions button[type="button"]:hover {
            background-color: #5a6268;
            transform: translateY(-2px);
        }

        /* Adjust the '戻る' link separately if not within form-actions */
        .back-link {
            display: inline-block; /* Make it block to control margin */
            margin-top: 25px; /* Space above the link */
            color: #007bff;
            text-decoration: none;
            margin-left: 125px; /* Align with form fields, roughly */
        }

        .back-link:hover {
            text-decoration: underline;
        }

        /* Clear floats (though flexbox usage reduces need) */
        .clearfix::after {
            content: "";
            display: table;
            clear: both;
        }
        /* ここまでCSSの記述 */
    </style>
</head>
<body>
    <div class="header">
        <h1>得点管理システム</h1>
        <div class="user-info">
        	<a href="${pageContext.request.contextPath}/log/LOGU001.jsp">ログアウト</a>
        </div>
    </div>

    <div class="main-container">
        <div class="nav-sidebar">
        	<h3>メニュー</h3>
        	<ul>
                <li><a href="${pageContext.request.contextPath}/log/STDM001.jsp">学生管理</a></li>
                <li><label>成績管理</label></li>
                <li><a href="${pageContext.request.contextPath}/log/GRMU001.jsp">成績登録</a></li>
                <li><a href="${pageContext.request.contextPath}/log/GRMR001.jsp">成績検索</a></li>
                <li><a href="${pageContext.request.contextPath}/log/SRJM002.jsp">科目管理</a></li>
            </ul>
        </div>

        <div class="main-content">
            <h2>科目情報変更</h2>
            <%-- エラーメッセージがあれば表示 --%>
            <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="success-message">${successMessage}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/subject_update_done" method="post">
                <div class="form-group">
                	<label for="no">科目コード</label>
                	<input type="hidden" id="no" name="no" value="${cd}" placeholder="科目コードを入力してください" required />
                	<p>${cd}</p>
                	<c:if test="${not empty errorStudentEmpty}">
                		<div class="error-message">${errorStudentNameDuplicate}></div>
                	</c:if>
                </div>

                <div class="form-group">
                    <label for="subjectName">科目名</label>
                    <input type="text" id="subjectName" name="name" value="${name}" placeholder="科目名を入力してください" required />
                    <c:if test="${not empty errorSubjectNameEmpty}">
                    	<div class="error-message">${errorSubjectNameEmpty}</div>
                	</c:if>
                </div>

                <div class="form-actions">
                    <button type="submit" name="execute">変更</button>
                    <button type="button" onclick="history.back()">戻る</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>