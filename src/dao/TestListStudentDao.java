package dao; // パッケージ名はご自身のプロジェクト構成に合わせて変更してください

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Bean.Student;

/**
 * TestListStudentDao クラス。
 * 学生ごとの成績一覧に関連するデータベースアクセスを担当します。
 * Daoクラスを継承します。
 */
public class TestListStudentDao extends Dao {

    /**
     * 検索のベースとなるSQL文を保持するプライベートフィールド。
     */
    private String baseSql = "SELECT ... FROM ... WHERE ..."; // ここにベースとなるSQL文を記述します

    /**
     * データベースのクエリ結果(ResultSet)を基に、TestListStudentのリストを作成します。
     *
     * @param rst データベースからの結果セット(ResultSet)
     * @return TestListStudentオブジェクトのリスト
     * @throws Exception データベースアクセスエラーやデータ変換エラーが発生した場合
     */
    public List<TestListStudent> postFilter(ResultSet rst) throws Exception {
        // 戻り値用のリストを初期化
        List<TestListStudent> list = new ArrayList<>();

        // TODO: ResultSetからデータを1件ずつ取り出し、TestListStudentオブジェクトにセットしてリストに追加する処理を実装します。
        // --- 実装例 ---
        // try {
        //     while (rst.next()) {
        //         TestListStudent testStudent = new TestListStudent();
        //         // rst.get...() を使って各カラムの値を取得し、testStudentにセットする
        //         // testStudent.setStudentName(rst.getString("student_name"));
        //         // ...
        //         list.add(testStudent);
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        return list; // 処理済みのリストを返す
    }

    /**
     * 指定された学生情報(Student)を基に、データベースを検索し、
     * 成績情報(TestListStudent)のリストを返します。
     *
     * @param student 検索条件となる学生情報を持つStudentオブジェクト
     * @return 検索結果であるTestListStudentオブジェクトのリスト
     * @throws Exception データベースアクセスエラーが発生した場合
     */
    public List<TestListStudent> filter(Student student) throws Exception {
        // 戻り値用のリストを初期化
        List<TestListStudent> list = new ArrayList<>();

        // TODO: データベースへの接続、PreparedStatementの作成、SQLの実行、結果の取得処理を実装します。
        // Connection connection = getConnection(); // 親クラスのgetConnection()を想定
        // PreparedStatement statement = connection.prepareStatement(baseSql);
        // // statementにstudentオブジェクトの値をセット
        // // statement.setString(1, student.getNo());
        // ResultSet rs = statement.executeQuery();
        // list = postFilter(rs); // 取得したResultSetをpostFilterに渡してリストに変換

        return list; // 検索結果のリストを返す
    }
}