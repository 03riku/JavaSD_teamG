<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        .register-button { margin-top: 20px; }
        .register-button input[type="submit"] {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        /* グレーボタンスタイル */
        .gray-button {
            background-color: #6c757d;
            color: white;
        }

        .gray-button:hover {
            background-color: #5a6268;
        }

        .menu { float: left; width: 15%; padding-right: 20px; }
        .menu ul { list-style-type: none; padding: 0; }
        .menu li a { display: block; padding: 8px 0; text-decoration: none; color: #007bff; }
        .menu li a:hover { text-decoration: underline; }
        .content { margin-left: 17%; }

        /* メッセージ表示用のスタイル */
        .message {
            color: red;
            font-weight: bold;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>得点管理システム</h1>
        <%-- LOGO001.jsp も log ディレクトリにあるため、絶対パスに修正 --%>
        <p style="text-align: right;"> <a href="${pageContext.request.contextPath}/log/LOGO001.jsp">ログアウト</a></p>
        <hr/>
    </div>

    <div class="menu">
        <ul>
            <%-- MMNU001.jsp も log ディレクトリにあるため、絶対パスに修正 --%>
            <li><a href="${pageContext.request.contextPath}/log/MMNU001.jsp">メニュー</a></li>
            <%-- STDM001.jsp も log ディレクトリにあるため、絶対パスに修正 --%>
            <li><a href="${pageContext.request.contextPath}/log/STDM001.jsp">学生管理</a></li>
            <li><a>成績管理</a></li> <%-- この「成績管理」はリンクになっていません --%>
            <%-- GRMR001.jsp も log ディレクトリにあるため、絶対パスに修正 --%>
            <li><a href="${pageContext.request.contextPath}/log/GRMR001.jsp">成績参照</a></li>
            <%-- TestListSubjectExecute.action はサーブレットなので変更なし --%>
            <li><a href="${pageContext.request.contextPath}/TestListSubjectExecute.action">成績登録</a></li>
            <%-- SBJM001.jsp も log ディレクトリにあるため、絶対パスに修正 --%>
            <li><a href="${pageContext.request.contextPath}/log/SBJM001.jsp">科目管理</a></li>
        </ul>
    </div>

    <div class="content">
        <h2>成績管理</h2>

        <%-- Controllerから渡されたメッセージがあれば表示 --%>
        <c:if test="${not empty message}">
            <p class="message">${message}</p>
        </c:if>

        <div class="search-form">
            <%-- 検索フォームの action 属性はサーブレットなので変更なし --%>
            <form action="${pageContext.request.contextPath}/TestListSubjectExecute.action" method="get">
                <table>
                    <tr>
                        <th>入学年度</th><th>クラス</th><th>科目</th><th>回数</th><th></th>
                    </tr>
                    <tr>
                        <td>
                            <select name="year"> <%-- name属性を "year" に修正 --%>
                                <option value="">選択してください</option> <%-- 初期値として空の選択肢を追加 --%>
                                <c:forEach var="entYearOption" items="${entYears}">
                                    <%-- Controllerから渡されたselectedEntYearと一致する場合にselected属性を付与 --%>
                                    <option value="${entYearOption}" ${selectedEntYear == entYearOption ? 'selected' : ''}>${entYearOption}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select name="class_num"> <%-- name属性を "class_num" に修正 --%>
                                <option value="">選択してください</option> <%-- 初期値として空の選択肢を追加 --%>
                                <c:forEach var="classOption" items="${classNums}">
                                    <%-- Controllerから渡されたselectedClassNumと一致する場合にselected属性を付与 --%>
                                    <option value="${classOption}" ${selectedClassNum == classOption ? 'selected' : ''}>${classOption}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select name="subject"> <%-- name属性を "subject" に修正 --%>
                                <option value="">選択してください</option> <%-- 初期値として空の選択肢を追加 --%>
                                <c:forEach var="subjectOption" items="${subjects}">
                                    <%-- Controllerから渡されたselectedSubjectCdと一致する場合にselected属性を付与 --%>
                                    <option value="${subjectOption.cd}" ${selectedSubjectCd == subjectOption.cd ? 'selected' : ''}>${subjectOption.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select name="num"> <%-- name属性を "num" に修正 (Controllerで使うなら) --%>
                                <option value="1" ${selectedNum == '1' ? 'selected' : ''}>1</option>
                                <option value="2" ${selectedNum == '2' ? 'selected' : ''}>2</option>
                            </select>
                        </td>
                        <td>
                            <input type="submit" value="検索">
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <%-- testsリストはTestListSubjectExecuteControllerから渡される想定 --%>
        <c:if test="${not empty tests}">
            <div class="grade-input-area">
                <%-- Controllerから渡されたsubjectオブジェクトの情報を表示 --%>
                <h3>科目: ${selectedSubjectCd}（${selectedNum}回）</h3> <%-- 表示内容を修正 --%>

                <%-- 成績登録用のフォーム。actionは別途成績登録処理用のサーブレットになる想定 --%>
                <form action="${pageContext.request.contextPath}/RegisterGradesExecute.action" method="post"> <%-- actionを絶対パスに修正 --%>
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
                            <%-- Testオブジェクトのリストをループして表示 --%>
                            <c:forEach var="test" items="${tests}">
                                <tr>
                                    <td>${test.student.entYear}</td>
                                    <td>${test.classNum}</td>
                                    <td>${test.student.no}</td>
                                    <td>${test.student.name}</td>
                                    <td>
                                        <%-- 点数入力フィールドのname属性はユニークになるように studentNo を含める --%>
                                        <input type="hidden" name="studentNo" value="${test.student.no}"> <%-- 学生番号をhiddenで送信 --%>
                                        <input type="number" name="score_${test.student.no}" value="${test.point}" min="0" max="100">
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="register-button" style="text-align: left;">
                        <input type="submit" value="登録して終了" class="gray-button">
                    </div>
                </form>
            </div>
        </c:if>
    </div>

    <div style="clear: both;"></div>
    <div class="footer" style="text-align: center; margin-top: 50px; font-size: 0.8em; color: #666;">
        <hr>
    </div>
</body>
</html>