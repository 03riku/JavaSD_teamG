<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>得点管理システム - 科目情報変更</title>
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
        <h2 style="margin-top: 0;">科目情報変更</h2>

        <form action="SubjectEditServlet" method="post"> <%-- 変更処理を行うサーブレット --%>
            <div style="background-color: #f9f9f9; padding: 15px; border: 1px solid #ddd; border-radius: 5px; margin-bottom: 20px;">
                <div style="margin-bottom: 15px;">
                    <label style="display: inline-block; width: 80px; font-weight: bold;">科目コード:</label>
                    <span style="font-weight: bold;"><c:out value="${subject.cd}" /></span> <%-- 変更不可として表示 --%>
                    <%-- 変更時に科目コードを送信するためのhiddenフィールド --%>
                    <input type="hidden" name="cd" value="<c:out value="${subject.cd}" />">
                </div>

                <div style="margin-bottom: 15px;">
                    <label for="subjectName" style="display: inline-block; width: 80px; font-weight: bold;">科目名:</label>
                    <input type="text" id="subjectName" name="name" value="<c:out value="${subject.name}" />" placeholder="科目名を入力してください" required style="padding: 8px; border: 1px solid #ccc; border-radius: 4px; width: 300px;" />
                </div>
            </div>

            <%-- エラーメッセージ表示エリア --%>
            <c:if test="${not empty errorMessage}">
                <div style="color: red; font-size: 0.9em; margin-top: 10px; margin-bottom: 10px;">
                    ${errorMessage}
                </div>
            </c:if>
            <%-- 成功メッセージ表示エリア --%>
            <c:if test="${not empty successMessage}">
                <div style="color: green; font-size: 0.9em; margin-top: 10px; margin-bottom: 10px;">
                    ${successMessage}
                </div>
            </c:if>

            <div style="margin-top: 20px;">
                <button type="submit" name="edit" style="padding: 10px 20px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer;">変更</button>
            </div>
        </form>

        <p style="margin-top: 20px;">
            <a href="SRJM002.jsp" style="text-decoration: none; color: #007bff;">戻る</a> <%-- 科目管理一覧へ --%>
        </p>
    </div>
</body>
</html>