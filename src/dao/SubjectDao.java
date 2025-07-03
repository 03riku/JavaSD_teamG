package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // SQLException をインポート
import java.util.ArrayList;
import java.util.List;

import Bean.School;
import Bean.Subject;

public class SubjectDao extends Dao {

    private String baseSql;

    public SubjectDao() {
        this.baseSql = "SELECT CD, NAME, SCHOOL_CD FROM SUBJECT";
    }

    /** 科目を cd＋schoolCd で取得 */
    public Subject get(String cd, School school) throws Exception {
        String sql = baseSql + " WHERE CD = ? AND SCHOOL_CD = ?";
        // ★★★ デバッグログ追加 ★★★
        System.out.println("DEBUG (SubjectDao.get): SQL: " + sql);
        System.out.println("DEBUG (SubjectDao.get): Parameters - CD: " + cd + ", School CD: " + (school != null ? school.getCd() : "null"));
        // ★★★ ここまで ★★★
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cd);
            ps.setString(2, school.getCd());

            try (ResultSet rs = ps.executeQuery()) {
                // ★★★ デバッグログ追加 ★★★
                System.out.println("DEBUG (SubjectDao.get): Query executed successfully. Checking results...");
                // ★★★ ここまで ★★★
                if (rs.next()) {
                    School s = new School();
                    s.setCd(rs.getString("SCHOOL_CD"));

                    Subject subject = new Subject();
                    subject.setCd(rs.getString("CD"));
                    subject.setName(rs.getString("NAME"));
                    subject.setSchool(s);
                    // ★★★ デバッグログ追加 ★★★
                    System.out.println("DEBUG (SubjectDao.get): Found Subject: " + subject.getName() + " (" + subject.getCd() + ")");
                    // ★★★ ここまで ★★★
                    return subject;
                } else {
                    // ★★★ デバッグログ追加 ★★★
                    System.out.println("DEBUG (SubjectDao.get): No subject found for CD: " + cd + ", School CD: " + school.getCd());
                    // ★★★ ここまで ★★★
                    return null;
                }
            }
        } catch (SQLException e) { // SQLException をキャッチ
            e.printStackTrace();
            // ★★★ エラーログ追加 ★★★
            System.err.println("ERROR (SubjectDao.get): SQLException occurred: " + e.getMessage());
            // ★★★ ここまで ★★★
            throw new Exception("科目の取得中にデータベースエラーが発生しました。", e); // 例外をラップして再スロー
        }
    }

    /** 学校に紐づく全科目を取得 */
    public List<Subject> filter(School school) throws Exception {
        String sql = baseSql + " WHERE SCHOOL_CD = ? ORDER BY CD";
        List<Subject> list = new ArrayList<>();
        // ★★★ デバッグログ追加 ★★★
        System.out.println("DEBUG (SubjectDao.filter): SQL: " + sql);
        System.out.println("DEBUG (SubjectDao.filter): Parameter School CD: " + (school != null ? school.getCd() : "null"));
        // ★★★ ここまで ★★★

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, school.getCd());
            try (ResultSet rs = ps.executeQuery()) {
                // ★★★ デバッグログ追加 ★★★
                System.out.println("DEBUG (SubjectDao.filter): Query executed successfully. Fetching results...");
                // ★★★ ここまで ★★★
                while (rs.next()) {
                    School s = new School();
                    s.setCd(rs.getString("SCHOOL_CD"));

                    Subject subject = new Subject();
                    subject.setCd(rs.getString("CD"));
                    subject.setName(rs.getString("NAME"));
                    subject.setSchool(s);
                    list.add(subject);
                    // ★★★ デバッグログ追加 ★★★
                    System.out.println("DEBUG (SubjectDao.filter): Found Subject: " + subject.getName() + " (" + subject.getCd() + ")");
                    // ★★★ ここまで ★★★
                }
                // ★★★ デバッグログ追加 ★★★
                System.out.println("DEBUG (SubjectDao.filter): Total subjects found: " + list.size());
                // ★★★ ここまで ★★★
            }
        } catch (SQLException e) { // SQLException をキャッチ
            e.printStackTrace();
            // ★★★ エラーログ追加 ★★★
            System.err.println("ERROR (SubjectDao.filter): SQLException occurred: " + e.getMessage());
            // ★★★ ここまで ★★★
            throw new Exception("科目のフィルタリング中にデータベースエラーが発生しました。", e); // 例外をラップして再スロー
        }
        return list;
    }

    /** 存在すれば更新、なければ挿入 */
    public boolean save(Subject subject) throws Exception {
        // ★★★ デバッグログ追加 ★★★
        System.out.println("DEBUG (SubjectDao.save): Saving subject CD: " + subject.getCd() + ", School CD: " + subject.getSchool().getCd());
        // ★★★ ここまで ★★★
        Subject exists = get(subject.getCd(), subject.getSchool());
        if (exists == null) {
            // ★★★ デバッグログ追加 ★★★
            System.out.println("DEBUG (SubjectDao.save): Subject not found, inserting...");
            // ★★★ ここまで ★★★
            return insert(subject);
        } else {
            // ★★★ デバッグログ追加 ★★★
            System.out.println("DEBUG (SubjectDao.save): Subject found, updating...");
            // ★★★ ここまで ★★★
            return update(subject);
        }
    }

    /** 削除 */
    public boolean delete(Subject subject) throws Exception {
    	boolean flag = false;
        String sql = "DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";

        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, subject.getCd());
            ps.setString(2, subject.getSchool().getCd());
            ps.executeUpdate();
             flag =true ;

            return flag;
        } catch (SQLException e) {
            e.printStackTrace();

            throw new Exception("科目の削除中にデータベースエラーが発生しました。", e);
        }
    }

    /** 挿入処理 */
    private boolean insert(Subject subject) throws Exception {
        String sql = "INSERT INTO SUBJECT (CD, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
        // ★★★ デバッグログ追加 ★★★
        System.out.println("DEBUG (SubjectDao.insert): SQL: " + sql);
        System.out.println("DEBUG (SubjectDao.insert): Inserting subject - CD: " + subject.getCd() + ", Name: " + subject.getName() + ", School CD: " + subject.getSchool().getCd());
        // ★★★ ここまで ★★★
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, subject.getCd());
            ps.setString(2, subject.getName());
            if (subject.getSchool() != null && subject.getSchool().getCd() != null) {
                ps.setString(3, subject.getSchool().getCd());
            } else {
                throw new IllegalArgumentException("insert: SCHOOL_CD must not be null");
            }

            boolean result = ps.executeUpdate() > 0;
            // ★★★ デバッグログ追加 ★★★
            System.out.println("DEBUG (SubjectDao.insert): Insert result: " + result);
            // ★★★ ここまで ★★★
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            // ★★★ エラーログ追加 ★★★
            System.err.println("ERROR (SubjectDao.insert): SQLException occurred: " + e.getMessage());
            // ★★★ ここまで ★★★
            throw new Exception("科目の挿入中にデータベースエラーが発生しました。", e);
        }
    }

    /** 更新処理 */
    private boolean update(Subject subject) throws Exception {
        String sql = "UPDATE SUBJECT SET NAME = ? WHERE CD = ? AND SCHOOL_CD = ?";
        // ★★★ デバッグログ追加 ★★★
        System.out.println("DEBUG (SubjectDao.update): SQL: " + sql);
        System.out.println("DEBUG (SubjectDao.update): Updating subject - Name: " + subject.getName() + ", CD: " + subject.getCd() + ", School CD: " + subject.getSchool().getCd());
        // ★★★ ここまで ★★★
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, subject.getName());
            ps.setString(2, subject.getCd());
            if (subject.getSchool() != null && subject.getSchool().getCd() != null) {
                ps.setString(3, subject.getSchool().getCd());
            } else {
                throw new IllegalArgumentException("update: SCHOOL_CD must not be null");
            }

            boolean result = ps.executeUpdate() > 0;
            // ★★★ デバッグログ追加 ★★★
            System.out.println("DEBUG (SubjectDao.update): Update result: " + result);
            // ★★★ ここまで ★★★
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            // ★★★ エラーログ追加 ★★★
            System.err.println("ERROR (SubjectDao.update): SQLException occurred: " + e.getMessage());
            // ★★★ ここまで ★★★
            throw new Exception("科目の更新中にデータベースエラーが発生しました。", e);
        }
    }

    /** 全件取得メソッド（必要なら） */
    public List<Subject> findAll() throws Exception {
        List<Subject> list = new ArrayList<>();
        // ★★★ デバッグログ追加 ★★★
        System.out.println("DEBUG (SubjectDao.findAll): SQL: " + baseSql + " ORDER BY CD");
        // ★★★ ここまで ★★★
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(baseSql + " ORDER BY CD"); // ORDER BY を追加
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                School s = new School();
                s.setCd(rs.getString("SCHOOL_CD"));

                Subject subject = new Subject();
                subject.setCd(rs.getString("CD"));
                subject.setName(rs.getString("NAME"));
                subject.setSchool(s);
                list.add(subject);
                // ★★★ デバッグログ追加 ★★★
                System.out.println("DEBUG (SubjectDao.findAll): Found Subject: " + subject.getName() + " (" + subject.getCd() + ") from findAll");
                // ★★★ ここまで ★★★
            }
            // ★★★ デバッグログ追加 ★★★
            System.out.println("DEBUG (SubjectDao.findAll): Total subjects found (findAll): " + list.size());
            // ★★★ ここまで ★★★
        } catch (SQLException e) { // SQLException をキャッチ
            e.printStackTrace();
            // ★★★ エラーログ追加 ★★★
            System.err.println("ERROR (SubjectDao.findAll): SQLException occurred: " + e.getMessage());
            // ★★★ ここまで ★★★
            throw new Exception("全科目の取得中にデータベースエラーが発生しました。", e);
        }
        return list;
    }
}