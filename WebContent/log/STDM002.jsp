<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%-- JSTLのc:ifタグを使用するために必要です --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>学生情報登録</title>
</head>
<body>
    <h2>学生情報登録</h2> <table width="100%" cellpadding="5" cellspacing="0" border="0">
    <tr>
      <td align="left">得点管理システム</td>
      <td align="right"><a href="LOGO001.jsp">ログアウト</a></td>
    </tr>
  </table>

  <div style="float:left; width:15%; height:100vh; border-right:2px solid black; padding:10px;">
  <ul>
      <li><a href="MMNU001.jsp">メニュー</a></li>
      <li><a href="StudentListServlet">学生管理</a></li>
      <li><label>成績管理</label></li>
      <li><a href="GradeSearchRegisterServlet">成績登録</a><br></li>
      <li><a href="GradeSearchServlet">成績検索</a></li>
      <li><a href="SubjectListServlet">科目管理</a></li>
  </ul>
  </div>

  <h2>学生情報登録</h2>

    <form action="/JavaSD/STDM003" method="post">
        <label>入学年度</label> <select name="ent_year"> <%
                int currentYear = java.time.Year.now().getValue();
                for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            %>
                <option value="<%= i %>"><%= i %></option>
            <% } %>
        </select>
        <br><br>

        <label>学生番号</label> <input type="text" name="no" value="${no}" placeholder="学生番号を入力してください" required /> <%-- 学生番号未入力エラーメッセージ --%>
        <c:if test="${not empty errorStudentNoEmpty}">
            <div style="color: red; font-size: 0.9em; margin-top: 5px;">${errorStudentNoEmpty}</div>
        </c:if>
        <%-- 学生番号重複エラーメッセージ --%>
        <c:if test="${not empty errorStudentNoDuplicate}">
            <div style="color: red; font-size: 0.9em; margin-top: 5px;">${errorStudentNoDuplicate}</div>
        </c:if>
        <br><br>

        <label>氏名</label> <input type="text" name="name" value="${name}" placeholder="氏名を入力してください" required /> <%-- 氏名未入力エラーメッセージ --%>
        <c:if test="${not empty errorStudentNameEmpty}">
           <div style="color: red; font-size: 0.9em; margin-top: 5px;">${errorStudentNameEmpty}</div>
        </c:if>
        <br><br>

        <label>クラス</label> <select name="class_num"> <option value="101">101</option>
            <option value="102">102</option>
            <option value="103">103</option>
            </select>
        <br><br>

        <button type="submit" name="end">登録して終了</button> </form>

    <a href="StudentListServlet">戻る</a> </body>
</html>