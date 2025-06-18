<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>学生情報登録</title>
</head>
<body>
    <h2>学生情報登録</h2> <!-- ① -->

    <table width="100%" cellpadding="5" cellspacing="0" border="0">
    <tr>
      <td align="left">得点管理システム</td>
      <td align="right"><a href="LOGO001.jsp">ログアウト</a></td>
    </tr>
  </table>

  <div style="float:left; width:15%; height:100vh; border-right:2px solid black; padding:10px;">
  <ul>
      <li><a href="MMNU001.jsp">メニュー</a></li>
      <li><a href="STDM001.jsp">学生管理</a></li>
      <li><label>成績管理</label></li>
      <li><a href="GRMU001.jsp">成績登録</a><br></li>
      <li><a href="GRMR001.jsp">成績検索</a></li>
      <li><a href="SRJM002.jsp">科目管理</a></li>
  </ul>
  </div>

  <h2>科目情報登録</h2>

    <form action="StudentRegisterServlet" method="post">
        <!-- 科目コード -->
        <label>科目コード</label> <!-- ④ -->
        <input type="text" name="no" value="${no}" placeholder="科目コードを入力してください" required /> <!-- ⑤ -->
        <%-- 科目コード未入力エラーメッセージ --%>
        <c:if test="${not empty errorSubjectNoEmpty}">
                <div class="error-message">${errorSubjectNoEmpty}</div>
        </c:if>
            <%-- 科目コード文字数エラーメッセージ --%>
        <c:if test="${not empty errorSubjectNoLength}">
                <div class="error-message">${errorSubjectNoLength}</div>
        </c:if>
        <br><br>

        <!-- 科目名 -->
        <label>科目名</label> <!-- ④ -->
        <input type="text" name="no" value="${no}" placeholder="科目名を入力してください" required /> <!-- ⑤ -->
        <%-- 科目名未入力エラーメッセージ (必要であれば追加) --%>
        <c:if test="${not empty errorSubjectNameEmpty}">
                <div class="error-message">${errorSubjectNameEmpty}</div>
        </c:if>
        <br><br>


        <!-- 登録ボタン -->
        <button type="submit" name="end">登録</button> <!-- ⑩ -->
    </form>

    <!-- 戻るリンク -->
    <a href="SRJM001.jsp">戻る</a> <!-- ⑪ -->
</body>
</html>
