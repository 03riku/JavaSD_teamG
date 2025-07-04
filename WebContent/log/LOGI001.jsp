<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>得点管理システム ログイン</title>
  <!-- Bootstrap CSS CDN -->
  <link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <style>
    body {
      background-color: #f8f9fa;
    }
    .login-container {
      max-width: 400px;
      margin: 80px auto;
      padding: 30px;
      background: white;
      border-radius: 8px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    .logo {
      margin-bottom: 20px;
      font-size: 1.5rem;
      font-weight: bold;
      text-align: center;
    }
    .form-check {
      margin-top: -10px;
    }
    footer {
      margin-top: 40px;
      text-align: center;
      color: #777;
      font-size: 0.9rem;
    }
    .error-message {
    	display: none;
    }
  </style>
</head>
<body>
  <div class="login-container">
    <div class="logo">得点管理システム</div>

    <%-- エラーメッセージ表示部分 --%>
    <%-- このdivはJavaScriptによって表示/非表示が切り替えられます --%>
    <div id="loginError" class="alert alert-danger error-message" role="alert">
        IDまたはパスワードが正しくありません。
    </div>

    <form id="loginForm" action="MMNU001" method="post">
      <div class="mb-3">
        <label for="id" class="form-label">ID</label>
        <input type="text" class="form-control" id="id" name="id"
               placeholder="" required>
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">パスワード</label>
        <input type="password" class="form-control" id="password"
               name="password" placeholder="" required>
      </div>
      <div class="form-check mb-3">
        <input type="checkbox" class="form-check-input" id="remember"
               name="remember" value="on">
        <label class="form-check-label" for="remember">パスワードを保存</label>
      </div>
      <button type="submit" class="btn btn-primary w-100">ログイン</button>
    </form>
    <footer>
    </footer>

    <script>
        // フォームが送信されたときの処理
        document.getElementById('loginForm').addEventListener('submit', function(event) {
            // ここで本来はサーバーサイドにデータを送信し、結果を受け取る
            // JSP単体で完結させるため、今回は常にエラーメッセージを表示する

            var errorMessageDiv = document.getElementById('loginError');
            errorMessageDiv.style.display = 'block'; // エラーメッセージを表示

            // 実際の認証ではないため、フォームの送信を一旦停止
            // サーバーサイドでの認証後にリダイレクトさせる場合は、event.preventDefault()を削除する
            event.preventDefault();
        });

        // ページロード時や、ID/パスワード入力欄が変更されたらエラーメッセージを非表示にする（オプション）
        document.getElementById('id').addEventListener('input', function() {
            document.getElementById('loginError').style.display = 'none';
        });
        document.getElementById('password').addEventListener('input', function() {
            document.getElementById('loginError').style.display = 'none';
        });
    </script>
  </div>
</body>
</html>