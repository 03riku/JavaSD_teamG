<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Teacher BeanとSchool Beanがセッションスコープに保存されていることを前提とします --%>
<%-- 例: session.setAttribute("teacher", teacherObject); --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目情報変更</title>
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

    <%-- メッセージ表示エリア --%>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>
    <c:if test="${not empty successMessage}">
        <div class="success-message">${successMessage}</div>
    </c:if>

    <form action="SubjectUpdateExecuteServlet" method="post"> <%-- 変更処理を行うサーブレット名を指定 --%>
        <div>
            <label>科目コード</label> <%-- ② 科目コード --%>
            <%-- 科目コードは表示専用で、変更不可にする (hiddenフィールドで値を送信) --%>
            <span class="readonly-field">${subject.cd}</span> <%-- ③ F02 など、DBから取得した値を表示 --%>
            <input type="hidden" name="cd" value="${subject.cd}"> <%-- 変更対象の科目を特定するためにhiddenで送信 --%>
        </div>
        <br><br>

        <div>
            <label>科目名</label> <%-- ④ 科目名 --%>
            <input type="text" name="name" value="${subject.name}" placeholder="科目名を入力してください" required /> <%-- ⑤ Javaプログラミング基礎 など、DBから取得した値を表示 --%>
            <%-- 科目名未入力エラーメッセージ (必要であれば追加) --%>
            <c:if test="${not empty errorSubjectNameEmpty}">
                <div class="error-message">${errorSubjectNameEmpty}</div>
            </c:if>
        </div>
        <br><br>

        <button type="submit" name="execute">変更</button> <%-- ⑥ 変更ボタン --%>
    </form>

    <a href="SRJM001.jsp">戻る</a> <%-- ⑦ 戻るリンク --%>
</body>
</html>