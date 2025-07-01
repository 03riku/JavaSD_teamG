<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Teacher BeanとSchool Beanがセッションスコープに保存されていることを前提とします --%>
<%-- 例: session.setAttribute("teacher", teacherObject); --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目情報削除</title>
    <style>
    	/* 共通全体レイアウト */
body {
    font-family: "メイリオ", Meiryo, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f9f9f9;
}

/* ヘッダー */
table {
    background-color: #d9e6f2;
    width: 100%;
    border-bottom: 2px solid #ccc;
}

td {
    padding: 10px;
    vertical-align: middle;
    font-weight: bold;
}

/* ログアウトリンク */
td a {
    text-decoration: none;
    color: #337ab7;
}

td a:hover {
    text-decoration: underline;
}

/* サイドバー */
div[style*="float:left"] {
    background-color: #f4f4f4;
    height: 100vh;
}

div[style*="float:left"] ul {
    list-style: none;
    padding-left: 0;
    margin: 0;
}

div[style*="float:left"] li {
    margin-bottom: 10px;
}

div[style*="float:left"] a, div[style*="float:left"] label {
    color: #333;
    text-decoration: none;
    font-weight: bold;
}

div[style*="float:left"] a:hover {
    color: #007BFF;
}

/* メインタイトル */
h2 {
    margin-left: 18%;
    padding-top: 20px;
    color: #333;
}

/* メッセージ表示 */
.error-message {
    color: red;
    font-weight: bold;
    margin-left: 18%;
}

.success-message {
    color: green;
    font-weight: bold;
    margin-left: 18%;
}

/* メインフォーム */
form {
    margin-left: 18%;
    background-color: #fff;
    padding: 20px;
    border-radius: 6px;
    box-shadow: 0 0 10px rgba(0,0,0,0.1);
    width: 50%;
}

/* ラベルと値 */
form label {
    font-weight: bold;
    display: inline-block;
    width: 100px;
}

form span {
    font-size: 1rem;
    margin-left: 10px;
}

/* 削除ボタン */
form button[type="submit"] {
    background-color: #d9534f;
    color: white;
    border: none;
    padding: 10px 20px;
    font-size: 1rem;
    cursor: pointer;
    border-radius: 4px;
}

form button[type="submit"]:hover {
    background-color: #c9302c;
}

/* 戻るリンク */
a[href="SRJM001.jsp"] {
    display: inline-block;
    margin-left: 18%;
    margin-top: 20px;
    color: #337ab7;
    text-decoration: none;
}

a[href="SRJM001.jsp"]:hover {
    text-decoration: underline;
}

    </style>
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
            <li><a href="SBJM001.jsp">科目管理</a></li>
        </ul>
    </div>

    <%-- メインコンテンツのタイトル --%>
    <h2>科目情報削除</h2>

    <%-- メッセージ表示エリア (エラーや成功メッセージをサーブレットから受け取って表示) --%>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>
    <c:if test="${not empty successMessage}">
        <div class="success-message">${successMessage}</div>
    </c:if>

    <p>以下の科目を削除します。よろしいですか？</p>

    <form action="SubjectDeleteExecuteServlet" method="post"> <%-- 削除処理を行うサーブレット名を指定 --%>
        <div>
            <label>科目コード:</label>
            <%-- 科目コードは表示専用 --%>
            <span>${subject.cd}</span>
            <input type="hidden" name="cd" value="${subject.cd}"> <%-- 削除対象の科目を特定するためにhiddenで送信 --%>
        </div>
        <br><br>

        <div>
            <label>科目名:</label>
            <%-- 科目名も表示専用 --%>
            <span>${subject.name}</span>
            <input type="hidden" name="name" value="${subject.name}"> <%-- 必要であれば科目名もhiddenで送信 --%>
        </div>
        <br><br>

        <button type="submit" name="execute">削除</button>
    </form>

    <a href="SRJM001.jsp">戻る</a>
</body>
</html>