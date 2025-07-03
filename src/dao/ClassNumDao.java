package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassNumDao extends Dao {

    public List<String> filter(String schoolCd) throws Exception {

        List<String> classNums = new ArrayList<>();

        // ★★★ 修正箇所: テーブル名を CLASS_NUM から STUDENT に戻す ★★★
        String sql = "SELECT DISTINCT CLASS_NUM FROM STUDENT WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";

        System.out.println("DEBUG (ClassNumDao.filter): SQL: " + sql); // デバッグログ追加
        System.out.println("DEBUG (ClassNumDao.filter): Parameter School CD: " + schoolCd); // デバッグログ追加

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, schoolCd);

            ResultSet rs = pstmt.executeQuery();

            System.out.println("DEBUG (ClassNumDao.filter): Query executed successfully. Fetching results..."); // デバッグログ追加

            while (rs.next()) {
                String classNum = rs.getString("CLASS_NUM");
                classNums.add(classNum);
                System.out.println("DEBUG (ClassNumDao.filter): Found ClassNum: " + classNum); // デバッグログ追加
            }

            System.out.println("DEBUG (ClassNumDao.filter): Total ClassNums found: " + classNums.size()); // デバッグログ追加

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR (ClassNumDao.filter): SQLException occurred: " + e.getMessage()); // エラーログ追加
            throw new Exception("クラス番号の取得中にデータベースエラーが発生しました。", e);
        }

        return classNums;
    }
}