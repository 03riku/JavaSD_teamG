<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    .header strong {
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
    .sidebar {
      float: left;
      width: 15%;
      height: 100vh;
      border-right: 2px solid black;
      padding: 10px;
    }
    .sidebar ul {
    	list-style: none;
            padding: 10px;
            margin: 15px;
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
    .content {
      flex-grow: 1; /* 残りのスペースを埋める */
            padding: 50px 30px; /* 左右のパディングを増やす */
            background-color: #fff;
    }
    .content h2 {
    	font-size: 1.8em;
            margin-top: 0;
            margin-bottom: 20px;
            color: #333;
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
<div class="header">
<div><strong>得点管理システム</strong></div>
<div><a href="LOGO001.jsp">ログアウト</a></div>
</div>

  <div class="sidebar">
<ul>
<li><a href="${pageContext.request.contextPath}/log/STDM001.jsp">学生管理</a></li>
<li><label>成績管理</label></li> <%-- ここはリンクなしのテキスト --%>
<li><a href="${pageContext.request.contextPath}/TestListSubjectExecute.action">成績登録</a></li>
<li><a href="${pageContext.request.contextPath}//log/GRMR001.jsp">成績参照</a></li>
<li><a href="${pageContext.request.contextPath}/log/SBJM001.jsp">科目管理</a></li>
</ul>
</div>

  <div class="content">
<h2>メニュー</h2> <div class="menu-box">
<div class="box box-red">
<a href="STDM001.jsp"> 学生管理</a>
</div>

      <div class="box box-green">
<div> 成績管理</div> <%-- この"成績管理"は元々リンクではないテキスト --%>
<a href="${pageContext.request.contextPath}/TestListSubjectExecute.action"> 成績登録</a>
<a href="${pageContext.request.contextPath}/log/GRMR001.jsp"> 成績参照</a>
</div>

      <div class="box box-blue">
<a href="${pageContext.request.contextPath}/log/SBJM001.jsp"> 科目管理</a>
</div>
</div>
</div>

  <%-- <footer>
<small>Copyright &copy; 2025.</small>
</footer> --%>

</body>
</html>