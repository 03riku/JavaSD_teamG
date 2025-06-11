package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import model.Teacher;
import util.DbUtil;

public class TeacherDao extends Dao {

    /**
     * ID で教師を取得。
     * SQLException を呼び出し元に伝播。
     */
    public Optional<Teacher> get(String id) throws SQLException {
        String sql = "SELECT id, password, name, school_id FROM teacher WHERE id = ?";
        Connection conn = DbUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(new Teacher(
                rs.getString("id"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("school_id")
            ));
        }
        return Optional.empty();
    }

    /**
     * ID／パスワードでログイン認証。
     * SQLException を上位に伝える。
     */
    public Optional<Teacher> login(String id, String password) throws SQLException {
        String sql = "SELECT id, name, school_id FROM teacher WHERE id = ? AND password = ?";
        Connection conn = DbUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(new Teacher(
                rs.getString("id"),
                null,
                rs.getString("name"),
                rs.getString("school_id")
            ));
        }
        return Optional.empty();
    }
}
