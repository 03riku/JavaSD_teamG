<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目登録完了</title>
</head>
<body>

<!-- ヘッダー -->
    <table width="100%" cellpadding="5" cellspacing="0" border="0" bgcolor="#e0e0ff">
        <tr>
            <td><b>得点管理システム</b></td>
            <td align="right">
                <%-- ユーザー情報とログアウトリンクの表示 --%>
                <c:if test="${not empty teacher}">
                    ${teacher.school.name} ${teacher.name}様&nbsp;
                </c:if>
                <a href="LOGO001.jsp">ログアウト</a>
            </td>
        </tr>
    </table>

    <table wigth="100%" border="0">
    	<tr valign="top">
        	<td wigth="180" style="padding: 10px;">
            	<a href="MMNU001.jsp">メニュー</a>
            	<a href="STDM001.jsp">学生管理</a>
            	<a>成績管理</a><br>
            	<a href="GRMU001.jsp">成績登録</a><br>
            	<a href="GRMR001.jsp">成績検索</a>
            	<a href="SBJM001.jsp">科目管理</a>
        	</td>

    		<%-- メインコンテンツのタイトル --%>
    		<td style="padding: 20px;">
    			<h2>科目情報登録</h2> <%-- ① 科目情報登録 --%>

    			<%-- 登録完了メッセージ --%>
    			<table width="60%" cellpadding="10" cellspacing="0" corder="1" bgcolor="#ccffcc">
    				<tr>
    					<td align="center">登録がかんりょうしました</td>
    				</tr>
    			</table>
    			<br>

    			<a href="SBJM002.jsp">戻る</a> <%-- ③ 戻る --%>
    			&nbsp;
    			<a href="SBJM001.jsp">科目一覧</a> <%-- ④ 科目一覧 (仮のリンク先) --%>
			</td>
		</tr>
	</table>
</body>
</html>