package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassNumDao extends Dao {

    public List<String> filter(String school) throws Exception {
        List<String> classNums = new ArrayList<>();

        String sql = "SELECT DISTINCT classNum FROM Test WHERE school = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, school);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                classNums.add(rs.getString("classNum"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 適切なログに置き換えてください
        }

        return classNums;
    }
}
