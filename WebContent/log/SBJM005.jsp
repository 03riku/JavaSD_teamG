<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Teacher BeanとSchool Beanがセッションスコープに保存されていることを前提とします --%>
<%-- 例: session.setAttribute("teacher", teacherObject); --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目変更完了</title>
    <%-- スタイルシートは外部ファイルで読み込むか、ここに記述しない --%>
    <%-- 例: <link rel="stylesheet" href="css/style.css"> --%>
</head>
<body>
    <table width="100%" cellpadding="5" cellspacing="0" border="0">
        <tr>
            <td align="left">得点管理システム</td>
            <td align="right">
                <%-- ユーザー情報とログアウトリンクの表示 --%>
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

    <%-- メインコンテンツのタイトル --%>
    <h2>科目情報変更</h2> <%-- ① 科目情報変更 --%>

    <%-- 変更完了メッセージ --%>
    <div class="success-message-box"> <%-- このクラスにCSSを適用してください --%>
        変更が完了しました
    </div> <%-- ② 変更が完了しました --%>

    <a href="${pageContext.request.contextPath}/log/SBJM001.jsp">科目一覧</a> <%-- ③ 科目一覧 (画像では「科目一覧」と表示されている) --%>

</body>
</html>