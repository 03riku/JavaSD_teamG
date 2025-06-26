<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>学生情報登録</title>
    <style>
        /* 全体のフォントと基本的なマージン */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        /* ヘッダーのスタイル */
        .header {
            width: 100%;
            padding: 10px 20px;
            border-bottom: 1px solid #ccc; /* ヘッダー下の線 */
            box-sizing: border-box; /* パディングを幅に含める */
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #f8f8f8; /* ヘッダーの背景色 */
        }

        .header h1 {
            margin: 0;
            font-size: 1.5em;
            color: #333;
        }

        .header a {
            text-decoration: none;
            color: #007bff;
            font-size: 0.9em;
        }

        .header a:hover {
            text-decoration: underline;
        }

        /* メインコンテンツのコンテナ */
        .container {
            display: flex;
            height: calc(100vh - 50px); /* ヘッダーの高さ分を引く */
        }

        /* サイドメニューのスタイル */
        .sidebar {
            width: 180px; /* サイドバーの幅を調整 */
            border-right: 1px solid #ccc; /* サイドバーの右側の線 */
            padding: 20px;
            box-sizing: border-box;
            background-color: #f0f0f0; /* サイドバーの背景色 */
        }

        .sidebar ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .sidebar li {
            margin-bottom: 10px;
        }

        .sidebar a, .sidebar label {
            text-decoration: none;
            color: #333;
            display: block;
            padding: 5px 0;
            font-size: 0.95em;
        }

        .sidebar a:hover {
            color: #007bff;
            text-decoration: underline;
        }

        .sidebar label {
            font-weight: bold;
            color: #007bff; /* 現在のページを強調 */
        }

        /* メインコンテンツエリア */
        .main-content {
            flex-grow: 1; /* 残りのスペースを埋める */
            padding: 20px 30px; /* 左右のパディングを増やす */
            background-color: #fff;
        }

        .main-content h2 {
            font-size: 1.8em;
            margin-top: 0;
            margin-bottom: 20px;
            color: #333;
        }

        /* フォームのセクション */
        .form-section {
            background-color: #f5f5f5; /* セクションの背景色 */
            border: 1px solid #ddd;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 5px; /* 角を丸く */
        }

        .form-section h3 {
            font-size: 1.2em;
            margin-top: 0;
            margin-bottom: 15px;
            color: #555;
            border-bottom: 1px solid #eee; /* タイトル下の線 */
            padding-bottom: 10px;
        }

        .form-row {
            display: flex;
            align-items: center;
            margin-bottom: 15px;
        }

        .form-row label {
            width: 100px; /* ラベルの幅を固定 */
            font-weight: bold;
            color: #444;
            flex-shrink: 0; /* ラベルが縮まないように */
        }

        .form-row input[type="text"],
        .form-row select {
            flex-grow: 1; /* 入力フィールドが残りのスペースを埋める */
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 1em;
            max-width: 300px; /* 入力フィールドの最大幅 */
        }

        .form-row input[type="text"]::placeholder {
            color: #999;
        }

        /* エラーメッセージのスタイル */
        .error-message {
            color: red;
            font-size: 0.85em;
            margin-left: 10px; /* 入力フィールドからの距離 */
            white-space: nowrap; /* 折り返しを防ぐ */
        }

        /* ボタンのスタイル */
        .button-group {
            margin-top: 30px;
            display: flex;
            gap: 15px; /* ボタン間のスペース */
        }

        .button-group button {
            padding: 10px 25px;
            font-size: 1em;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.2s ease;
        }

        .button-group button[type="submit"] {
            background-color: #007bff;
            color: white;
        }

        .button-group button[type="submit"]:hover {
            background-color: #0056b3;
        }

        .button-group .back-link {
            display: inline-block; /* aタグをボタンのように見せる */
            padding: 10px 25px;
            font-size: 1em;
            border: 1px solid #ccc;
            border-radius: 5px;
            color: #333;
            text-decoration: none;
            background-color: #f0f0f0;
        }

        .button-group .back-link:hover {
            background-color: #e0e0e0;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>得点管理システム</h1>
        <a href="LOGO001.jsp">ログアウト</a>
    </div>

    <div class="container">
        <div class="sidebar">
            <ul>
                <li><a href="MMNU001.jsp">メニュー</a></li>
                <li><a href="STDM001.jsp">学生管理</a></li>
                <li><label>成績管理</label></li>
                <li><a href="GRMU001.jsp">成績登録</a></li>
                <li><a href="GRMR001.jsp">成績参照</a></li>
                <li><a href="SBJM001.jsp">科目管理</a></li>
            </ul>
        </div>

        <div class="main-content">
            <h2>学生情報登録</h2>

            <form action="STDM003.jsp" method="post">
                <div class="form-section">
                    <h3>学生情報登録</h3>
                    <div class="form-row">
                        <label for="ent_year">入学年度</label>
                        <select id="ent_year" name="ent_year">
                            <%
                                int currentYear = java.time.Year.now().getValue();
                                for (int i = currentYear - 10; i <= currentYear + 10; i++) {
                            %>
                                <option value="<%= i %>" <%= (request.getAttribute("ent_year") != null && Integer.parseInt(request.getAttribute("ent_year").toString()) == i) ? "selected" : "" %>><%= i %></option>
                            <% } %>
                        </select>
                    </div>

                    <div class="form-row">
                        <label for="no">学生番号</label>
                        <input type="text" id="no" name="no" value="${no}" placeholder="学生番号を入力してください" required />
                        <c:if test="${not empty errorStudentNoEmpty}">
                            <div class="error-message">${errorStudentNoEmpty}</div>
                        </c:if>
                        <c:if test="${not empty errorStudentNoDuplicate}">
                            <div class="error-message">${errorStudentNoDuplicate}</div>
                        </c:if>
                    </div>

                    <div class="form-row">
                        <label for="name">氏名</label>
                        <input type="text" id="name" name="name" value="${name}" placeholder="氏名を入力してください" required />
                        <c:if test="${not empty errorStudentNameEmpty}">
                            <div class="error-message">${errorStudentNameEmpty}</div>
                        </c:if>
                    </div>

                    <div class="form-row">
                        <label for="class_num">クラス</label>
                        <select id="class_num" name="class_num">
                            <option value="101" <%= (request.getAttribute("class_num") != null && request.getAttribute("class_num").equals("101")) ? "selected" : "" %>>101</option>
                            <option value="102" <%= (request.getAttribute("class_num") != null && request.getAttribute("class_num").equals("102")) ? "selected" : "" %>>102</option>
                            <option value="103" <%= (request.getAttribute("class_num") != null && request.getAttribute("class_num").equals("103")) ? "selected" : "" %>>103</option>
                        </select>
                    </div>
                </div>

                <div class="button-group">
                    <button type="submit" name="end">登録して終了</button>
                    <a href="STDM001.jsp" class="back-link">戻る</a> <%-- 学生管理画面に戻るのが自然 --%>
                </div>
            </form>
        </div>
    </div>
</body>
</html>