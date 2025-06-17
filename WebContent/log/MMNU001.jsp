<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
  <!-- ① ヘッダー -->
  <div class="header">
    <div><strong>得点管理システム</strong></div>
    <div><a href="LOGO001.jsp">ログアウト</a></div>
  </div>

  <!-- サイドバー -->
  <div class="sidebar">
    <ul>
      <li><a href="StudentListServlet">学生管理</a></li>
      <li><label>成績管理</label></li>
      <li><a href="STDM002.jsp">成績登録</a></li>
      <li><a href="GradeSearchServlet">成績参照</a></li>
      <li><a href="SubjectListServlet">科目管理</a></li>
    </ul>
  </div>

  <!-- メインコンテンツ -->
  <div class="content">
    <h2>メニュー</h2> <!-- ① -->

    <div class="menu-box">
      <!-- ② -->
      <div class="box box-red">
        <a href="StudentListServlet">② 学生管理</a>
      </div>

      <!-- ③～⑤ -->
      <div class="box box-green">
        <div>③ 成績管理</div>
        <a href="STDM002.jsp">④ 成績登録</a>
        <a href="GradeSearchServlet">⑤ 成績参照</a>
      </div>

      <!-- ⑥ -->
      <div class="box box-blue">
        <a href="SubjectListServlet">⑥ 科目管理</a>
      </div>
    </div>
  </div>

  <!-- フッター -->
  <footer>
    &copy; 2023 TIC
  </footer>
</body>
</html>
