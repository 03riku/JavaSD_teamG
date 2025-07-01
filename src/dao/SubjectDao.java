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

    // SQL文の定義
    private static final String GET_SQL = "SELECT cd, name, school_cd FROM subject WHERE cd = ? AND school_cd = ?";
    private static final String FILTER_SQL = "SELECT cd, name, school_cd FROM subject WHERE school_cd = ? ORDER BY cd";
    private static final String INSERT_SQL = "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE subject SET name = ? WHERE cd = ? AND school_cd = ?";
    private static final String DELETE_SQL = "DELETE FROM subject WHERE cd = ? AND school_cd = ?";

    /**
     * ResultSetからSubjectオブジェクトを生成します。
     * @param rSet データベースからの結果セット
     * @param school Schoolオブジェクト (必要に応じて利用)
     * @return 生成されたSubjectオブジェクト
     * @throws Exception データベースアクセスエラーが発生した場合
     */
    private Subject postFilter(ResultSet rSet, School school) throws Exception {
        Subject subject = new Subject();
        subject.setCd(rSet.getString("cd"));
        subject.setName(rSet.getString("name"));
        // Schoolオブジェクトを設定
        // もしschool_cdがResultSetから取得可能で、かつSchoolオブジェクトも必要なら
        if (school != null) {
            subject.setSchool(school);
        } else {
            // ResultSetからschool_cdを取得してSchoolオブジェクトを生成する場合
            School s = new School();
            s.setCd(rSet.getString("school_cd"));
            subject.setSchool(s);
        }
        return subject;
    }

    /**
     * 指定された科目コードと学校で科目を取得します。
     * @param cd 科目コード
     * @param school 学校オブジェクト
     * @return 該当するSubjectオブジェクト、存在しない場合はnull
     * @throws Exception データベースアクセスエラーが発生した場合
     */
    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(GET_SQL)) {
            st.setString(1, cd);
            st.setString(2, school.getCd());
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    subject = postFilter(rs, school);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 例外を上位にスロー
        }
        return subject;
    }

    /**
     * 指定した学校の科目一覧を取得します。
     * @param school 学校オブジェクト
     * @return 該当するSubjectオブジェクトのリスト
     * @throws Exception データベースアクセスエラーが発生した場合
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(FILTER_SQL)) {
            st.setString(1, school.getCd());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(postFilter(rs, school));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 例外を上位にスロー
        }
        return list;
    }

    /**
     * 科目をデータベースに保存または更新します。
     * 科目コードと学校コードが一致するレコードがあれば更新、なければ挿入します。
     * @param subject 保存または更新するSubjectオブジェクト
     * @return 処理が成功した場合はtrue、失敗した場合はfalse
     * @throws Exception データベースアクセスエラーが発生した場合
     */
    public boolean save(Subject subject) throws Exception {
        boolean isSaved = false;
        Connection con = null;
        PreparedStatement st = null;

        try {
            con = getConnection();
            // まず既存のレコードがあるか確認
            Subject oldSubject = get(subject.getCd(), subject.getSchool());

            if (oldSubject == null) {
                // 既存レコードがない場合は挿入
                st = con.prepareStatement(INSERT_SQL);
                st.setString(1, subject.getCd());
                st.setString(2, subject.getName());
                st.setString(3, subject.getSchool().getCd());
            } else {
                // 既存レコードがある場合は更新
                st = con.prepareStatement(UPDATE_SQL);
                st.setString(1, subject.getName()); // 更新する科目名
                st.setString(2, subject.getCd());   // WHERE句の科目コード
                st.setString(3, subject.getSchool().getCd()); // WHERE句の学校コード
            }

            int count = st.executeUpdate(); // SQLを実行
            if (count > 0) {
                isSaved = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // リソースのクローズ処理
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSaved;
    }

 // 科目の新規登録
    // 科目の新規登録（Subject テーブルに挿入）
       public void insert(Subject subject) throws Exception {
           System.out.println("=====================================================================");
           String sql = "INSERT INTO SUBJECT (SCHOOL_CD,SUBJECT_CD,SUBJECT_NAME)VALUES (?, ?, ?)";
           try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
               ps.setString(1, subject.getCd());
               ps.setString(2, subject.getName());

               // --- 重要：SCHOOL_CD を必ずセット、NULLは不可！
               if (subject.getSchool() != null && subject.getSchool().getCd() != null) {
                   ps.setString(3, subject.getSchool().getCd());
               } else {
                   throw new IllegalArgumentException("insert: SCHOOL_CD must not be null (subject=" + subject + ")");
               }

               ps.executeUpdate();
           }
       }
    /**
     * 科目をデータベースから削除します。
     * @param subject 削除するSubjectオブジェクト
     * @return 処理が成功した場合はtrue、失敗した場合はfalse
     * @throws Exception データベースアクセスエラーが発生した場合
     */
    public boolean delete(Subject subject) throws Exception {
        boolean isDeleted = false;
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(DELETE_SQL)) {
            st.setString(1, subject.getCd());
            st.setString(2, subject.getSchool().getCd());

            int count = st.executeUpdate(); // SQLを実行
            if (count > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 例外を上位にスロー
        }
        return isDeleted;
    }
}