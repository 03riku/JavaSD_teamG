<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生情報変更 - 得点管理システム</title>
<style>
/* ここからCSSの記述 */
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

/* 現在のアクティブなメニュー項目にスタイルを追加することもできます */
/* .sidebar a.active {
    background-color: #007bff;
    color: white;
} */

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

.form-group input[type="radio"] {
    margin-right: 8px;
}

.form-group input[type="radio"] + label { /* ラジオボタンのラベル */
    width: auto; /* ラジオボタンのラベルの幅は自動 */
    text-align: left;
    margin-right: 20px;
    font-weight: normal; /* ラジオボタンのラベルは太字にしない */
}


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

/* 番号付きの丸印のテキストはCSSで直接再現が難しいですが、HTMLで表現可能です。
   ここでは見た目上のデザインを重視しています。 */
/* 特定の要素に番号の丸印を付ける場合は、HTMLで対応する要素の近くに配置し、
   CSSで丸い背景と中央揃えを適用することになります。 */

/* 例: 番号付きの丸印（HTML側に要素を追加する必要があります） */
/*
.circled-number {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    width: 24px;
    height: 24px;
    border-radius: 50%;
    background-color: #007bff;
    color: white;
    font-size: 14px;
    font-weight: bold;
    margin-right: 5px;
}
*/

/* グループ化されたオブジェクトのテキストは、HTMLのコメントや単純なdivで表現 */
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
    <div class="header">
        <h1>得点管理システム</h1>
        <div class="user-info">
            大原太郎様 <a href="LOGO001.jsp">ログアウト</a>
        </div>
    </div>

    <div class="container">
        <div class="sidebar">
            <h3>メニュー</h3>
            <ul>
                <li><a href="STDM001.jsp">学生管理</a></li>
                <li><a>成績管理</a></li>
                <li><a href="GRMU001.jsp">成績登録</a></li>
                <li><a href="GRMR001.jsp">成績参照</a></li>
                <li><a href="SBJM001.jsp">科目管理</a></li>
            </ul>
        </div>

        <div class="main-content">
            <h2>学生情報変更</h2>
            <form action="STDM005.jsp" method="post"> <%-- アクションはサーブレットを指します --%>
                <div class="form-group">
                    <label for="admissionYear">入学年度</label>
                    <input type="text" id="admissionYear" name="admissionYear" value="2023" readonly>
                </div>
                <div class="form-group">
                    <label for="studentId">学生番号</label>
                    <input type="text" id="studentId" name="studentId" value="123456" readonly>
                </div>
                <div class="form-group">
                    <label for="studentName">氏名</label>
                    <input type="text" id="studentName" name="studentName" value="大原 次郎">
                </div>
                <div class="form-group">
                    <label for="studentClass">クラス</label>
                    <select id="studentClass" name="studentClass">
                        <option value="101" selected>101</option>
                        <option value="102">102</option>
                        <option value="103">103</option>
                    </select>
                </div>
                <div class="form-group">
                    <input type="radio" id="currentStudent" name="enrollmentStatus" value="active" checked>
                    <label for="currentStudent">在学中</label>
                </div>
                <div class="form-actions">
                    <button type="submit">変更</button>
                    <button type="button" onclick="history.back()">戻る</button>
                </div>
            </form>
            <div class="grouped-object-note">
            </div>
        </div>
    </div>
</body>