<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Bean.Student" %> <%-- Student Beanをインポート --%>
<%@ page import="Bean.School" %> <%-- School Beanをインポート（念のため） --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生情報変更 - 得点管理システム</title>
<style>
/* ここからCSSの記述 - STDM005.jspと同じCSSを想定 */
body {
    font-family: sans-serif;
    margin: 0;
    display: flex;
    flex-direction: column;
    min-height: 100vh;
    background-color: #f4f4f4; /* 全体の背景色を追加 */
}

.header {
    background-color: #ffffff; /* 白背景 */
    padding: 10px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #ddd; /* 境界線 */
    box-shadow: 0 2px 4px rgba(0,0,0,0.1); /* 軽い影 */
}

.header h1 {
    margin: 0;
    font-size: 24px;
    color: #333;
}

.user-info {
    font-size: 14px;
    color: #555;
}

.user-info a {
    color: #007bff;
    text-decoration: none;
    margin-left: 10px;
}

.user-info a:hover {
    text-decoration: underline;
}

.container {
    display: flex;
    flex-grow: 1;
    margin-top: 10px; /* ヘッダーとの間に少しスペース */
}

.sidebar {
    width: 200px;
    background-color: #ffffff; /* 白背景 */
    padding: 20px 0; /* 上下左右のパディング調整 */
    border-right: 1px solid #ddd;
    box-shadow: 2px 0 4px rgba(0,0,0,0.05); /* 軽い影 */
}

.sidebar h3 {
    margin-top: 0;
    padding: 0 20px; /* パディング */
    color: #444;
    border-bottom: 1px solid #eee;
    padding-bottom: 10px;
    margin-bottom: 15px;
}

.sidebar ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.sidebar li {
    margin-bottom: 5px; /* リスト項目の間隔 */
}

.sidebar a {
    text-decoration: none;
    color: #333;
    display: block;
    padding: 10px 20px; /* パディング */
    transition: background-color 0.3s ease; /* ホバーアニメーション */
}

.sidebar a:hover {
    background-color: #e9e9e9;
    color: #007bff;
}

.main-content {
    flex-grow: 1;
    padding: 20px;
    background-color: #ffffff; /* 白背景 */
    margin-left: 10px; /* サイドバーとの間に少しスペース */
    border-radius: 8px; /* 角を丸くする */
    box-shadow: 0 0 10px rgba(0,0,0,0.05); /* 軽い影 */
}

.main-content h2 {
    margin-top: 0;
    padding-bottom: 15px;
    border-bottom: 2px solid #007bff; /* 青い下線 */
    color: #333;
    margin-bottom: 25px;
    font-size: 28px;
}

.form-group {
    margin-bottom: 20px;
    display: flex; /* Flexboxでラベルと入力を横並びに */
    align-items: center; /* 垂直方向中央揃え */
}

.form-group label {
    display: inline-block;
    width: 120px; /* ラベルの幅を固定 */
    font-weight: bold;
    color: #555;
    text-align: right; /* ラベルを右揃え */
    margin-right: 20px; /* ラベルと入力の間のスペース */
}

.form-group input[type="text"],
.form-group select {
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px; /* 少し丸みを帯びた角 */
    width: 250px; /* 入力フィールドの幅を調整 */
    font-size: 16px;
    box-sizing: border-box; /* パディングとボーダーを幅に含める */
}

.form-group input[type="text"][readonly] {
    background-color: #e9e9e9; /* 読み取り専用フィールドの背景色 */
    color: #777;
    cursor: not-allowed;
}

/* ラジオボタンのスタイルは今回は使用しないためコメントアウトするか削除します
.form-group input[type="radio"] {
    margin-right: 8px;
}

.form-group input[type="radio"] + label {
    width: auto;
    text-align: left;
    margin-right: 20px;
    font-weight: normal;
}
*/

.form-actions {
    margin-top: 30px;
    text-align: center; /* ボタンを中央揃え */
}

.form-actions button {
    padding: 12px 25px;
    margin: 0 10px; /* ボタン間のスペース */
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    transition: background-color 0.3s ease, transform 0.2s ease; /* ホバーアニメーション */
}

.form-actions button[type="submit"] {
    background-color: #007bff;
    color: white;
}

.form-actions button[type="submit"]:hover {
    background-color: #0056b3;
    transform: translateY(-2px); /* 少し上に移動 */
}

.form-actions button[type="button"] {
    background-color: #6c757d;
    color: white;
}

.form-actions button[type="button"]:hover {
    background-color: #5a6268;
    transform: translateY(-2px); /* 少し上に移動 */
}

.grouped-object-note {
    text-align: right;
    margin-top: 30px;
    font-size: 14px;
    color: #777;
    padding-right: 20px; /* 右側に余白 */
}
/* ここまでCSSの記述 */
</style>
</head>
<body>
    <%
        // Student_updateServletから渡されたStudentオブジェクトを取得
        Student student = (Student) request.getAttribute("student");
        // エラーメッセージの取得 (Student_updateServletは"error"属性で設定)
        String errorMessage = (String) request.getAttribute("error");

        // studentオブジェクトがnullの場合のエラーハンドリング
        if (student == null) {
            // エラーメッセージがあれば表示
            if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
                <p style="color: red; text-align: center; font-size: 18px;"><%= errorMessage %></p>
    <%
            } else {
    %>
                <p style="color: red; text-align: center; font-size: 18px;">学生情報が取得できませんでした。</p>
    <%
            }
    %>
            <div style="text-align: center; margin-top: 20px;">
                <button type="button" onclick="history.back()">戻る</button>
            </div>
    <%
            return; // これ以上JSPの描画を続行しない
        }
    %>

    <div class="header">
        <h1>得点管理システム</h1>
        <div class="user-info">
            大原太郎様 <a href="${pageContext.request.contextPath}/log/LOGO001.jsp">ログアウト</a>
        </div>
    </div>

    <div class="container">
        <div class="sidebar">
            <h3>メニュー</h3>
            <ul>
                <li><a href="${pageContext.request.contextPath}/log/STDM001.jsp">学生管理</a></li>
                <li><a>成績管理</a></li>
                <li><a href="${pageContext.request.contextPath}/log/GRMU001.jsp">成績登録</a></li>
                <li><a href="${pageContext.request.contextPath}/log/GRMR001.jsp">成績参照</a></li>
                <li><a href="${pageContext.request.contextPath}/log/SBJM001.jsp">科目管理</a></li>
            </ul>
        </div>

        <div class="main-content">
            <h2>学生情報変更</h2>
            <%-- エラーメッセージがあれば表示 --%>
            <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
                <p style="color: red; margin-bottom: 20px;"><%= errorMessage %></p>
            <% } %>

            <%-- actionは学生情報更新処理を実行するサーブレット（student_update_done）を指します --%>
            <form action="${pageContext.request.contextPath}/student_update_done" method="post">
                <%-- 必須: 学生番号 (変更不可なのでhidden) --%>
                <input type="hidden" name="no" value="<%= student.getNo() %>">

                <%-- 必須: 学校コード (ユーザーには表示せず、更新時に必要なのでhidden) --%>
                <%-- StudentオブジェクトにSchoolオブジェクトがセットされていることを前提とします --%>
                <input type="hidden" name="school_cd" value="<%= student.getSchool() != null ? student.getSchool().getCd() : "" %>">

                <div class="form-group">
                    <label for="entYear">入学年度</label>
                    <%-- readonlyなので変更不可。Student Beanから値を取得。name属性をent_yearに変更 --%>
                    <input type="text" id="entYear" name="ent_year" value="<%= student.getEntYear() %>" readonly>
                </div>
                <div class="form-group">
                    <label for="studentNo">学生番号</label>
                    <%-- readonlyなので変更不可。Student Beanから値を取得。これが更新対象のキーとなる。name属性をnoに変更 --%>
                    <input type="text" id="studentNo" name="no" value="<%= student.getNo() %>" readonly>
                </div>
                <div class="form-group">
                    <label for="studentName">氏名</label>
                    <%-- 編集可能。Student Beanから値を取得。name属性をnameに変更 --%>
                    <input type="text" id="studentName" name="name" value="<%= student.getName() != null ? student.getName() : "" %>" required>
                </div>
                <div class="form-group">
                    <label for="classNum">クラス</label>
                    <%-- クラスを選択。Student Beanの値に応じてselectedを設定。name属性をclass_numに変更 --%>
                    <select id="classNum" name="class_num" required>
                        <option value="">選択してください</option>
                        <option value="131" <%= (student.getClassNum() != null && student.getClassNum().equals("131")) ? "selected" : "" %>>301</option>
                        <option value="132" <%= (student.getClassNum() != null && student.getClassNum().equals("132")) ? "selected" : "" %>>302</option>
                        <option value="133" <%= (student.getClassNum() != null && student.getClassNum().equals("133")) ? "selected" : "" %>>303</option>
                        <%-- 必要に応じて他のクラスオプションを追加 --%>
                    </select>
                </div>
                <div class="form-group">
                    <label for="isAttend">在学中</label>
                    <%-- 在学中チェックボックス。Student Beanのattendプロパティに応じてcheckedを設定 --%>
                    <%-- nameをis_attendに、valueをonに設定。ラジオボタンをチェックボックスに変更 --%>
                    <input type="checkbox" id="isAttend" name="is_attend" value="on" <%= student.isAttend() ? "checked" : "" %>>
                </div>
                <div class="form-actions">
                    <button type="submit">変更</button>
                    <%-- 戻るボタンはJavaScriptのhistory.back()で戻る --%>
                    <button type="button" onclick="history.back()">戻る</button>
                </div>
            </form>
            <div class="grouped-object-note">
            </div>
        </div>
    </div>
</body>
</html>