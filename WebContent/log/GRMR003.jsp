<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>得点管理システム - 成績一覧（学生）</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 90%;
            margin: 20px auto;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .user-info {
            font-size: 14px;
        }
        .user-info a {
            color: #007bff;
            text-decoration: none;
        }
        .menu {
            float: left;
            width: 150px;
            padding-right: 20px;
            border-right: 1px solid #eee;
            min-height: 400px; /* Adjust as needed */
        }
        .menu ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .menu ul li {
            margin-bottom: 10px;
        }
        .menu ul li a {
            text-decoration: none;
            color: #333;
            display: block;
            padding: 5px 0;
        }
        .menu ul li a.current {
            color: #007bff;
            font-weight: bold;
        }
        .main-content {
            margin-left: 180px; /* Space for the menu */
            padding-left: 20px;
        }
        .main-content h2 {
            margin-top: 0;
            border-bottom: 2px solid #007bff;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }
        .filter-section {
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 20px;
        }
        .filter-section .row {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }
        .filter-section .row:last-child {
            margin-bottom: 0;
        }
        .filter-section label {
            margin-right: 10px;
            font-weight: bold;
            min-width: 80px; /* Adjust as needed for alignment */
        }
        .filter-section select,
        .filter-section input[type="text"] {
            margin-right: 20px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        .filter-section button {
            padding: 8px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .filter-section button:hover {
            background-color: #0056b3;
        }
        .error-message {
            color: red;
            font-weight: bold;
            margin-top: 5px;
            font-size: 0.9em;
        }
        .student-info {
            margin-top: 20px;
            font-weight: bold;
        }
        .student-info span {
            color: #007bff;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        table th, table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        table th {
            background-color: #f2f2f2;
        }
        .footer {
            clear: both;
            text-align: center;
            padding-top: 20px;
            border-top: 1px solid #eee;
            margin-top: 30px;
            font-size: 12px;
            color: #777;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>得点管理システム</h1>
            <div class="user-info">
                <span>大原太郎</span> (<a href="LOGO001.jsp">ログアウト</a>)
            </div>
        </div>

        <div class="menu">
            <ul>
                <li><a href="MMNU001.jsp">メニュー</a></li>
                <li><a href="STDM001.jsp">学生管理</a></li>
                <li><a>成績管理</a></li>
                <li><a href="GRMU001.jsp">成績登録</a></li>
                <li><a href="GRMR001.jsp" class="current">成績参照</a></li>
                <li><a href="SBJM001.jsp">科目管理</a></li>
            </ul>
        </div>

        <div class="main-content">
            <h2>成績一覧（学生）</h2>

            <div class="filter-section">
                <form action="scoreListStudent.jsp" method="get">
                    <div class="row">
                        <label for="subjectInfo">科目情報</label>
                        <select id="entYear" name="entYear">
                            <option value="">入学年度</option>
                            <%
                                int currentYear = java.time.Year.now().getValue();
                                for (int i = 0; i < 5; i++) {
                                    int yearOption = currentYear - i;
                                    String selectedEntYear = request.getParameter("entYear");
                                    String selected = (selectedEntYear != null && selectedEntYear.equals(String.valueOf(yearOption))) ? "selected" : "";
                            %>
                                    <option value="<%= yearOption %>" <%= selected %>><%= yearOption %></option>
                            <%
                                }
                            %>
                        </select>
                        <select id="classNum" name="classNum">
                            <option value="">クラス</option>
                            <%
                                // 仮のクラスリスト。実際はDBから取得するか、設定ファイルから読み込む
                                String[] classNums = {"101", "102", "131", "201", "202", "301"};
                                String selectedClassNum = request.getParameter("classNum");
                                for (String cn : classNums) {
                                    String selected = (selectedClassNum != null && selectedClassNum.equals(cn)) ? "selected" : "";
                            %>
                                    <option value="<%= cn %>" <%= selected %>><%= cn %></option>
                            <%
                                }
                            %>
                        </select>
                        <select id="subjectCd" name="subjectCd">
                            <option value="">科目</option>
                            <%
                                List<Map<String, String>> subjectsFromDb = new ArrayList<>();
                                Connection connForSubjects = null;
                                PreparedStatement pstmtForSubjects = null;
                                ResultSet rsForSubjects = null;
                                try {
                                    Class.forName("org.h2.Driver");
                                    String DB_URL = "jdbc:h2:tcp://localhost/~/kaihatsu"; // H2のDB接続情報
                                    String USER = "sa";
                                    String PASS = "";
                                    connForSubjects = DriverManager.getConnection(DB_URL, USER, PASS);
                                    // SUBJECTテーブルのSCHOOL_CDカラムを考慮
                                    // ご提示のINSERT文では'NULL'という文字列が入っていますので、そのように検索します。
                                    // もし実際のNULL値として扱いたい場合は "WHERE SCHOOL_CD IS NULL" に変更してください。
                                    pstmtForSubjects = connForSubjects.prepareStatement(
                                        "SELECT SUBJECT_CD, SUBJECT_NAME FROM SUBJECT WHERE SCHOOL_CD = 'NULL' ORDER BY SUBJECT_CD"
                                    );
                                    rsForSubjects = pstmtForSubjects.executeQuery();
                                    while(rsForSubjects.next()){
                                        Map<String, String> subject = new HashMap<>();
                                        subject.put("cd", rsForSubjects.getString("SUBJECT_CD"));
                                        subject.put("name", rsForSubjects.getString("SUBJECT_NAME"));
                                        subjectsFromDb.add(subject);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    // エラーログ出力のみ、画面には表示しない（学生情報検索に影響させないため）
                                } finally {
                                    try { if (rsForSubjects != null) rsForSubjects.close(); } catch (SQLException e) { /* log error */ }
                                    try { if (pstmtForSubjects != null) pstmtForSubjects.close(); } catch (SQLException e) { /* log error */ }
                                    try { if (connForSubjects != null) connForSubjects.close(); } catch (SQLException e) { /* log error */ }
                                }

                                String selectedSubjectCd = request.getParameter("subjectCd");
                                for(Map<String, String> sub : subjectsFromDb) {
                                    String selected = (selectedSubjectCd != null && selectedSubjectCd.equals(sub.get("cd"))) ? "selected" : "";
                            %>
                                    <option value="<%= sub.get("cd") %>" <%= selected %>><%= sub.get("name") %></option>
                            <%
                                }
                            %>
                        </select>
                        <button type="submit">検索</button>
                    </div>

                    <div class="row">
                        <label for="studentNo">学生番号</label>
                        <input type="text" id="studentNo" name="studentNo" value="<%= request.getParameter("studentNo") != null ? request.getParameter("studentNo") : "" %>">
                        <button type="submit">検索</button>
                    </div>
                </form>
            </div>

            <%
            List<Map<String, String>> scores = new ArrayList<>();
            String studentName = null;
            String currentStudentNo = request.getParameter("studentNo");

            String errorMessage = null;
            String studentInfoMessage = null; // 学生情報表示メッセージ
            String noScoreMessage = null;     // 成績がない場合のメッセージ

            boolean studentNoEntered = (currentStudentNo != null && !currentStudentNo.trim().isEmpty());

            boolean isSubjectInfoSearch = false;
            String filterEntYear = request.getParameter("entYear");
            String filterClassNum = request.getParameter("classNum");
            String filterSubjectCd = request.getParameter("subjectCd");

            if ((filterEntYear != null && !filterEntYear.isEmpty()) ||
                (filterClassNum != null && !filterClassNum.isEmpty()) ||
                (filterSubjectCd != null && !filterSubjectCd.isEmpty())) {
                isSubjectInfoSearch = true;
            }

            // 検索が実行された場合、または初期表示時以外で何か入力があった場合のみDB検索を試みる
            if (studentNoEntered || isSubjectInfoSearch || request.getParameterMap().size() > 0) { // 少なくとも何かパラメータがあれば
                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;

                try {
                    Class.forName("org.h2.Driver");
                    String DB_URL = "jdbc:h2:tcp://localhost/~/testdb";
                    String USER = "sa";
                    String PASS = "";

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);

                    if (studentNoEntered) { // 学生番号で検索する場合のロジック
                        // 学生情報の取得
                        pstmt = conn.prepareStatement("SELECT NAME, ENT_YEAR, CLASS_NUM FROM STUDENT_NEW WHERE NO = ?");
                        pstmt.setString(1, currentStudentNo);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            studentName = rs.getString("NAME");
                            String entYear = String.valueOf(rs.getInt("ENT_YEAR"));
                            String classNum = rs.getString("CLASS_NUM");
                            studentInfoMessage = "氏名: <span>" + studentName + " (" + currentStudentNo + ")</span>";
                        } else {
                            // 学生が見つからない場合
                            errorMessage = "指定された学生番号の学生が見つかりません。";
                        }
                        try { if (rs != null) rs.close(); } catch (SQLException e) { /* log error */ }
                        try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { /* log error */ }

                        // その学生の成績を取得 (学生が見つかった場合のみ)
                        if (studentName != null) {
                            StringBuilder scoreSqlBuilder = new StringBuilder();
                            // SUBJECTテーブルのSUBJECT_CDとSUBJECT_NAMEカラムを使用
                            scoreSqlBuilder.append("SELECT S.SUBJECT_NAME AS SUBJECT_NAME, SC.SUBJECT_CD, SC.NO AS SCORE_NO, SC.POINT ");
                            scoreSqlBuilder.append("FROM SCORE SC ");
                            scoreSqlBuilder.append("JOIN SUBJECT S ON SC.SUBJECT_CD = S.SUBJECT_CD "); // SUBJECT_CDで結合
                            scoreSqlBuilder.append("WHERE SC.STUDENT_NO = ? ");
                            scoreSqlBuilder.append("ORDER BY SC.SUBJECT_CD, SC.NO");

                            pstmt = conn.prepareStatement(scoreSqlBuilder.toString());
                            pstmt.setString(1, currentStudentNo);
                            rs = pstmt.executeQuery();

                            while (rs.next()) {
                                Map<String, String> score = new HashMap<>();
                                score.put("subjectName", rs.getString("SUBJECT_NAME"));
                                score.put("subjectCd", rs.getString("SUBJECT_CD"));
                                score.put("scoreNo", String.valueOf(rs.getInt("SCORE_NO")));
                                score.put("point", (rs.getObject("POINT") != null) ? String.valueOf(rs.getInt("POINT")) : "-");
                                scores.add(score);
                            }
                            if (scores.isEmpty()) {
                                noScoreMessage = "成績情報が存在しませんでした。";
                            }
                        }
                    } else if (isSubjectInfoSearch) { // 科目情報で検索する場合のロジック
                        // 学生番号が空で、科目情報で絞り込み検索が行われた場合
                        StringBuilder searchSqlBuilder = new StringBuilder();
                        // STUDENT_NEWのデータも取得して、学生名などを表示できるようにします。
                        searchSqlBuilder.append("SELECT S.SUBJECT_NAME AS SUBJECT_NAME, SC.SUBJECT_CD, SC.NO AS SCORE_NO, SC.POINT, ");
                        searchSqlBuilder.append("ST.NO AS STUDENT_NO, ST.NAME AS STUDENT_NAME, ST.ENT_YEAR, ST.CLASS_NUM ");
                        searchSqlBuilder.append("FROM SCORE SC ");
                        searchSqlBuilder.append("JOIN SUBJECT S ON SC.SUBJECT_CD = S.SUBJECT_CD "); // SUBJECT_CDで結合
                        searchSqlBuilder.append("JOIN STUDENT_NEW ST ON SC.STUDENT_NO = ST.NO ");
                        searchSqlBuilder.append("WHERE 1=1 ");

                        List<Object> searchParams = new ArrayList<>();

                        if (filterEntYear != null && !filterEntYear.isEmpty()) {
                            searchSqlBuilder.append(" AND ST.ENT_YEAR = ?");
                            searchParams.add(Integer.parseInt(filterEntYear));
                        }
                        if (filterClassNum != null && !filterClassNum.isEmpty()) {
                            searchSqlBuilder.append(" AND ST.CLASS_NUM = ?");
                            searchParams.add(filterClassNum);
                        }
                        if (filterSubjectCd != null && !filterSubjectCd.isEmpty()) {
                            searchSqlBuilder.append(" AND SC.SUBJECT_CD = ?");
                            searchParams.add(filterSubjectCd);
                        }

                        searchSqlBuilder.append(" ORDER BY ST.ENT_YEAR, ST.CLASS_NUM, ST.NO, SC.SUBJECT_CD, SC.NO");

                        pstmt = conn.prepareStatement(searchSqlBuilder.toString());
                        for (int i = 0; i < searchParams.size(); i++) {
                            pstmt.setObject(i + 1, searchParams.get(i));
                        }
                        rs = pstmt.executeQuery();

                        // この検索では複数の学生の成績が表示される可能性があるので、
                        // 学生情報 (氏名など) は各行に表示するか、またはこの検索結果表示では省くか考慮が必要です。
                        // スクリーンショット125は学生一人に絞り込まれた結果に見えるため、
                        // 学生情報検索では「氏名」は表示しない（テーブルには含めない）方針とします。
                        // 必要であればテーブルの列を追加してください。
                        while (rs.next()) {
                            Map<String, String> score = new HashMap<>();
                            score.put("subjectName", rs.getString("SUBJECT_NAME"));
                            score.put("subjectCd", rs.getString("SUBJECT_CD"));
                            score.put("scoreNo", String.valueOf(rs.getInt("SCORE_NO")));
                            score.put("point", (rs.getObject("POINT") != null) ? String.valueOf(rs.getInt("POINT")) : "-");
                            scores.add(score);
                        }
                        if (scores.isEmpty()) {
                            noScoreMessage = "絞り込み条件に該当する成績情報が見つかりませんでした。";
                        }
                    } else {
                        // 検索ボタンが押されたが、学生番号も科目情報も入力されていない場合
                        errorMessage = "科目情報を選択または学生番号を入力してください。";
                    }

                } catch (ClassNotFoundException e) {
                    errorMessage = "H2 JDBCドライバーが見つかりません。WEB-INF/libにh2*.jarを配置してください。: " + e.getMessage();
                    e.printStackTrace();
                } catch (SQLException e) {
                    errorMessage = "データベースエラーが発生しました: " + e.getMessage();
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    errorMessage = "入学年度が不正な値です。: " + e.getMessage();
                    e.printStackTrace();
                } finally {
                    try { if (rs != null) rs.close(); } catch (SQLException e) { /* log error */ }
                    try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { /* log error */ }
                    try { if (conn != null) conn.close(); } catch (SQLException e) { /* log error */ }
                }
            } else {
                 // ページ初期ロード時（何も検索されていない状態）はエラーメッセージを表示しない
                 errorMessage = null;
            }
            %>

            <% if (errorMessage != null) { %>
                <p class="error-message"><%= errorMessage %></p>
            <% } %>

            <% if (studentInfoMessage != null) { %>
                <p class="student-info"><%= studentInfoMessage %></p>
            <% } %>

            <% if (noScoreMessage != null && errorMessage == null) { %>
                <p class="error-message"><%= noScoreMessage %></p>
            <% } %>

            <%-- 成績テーブルは、成績データがある場合のみ表示 --%>
            <% if (!scores.isEmpty()) { %>
                <table>
                    <thead>
                        <tr>
                            <th>科目名</th>
                            <th>科目コード</th>
                            <th>回数</th>
                            <th>得点</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        for (Map<String, String> score : scores) {
                        %>
                        <tr>
                            <td><%= score.get("subjectName") %></td>
                            <td><%= score.get("subjectCd") %></td>
                            <td><%= score.get("scoreNo") %></td>
                            <td><%= score.get("point") %></td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            <% } %>

        </div>

        <div class="footer">
            © 2023 TIC 大原学園
        </div>
    </div>
</body>
</html>