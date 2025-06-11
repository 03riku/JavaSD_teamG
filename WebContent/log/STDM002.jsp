<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <title>学生追加</title>
</head>
<body style="margin:0; padding:0;">
    <!-- 共通メニューの読み込み -->
    <jsp:include page="MMNU001.jsp" />

    <div style="margin-left:17%; padding:20px;">
    <p>追加する学生の情報を入力してください。</p>
    　<form action="<%= request.getContextPath() %>/StudentAdd" method="post">
        <table>
           <tr>
             <td>学生番号:</td>
             <td><input type="number" name="studentId" required></td>
           </tr>
             <tr>
              <td>学生名:</td>
              <td><input type="text" name="studentName" required></td>
           </tr>
             <tr>
               <td>コース名:</td>
               <td>
                 <select name="courseId" required>
                   <option value="1">システム開発コース</option>
                   <option value="2">ネットワークセキュリティコース</option>
                   <option value="3">AIシステム・データサイエンスコース</option>
                 </select>
               </td>
             </tr>
              </table>
              <input type="submit" value="送信">
              </form>
              </div>
</body>
</html>
