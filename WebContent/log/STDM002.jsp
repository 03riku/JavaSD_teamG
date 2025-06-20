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
      <li><a href="GRMR001.jsp">成績参照</a></li>
      <li><a href="SBJM001.jsp">科目管理</a></li>
  </ul>
  </div>

  <h2>学生情報登録</h2>

    <form action="StudentRegisterServlet" method="post">
        <!-- 入学年度 -->
        <label>入学年度</label> <!-- ② -->
        <select name="ent_year"> <!-- ③ -->
            <%
                int currentYear = java.time.Year.now().getValue();
                for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            %>
                <option value="<%= i %>"><%= i %></option>
            <% } %>
        </select>
        <br><br>

        <!-- 学生番号 -->
        <label>学生番号</label> <!-- ④ -->
        <input type="text" name="no" value="${no}" placeholder="学生番号を入力してください" required /> <!-- ⑤ -->
        <%-- 学生番号未入力エラーメッセージ --%>
        <c:if test="${not empty errorStudentNoEmpty}">
            <div style="color: red; font-size: 0.9em; margin-top: 5px;">${errorStudentNoEmpty}</div>
        </c:if>
        <%-- 学生番号重複エラーメッセージ --%>
        <c:if test="${not empty errorStudentNoDuplicate}">
            <div style="color: red; font-size: 0.9em; margin-top: 5px;">${errorStudentNoDuplicate}</div>
        </c:if>
        <br><br>

        <!-- 氏名 -->
        <label>氏名</label> <!-- ⑥ -->
        <input type="text" name="name" value="${name}" placeholder="氏名を入力してください" required /> <!-- ⑦ -->
        <%-- 氏名未入力エラーメッセージ --%>
        <c:if test="${not empty errorStudentNameEmpty}">
           <div style="color: red; font-size: 0.9em; margin-top: 5px;">${errorStudentNameEmpty}</div>
        </c:if>
        <br><br>

        <!-- クラス -->
        <label>クラス</label> <!-- ⑧ -->
        <select name="class_num"> <!-- ⑨ -->
            <option value="101">101</option>
            <option value="102">102</option>
            <option value="103">103</option>
            <!-- 他のクラスも必要に応じて追加 -->
        </select>
        <br><br>

        <!-- 登録ボタン -->
        <button type="submit" name="end">登録して終了</button> <!-- ⑩ -->
    </form>

    <!-- 戻るリンク -->
    <a href="StudentListServlet">戻る</a> <!-- ⑪ -->
</body>
</html>
