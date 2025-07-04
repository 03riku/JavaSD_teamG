<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Teacher BeanとSchool Beanがセッションスコープに保存されていることを前提とします --%>
<%-- 例: session.setAttribute("teacher", teacherObject); --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目情報削除</title>
    <style>
    	body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .header {
            width: 100%;
            padding: 10px 20px;
            border-bottom: 1px solid #ccc; /* ヘッダー下の線 */
            box-sizing: border-box; /* パディングを幅に含める */
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #f8f8f8; /* ヘッダーの背景色 */

        }
        .header h1 {
            margin: 0;
            font-size: 1.5em;
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
        .container {
        	display: flex;
            height: calc(100vh - 50px); /* ヘッダーの高さ分を引く */
        }
        .sidebar {
        	width: 180px; /* Fixed width for navigation */
            background-color: #f0f0f0; /* Dark blue/gray background */
            padding: 20px 0; /* Vertical padding */
            border-right: 1px solid #ccc; /* Darker border for separation */
            box-sizing: border-box;
        }
        .sidebar ul {
        	list-style: none;
            padding: 0;
            margin: 0;
        }
        .sidebar li {
        	margin-bottom: 10px; /* Space between list items */
        }
        .sidebar a, .sidebar label {
        	text-decoration: none;
            color: #333;
            display: block;
            padding: 5px 0;
            font-size: 0.95em;
        }
        .sidebar a:hover {
        	background-color: #2c3e50; /* Slightly darker on hover */
        }
        .sidebar label {
        	font-weight: bold; /* Make labels bold */
            color: #007bff; /* 現在のページを強調 */
        }
        /* メインコンテンツ */
        .content {
        	flex-grow: 1; /* 残りのスペースを埋める */
            padding: 20px 30px; /* 左右のパディングを増やす */
            background-color: #fff;
        }
        .content h2 {
        	font-size: 1.8em;
            color: #333;
            margin-top: 0; /* Remove default top margin */
            margin-bottom: 15px;
            border-bottom: 1px solid #eee; /* Subtle line under title */
            padding-bottom: 10px;
        }

    </style>
</head>
<body>
    <div class="header">
    	<h1>得点管理システム</h1>
        <a href="LOGO001.jsp">ログアウト</a>
    </div>

    <div class="container">
    	<div class="sidebar">
	        <ul>
	            <li><a href="MMNU001.jsp">メニュー</a></li>
	            <li><a href="STDM001.jsp">学生管理</a></li>
	            <li><label>成績管理</label></li>
	            <li><a href="GRMU001.jsp">成績登録</a><br></li>
	            <li><a href="GRMR001.jsp">成績検索</a></li>
	            <li><a href="SBJM001.jsp">科目管理</a></li>
	        </ul>
		</div>


	    <div class="content">
			<h2>科目情報削除</h2>

			<%-- メッセージ表示エリア (エラーや成功メッセージをサーブレットから受け取って表示) --%>
			<c:if test="${not empty errorMessage}">
				<div class="error-message">${errorMessage}</div>
			</c:if>
			<c:if test="${not empty successMessage}">
				<div class="success-message">${successMessage}</div>
			</c:if>

			<p>以下の科目を削除します。よろしいですか？</p>

			<form action="${pageContext.request.contextPath}/main/subjectdelete_done" method="post"> <%-- 削除処理を行うサーブレット名を指定 --%>
				<div>
					<label>科目コード:</label>
					<%-- 科目コードは表示専用 --%>
				    <span>${cd}</span>
				    <input type="hidden" name="cd" value="${cd}"> <%-- 削除対象の科目を特定するためにhiddenで送信 --%>
				</div>
				 <br><br>

				<div>
				 	<label>科目名:</label>
				    <%-- 科目名も表示専用 --%>
				    <span>${name}</span>
				    <input type="hidden" name="name" value="${name}"> <%-- 必要であれば科目名もhiddenで送信 --%>
				</div>
				<br><br>

				<div class="button-group">
					<button type="submit" name="execute">削除</button>
				    <a href="SRJM001.jsp">戻る</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>