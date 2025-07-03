<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>メインメニュー</title>
	<style>
    	body {
      		font-family: Arial, sans-serif;
       		margin: 0;
        	padding: 0;
        	background-color: #f4f4f4;
    	}
    	.header {
      		background-color: #e0efff;
      		padding: 10px;
      		display: flex;
      		justify-content: space-between;
    	}
    	.sidebar {
      		float: left;
      		width: 15%;
      		height: 100vh;
      		border-right: 2px solid black;
      		padding: 10px;
    	}
    	.sidebar ul {
      		list-style: none;
            padding: 0;
            margin: 0;
      	}
    	.sidebar ul li {
       		margin-bottom: 10px;
    	}
    	.sidebar ul li a {
     		text-decoration: none;
            color: #333;
            display: block;
            padding: 5px 0;
     	}
    	.sidebar ul li a.current {
            color: #007bff;
            font-weight: bold;
    	}
    	.content {
      		margin-left: 17%;
      		padding: 20px;
    	}
    	.menu-box {
      		display: flex;
      		gap: 20px;
    	}
    	.box {
      		width: 180px;
      		height: 120px;
      		padding: 10px;
      		border-radius: 10px;
      		box-shadow: 0px 4px 8px rgba(0,0,0,0.1);
    	}
    	.box a {
      		display: block;
      		margin: 5px 0;
      		text-decoration: none;
      		color: blue;
    	}
    	.box-red { background-color: #f5c6cb; }
    	.box-green { background-color: #d4edda; }
    	.box-blue { background-color: #d6d8fb; }
    	footer {
     		text-align: center;
      		font-size: small;
      		margin-top: 40px;
    	}
	</style>
</head>
<body>
	<div class="container">
            <h1>得点管理システム</h1>
            <div class="user-info">
                <%-- ★ここも修正が必要な可能性あり。通常はセッションからユーザー名を取得。 --%>
                <%-- 例: <% Teacher teacher = (Teacher) session.getAttribute("teacher"); %> --%>
                <%-- <%= teacher != null ? teacher.getName() : "ゲスト" %>様 --%>
                <td align="right"><a href="${pageContext.request.contextPath}/log/LOGO001.jsp">ログアウト</a></td>
            </div>
        </div>

  	<div class="sidebar">
		<ul>
			<li><a href="STDM001.jsp">学生管理</a></li>
			<li>成績管理</li> <%-- ここをリンクなしのテキストに変更 --%>
			<li><a href="GRMU001.jsp">成績登録</a></li>
			<li><a href="TestListSubjectExecute.action">成績参照</a></li> <%-- ここはControllerへのリンクのまま --%>
			<li><a href="SBJM001.jsp">科目管理</a></li>
		</ul>
	</div>

  	<div class="content">
		<h2>メニュー</h2>
		<div class="menu-box">
			<div class="box box-red">
				<a href="STDM001.jsp"> 学生管理</a>
			</div>

      		<div class="box box-green">
				<div> 成績管理</div> <%-- この"成績管理"は元々リンクではないテキスト --%>
					<a href="GRMU001.jsp"> 成績登録</a>
					<a href="TestListSubjectExecute.action"> 成績参照</a> <%-- ここはControllerへのリンクのまま --%>
			</div>

      		<div class="box box-blue">
				<a href="SBJM001.jsp"> 科目管理</a>
			</div>
		</div>
	</div>

  <%-- <footer>
    <small>Copyright &copy; 2025.</small>
</footer> --%>

</body>
</html>