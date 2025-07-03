<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メインメニュー</title>
<style>

    body {

      font-family: sans-serif;

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

    }k]5x,

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
<div class="header">
<div><strong>得点管理システム</strong></div>
<div><a href="LOGO001.jsp">ログアウト</a></div>
</div>

  <div class="sidebar">
<ul>
<li><a href="${pageContext.request.contextPath}/log/STDM001.jsp">学生管理</a></li>
<li>成績管理</li> <%-- ここはリンクなしのテキスト --%>
<li><a href="${pageContext.request.contextPath}/TestListSubjectExecute.action">成績登録</a></li>
<li><a href="${pageContext.request.contextPath}/TestListStudentExecute.action">成績参照</a></li>
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
<a href="${pageContext.request.contextPath}/TestListStudentExecute.action"> 成績参照</a>
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
