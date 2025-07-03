<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="java.sql.*" %> <%-- JDBC関連のクラスをインポート --%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目管理システム - 科目管理</title>
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
        .new-subject-link {
            float: right;
            margin-top: -40px; /* Adjust to align with h2 */
            color: #007bff;
            text-decoration: none;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
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
        .error-message {
            color: red;
            font-weight: bold;
            margin-bottom: 10px;
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
                <li><a href="MMNU001.jsp" class="current">メニュー</a></li>
                <li><a href="STDM001.jsp" class="current">学生管理</a></li>
                <li><a>成績管理</a></li>
                <li><a href="GRMU001.jsp" class="current">成績登録</a></li>
                <li><a href="GRMR001.jsp" class="current">成績参照</a></li>
                <li><a href="SBJM001.jsp" class="current">科目管理</a></li>
            </ul>
        </div>

        <div class="main-content">
            <h2>科目管理</h2>
            <a href="SBJM002.jsp" class="new-subject-link">新規登録</a>

            <%
            // 科目データを格納するリスト
            List<Map<String, String>> subjects = new ArrayList<>();
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String errorMessage = null;

            try {
                // 1. JDBCドライバーのロード
                Class.forName("org.h2.Driver");

                // 2. データベース接続
                // ここはH2データベースの接続URLに置き換えてください。
                // 例: JDBC URL for an embedded database (データベースファイルはWEB-INF/data/testdb.mv.dbなど)
                // String DB_URL = "jdbc:h2:" + application.getRealPath("/WEB-INF/data/testdb");
                // 例: JDBC URL for a TCP server (H2サーバーが別途起動している場合)
                String DB_URL = "jdbc:h2:tcp://localhost/~/kaihatsu"; // H2のデフォルトTCPサーバーとデータベース名

                String USER = "sa"; // H2のデフォルトユーザー名
                String PASS = "";   // H2のデフォルトパスワード (空の場合が多い)

                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                // 3. SQLクエリの準備
                String sql = "SELECT SCHOOL_CD, CD, NAME FROM SUBJECT";
                pstmt = conn.prepareStatement(sql);

                // 4. クエリの実行
                rs = pstmt.executeQuery();

                // 5. 結果の取得
                while (rs.next()) {
                    Map<String, String> subject = new HashMap<>();
                    subject.put("cd", rs.getString("CD"));
                    subject.put("name", rs.getString("NAME"));
                    subjects.add(subject);
                }

            } catch (ClassNotFoundException e) {
                errorMessage = "H2 JDBCドライバーが見つかりません。WEB-INF/libにh2*.jarを配置してください。: " + e.getMessage();
                e.printStackTrace();
            } catch (SQLException e) {
                errorMessage = "データベースエラーが発生しました: " + e.getMessage();
                e.printStackTrace();
            } finally {
                // 6. リソースのクローズ
                try { if (rs != null) rs.close(); } catch (SQLException e) { /* log error */ }
                try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { /* log error */ }
                try { if (conn != null) conn.close(); } catch (SQLException e) { /* log error */ }
            }
            %>

            <% if (errorMessage != null) { %>
                <p class="error-message"><%= errorMessage %></p>
            <% } %>

            <table>
                <thead>
                    <tr>
                        <th>科目コード</th>
                        <th>科目名</th>
                        <th></th> <%-- For 変更 link --%>
                        <th></th> <%-- For 削除 link --%>
                    </tr>
                </thead>
                <tbody>
                    <%
                    // データベースから取得した科目データを表示
                    for (Map<String, String> subject : subjects) {
                    %>
                    <tr>
                        <td><%= subject.get("cd") %></td>
                        <td><%= subject.get("name") %></td>
                        <td><a href="${pageContext.request.contextPath}/main/subject_update?cd=<%= subject.get("cd") %>&name=<%= subject.get("name") %>">変更</a></td>
                        <td><a href="${pageContext.request.contextPath}/main/subject_delete?cd=<%= subject.get("cd") %>&name=<%= subject.get("name") %>">削除</a></td>
                    </tr>
                    <%
                    }
                    %>
                    <% if (subjects.isEmpty() && errorMessage == null) { %>
                    <tr>
                        <td colspan="4">科目データがありません。</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <div class="footer">
        </div>
    </div>
</body>
</html>