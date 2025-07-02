<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目情報登録</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        /* Header Styles */
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

        .header h1:first-child {
            font-weight: bold;
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

        /* Main Layout Container for Navigation and Content */
        .main-container {
            display: flex;
            height: calc(100vh - 50px); /* ヘッダーの高さ分を引く */

        }

        /* Left Navigation Bar */
        .nav-sidebar {
            width: 180px; /* Fixed width for navigation */
            background-color: #f0f0f0; /* Dark blue/gray background */
            padding: 20px 0; /* Vertical padding */
            border-right: 1px solid #ccc; /* Darker border for separation */
            box-sizing: border-box;
        }

        .nav-sidebar ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .nav-sidebar li {
            margin-bottom: 10px; /* Space between list items */
        }

        .nav-sidebar a,
        .nav-sidebar label {
            text-decoration: none;
            color: #333;
            display: block;
            padding: 5px 0;
            font-size: 0.95em;
        }

        .nav-sidebar a:hover {
            background-color: #2c3e50; /* Slightly darker on hover */
        }

        .nav-sidebar label {
            font-weight: bold; /* Make labels bold */
            color: #007bff; /* 現在のページを強調 */

        }

        /* Main Content Area */
        .main-content {
            flex-grow: 1; /* 残りのスペースを埋める */
            padding: 20px 30px; /* 左右のパディングを増やす */
            background-color: #fff;

        }

        h2 {
        	font-size: 1.8em;
            color: #333;
            margin-top: 0; /* Remove default top margin */
            margin-bottom: 15px;
            border-bottom: 1px solid #eee; /* Subtle line under title */
            padding-bottom: 10px;
        }

        /* Form Styling */
        form {
            margin-top: 20px;
            background-color: #ffffff; /* Ensure form background is white if content area has slight color */
            padding: 0;
        }

        form > label { /* Target labels directly within the form */
            display: inline-block; /* Keep labels inline with inputs */
            width: 120px; /* Fixed width for label alignment */
            margin-bottom: 15px; /* Space below label-input pair */
            vertical-align: top; /* Align with top of input for multi-line error messages */
            color: #555;
            font-weight: bold;
        }

        input[type="text"] {
            padding: 9px 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 300px; /* Adjust width as needed for input fields */
            box-sizing: border-box; /* Include padding and border in width */
            margin-bottom: 15px; /* Space below input field */
        }

        /* Error and Success Messages */
        .error-message {
            color: #dc3545; /* Bootstrap-like red for errors */
            font-size: 0.85em;
            margin-top: -10px; /* Pull error message closer to input */
            margin-left: 125px; /* Align with input field (label width + slight gap) */
            display: block; /* Ensure it's on its own line */
            padding-bottom: 5px; /* Add some space below the error */
        }

        .success-message {
            color: #28a745; /* Bootstrap-like green for success */
            font-size: 1em;
            margin-bottom: 20px;
            padding: 10px 15px;
            background-color: #d4edda; /* Light green background */
            border: 1px solid #c3e6cb;
            border-radius: 4px;
        }

        button[type="submit"] {
            background-color: #007bff; /* Primary blue button */
            color: white;
            padding: 10px 25px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
            margin-top: 15px;
            transition: background-color 0.2s ease;
        }

        button[type="submit"]:hover {
            background-color: #0056b3; /* Darker blue on hover */
        }

        .back-link {
            display: inline-block; /* aタグをボタンのように見せる */
            padding: 10px 25px;
            font-size: 1em;
            border: 1px solid #ccc;
            border-radius: 5px;
            color: #333;
            text-decoration: none;
            background-color: #f0f0f0;
        }

        .back-link:hover {
            background-color: #e0e0e0;
        }

        /* Clear floats if used (though flexbox mitigates need for this often) */
        .clearfix::after {
            content: "";
            display: table;
            clear: both;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>得点管理システム</h1>
        <a href="LOGO001.jsp">ログアウト</a>
    </div>

    <div class="main-container">
        <div class="nav-sidebar">
            <ul>
                <li><a href="MMNU001.jsp">メニュー</a></li>
                <li><a href="STDM001.jsp">学生管理</a></li>
                <li><label>成績管理</label></li> <%-- これはリンクではなくラベルです --%>
                <li><a href="GRMU001.jsp">成績登録</a></li>
                <li><a href="GRMR001.jsp">成績検索</a></li>
                <li><a href="SBJM001.jsp">科目管理</a></li> <%-- 科目管理のリンク先 --%>
            </ul>
        </div>

        <div class="main-content">
            <%-- メインコンテンツのタイトル --%>
            <h2>科目情報登録</h2>

            <%-- 登録成功メッセージの表示（必要であれば追加） --%>
            <c:if test="${not empty successMessage}">
                <div class="success-message">${successMessage}</div>
            </c:if>

            <form action="SBJM003.jsp" method="post"> <%-- サーブレット名を適宜変更してください (StudentRegisterServlet -> SubjectRegisterServlet) --%>
                <label for="subjectCd">科目コード</label> <input type="text" id="subjectCd" name="cd" value="${subject.cd}" placeholder="科目コードを入力してください" required />
                <%-- 科目コード関連のエラーメッセージ --%>
                <c:if test="${not empty errorSubjectCdEmpty}">
                    <div class="error-message">${errorSubjectCdEmpty}</div>
                </c:if>
                <c:if test="${not empty errorSubjectCdLength}">
                    <div class="error-message">${errorSubjectCdLength}</div>
                </c:if>
                <br>

                <label for="subjectName">科目名</label> <input type="text" id="subjectName" name="name" value="${subject.name}" placeholder="科目名を入力してください" required />
                <%-- 科目名関連のエラーメッセージ --%>
                <c:if test="${not empty errorSubjectNameEmpty}">
                    <div class="error-message">${errorSubjectNameEmpty}</div>
                </c:if>
                <br>

                <div class="button-group">
                    <button type="submit" name="execute">登録</button>
                    <a href="SBJM001.jsp" class="back-link">戻る</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
