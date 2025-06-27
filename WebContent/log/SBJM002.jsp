<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Teacher BeanとSchool Beanがセッションスコープに保存されていることを前提とします --%>
<%-- 例: session.setAttribute("teacher", teacherObject); --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目情報登録</title>
    <style>
        /* エラーメッセージ用のスタイル（ご提示のコードに合わせるため追加） */
        .error-message {
            color: red;
            font-size: 0.9em;
            margin-top: 5px;
        }
        /* 必要に応じて、その他のスタイル（success-messageなど）も追加してください */
    </style>
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
            <li><label>成績管理</label></li> <%-- これはリンクではなくラベルです --%>
            <li><a href="GRMU001.jsp">成績登録</a><br></li>
            <li><a href="GRMR001.jsp">成績検索</a></li>
            <li><a href="SBJM001.jsp">科目管理</a></li> <%-- 科目管理のリンク先 --%>
        </ul>
    </div>

    <%-- メインコンテンツのタイトル --%>
    <h2>科目情報登録</h2>

    <%-- 登録成功メッセージの表示（必要であれば追加） --%>
    <c:if test="${not empty successMessage}">
        <div class="success-message">${successMessage}</div>
    </c:if>

    <form action="SBJM003.jsp" method="post"> <%-- サーブレット名を適宜変更してください (StudentRegisterServlet -> SubjectRegisterServlet) --%>
        <label>科目コード</label> <input type="text" name="cd" value="${subject.cd}" placeholder="科目コードを入力してください" required /> <%-- 科目コード関連のエラーメッセージ --%>
        <c:if test="${not empty errorSubjectCdEmpty}">
            <div class="error-message">${errorSubjectCdEmpty}</div>
        </c:if>
        <c:if test="${not empty errorSubjectCdLength}">
            <div class="error-message">${errorSubjectCdLength}</div>
        </c:if>
        <br><br>

        <label>科目名</label> <input type="text" name="name" value="${subject.name}" placeholder="科目名を入力してください" required /> <%-- 科目名関連のエラーメッセージ --%>
        <c:if test="${not empty errorSubjectNameEmpty}">
            <div class="error-message">${errorSubjectNameEmpty}</div>
        </c:if>
        <br><br>

        <button type="submit" name="execute">登録</button> <%-- ⑩ name="end" から name="execute" に変更することが多いですが、ここはご提示に合わせて変更しました --%>
    </form>

    <a href="SBJM001.jsp">戻る</a> <%-- ⑪ --%>

    <br><br>

</body>
</html>