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
        /* エラーメッセージ用のスタイル */
        .error-message {
            color: red;
            font-size: 0.9em;
            margin-top: 5px;
        }
        /* 成功メッセージ用のスタイル */
        .success-message {
            color: green;
            font-size: 0.9em;
            margin-top: 5px;
        }

        /* 全体レイアウトのためのFlexbox設定 */
        body {
            display: flex;
            flex-direction: column; /* ヘッダーとメインコンテンツを縦に並べる */
            margin: 0;
            font-family: sans-serif;
            min-height: 100vh; /* ページ全体の高さを確保 */
        }
        header {
            width: 100%;
            padding: 10px 20px;
            box-sizing: border-box;
            display: flex;
            justify-content: space-between; /* 要素を左右に配置 */
            align-items: center; /* 垂直方向中央揃え */
            border-bottom: 1px solid #ccc; /* ヘッダーの下に線を追加 */
            background-color: #f8f8f8; /* ヘッダーの背景色 */
        }
        .container {
            display: flex; /* ナビゲーションとメインコンテンツを横に並べる */
            width: 100%;
            flex-grow: 1; /* 残りの高さを占有 */
        }
        nav {
            width: 180px; /* ナビゲーションバーの固定幅 */
            padding: 20px 10px;
            border-right: 2px solid black;
            box-sizing: border-box;
            flex-shrink: 0; /* 縮小しない */
        }
        nav ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        nav li {
            margin-bottom: 10px;
        }
        nav a {
            text-decoration: none;
            color: #007bff; /* リンクの色 */
            display: block; /* リンク全体をクリック可能に */
            padding: 5px 0;
        }
        nav a:hover {
            text-decoration: underline;
        }
        main {
            flex-grow: 1; /* 残りのスペースを占有 */
            padding: 20px;
            box-sizing: border-box;
        }
        /* フォームとテーブルの共通スタイル */
        h2 {
            color: #333;
            margin-bottom: 20px;
            border-bottom: 2px solid #eee;
            padding-bottom: 5px;
        }
        form {
            margin-bottom: 30px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #fff;
        }
        form label {
            display: inline-block;
            width: 100px;
            margin-bottom: 10px;
            font-weight: bold;
        }
        form input[type="text"] {
            width: 250px;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        form button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        form button:hover {
            background-color: #0056b3;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
            background-color: #fff;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
            color: #555;
        }
        tbody tr:nth-child(even) {
            background-color: #f9f9f9; /* 偶数行の背景色 */
        }
        tbody tr:hover {
            background-color: #e9e9e9; /* ホバー時の背景色 */
        }
    </style>
</head>
<body>
    <header>
        <div>得点管理システム</div>
        <div>
            <%-- ユーザー情報とログアウトリンクの表示 --%>
            <c:if test="${not empty teacher}">
                ${teacher.school.name} ${teacher.name}様&nbsp;
            </c:if>
            <a href="LOGO001.jsp">ログアウト</a>
        </div>
    </header>

    <div class="container">
        <nav>
            <ul>
                <li><a href="MMNU001.jsp">メニュー</a></li>
                <li><a href="STDM001.jsp">学生管理</a></li>
                <li><label>成績管理</label></li> <%-- これはリンクではなくラベルです --%>
                <li><a href="GRMU001.jsp">成績登録</a></li>
                <li><a href="GRMR001.jsp">成績検索</a></li>
                <li><a href="SBJM002.jsp">科目管理</a></li>
            </ul>
        </nav>

        <main>
            <h2>科目情報登録</h2>

            <%-- 登録成功メッセージの表示 --%>
            <c:if test="${not empty successMessage}">
                <div class="success-message">${successMessage}</div>
            </c:if>

            <form action="SubjectRegisterServlet" method="post">
			    <%-- 他のフォーム要素はそのまま --%>
			    <label for="cd">科目コード</label>
			    <input type="text" id="cd" name="cd" value="${subject.cd}" placeholder="科目コードを入力してください" required maxlength="3" />
			    <%-- 既存のバリデーションエラーメッセージに加え、重複エラーメッセージを追加 --%>
			    <c:if test="${not empty errorSubjectCdEmpty}">
			        <div class="error-message">${errorSubjectCdEmpty}</div>
			    </c:if>
			    <c:if test="${not empty errorSubjectCdLength}">
			        <div class="error-message">${errorSubjectCdLength}</div>
			    </c:if>
			    <c:if test="${not empty errorSubjectCdExists}">
			        <div class="error-message">${errorSubjectCdExists}</div>
			    </c:if>
			    <br>

			    <label for="name">科目名</label>
			    <input type="text" id="name" name="name" value="${subject.name}" placeholder="科目名を入力してください" required />
			    <c:if test="${not empty errorSubjectNameEmpty}">
			        <div class="error-message">${errorSubjectNameEmpty}</div>
			    </c:if>
			    <br><br>

			    <button type="submit" name="execute">登録</button>
			</form>

            <a href="SBJM001.jsp">戻る</a>

            <br><br>
            <h2>科目情報一覧</h2>
            <c:choose>
                <%-- 科目リストが空の場合、メッセージを表示 --%>
                <c:when test="${empty subjects}">
                    <p>登録されている科目情報はありません。</p>
                </c:when>
                <%-- 科目リストにデータがある場合、テーブルで表示 --%>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>科目コード</th>
                                <th>科目名</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="subject" items="${subjects}">
                                <tr>
                                    <td><c:out value="${subject.cd}"/></td>
                                    <td><c:out value="${subject.name}"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </main>
    </div>
</body>
</html>