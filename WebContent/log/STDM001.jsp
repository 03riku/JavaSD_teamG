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
    <title>得点管理システム - 学生管理</title>
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
        .new-student-link {
            float: right;
            margin-top: -40px; /* Adjust to align with h2 */
            color: #007bff;
            text-decoration: none;
        }
        .filter-form {
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 20px;
            overflow: auto; /* Clear floats */
        }
        .filter-form label {
            margin-right: 10px;
            font-weight: bold;
        }
        .filter-form select,
        .filter-form input[type="text"],
        .filter-form input[type="checkbox"] {
            margin-right: 20px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        .filter-form button {
            padding: 8px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .filter-form button:hover {
            background-color: #0056b3;
        }
        .student-count {
            margin-bottom: 10px;
            font-weight: bold;
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
        table td a {
            color: #007bff;
            text-decoration: none;
            margin-right: 10px;
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
        .error-message, .no-data-message {
            color: red;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .no-data-message {
            color: #555;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>得点管理システム</h1>
            <div class="user-info">
                  <td align="right"><a href="LOGO001.jsp">ログアウト</a></td>
            </div>
        </div>

        <div class="menu">
            <ul>
                <li><a href="MMNU001.jsp">メニュー</a></li>
                <li><a href="STDM001.jsp">学生管理</a></li>
                <li><a>成績管理</a></li>
                <li><a href="GRMU001.jsp">成績登録</a></li>
                <li><a href="GRMR001.jsp">成績参照</a></li>
                <li><a href="SBJM001.jsp">科目管理</a></li>
            </ul>
        </div>

        <div class="main-content">
            <h2>学生管理</h2>
            <a href="STDM002.jsp" class="new-student-link">学生登録</a>

            <div class="filter-form">
                <form action="studentManagement.jsp" method="get">
                    <label for="entYear">入学年度</label>
                    <select id="entYear" name="entYear">
                        <option value="">----</option>
                        <%
                            // 現在の年と過去数年を生成して選択肢に表示
                            int currentYear = java.time.Year.now().getValue();
                            for (int i = 0; i < 5; i++) { // 例: 現在の年-4年から現在まで
                                int yearOption = currentYear - i;
                                String selectedEntYear = request.getParameter("entYear");
                                String selected = (selectedEntYear != null && selectedEntYear.equals(String.valueOf(yearOption))) ? "selected" : "";
                        %>
                                <option value="<%= yearOption %>" <%= selected %>><%= yearOption %></option>
                        <%
                            }
                        %>
                    </select>

                    <label for="classNum">クラス</label>
                    <select id="classNum" name="classNum">
                        <option value="">------</option>
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

                    <label for="isAttend">在学中</label>
                    <input type="checkbox" id="isAttend" name="isAttend" value="true"
                           <% if (request.getParameter("isAttend") != null && request.getParameter("isAttend").equals("true")) { %>checked<% } %>>


                    <button type="submit">絞込み</button>
                </form>
            </div>

            <%
            List<Map<String, String>> students = new ArrayList<>();
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String errorMessage = null;
            int studentCount = 0; // 検索結果件数

            String filterEntYear = request.getParameter("entYear");
            String filterClassNum = request.getParameter("classNum");
            String filterIsAttend = request.getParameter("isAttend");
            String filterStudentName = request.getParameter("studentName");

            try {
                Class.forName("org.h2.Driver");
                String DB_URL = "jdbc:h2:tcp://localhost/~/kaihatsu"; // H2のデフォルトTCPサーバーとデータベース名
                String USER = "sa";
                String PASS = "";

                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                StringBuilder sqlBuilder = new StringBuilder("SELECT NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND FROM STUDENT WHERE 1=1");
                List<Object> params = new ArrayList<>();

                if (filterEntYear != null && !filterEntYear.isEmpty()) {
                    sqlBuilder.append(" AND ENT_YEAR = ?");
                    params.add(Integer.parseInt(filterEntYear));
                }
                if (filterClassNum != null && !filterClassNum.isEmpty()) {
                    sqlBuilder.append(" AND CLASS_NUM = ?");
                    params.add(filterClassNum);
                }
                if (filterIsAttend != null && filterIsAttend.equals("true")) {
                    sqlBuilder.append(" AND IS_ATTEND = 'O'"); // 'O'を在学中と見なす
                }
                if (filterStudentName != null && !filterStudentName.isEmpty()) {
                    sqlBuilder.append(" AND NAME LIKE ?");
                    params.add("%" + filterStudentName + "%");
                }

                sqlBuilder.append(" ORDER BY ENT_YEAR, CLASS_NUM, NO"); // ソート順を追加

                pstmt = conn.prepareStatement(sqlBuilder.toString());

                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(i + 1, params.get(i));
                }

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    Map<String, String> student = new HashMap<>();
                    student.put("no", rs.getString("NO"));
                    student.put("name", rs.getString("NAME"));
                    student.put("entYear", String.valueOf(rs.getInt("ENT_YEAR")));
                    student.put("classNum", rs.getString("CLASS_NUM"));
                    student.put("isAttend", rs.getString("IS_ATTEND"));
                    students.add(student);
                }
                studentCount = students.size();

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
            %>

            <% if (errorMessage != null) { %>
                <p class="error-message"><%= errorMessage %></p>
            <% } %>

            <p class="student-count">検索結果：<%= studentCount %>件</p>

            <table>
                <thead>
                    <tr>
                        <th>入学年度</th>
                        <th>学生番号</th>
                        <th>氏名</th>
                        <th>クラス</th>
                        <th>在学中</th>
                        <th></th> <%-- 変更リンク用 --%>
                        <th></th> <%-- 削除リンク用 --%>
                    </tr>
                </thead>
                <tbody>
                    <%
                    if (students.isEmpty() && errorMessage == null) {
                    %>
                    <tr>
                        <td colspan="7" class="no-data-message">絞り込み条件に該当する学生情報がありません。</td>
                    </tr>
                    <%
                    } else {
                        for (Map<String, String> student : students) {
                    %>
                    <tr>
                        <td><%= student.get("entYear") %></td>
                        <td><%= student.get("no") %></td>
                        <td><%= student.get("name") %></td>
                        <td><%= student.get("classNum") %></td>
                        <td><%= "O".equals(student.get("isAttend")) ? "◯" : "×" %></td>
                        <td><a href="STDM004.jsp">変更</a></td>
                    </tr>
                    <%
                        }
                    }
                    %>
                </tbody>
            </table>
        </div>

        <div class="footer">
        </div>
    </div>
</body>
</html>