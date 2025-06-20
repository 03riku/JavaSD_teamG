<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>エラーページ</title>
  <style>
    body {
      font-family: sans-serif;
      background-color: #f8f9fa;
    }
    .header {
      background-color: #e0efff;
      padding: 10px;
      display: flex;
      justify-content: space-between;
    }
    .container {
      padding: 50px;
      text-align: center;
    }
    .error-message {
      color: red;
      font-weight: bold;
      margin-top: 20px;
    }
    footer {
      text-align: center;
      font-size: small;
      margin-top: 60px;
      color: #777;
    }
  </style>
</head>
<body>

  <!-- ヘッダー -->
  <div class="header">
    <div><strong>得点管理システム</strong></div>
  </div>

  <!-- メインエリア -->
  <div class="container">
    <h2>エラーが発生しました</h2>  <!-- メッセージ部分 -->
    <p class="error-message">システムエラーが発生しました。<br>管理者に連絡してください。</p>
  </div>

  <!-- フッター -->
  <footer>
  </footer>

</body>
</html>
