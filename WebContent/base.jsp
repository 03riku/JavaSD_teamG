<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<head>
<meta charset="UTF-8">
<title>メインメニュー</title>

</head>
<body>
<!-- ① ヘッダー -->
<div class="header">
<div><strong>得点管理システム</strong></div>
<div><a href="LOGO001.jsp">ログアウト</a></div>
</div>


  <!-- メインコンテンツ -->
<div class="content">
<h2>メニュー</h2> <!-- ① -->

    <div class="menu-box">

<div class="box box-red">
<a href="STDM001.jsp"> 学生管理</a>
</div>

<div class="box box-green">
<div> 成績管理</div>
<a href="GRMU001.jsp"> 成績登録</a>
<a href="GRMR001.jsp"> 成績参照</a>
</div>

<div class="box box-blue">
<a href="SBJM001.jsp"> 科目管理</a>
</div>
</div>
</div>

  <!-- フッター 作成する-->
<!-- フッター 作成する-->
<footer class="bg-light text-center text-muted py-3" style="font-size: 0.9em;">
  <div>© 2023 TIC</div>
  <div>大原学園</div>
</footer>

</body>
</html>