<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>成績管理システム - 成績管理</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        .container { width: 80%; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
        .search-form table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        .search-form th, .search-form td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }
        .search-form select, .search-form input[type="text"] { width: 150px; padding: 5px; }
        .search-form input[type="submit"] { padding: 8px 15px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .search-form input[type="submit"]:hover { background-color: #0056b3; }

        .grade-input-area h2 { margin-top: 30px; border-bottom: 1px solid #eee; padding-bottom: 10px; }
        .grade-input-area table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        .grade-input-area th, .grade-input-area td { padding: 8px; text-align: center; border: 1px solid #ddd; }
        .grade-input-area th { background-color: #f2f2f2; }
        .grade-input-area input[type="number"] { width: 80px; padding: 5px; text-align: center; }
        .register-button { text-align: right; margin-top: 20px; }
        .register-button input[type="submit"] { padding: 10px 20px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        .register-button input[type="submit"]:hover { background-color: #218838; }

        /* メニューのスタイル (簡易版) */
        .menu { float: left; width: 15%; padding-right: 20px; }
        .menu ul { list-style-type: none; padding: 0; }
        .menu li a { display: block; padding: 8px 0; text-decoration: none; color: #007bff; }
        .menu li a:hover { text-decoration: underline; }
        .content { margin-left: 17%; }
    </style>
</head>
<body>
    <div class="header">
        <h1>得点管理システム</h1>
        <p style="text-align: right;"> <a href="LOGO001.jsp">ログアウト</a></p>
        <hr/>
    </div>

    <div class="menu">
        <ul>
            <li><a href="MMNU001.jsp">メニュー</a></li>
            <li><a href="STDM001.jsp">学生管理</a></li>
            <li><a>成績管理</a></li> <%-- 現在のページ --%>
            <li><a href="GRMR001.jsp">成績参照</a></li>
            <li><a href="GRMU001.jsp">成績登録</a></li>
            <li><a href="SBJM001.jsp">科目管理</a></li>
        </ul>
    </div>

    <div class="content">
        <h2>成績管理</h2>

        <div class="search-form">
            <form action="gradeManagementList" method="get"> <%-- 仮のaction URL --%>
                <table>
                    <tr>
                        <th>入学年度</th>
                        <th>クラス</th>
                        <th>科目</th>
                        <th>回数</th>
                        <th></th> <%-- 検索ボタン用セル --%>
                    </tr>
                    <tr>
                        <td>
                            <select name="enrollmentYear">
                                <option value="2015" selected>2015</option>
                                <option value="2016">2016</option>
                                <option value="2017">2017</option>
                                <option value="2018">2018</option>
                                <option value="2019">2019</option>
                                <option value="2020">2020</option>
                                <option value="2021">2021</option>
                                <option value="2022">2022</option>
                                <option value="2023">2023</option>
                                <option value="2024">2024</option>
                                <option value="2025">2025</option>
                            </select>
                        </td>
                        <td>
                            <select name="classId">
                                <option value="131" selected>131</option>
                                <option value="132">132</option>
                                <option value="133">133</option>
                            </select>
                        </td>
                        <td>
                            <select name="subject">
                                <option value="Python1" selected>Python1</option>
                                <option value="Java2">Java2</option>
                                <option value="Database">Database</option>
                            </select>
                        </td>
                        <td>
                            <select name="attemptNo">
                                <option value="1" selected>1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                        </td>
                        <td>
                            <input type="submit" value="検索">
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div class="grade-input-area">
            <%
                // ここはJSPが実行されたときに、サーブレットから渡されるデータがあると想定します。
                // 例として、検索結果がある場合のみ表示するロジックをここに記述できます。
                // 例えば、request.getAttribute("students") != null など。
                // 今回はデモのため、常に表示されるようにします。

                // 仮の検索条件 (本来はリクエストパラメータから取得)
                String selectedSubject = request.getParameter("subject") != null ? request.getParameter("subject") : "Python1";
                String selectedAttemptNo = request.getParameter("attemptNo") != null ? request.getParameter("attemptNo") : "1";
            %>

            <h3>科目: <%= selectedSubject %> (<%= selectedAttemptNo %>回)</h3>

            <form action="registerGrades" method="post"> <%-- 仮のaction URL --%>
                <table>
                    <thead>
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>点数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            // ここに、サーブレット/JavaBeansから取得した学生データのリストをループして表示するロジックが入ります。
                            // 例: List<Student> students = (List<Student>) request.getAttribute("students");
                            //     if (students != null) {
                            //         for (Student student : students) {
                            //             // ... 以下の<tr>を生成 ...
                            //         }
                            //     } else {
                            //          // デモデータ
                            //     }
                        %>
                        <%-- デモ用の静的な学生データ --%>
                        <tr>
                            <td>2023</td>
                            <td>131</td>
                            <td>2325001</td>
                            <td>大原一郎1</td>
                            <td><input type="number" name="score_2325001" value="65" min="0" max="100"></td>
                        </tr>
                        <tr>
                            <td>2023</td>
                            <td>131</td>
                            <td>2325002</td>
                            <td>大原二郎</td>
                            <td><input type="number" name="score_2325002" value="2" min="0" max="100"></td>
                        </tr>
                        <tr>
                            <td>2023</td>
                            <td>131</td>
                            <td>2325003</td>
                            <td>大原三郎</td>
                            <td><input type="number" name="score_2325003" value="85" min="0" max="100"></td>
                        </tr>
                        <%-- 他の学生データが続く場合はここに追加 --%>
                    </tbody>
                </table>
                </div>
            </form>
        </div>
    </div>

    <div style="clear: both;"></div> <%-- Float解除 --%>
    <div class="footer" style="text-align: center; margin-top: 50px; font-size: 0.8em; color: #666;">
        <hr/>
        <p>&copy; 2023 TIC</p>
        <p>大原学園</p>
    </div>
</body>
</html>