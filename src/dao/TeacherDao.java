package dao;

import java.sql.*;
import java.util.Optional;
import model.Teacher;
import util.DbUtil; // 例：コネクション取得ユーティリティ

public class TeacherDao extends Dao {

    /** IDで教師情報を取得 */
    public Optional<Teacher> get(String id) {
        String sql = "SELECT id, password, name, school_id FROM teacher WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Teacher t = new Teacher(
                        rs.getString("id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("school_id")
                    );
                    return Optional.of(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /** ID／パスワードでログイン（認証） */
    public Optional<Teacher> login(String id, String password) {
        String sql = "SELECT id, name, school_id FROM teacher WHERE id = ? AND password = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Teacher t = new Teacher(
                        rs.getString("id"),
                        null, // パスワードは不要
                        rs.getString("name"),
                        rs.getString("school_id")
                    );
                    return Optional.of(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
