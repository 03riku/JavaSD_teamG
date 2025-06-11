<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>メインメニュー</title>
</head>
<body>
  <!-- ヘッダー -->
  <table width="100%" cellpadding="5" cellspacing="0" border="0">
    <tr>
      <td align="left">得点管理システム</td>
      <td align="right"><a href="LOGO001.jsp">ログアウト</a></td>
    </tr>
  </table>

  <table align="center" cellpadding="10" cellspacing="10" border="0">
    <tr>
      <td>
        <a href="StudentListServlet">学生管理</a>
      </td>
      <td>
        <a href="GradeManageServlet">成績管理</a><br>
        <a href="GradeSearchServlet">成績参照検索</a>
      </td>
      <td>
        <a href="SubjectListServlet">科目管理</a>
      </td>
    </tr>
  </table>

  <!-- フッター -->
  <hr>
  <footer style="text-align:center; font-size:small;">
    &copy; 得点管理システム
  </footer>
</body>
</html>
