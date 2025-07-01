<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>得点管理システム - 学生情報変更</title>
    <style>
        /* 全体のスタイル */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4; /* 全体の背景色 */
            color: #333;
        }

        /* ヘッダーのスタイル */
        .header {
            background-color: #e6f0fc; /* ヘッダーの背景色 */
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
            white-space: nowrap;
        }
        .user-info a {
            color: #007bff;
            text-decoration: none;
            margin-left: 10px;
        }
        .user-info a:hover {
            text-decoration: underline;
        }

        /* コンテナ（サイドバーとメインコンテンツ）のスタイル */
        .container {
            display: flex;
            /* ヘッダーの高さが41pxと仮定して、ビューポートから引く */
            min-height: calc(100vh - 41px);
            background-color: #fff;
            box-sizing: border-box;
        }

        /* サイドバーのスタイル */
        .sidebar {
            width: 200px;
            padding: 20px 0;
            border-right: 1px solid #e0e0e0;
            background-color: #fdfdfd;
            box-shadow: 2px 0 5px rgba(0,0,0,0.03);
        }

        .sidebar ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .sidebar ul li {
            margin-bottom: 5px;
        }

        .sidebar ul li a {
            text-decoration: none;
            color: #007bff;
            display: block;
            padding: 8px 25px;
            font-size: 14px;
            transition: background-color 0.2s ease, color 0.2s ease;
        }

        .sidebar ul li a:hover {
            background-color: #e0f2ff;
            color: #0056b3;
        }

        /* アクティブなリンクのスタイル */
        .sidebar ul li a.active {
            background-color: #dbeaff;
            color: #0056b3;
            font-weight: bold;
        }

        /* サブメニューのスタイル */
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
            background-color: #fff; /* メインコンテンツの背景は白のまま */
        }

        /* セクションのスタイル */
        .section {
            background-color: #f8f8f8; /* 学生情報変更の背景色を薄いグレーに変更 */
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

        /* 完了メッセージのスタイル */
        .completion-message {
            background-color: #a6d1a6; /* 緑系の背景色を濃く変更 */
            color: #155724;
            padding: 10px 15px;
            border-radius: 4px;
            margin-top: 20px;
            text-align: center;
            font-size: 14px;
            border: 1px solid #94b994; /* ボーダーの色も調整 */
        }

        /* 学生一覧リンクのスタイル */
        .student-list-link-container {
            margin-top: 20px;
        }
        .student-list-link-container a {
            color: #007bff;
            text-decoration: none;
            font-size: 14px;
        }
        .student-list-link-container a:hover {
            text-decoration: underline;
        }

    </style>
</head>
<body>

    <div class="header">
        <h1>得点管理システム</h1>
        <div class="user-info">
            大原 太郎様 <a href="${pageContext.request.contextPath}/log/LOGO001.jsp">ログアウト</a>
        </div>
    </div>

    <div class="container">
        <div class="sidebar">
            <ul>
                <li><a href="${pageContext.request.contextPath}/log/MMNU001.jsp">メニュー</a></li>
                <li><a href="${pageContext.request.contextPath}/log/STDM001.jsp" class="active">学生管理</a></li>
                <li>
                    <a>成績管理</a>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/log/GRMU001.jsp">成績登録</a></li>
                        <li><a href="${pageContext.request.contextPath}/log/GRMR001.jsp">成績参照</a></li>
                    </ul>
                </li>
                <li><a href="${pageContext.request.contextPath}/log/SBJM001.jsp">科目管理</a></li>
            </ul>
        </div>

        <div class="main-content">
            <div class="section">
                <div class="section-title">学生情報変更</div>

                <div class="completion-message">
                    変更が完了しました
                </div>

                <div class="student-list-link-container">
                    <a href="${pageContext.request.contextPath}/log/STDM001.jsp">学生一覧</a>
                </div>

            </div>
        </div>
    </div>

</body>
</html>