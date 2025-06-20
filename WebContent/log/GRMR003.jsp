<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>成績一覧（学生）</title>
    <style>
        /* CSSスタイルは以前のバージョンと同じなので省略します。
           変更がある場合はここに追加してください。
           特に、以前追加した.search-section, .search-section-title, .form-group label
           .input-error, .field-tip, .caution-message, .footer などのスタイルはそのまま有効です。
        */

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        .header {
            background-color: #e6f0fc;
            padding: 10px 20px;
            border-bottom: 1px solid #c0d9ef;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header h1 {
            font-size: 24px;
            margin: 0;
        }

        .user-info {
            font-size: 14px;
        }

        .container {
            display: flex;
        }

        .sidebar {
            width: 200px;
            background-color: #fff;
            padding: 20px;
            border-right: 1px solid #ccc;
        }

        .sidebar a {
            display: block;
            margin-bottom: 10px;
            color: #007bff;
            text-decoration: none;
        }
        .sidebar a:hover {
            text-decoration: underline;
        }
        .sidebar div { /* 現在のページを示す要素のスタイル */
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }


        .main-content {
            flex-grow: 1;
            padding: 20px;
            background-color: #fff;
        }

        .section-title {
            font-size: 20px;
            margin-bottom: 20px;
            font-weight: bold;
        }

        /* 検索フォームの新しいセクションタイトルスタイル */
        .search-section {
            display: flex; /* 項目の配置にflexboxを使用 */
            align-items: center; /* 垂直方向中央揃え */
            margin-bottom: 15px; /* セクション間の余白 */
            flex-wrap: wrap; /* 小さな画面で折り返す */
        }

        .search-section-title {
            font-weight: bold;
            width: 100px; /* タイトル部分の幅を固定 */
            flex-shrink: 0; /* 幅を固定し、縮まないようにする */
            text-align: left; /* テキスト左揃え */
            margin-right: 20px; /* タイトルと入力フィールドの間の余白 */
        }

        .form-box {
            padding: 20px;
            background-color: #fafafa;
            border: 1px solid #ddd;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 10px;
            display: flex; /* ラベルと入力フィールドを横並びにするため */
            align-items: center; /* 垂直方向中央揃え */
            margin-right: 20px; /* 各form-group間の余白 */
        }
        .form-group:last-child {
             margin-right: 0;
        }


        /* ラベルのスタイル調整 */
        .form-group label {
            display: inline-block;
            width: 80px; /* ラベルの幅を固定 */
            font-weight: bold;
            text-align: right; /* ラベルテキストを右揃え */
            margin-right: 10px; /* ラベルと入力フィールドの間の余白 */
        }

        select, input[type="text"] {
            padding: 5px;
            width: 150px; /* 入力フィールドの幅を固定 */
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        /* 学生番号入力フィールドのエラー表示用スタイル */
        .input-error {
            border-color: red !important;
            background-color: #ffeaea;
        }

        .search-button {
            padding: 6px 12px;
            margin-left: 10px;
            background-color: #4f4f4f;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .search-button:hover {
            background-color: #6a6a6a;
        }


        .student-name {
            margin-top: 20px;
            font-weight: bold;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: center;
        }

        th {
            background-color: #f2f2f2;
        }

        /* エラーメッセージと情報メッセージ */
        .error-message {
            color: red;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .info-message {
            color: #00796b;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        /* 学生番号未入力時の追加メッセージ */
        .field-tip {
            color: #888;
            font-size: 0.9em;
            margin-top: 5px;
            margin-left: 120px; /* ラベルと入力フィールドの幅に合わせる */
        }
        .caution-message {
            color: orange; /* 注意を促す色 */
            font-size: 0.9em;
            margin-top: 5px;
            display: flex;
            align-items: center;
            margin-left: 120px; /* 学生番号入力欄の開始位置に合わせる */
        }
        .caution-message::before {
            content: '⚠️'; /* 警告アイコン */
            margin-right: 5px;
        }

        /* 画面下部の著作権表示 */
        .footer {
            text-align: center;
            font-size: 0.8em;
            color: #777;
            margin-top: 30px;
            padding-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="header">
    <h1>得点管理システム</h1>
    <div class="user-info">
        大原 太郎様 <a href="#">ログアウト</a>
    </div>
</div>

<div class="container">
    <div class="sidebar">
        <a href="#">メニュー</a>
        <a href="#">学生管理</a>
        <div>成績管理</div>
        <a href="#">成績登録</a>
        <a href="#">成績参照</a>
        <a href="#">科目管理</a>
    </div>

    <div class="main-content">
        <div class="section-title">成績一覧（学生）</div>

        <div class="form-box">
            <form action="StudentScoreListServlet" method="post">
                <input type="hidden" name="searchType" value="subjectInfo">
                <div class="search-section">
                    <div class="search-section-title">科目情報</div>
                    <div style="display: flex; align-items: center;">
                        <div class="form-group">
                            <label>入学年度</label>
                            <select name="year">
                                <option value="">------</option>
                                <option value="2025" <c:if test="${param.year == '2025'}">selected</c:if>>2025</option>
                                <option value="2024" <c:if test="${param.year == '2024'}">selected</c:if>>2024</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>クラス</label>
                            <select name="class">
                                <option value="">------</option>
                                <option value="A" <c:if test="${param['class'] == 'A'}">selected</c:if>>A</option>
                                <option value="B" <c:if test="${param['class'] == 'B'}">selected</c:if>>B</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>科目</label>
                            <select name="subject">
                                <option value="">------</option>
                                <%-- ここからH2から取得した科目名を動的に表示 --%>
                                <c:forEach var="sub" items="${subjects}">
                                    <option value="${sub.subjectName}" <c:if test="${param.subject == sub.subjectName}">selected</c:if>>
                                        ${sub.subjectName}
                                    </option>
                                </c:forEach>
                                <%-- 以前の静的な科目名は削除 --%>
                            </select>
                            <button type="submit" class="search-button">検索</button>
                        </div>
                    </div>
                </div>
            </form>

            <form action="StudentScoreListServlet" method="post">
                <input type="hidden" name="searchType" value="studentInfo">
                <div class="search-section">
                    <div class="search-section-title">学生情報</div>
                    <div style="display: flex; flex-direction: column;">
                        <div class="form-group">
                            <label>学生番号</label>
                            <input type="text" name="studentId"
                                   value="${param.studentId != null ? param.studentId : ''}"
                                   placeholder="学生番号を入力してください"
                                   class="${not empty errorMessage && param.searchType == 'studentInfo' ? 'input-error' : ''}">
                            <button type="submit" class="search-button">検索</button>
                        </div>
                        <c:if test="${not empty errorMessage && param.searchType == 'studentInfo'}">
                            <div class="field-tip">このフィールドを入力してください</div>
                            <div class="caution-message">科目情報を選択または学生情報を入力してください</div>
                        </c:if>
                    </div>
                </div>
            </form>

            <c:if test="${not empty studentSearchError}">
                <div class="error-message">${studentSearchError}</div>
            </c:if>

        </div>

        <c:if test="${not empty studentName}">
            <div class="student-name">氏名：${studentName}（${studentId}）</div>
        </c:if>

        <c:if test="${not empty scoreList}">
            <table>
                <thead>
                    <tr>
                        <th>科目名</th>
                        <th>科目コード</th>
                        <th>回数</th>
                        <th>点数</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="score" items="${scoreList}">
                        <tr>
                            <td>${score.subjectName}</td>
                            <td>${score.subjectCode}</td>
                            <td>${score.testNo}</td>
                            <td>${score.point}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${not empty noResultMessage}">
            <div class="info-message">
                <c:if test="${not empty studentName}">
                    氏名：${studentName}（${studentId}）<br>
                </c:if>
                ${noResultMessage}
            </div>
        </c:if>
    </div>
</div>
<div class="footer">
    &copy; 2023 TIC<br>
    大原学園
</div>
</body>
</html>