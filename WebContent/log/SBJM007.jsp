<<<<<<< HEAD
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Teacher BeanとSchool Beanがセッションスコープに保存されていることを前提とします --%>
<%-- 例: session.setAttribute("teacher", teacherObject); --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科目削除完了</title>
    <%-- スタイルシートは外部ファイルで読み込むか、ここに記述しない --%>
    <%-- 例: <link rel="stylesheet" href="css/style.css"> --%>
</head>
<body>
    <table width="100%" cellpadding="5" cellspacing="0" border="0">
        <tr>
            <td align="left">得点管理システム</td>
            <td align="right">
                <%-- ユーザー情報とログアウトリンクの表示 --%>
                <c:if test="${not empty teacher}">
                    ${teacher.school.name} ${teacher.name}様&nbsp;
                </c:if>
                <a href="LOGO001.jsp">ログアウト</a>
            </td>
        </tr>
    </table>

    <div style="float:left; width:15%; height:100vh; border-right:2px solid black; padding:10px;">
        <ul>
            <li><a href="MMNU001.jsp">メニュー</a></li>
            <li><a href="STDM001.jsp">学生管理</a></li>
            <li><label>成績管理</label></li>
            <li><a href="GRMU001.jsp">成績登録</a><br></li>
            <li><a href="GRMR001.jsp">成績検索</a></li>
            <li><a href="SRJM002.jsp">科目管理</a></li>
        </ul>
    </div>

    <%-- メインコンテンツのタイトル --%>
    <h2>科目情報削除</h2> <%-- ① 科目情報削除 --%>

    <%-- 削除完了メッセージ --%>
    <div class="success-message-box"> <%-- このクラスにCSSを適用してください --%>
        削除が完了しました
    </div> <%-- ② 削除が完了しました --%>

    <a href="SRJM001.jsp">科目一覧</a> <%-- ③ 科目一覧 (画像に表示されているリンク) --%>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>得点管理システム - 科目情報削除</title>
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
            box-sizing: border-box; /* paddingとborderをwidth/heightに含める */
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

        /* サブメニューのスタイル */
        .sidebar ul li ul {
            padding-left: 30px; /* サブメニューのインデント */
            margin-top: 5px;
        }

        .sidebar ul li ul li {
            margin-bottom: 0;
        }

        .sidebar ul li ul li a {
            color: #666; /* サブメニューのリンク色 */
            font-size: 13px;
            padding: 5px 10px; /* サブメニューのパディング */
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
            background-color: #f8f8f8; /* 学生情報変更の背景色と同じ薄いグレー */
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
            background-color: #a6d1a6; /* 緑系の背景色 */
            color: #155724; /* 緑系の文字色 */
            padding: 10px 15px;
            border-radius: 4px;
            margin-top: 20px;
            text-align: center; /* 中央寄せ */
            font-size: 14px;
            border: 1px solid #94b994; /* 緑系のボーダー */
        }

        /* 科目一覧リンクのスタイル */
        .subject-list-link-container { /* クラス名を変更 */
            margin-top: 30px; /* ここを20pxから30pxに変更 */
        }
        .subject-list-link-container a {
            color: #007bff;
            text-decoration: none;
            font-size: 14px;
        }
        .subject-list-link-container a:hover {
            text-decoration: underline;
        }

    </style>
</head>
<body>

    <div class="header">
        <h1>得点管理システム</h1>
        <div class="user-info">
            大原 太郎様 <a href="LOGO001.jsp">ログアウト</a>
        </div>
    </div>

    <div class="container">
        <div class="sidebar">
            <ul>
                <li><a href="MMNU001.jsp">メニュー</a></li>
                <li><a href="STDM001.jsp">学生管理</a></li>
                <li>
                    <a>成績管理</a>
                    <ul>
                        <li><a href="GRMU001.jsp">成績登録</a></li>
                        <li><a href="GRMR001.jsp">成績参照</a></li>
                    </ul>
                </li>
                <li><a href="SRJM002.jsp" class="active">科目管理</a></li> </ul>
        </div>

        <div class="main-content">
            <div class="section">
                <div class="section-title">科目情報削除</div> <div class="completion-message">
                    削除が完了しました
                </div>

                <div class="subject-list-link-container">
                    <a href="SBJM001.jsp">科目一覧</a>
                </div>

            </div>
        </div>
    </div>
</body>
</html>