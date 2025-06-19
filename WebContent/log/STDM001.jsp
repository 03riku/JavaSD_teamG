<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>得点管理システム - 学生管理一覧</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
        }
        .sidebar {
            width: 200px;
            background-color: #f0f0f0;
            height: 100vh;
            float: left;
            padding: 20px;
            box-sizing: border-box;
        }
        .sidebar a {
            display: block;
            margin-bottom: 15px;
            text-decoration: none;
            color: black;
        }
        .main-content {
            margin-left: 200px;
            padding: 20px;
        }
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 5px 10px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <h3>メニュー</h3>
        <a href="STDM001.jsp">学生管理</a>
        <a href="TESTM001.jsp">成績管理</a>
    </div>

    <div class="main-content">
        <h2>得点管理システム</h2>
        <div>
            <span>ようこそ <strong>○○高校 教員</strong> さん</span> |
            <a href="logout.jsp">ログアウト</a>
        </div>

        <h3>学生管理</h3>

        <form action="STDM001" method="get">
            <label for="entYear">入学年度：</label>
            <select name="entYear" id="entYear">
                <option value="2021">2021</option>
                <option value="2022">2022</option>
                <option value="2023">2023</option>
            </select>

            <label for="class">クラス：</label>
            <select name="class" id="class">
                <option value="">--</option>
                <option value="1">1組</option>
                <option value="2">2組</option>
                <option value="3">3組</option>
            </select>

            <label>在学中：</label>
            <input type="checkbox" name="isAttend" id="isAttend" checked>

            <button type="submit">絞り込み</button>
        </form>

        <br>

        <table>
            <thead>
                <tr>
                    <th>入学年度</th>
                    <th>学籍番号</th>
                    <th>氏名</th>
                    <th>高校名</th>
                    <th>学年</th>
                    <th>クラス</th>
                    <th>在学</th>
                    <th>詳細</th>
                </tr>
            </thead>
            <tbody>
                <!-- JSTLで繰り返し -->
                <tr>
                    <td>2021</td>
                    <td>2123456</td>
                    <td>山田 太郎</td>
                    <td>大阪 第一高校</td>
                    <td>3</td>
                    <td>1</td>
                    <td><input type="radio" checked></td>
                    <td><a href="STDM002?studentId=2123456">詳細</a></td>
                </tr>
                <!-- 条件に該当しない場合 -->
                <!-- <tr><td colspan="8">※絞り込み条件に該当する学生情報がありません</td></tr> -->
            </tbody>
        </table>
    </div>
</body>
</html>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>得点管理システム - 学生管理</title>
    <style>
        /* 全体のスタイル */
        body {
            font-family: Arial, sans-serif; /* 画像に近いフォント */
            margin: 0;
            padding: 0;
            background-color: #f4f4f4; /* 全体の背景色 */
            color: #333;
        }

        /* ヘッダーのスタイル */
        .header {
            background-color: #e6f0fc; /* ヘッダーの背景色 */
            padding: 10px 20px; /* パディング */
            border-bottom: 1px solid #c0d9ef; /* ヘッダー下部の薄いボーダー */
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05); /* 軽い影 */
        }

        .header h1 {
            font-size: 24px;
            margin: 0;
            color: #333; /* H1の文字色 */
        }

        .user-info {
            font-size: 14px;
            color: #555;
            white-space: nowrap;
        }
        .user-info a {
            color: #007bff; /* ログアウトリンクの色 */
            text-decoration: none;
            margin-left: 10px;
        }
        .user-info a:hover {
            text-decoration: underline;
        }

        /* コンテナ（サイドバーとメインコンテンツ）のスタイル */
        .container {
            display: flex;
            min-height: calc(100vh - 41px); /* ヘッダーの高さを考慮 (約41px) */
            background-color: #fff;
            box-sizing: border-box;
        }

        /* サイドバーのスタイル */
        .sidebar {
            width: 200px; /* 幅 */
            padding: 20px 0; /* 上下のパディング */
            border-right: 1px solid #e0e0e0; /* 右ボーダー */
            background-color: #fdfdfd; /* 背景色 */
            box-shadow: 2px 0 5px rgba(0,0,0,0.03); /* 軽い影 */
        }

        .sidebar ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .sidebar ul li {
            margin-bottom: 5px; /* メニュー項目の間隔 */
        }

        .sidebar ul li a {
            text-decoration: none;
            color: #007bff; /* メインメニューのリンク色 */
            display: block;
            padding: 8px 25px; /* パディング */
            font-size: 14px;
            transition: background-color 0.2s ease, color 0.2s ease;
        }

        .sidebar ul li a:hover {
            background-color: #e0f2ff; /* ホバー時の背景色 */
            color: #0056b3; /* ホバー時の文字色 */
        }

        /* アクティブなリンクのスタイル */
        .sidebar ul li a.active {
            background-color: #dbeaff; /* アクティブ時の背景色 */
            color: #0056b3; /* アクティブ時の文字色 */
            font-weight: bold;
        }

        /* サブメニューのスタイル（この画面では使用されないが、全体のスタイルとして残しておく） */
        .sidebar ul li ul {
            padding-left: 30px;
            margin-top: 5px;
        }

        .sidebar ul li ul li {
            margin-bottom: 0;
        }

        .sidebar ul li ul li a {
            color: #666;
            font-size: 13px;
            padding: 5px 10px;
        }

        .sidebar ul li ul li a:hover {
            background-color: #f0f6ff;
        }
        .sidebar ul li ul li a.active {
            background-color: #e0f2ff;
            color: #333;
            font-weight: bold;
        }


        /* メインコンテンツのスタイル */
        .main-content {
            flex-grow: 1;
            padding: 20px 30px;
            background-color: #fff;
        }

        /* セクションのスタイル */
        .section {
            background-color: #f8f8f8; /* 背景色を薄いグレーに */
            padding: 20px 25px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }

        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }

        .section-title {
            font-size: 20px;
            color: #333;
            font-weight: bold;
            margin: 0;
        }

        /* 新規登録ボタンのスタイル */
        .new-registration-button {
            background-color: #007bff; /* 青系の色 */
            color: white;
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
            transition: background-color 0.2s ease, transform 0.1s ease;
            white-space: nowrap;
            text-decoration: none; /* aタグの場合に下線を消す */
            display: inline-block; /* aタグの場合にpaddingが効くように */
        }
        .new-registration-button:hover {
            background-color: #0056b3;
            transform: translateY(-1px);
        }
        .new-registration-button:active {
            transform: translateY(0);
        }

        /* 絞り込みフォームのスタイル */
        .filter-form {
            display: flex;
            align-items: center;
            gap: 15px; /* 要素間の隙間 */
            margin-bottom: 20px;
            flex-wrap: wrap; /* 小さい画面で折り返す */
        }
        .filter-form label {
            font-size: 14px;
            color: #555;
            white-space: nowrap;
        }
        .filter-form select {
            padding: 8px 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
            max-width: 150px; /* セレクトボックスの幅を調整 */
            min-width: 100px; /* 最小幅 */
        }
        .filter-form input[type="checkbox"] {
            transform: scale(1.2); /* チェックボックスのサイズを少し大きくする */
            margin-right: 5px; /* ラベルとの間隔 */
        }

        /* 絞込みボタンのスタイル */
        .filter-button {
            background-color: #4f4f4f; /* 濃いグレー */
            color: white;
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
            transition: background-color 0.2s ease, transform 0.1s ease;
            white-space: nowrap;
            margin-left: auto; /* 右に寄せる */
        }
        .filter-button:hover {
            background-color: #3a3a3a;
            transform: translateY(-1px);
        }
        .filter-button:active {
            transform: translateY(0);
        }

        /* 検索結果件数表示のスタイル */
        .search-results-info {
            font-size: 14px;
            color: #555;
            margin-bottom: 10px;
        }

        /* 学生情報テーブルのスタイル */
        .student-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
            background-color: #fff; /* テーブルの背景色 */
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }
        .student-table th,
        .student-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
            font-size: 14px;
        }
        .student-table th {
            background-color: #e6f0fc; /* ヘッダーと同じような背景色 */
            color: #333;
            font-weight: bold;
            white-space: nowrap;
        }
        .student-table td {
            background-color: #fff;
        }
        .student-table tr:nth-child(even) td { /* 偶数行の背景色を少し変える */
            background-color: #f9f9f9;
        }
        .student-table tr:hover td {
            background-color: #f0f6ff; /* ホバー時の背景色 */
        }

        .student-table td.center-text {
            text-align: center; /* 在学中/休学中のラジオボタンの列を中央寄せ */
        }
        .student-table td a {
            color: #007bff; /* 変更リンクの色 */
            text-decoration: none;
        }
        .student-table td a:hover {
            text-decoration: underline;
        }

        /* 学生情報なしメッセージのスタイル */
        .no-student-info-message {
            margin-top: 20px;
            padding: 15px;
            background-color: #ffe0e0; /* 赤系の背景色（エラーっぽい色） */
            border: 1px solid #ffb3b3;
            border-radius: 5px;
            color: #cc0000;
            text-align: center;
            font-size: 15px;
            font-weight: bold;
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
            <ul>
                <li><a href="#">メニュー</a></li>
                <li><a href="#" class="active">学生管理</a></li>
                <li>
                    <a href="#">成績管理</a>
                    <ul>
                        <li><a href="#">成績登録</a></li>
                        <li><a href="#">成績参照</a></li>
                    </ul>
                </li>
                <li><a href="#">科目管理</a></li>
            </ul>
        </div>

        <div class="main-content">
            <div class="section">
                <div class="section-header">
                    <h2 class="section-title">学生管理</h2>
                    <a href="#" class="new-registration-button">新規登録</a>
                </div>

                <form id="filterForm" onsubmit="event.preventDefault(); filterStudents();">
                    <div class="filter-form">
                        <label for="filterYear">入学年度</label>
                        <select id="filterYear" name="filterYear">
                            <option value="">------</option>
                            <option value="2021">2021</option>
                            <option value="2022">2022</option>
                            <option value="2023">2023</option>
                            <option value="2024">2024</option>
                        </select>

                        <label for="filterClass">クラス</label>
                        <select id="filterClass" name="filterClass">
                            <option value="">------</option>
                            <option value="201">201</option>
                            <option value="202">202</option>
                            <option value="203">203</option>
                        </select>

                        <label for="filterActive">在学中</label> <input type="checkbox" id="filterActive" name="filterActive" value="true"> <button type="submit" class="filter-button">絞込み</button>
                    </div>
                </form>

                <div id="searchResultsContainer">
                    <div class="search-results-info" id="searchResultsCount">検索結果：5件</div>
                    <table class="student-table" id="studentTable">
                        <thead>
                            <tr>
                                <th>入学年度</th>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>クラス</th>
                                <th>在学中</th>
                                <th></th> </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>2021</td>
                                <td>2125001</td>
                                <td>大原 一郎</td>
                                <td>201</td>
                                <td class="center-text">〇</td>
                                <td><a href="#">変更</a></td>
                            </tr>
                            <tr>
                                <td>2021</td>
                                <td>2125002</td>
                                <td>大原 花子</td>
                                <td>201</td>
                                <td class="center-text">〇</td>
                                <td><a href="#">変更</a></td>
                            </tr>
                            <tr>
                                <td>2021</td>
                                <td>2125003</td>
                                <td>大原 良子</td>
                                <td>201</td>
                                <td class="center-text">〇</td>
                                <td><a href="#">変更</a></td>
                            </tr>
                            <tr>
                                <td>2021</td>
                                <td>2125004</td>
                                <td>大原 二郎</td>
                                <td>201</td>
                                <td class="center-text">〇</td>
                                <td><a href="#">変更</a></td>
                            </tr>
                             <tr>
                                <td>2021</td>
                                <td>2125005</td>
                                <td>大原 三郎</td>
                                <td>202</td>
                                <td class="center-text">〇</td>
                                <td><a href="#">変更</a></td>
                            </tr>
                            <tr>
                                <td>2022</td>
                                <td>2225001</td>
                                <td>休学中 太郎</td>
                                <td>201</td>
                                <td class="center-text">－</td> <td><a href="#">変更</a></td>
                            </tr>
                            <tr>
                                <td>2023</td>
                                <td>2325002</td>
                                <td>休学中 花子</td>
                                <td>203</td>
                                <td class="center-text">－</td> <td><a href="#">変更</a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="no-student-info-message" style="display: none;">
                    学生情報が存在しませんでした
                </div>

            </div>
        </div>
    </div>

    <script>
        function filterStudents() {
            const filterYear = document.getElementById('filterYear').value;
            const filterClass = document.getElementById('filterClass').value;
            const filterActive = document.getElementById('filterActive').checked; // true または false を取得

            const studentTable = document.getElementById('studentTable');
            const searchResultsCount = document.getElementById('searchResultsCount');
            const noStudentInfoMessage = document.querySelector('.no-student-info-message');
            const tableBody = studentTable.querySelector('tbody');

            // 一旦全ての行を非表示にする
            const rows = tableBody.getElementsByTagName('tr');
            for (let i = 0; i < rows.length; i++) {
                rows[i].style.display = 'none';
            }

            let filteredCount = 0;

            // フィルタリングロジック
            document.querySelectorAll('#studentTable tbody tr').forEach(row => {
                const year = row.cells[0].innerText;
                const studentClass = row.cells[3].innerText;
                // テーブルの「在学中」列が'〇'であれば在学中、そうでなければ休学中と判断
                const isActiveStudent = row.cells[4].innerText === '〇';

                let matches = true;

                // 入学年度でフィルタリング
                if (filterYear !== '' && year !== filterYear) {
                    matches = false;
                }
                // クラスでフィルタリング
                if (filterClass !== '' && studentClass !== filterClass) {
                    matches = false;
                }

                // 在学中チェックボックスでのフィルタリングロジック
                if (filterActive) {
                    // チェックが入っている場合: 在学中の学生のみを表示
                    if (!isActiveStudent) { // 在学中でない場合は非表示
                        matches = false;
                    }
                } else {
                    // チェックが入っていない場合: 休学中の学生のみを表示
                    if (isActiveStudent) { // 在学中の学生であれば非表示
                        matches = false;
                    }
                }

                if (matches) {
                    row.style.display = ''; // 表示する
                    filteredCount++;
                }
            });

            if (filteredCount > 0) {
                searchResultsCount.innerText = `検索結果：${filteredCount}件`;
                searchResultsCount.style.display = '';
                studentTable.style.display = '';
                noStudentInfoMessage.style.display = 'none';
            } else {
                searchResultsCount.style.display = 'none';
                studentTable.style.display = 'none';
                noStudentInfoMessage.style.display = ''; // メッセージを表示
            }
        }

        // ページ読み込み時に初期フィルタリングを実行
        // 初期状態では「在学中」チェックボックスはチェックなしなので、休学中の学生のみが表示されます
        document.addEventListener('DOMContentLoaded', () => {
            filterStudents();
        });
    </script>
</body>
</html>
>>>>>>> branch 'master' of https://github.com/03riku/JavaSD_teamG.git
