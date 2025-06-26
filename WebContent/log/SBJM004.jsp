<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Teacher BeanとSchool Beanがセッションスコープに保存されていることを前提とします --%>
<%-- 例: session.setAttribute("teacher", teacherObject); --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目情報変更</title>
</head>
<body>
    <table width="100%" cellpadding="5" cellspacing="0" border="0">
        <tr>
            <td align="left">得点管理システム</td>
            <td align="right">
                <c:if test="${not empty teacher}">
                    ${teacher.school.name} ${teacher.name}様&nbsp;
                </c:if>
                <a href="LOGO001.jsp">ログアウト</a>
            </td>
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

    <h2>科目情報変更</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>
    <c:if test="${not empty successMessage}">
        <div class="success-message">${successMessage}</div>
    </c:if>

    <form action="SubjectUpdateExecuteServlet" method="post">
        <div>
            <label>科目コード</label>
            <span class="readonly-field">${subject.cd}</span>
            <input type="hidden" name="cd" value="${subject.cd}">
        </div>
        <br><br>

        <div>
            <label>科目名</label>
            <input type="text" name="name" value="${subject.name}" placeholder="科目名を入力してください" required />
            <c:if test="${not empty errorSubjectNameEmpty}">
                <div class="error-message">${errorSubjectNameEmpty}</div>
            </c:if>
        </div>
        <br><br>

        <!-- 修正点：このボタンでSBJM005.jspへ遷移 -->
        <button type="submit" name="execute" formaction="SBJM005.jsp">変更</button>
    </form>

    <a href="SRJM002.jsp">戻る</a>
</body>
</html>
