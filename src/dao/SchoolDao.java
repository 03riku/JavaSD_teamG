package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // SQLExceptionのインポートを追加
import java.util.ArrayList;
import java.util.List;

import Bean.School;

public class SchoolDao extends Dao {

    // すべての学校を取得
    public List<School> findAll() {
        List<School> list = new ArrayList<>();
        String sql = "SELECT * FROM SCHOOL";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                School school = new School();
                school.setCd(rs.getString("school_cd")); // カラム名を 'school_cd' に修正
                school.setName(rs.getString("school_name")); // カラム名を 'school_name' に修正
                list.add(school);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 学校コードで1件取得
    public School find(String cd) {
        School school = null;
        String sql = "SELECT * FROM SCHOOL WHERE cd = ?";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cd);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                school = new School();
                school.setCd(rs.getString("school_cd")); // カラム名を 'school_cd' に修正
                school.setName(rs.getString("school_name")); // カラム名を 'school_name' に修正
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return school;
    }

    /**
     * 新しい学校レコードをデータベースに挿入します。
     * @param school 挿入する学校データを含むSchoolオブジェクト。
     * @return 挿入が成功した場合はtrue、失敗した場合はfalse。
     * @throws Exception
     */
    public boolean insert(School school) throws Exception {
        boolean success = false;
        Connection con = null;
        PreparedStatement stmt = null;
        String sql = "INSERT INTO school (school_cd, school_name) VALUES (?, ?)";

        try {
            con = getConnection(); // データベース接続を取得
            stmt = con.prepareStatement(sql); // INSERT文をセット

            stmt.setString(1, school.getCd());   // 学校コードをセット
            stmt.setString(2, school.getName()); // 学校名をセット

            int rowsAffected = stmt.executeUpdate(); // SQLを実行し、影響を受けた行数を取得
            if (rowsAffected > 0) { // 1行以上影響があれば成功
                success = true;
            }

        } catch (SQLException e) { // SQLExceptionを具体的にキャッチ
            e.printStackTrace(); // エラーがあればコンソールに出力
        } finally {
            // リソースのクローズ処理を確実に行う
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // クローズ中のエラーも出力
            }
        }
        return success;
    }
}