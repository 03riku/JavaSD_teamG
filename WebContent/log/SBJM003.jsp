<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>得点管理システム - 科目登録完了</title>
</head>
<body style="font-family: Arial, sans-serif; margin: 0; padding: 0;">
    <table style="width: 100%; padding: 5px; border-bottom: 1px solid #ccc;">
        <tr>
            <td style="padding: 5px; text-align: left;">得点管理システム</td>
            <td style="padding: 5px; text-align: right;">
                <c:if test="${not empty userInfo}">
                    <span style="margin-right: 5px;">${userInfo.userName}</span> |
                </c:if>
                <a href="LOGO001.jsp" style="text-decoration: none; color: #007bff;">ログアウト</a>
            </td>
        </tr>
    </table>

    <div style="float: left; width: 15%; min-height: calc(100vh - 40px); border-right: 2px solid black; padding: 10px; box-sizing: border-box;">
        <ul style="list-style: none; padding: 0; margin: 0;">
            <li style="margin-bottom: 10px;"><a href="MMNU001.jsp" style="text-decoration: none; color: #007bff;">メニュー</a></li>
            <li style="margin-bottom: 10px;"><a href="STDM001.jsp" style="text-decoration: none; color: #007bff;">学生管理</a></li>
            <li style="margin-bottom: 10px;"><label style="color: #555;">成績管理</label></li>
            <li style="margin-bottom: 10px;"><a href="GRMU001.jsp" style="text-decoration: none; color: #007bff;">成績登録</a><br></li>
            <li style="margin-bottom: 10px;"><a href="GRMR001.jsp" style="text-decoration: none; color: #007bff;">成績検索</a></li>
            <li style="margin-bottom: 10px;"><a href="SRJM002.jsp" style="text-decoration: none; color: #007bff;">科目管理</a></li>
        </ul>
    </div>

    <div style="margin-left: 17%; padding: 20px;">
        <h2 style="margin-top: 0;">科目登録完了</h2>

        <div style="background-color: #e6ffe6; padding: 15px; border: 1px solid #a3e6a3; border-radius: 5px; margin-bottom: 20px; color: #006600;">
            <p style="font-weight: bold; margin: 0;">科目情報が正常に登録されました。</p>
            <c:if test="${not empty registeredSubject}">
                <p style="margin: 5px 0 0 0;">登録された科目: <c:out value="${registeredSubject.name}" /> (<c:out value="${registeredSubject.cd}" />)</p>
            </c:if>
        </div>

        <p style="margin-top: 20px;">
            <a href="SRJM002.jsp" style="text-decoration: none; color: #007bff;">科目管理一覧へ戻る</a>
        </p>
        <p>
            <a href="MMNU001.jsp" style="text-decoration: none; color: #007bff;">メニューへ戻る</a>
        </p>
    </div>
</body>
</html>