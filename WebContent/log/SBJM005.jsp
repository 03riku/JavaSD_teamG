<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目情報変更完了</title>
</head>
<body>

<table width="100%" cellpadding="10" cellspacing="0" border="0" bgcolor="#e0e0ff">
  <tr>
    <td><b>得点管理システム</b></td>
    <td align="right">
      大原 太郎様　
      <a href="${pageContext.request.contextPath}/log/LOGO001.jsp">ログアウト</a>
    </td>
  </tr>
</table>

<table width="100%" border="0">
  <tr valign="top">
    <td width="180" style="padding: 10px;">
      <a href="${pageContext.request.contextPath}/log/MMNU001.jsp">メニュー</a><br><br>
      <a href="${pageContext.request.contextPath}/log/STDM001.jsp">学生管理</a><br>
      <a>成績管理</a><br>
      <a href="${pageContext.request.contextPath}/log/GRMU001.jsp">成績登録</a><br>
      <a href="${pageContext.request.contextPath}/log/GRMR001.jsp">成績参照</a><br>
      <a href="${pageContext.request.contextPath}/log/SBJM001.jsp">科目管理</a><br>
    </td>

    <td style="padding: 20px;">
      <h3>科目情報変更</h3>

      <table width="60%" cellpadding="10" cellspacing="0" border="1" bgcolor="#ccffcc">
        <tr>
          <td align="center">登録が完了しました</td>
        </tr>
      </table>
      <br>

      <a href="${pageContext.request.contextPath}/log/STDM001.jsp">科目一覧</a>
    </td>
  </tr>
</table>

<hr>
<div align="center">

</div>

</body>
</html>