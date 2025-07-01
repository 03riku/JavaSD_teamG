package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.School;
import Bean.Subject;

public class SubjectDao extends Dao {

    /** すべての科目を取得 */
    public List<Subject> findAll() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT cd, name, school_cd FROM subject ORDER BY school_cd, cd";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));
                School school = new School();
                school.setCd(rs.getString("school_cd"));
                subject.setSchool(school);
                list.add(subject);
            }

        } catch (SQLException e) {  // SQLExceptionを使うことで例外処理が明確になります
            e.printStackTrace();
        }

        return list;
    }

    /** 科目コード + 学校コードで取得 */
    public Subject find(String cd, String schoolCd) {
        Subject subject = null;
        String sql = "SELECT cd, name, school_cd FROM subject WHERE cd = ? AND school_cd = ?";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cd);
            stmt.setString(2, schoolCd);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    subject = new Subject();
                    subject.setCd(rs.getString("cd"));
                    subject.setName(rs.getString("name"));
                    School school = new School();
                    school.setCd(rs.getString("school_cd"));
                    subject.setSchool(school);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subject;
    }

    /** 新規登録 */
    public boolean insert(Subject subject) {
        boolean success = false;
        String sql = "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, subject.getCd());
            stmt.setString(2, subject.getName());
            stmt.setString(3, subject.getSchool().getCd());
            success = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /** 更新 */
    public boolean update(Subject subject) {
        boolean success = false;
        String sql = "UPDATE subject SET name = ? WHERE cd = ? AND school_cd = ?";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, subject.getName());
            stmt.setString(2, subject.getCd());
            stmt.setString(3, subject.getSchool().getCd());
            success = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /** 存在すれば更新、なければ新規登録 */
    public boolean save(Subject subject) {
        Subject exists = find(subject.getCd(), subject.getSchool().getCd());
        if (exists == null) {
            return insert(subject);
        } else {
            return update(subject);
        }
    }

    /** 削除 */
    public boolean delete(Subject subject) {
        boolean success = false;
        String sql = "DELETE FROM subject WHERE cd = ? AND school_cd = ?";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, subject.getCd());
            stmt.setString(2, subject.getSchool().getCd());
            success = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}
