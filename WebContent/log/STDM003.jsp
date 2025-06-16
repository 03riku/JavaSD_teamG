<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生情報登録完了</title>
</head>
<body>

<!-- ヘッダー -->
<table width="100%" cellpadding="10" cellspacing="0" border="0" bgcolor="#e0e0ff">
  <tr>
    <td><b>得点管理システム</b></td>
    <td align="right">
      大原 太郎様　
      <a href="LOGO001.jsp">ログアウト</a>
    </td>
  </tr>
</table>

<!-- レイアウトテーブル -->
<table width="100%" border="0">
  <tr valign="top">
    <!-- 左メニュー -->
    <td width="180" style="padding: 10px;">
      <a href="#">メニュー</a><br><br>
      <a href="StudentListServlet">学生管理</a><br>
      <a href="#">成績管理</a><br>
      <a href="GradeSearchRegisterServlet">成績登録</a><br>
      <a href="GradeSearchServlet">成績検索</a><br>
      <a href="SubjectListServlet">科目管理</a><br>
    </td>

    <!-- メイン -->
    <td style="padding: 20px;">
      <h3>学生情報登録</h3> <!-- ① -->

      <!-- 登録完了メッセージ -->
      <table width="60%" cellpadding="10" cellspacing="0" border="1" bgcolor="#ccffcc">
        <tr>
          <td align="center">登録が完了しました</td> <!-- ② -->
        </tr>
      </table>
      <br>

      <!-- 戻るリンク -->
      <a href="StudentRegister.jsp">戻る</a>　<!-- ③ -->
      <a href="StudentListServlet">学生一覧</a> <!-- ④ -->
    </td>
  </tr>
</table>

<!-- フッター -->
<hr>
<div align="center">
  &copy; 2023 TIC<br>
  大原学園
</div>

</body>
</html>
