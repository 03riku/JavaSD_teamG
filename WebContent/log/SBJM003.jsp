<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目登録完了</title>
    <style>
    	/* 全体のフォントと基本的なマージン */
    	body{
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
    	.header a {
    		text-decoration: none;
            color: #007bff;
            font-size: 0.9em;
        }
        .header a:hover {
        	text-decoration: underline;
        }

        /* メインコンテンツのコンテナ */
        .container {
        	display: flex;
            height: calc(100vh - 50px); /* ヘッダーの高さ分を引く */
        }

        /* サイドメニューのスタイル */
        .sidebar {
        	width: 180px; /* サイドバーの幅を調整 */
            border-right: 1px solid #ccc; /* サイドバーの右側の線 */
            padding: 20px;
            box-sizing: border-box;
            background-color: #f0f0f0; /* サイドバーの背景色 */
        }
        .sidebar ul {
        	list-style: none;
            padding: 0;
            margin: 0;
        }
        .sidebar li {
        	margin-bottom: 10px;
        }
        .sidebar a, .sidebar label {
        	text-decoration: none;
            color: #333;
            display: block;
            padding: 5px 0;
            font-size: 0.95em;
        }
        .sidebar a:hover {
        	color: #007bff;
            text-decoration: underline;
        }
        .sidebar label {
        	font-weight: bold;
            color: #007bff; /* 現在のページを強調 */
        }

        /* メインコンテンツエリア */
        .main-content {
        	flex-grow: 1; /* 残りのスペースを埋める */
            padding: 20px 30px; /* 左右のパディングを増やす */
            background-color: #fff;
        }

        .main-content h2 {
        	font-size: 1.8em;
            margin-top: 0;
            margin-bottom: 20px;
            color: #333;
        }


    </style>
</head>
<body>
<div class="header">
	<h1>得点管理システム</h1>
    	<c:if test="${not empty teacher}">
        	${teacher.school.name} ${teacher.name}様&nbsp;
        </c:if>
        <a href="LOGO001.jsp">ログアウト</a>
</div>

<div class="container">
	<div class="sidebar">
    	<ul>
            <li><a href="${pageContext.request.contextPath}/log/MMNU001.jsp">メニュー</a></li>
            <li><a href="${pageContext.request.contextPath}/log/STDM001.jsp">学生管理</a></li>
            <li><label>成績管理</label></li>
            <li><a href="${pageContext.request.contextPath}/log/GRMU001.jsp">成績登録</a></li>
            <li><a href="${pageContext.request.contextPath}/log/GRMR001.jsp">成績検索</a></li>
            <li><a href="${pageContext.request.contextPath}/log/SBJM001.jsp">科目管理</a></li>
        </ul>
	</div>
   	<%-- メインコンテンツのタイトル --%>
    <div class="main-content">
    	<h2>科目情報登録</h2> <%-- ① 科目情報登録 --%>
    			<%-- 登録完了メッセージ --%>
    			<table width="60%" cellpadding="10" cellspacing="0" border="1" bgcolor="#ccffcc">
    				<tr>
    					<td align="center">登録が完了しました</td>
    				</tr>
    			</table>
    			<br>
    	<div class="button-group">
    		<a href="${pageContext.request.contextPath}/log/SBJM002.jsp">戻る</a> <%-- ③ 戻る --%>
    			&nbsp;
    		<a href="${pageContext.request.contextPath}/log/SBJM001.jsp">科目一覧</a> <%-- ④ 科目一覧 (仮のリンク先) --%>
		</div>
	</div>
</div>
</body>
</html>