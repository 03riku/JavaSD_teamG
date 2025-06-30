package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // SQLExceptionのインポートを追加

import Bean.School;
import Bean.Teacher;

public class TeacherDao extends Dao {

    /**
     * 指定されたIDの教師情報を取得します。
     * 関連する学校情報もTeacherオブジェクトに設定されます。
     * @param id 取得する教師のID
     * @return 該当するTeacherオブジェクト、または見つからない場合はnull
     * @throws Exception データベースアクセスエラーが発生した場合
     */
    public Teacher get(String id) throws Exception {
        Teacher teacher = null; // Teacherオブジェクトをnullで初期化

        // 1つ目のSQL: Teacherテーブルから情報を取得
        String sql = "SELECT * FROM teacher WHERE id = ?";

        // SchoolDaoのインスタンスを作成 (学校情報を取得するため)
        SchoolDao schoolDao = new SchoolDao();

        try (Connection con = getConnection(); // データベース接続を取得
             PreparedStatement st = con.prepareStatement(sql)) { // PreparedStatementを作成

            st.setString(1, id); // プレースホルダーにIDをセット

            try (ResultSet rs = st.executeQuery()) { // SQLを実行し、ResultSetを取得
                if (rs.next()) { // 結果セットに次の行がある場合（データが見つかった場合）
                    teacher = new Teacher(); // Teacherオブジェクトを初期化
                    School school = null;

                    // ResultSetから教師情報を取得し、Beanにセット
                    teacher.setId(rs.getString("id"));
                    teacher.setPassword(rs.getString("password"));
                    teacher.setName(rs.getString("name"));
                    school = schoolDao.find(rs.getString("school_cd"));
                    
                    System.out.println("rs.getString('school_cd'):"+rs.getString("school_cd"));
                    System.out.println("school:"+school);

                    teacher.setSchool(school); // TeacherオブジェクトにSchoolを設定
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // エラーの詳細を出力
            throw e; // 例外を上位にスロー
        }
        return teacher; // 取得したTeacherオブジェクト、またはnullを返す
    }
    public Teacher login(String id, String password) throws Exception {

        Teacher teacher = get(id);

       if(teacher != null && teacher.getPassword().equals(password)){

    	   return teacher;
       }
        return null;

    }
}