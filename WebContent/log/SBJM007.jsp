<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目削除完了</title>
    <%-- 必要なら CSS を読み込んでください --%>
    <%-- 例: <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> --%>
</head>
<body>
    <table width="100%" cellpadding="5" cellspacing="0" border="0">
        <tr>
            <td align="left">得点管理システム</td>
            <td align="right">
                <%-- Teacher・School 情報がセッションにあれば表示 --%>
                <c:if test="${not empty teacher}">
                    ${teacher.school.name} ${teacher.name}様&nbsp;
                </c:if>
                <a href="${pageContext.request.contextPath}/LOGO001.jsp">ログアウト</a>
            </td>
        </tr>
    </table>

    <div style="float:left; width:15%; height:100vh; border-right:2px solid #000; padding:10px;">
        <ul>
            <li><a href="${pageContext.request.contextPath}/MMNU001.jsp">メニュー</a></li>
            <li><a href="${pageContext.request.contextPath}/STDM001.jsp">学生管理</a></li>
            <li><label>成績管理</label></li>
            <li><a href="${pageContext.request.contextPath}/GRMU001.jsp">成績登録</a></li>
            <li><a href="${pageContext.request.contextPath}/GRMR001.jsp">成績検索</a></li>
            <li><a href="${pageContext.request.contextPath}/SRJM002.jsp">科目管理</a></li>
        </ul>
    </div>

    <div style="margin-left:17%; padding:20px;">
        <h2>科目削除完了</h2>

        <div class="success-message-box">
            削除が完了しました。
        </div>

        <p>
            <a href="${pageContext.request.contextPath}/log/SBJM001.jsp">科目一覧へ戻る</a>
        </p>
    </div>
</body>
</html>
