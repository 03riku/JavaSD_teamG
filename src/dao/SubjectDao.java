package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Bean.School;
import Bean.Subject;

public class SubjectDao extends Dao {

    private String baseSql;

    public SubjectDao() {
        // subjectテーブルの基本SELECT文
        this.baseSql = "SELECT CD, NAME, SCHOOL_CD FROM SUBJECT";
    }

    /** 科目を cd＋schoolCd で取得 */
    public Subject get(String cd, School school) throws Exception {
        String sql = baseSql + " WHERE CD = ? AND SCHOOL_CD = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cd);
            ps.setString(2, school.getCd());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    School s = new School();
                    s.setCd(rs.getString("SCHOOL_CD"));

                    Subject subject = new Subject();
                    subject.setCd(rs.getString("CD"));
                    subject.setName(rs.getString("NAME"));
                    subject.setSchool(s);

                    return subject;
                } else {
                    return null;
                }
            }
        }
    }

    /** 学校に紐づく全科目を取得 */
    public List<Subject> filter(School school) throws Exception {
        String sql = baseSql + " WHERE SCHOOL_CD = ? ORDER BY CD";
        List<Subject> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, school.getCd());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    School s = new School();
                    s.setCd(rs.getString("SCHOOL_CD"));

                    Subject subject = new Subject();
                    subject.setCd(rs.getString("CD"));
                    subject.setName(rs.getString("NAME"));
                    subject.setSchool(s);
                    list.add(subject);
                }
            }
        }
        return list;
    }

    /** 存在すれば更新、なければ挿入 */
    public boolean save(Subject subject) throws Exception {
        Subject exists = get(subject.getCd(), subject.getSchool());
        if (exists == null) {
            return insert(subject);
        } else {
            return update(subject);
        }
    }

    /** 削除 */
    public boolean delete(Subject subject) throws Exception {
        String sql = "DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, subject.getCd());
            ps.setString(2, subject.getSchool().getCd());
            return ps.executeUpdate() > 0;
        }
    }

    /** 挿入処理 */
    private boolean insert(Subject subject) throws Exception {
        String sql = "INSERT INTO SUBJECT (CD, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, subject.getCd());
            ps.setString(2, subject.getName());
            if (subject.getSchool() != null && subject.getSchool().getCd() != null) {
                ps.setString(3, subject.getSchool().getCd());
            } else {
                throw new IllegalArgumentException("insert: SCHOOL_CD must not be null");
            }

            return ps.executeUpdate() > 0;
        }
    }

    /** 更新処理 */
    private boolean update(Subject subject) throws Exception {
        String sql = "UPDATE SUBJECT SET NAME = ? WHERE CD = ? AND SCHOOL_CD = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, subject.getName());
            ps.setString(2, subject.getCd());
            if (subject.getSchool() != null && subject.getSchool().getCd() != null) {
                ps.setString(3, subject.getSchool().getCd());
            } else {
                throw new IllegalArgumentException("update: SCHOOL_CD must not be null");
            }

            return ps.executeUpdate() > 0;
        }
    }

    /** 全件取得メソッド（必要なら） */
    public List<Subject> findAll() throws Exception {
        List<Subject> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(baseSql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                School s = new School();
                s.setCd(rs.getString("SCHOOL_CD"));

                Subject subject = new Subject();
                subject.setCd(rs.getString("CD"));
                subject.setName(rs.getString("NAME"));
                subject.setSchool(s);
                list.add(subject);
            }
        }
        return list;
    }
}
