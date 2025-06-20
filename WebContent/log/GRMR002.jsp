<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>得点管理システム - 成績一覧(科目)</title>
	<style>
		body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }

        .header {
            background-color: #e6f0fc;
            padding: 10px 20px;
            border-bottom: 1px solid #c0d9ef;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        }

        .header h1 {
            font-size: 24px;
            margin: 0;
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
            min-height: calc(100vh - 41px); /* ヘッダーの高さに合わせて調整 */
            background-color: #fff;
        }

        .menu {
            width: 200px;
            padding: 20px 0;
            border-right: 1px solid #e0e0e0;
            background-color: #fdfdfd;
            box-shadow: 2px 0 5px rgba(0,0,0,0.03);
        }

        .menu ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .menu ul li {
            margin-bottom: 5px;
        }

        .menu ul li a {
            text-decoration: none;
            color: #007bff;
            display: block;
            padding: 8px 25px;
            font-size: 14px;
            transition: background-color 0.2s ease, color 0.2s ease;
        }

        .menu ul li a:hover {
            background-color: #e0f2ff;
            color: #0056b3;
        }

        .menu ul li a.active {
            background-color: #dbeaff;
            color: #0056b3;
            font-weight: bold;
        }

        .main-content {
            flex-grow: 1;
            padding: 20px 30px;
            background-color: #fff;
        }

        .section {
            background-color: #fff;
            padding: 20px 25px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }

        .section-title {
            font-size: 20px;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
            color: #333;
            font-weight: bold;
        }

        .form-group {
            display: flex;
            align-items: center;
            margin-bottom: 15px;
            gap: 15px;
            flex-wrap: wrap;
        }

        .form-group label {
            width: 80px;
            min-width: 80px;
            text-align: right;
            font-weight: bold;
            color: #555;
            flex-shrink: 0;
        }

        .sub-section-label {
            text-align: left;
            font-weight: bold;
            width: auto;
            flex-grow: 1;
            color: #333;
            font-size: 16px;
            margin-top: 15px;
        }

        .form-group select,
        .form-group input[type="text"] {
            flex-grow: 1;
            max-width: 180px;
            padding: 8px 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }

        .form-group input[type="text"]::placeholder {
            color: #aaa;
        }

        .search-button {
            background-color: #4f4f4f;
            color: white;
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
            transition: background-color 0.2s ease, transform 0.1s ease;
            margin-left: 10px;
            white-space: nowrap;
        }

        .search-button:hover {
            background-color: #3a3a3a;
            transform: translateY(-1px);
        }

        .search-button:active {
            transform: translateY(0);
        }

        .info-message {
            margin-top: 25px;
            padding: 12px;
            background-color: #e0f7fa;
            border: 1px solid #b2ebf2;
            border-radius: 5px;
            color: #00796b;
            font-size: 14px;
            text-align: center;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }

        .error-message { /* 新たに追加 */
            margin-top: 25px;
            padding: 12px;
            background-color: #ffebee;
            border: 1px solid #ef9a9a;
            border-radius: 5px;
            color: #d32f2f;
            font-size: 14px;
            text-align: center;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }

        .form-group.field-row {
            justify-content: flex-start;
            gap: 15px;
        }

        .form-group.field-row > label {
            width: auto;
            text-align: left;
            font-weight: normal;
            color: #333;
        }

        .form-group.student-search {
            align-items: center;
        }

        .form-group.student-search .search-button {
            margin-left: 20px;
        }

        .table-container {
        	background-color: white;
        	padding: 25px;
        	border-radius: 8px;
        	box-shadow: 0 2px 5px rgba(0,0,0,0.05);
            margin-top: 20px; /* 検索フォームとの間に余白を追加 */
        }

        .table { /* tabel を table に修正 */
            width: 100%;
            border-collapse: collapse; /* セルの境界線を結合 */
        }

        .table thead th,
        .table tbody td {
            border: 1px solid #ddd; /* セルの枠線 */
            padding: 10px 15px;
            vertical-align: middle;
            text-align: center;
        }

        .table thead th {
        	background-color: #e9ecef;
        	font-weight: bold;
        	color: #495057;
        }
        .table tbody tr:nth-child(even) { /* 偶数行の背景色 */
            background-color: #f9f9f9;
        }

        .table .score-column {
        	width: 80px;
        }

        .subject-display { /* 科目名表示用の新しいスタイル */
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 15px;
            color: #333;
            padding-bottom: 5px;
            border-bottom: 1px dashed #eee;
        }
	</style>
</head>
<body>
	<div class="header">
		<h1>得点管理システム</h1>
		<div class="user-info">
			大原 太郎様<a href="#">ログアウト</a>
		</div>
	</div>

	<div class="container">
		<div class="menu">
			<ul>
				<li><a href="MMNU001.jsp">メニュー</a></li>
				<li><a href="STDM001.jsp">学生管理</a></li>
				<li><a>成績管理</a></li>
				<li><a href="GRMA001.jsp">成績一覧 (学生)</a></li> <%-- 学生一覧にリンクを修正 --%>
				<li><a href="GRML001.jsp" class="active">成績一覧 (科目)</a></li> <%-- このページをアクティブに設定 --%>
				<li><a href="GRMR001.jsp">成績登録</a></li>
				<li><a href="SBJM002.jsp">科目管理</a></li>
			</ul>
		</div>

		<div class="main-content">
			<div class="section">
				<div class="section-title">成績一覧 (科目)</div> <%-- タイトルを明確化 --%>

				<form action="GRML001.action" method="get"> <%-- アクション名を適切に変更、method="get" に --%>
					<div class="form-group field-row">
						<span class="sub-section-label">科目情報</span>
					</div>
					<div class="form-group field-row">

						<label for="entYear">入学年度</label> <%-- name属性をentYearに修正 --%>
						<select id="entYear" name="entYear">
							<option value="">------</option>
							<%-- ここに動的に入学年度のオプションを生成するJSP/JSTLコードを記述 --%>
							<c:forEach begin="2020" end="2025" var="year"> <%-- 例: 2020年から2025年まで --%>
                                <option value="${year}" <c:if test="${param.entYear == year}">selected</c:if>>${year}</option>
                            </c:forEach>
						</select>

						<label for="classNum">クラス</label> <%-- name属性をclassNumに修正 --%>
						<select id="classNum" name="classNum">
							<option value="">------</option>
							<%-- ここに動的にクラスのオプションを生成するJSP/JSTLコードを記述 --%>
                            <option value="A" <c:if test="${param.classNum == 'A'}">selected</c:if>>A</option>
                            <option value="B" <c:if test="${param.classNum == 'B'}">selected</c:if>>B</option>
                            <option value="C" <c:if test="${param.classNum == 'C'}">selected</c:if>>C</option>
						</select>

						<label for="subjectCd">科目</label> <%-- name属性をsubjectCdに修正 --%>
						<select id="subjectCd" name="subjectCd">
							<option value="">------</option>
							<%-- ここに動的に科目のオプションを生成するJSP/JSTLコードを記述 --%>
                            <option value="J001" <c:if test="${param.subjectCd == 'J001'}">selected</c:if>>情報処理基礎知識I</option>
                            <option value="J002" <c:if test="${param.subjectCd == 'J002'}">selected</c:if>>プログラミング演習</option>
                            <option value="J003" <c:if test="${param.subjectCd == 'J003'}">selected</c:if>>データベース</option>
						</select>

						<button type="submit" class="search-button">検索</button>
					</div>

					<div class="form-group" style="margin-top: 15px;">
						<span class="sub-section-label">学生情報</span>
					</div>

					<div class="form-group student-search">
						<label for="studentNo">学生番号</label> <%-- inputタグに修正、idとnameをstudentNoに --%>
						<input type="text" id="studentNo" name="studentNo" placeholder="学生番号を入力してください" value="${param.studentNo}">
						<button type="submit" class="search-button">検索</button>
					</div>
				</form>

                <%-- メッセージ表示エリア --%>
                <c:choose>
                    <c:when test="${not empty requestScope.message}">
                        <div class="info-message">
                            ${requestScope.message}
                        </div>
                    </c:when>
                    <c:when test="${empty param.entYear and empty param.classNum and empty param.subjectCd and empty param.studentNo and empty requestScope.subjectScores}">
                        <div class="info-message">
                            科目情報または学生番号を入力して検索ボタンをクリックしてください
                        </div>
                    </c:when>
                    <c:when test="${not empty param.subjectCd and empty requestScope.subjectScores}">
                         <div class="error-message">
                             選択された科目、または検索条件に該当する成績データがありません。
                         </div>
                    </c:when>
                </c:choose>

                <%-- 科目名表示 (検索結果がある場合のみ表示) --%>
                <c:if test="${not empty requestScope.subjectScores}">
                    <div class="table-container">
                        <p class="subject-display">科目:
                            <c:choose>
                                <c:when test="${not empty requestScope.selectedSubjectName}">${requestScope.selectedSubjectName}</c:when> <%-- サーブレットから渡される科目名 --%>
                                <c:when test="${param.subjectCd == 'J001'}">情報処理基礎知識I</c:when>
                                <c:when test="${param.subjectCd == 'J002'}">プログラミング演習</c:when>
                                <c:when test="${param.subjectCd == 'J003'}">データベース</c:when>
                                <c:otherwise>選択されていません</c:otherwise>
                            </c:choose>
                        </p>
                        <table class="table"> <%-- class="tabel"を"table"に修正 --%>
                            <thead>
                                <tr>
                                    <th>入学年度</th>
                                    <th>クラス</th>
                                    <th>学生番号</th>
                                    <th>氏名</th>
                                    <th class="score-column">1回</th>
                                    <th class="score-column">2回</th>
                                    <th class="score-column">...</th> <%-- 必要に応じて回数を追加 --%>
                                </tr>
                            </thead>
                            <tbody>
                                <%-- 成績データを表示 --%>
                                <c:forEach var="score" items="${requestScope.subjectScores}"> <%-- requestScore を requestScope に修正 --%>
                                    <tr>
                                        <td>${score.entYear}</td>
                                        <td>${score.classNum}</td>
                                        <td>${score.studentNo}</td>
                                        <td>${score.studentName}</td>
                                        <td><c:out value="${score.score1}" default="-"/></td> <%-- nullの場合はハイフンを表示 --%>
                                        <td><c:out value="${score.score2}" default="-"/></td>
                                        <td>-</td> <%-- さらに回数分の<td>を追加 --%>
                                    </tr>
                                </c:forEach>

                                <%-- データがない場合のメッセージ --%>
                                <c:if test="${empty requestScope.subjectScores}"> <%-- requestScore を requestScope に修正 --%>
                                    <tr>
                                        <td colspan="7">該当するデータがありません</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </c:if>

			</div>
		</div>
	</div>
</body>
</html>