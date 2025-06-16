package dao;

import java.util.ArrayList;
import java.util.List;

import Bean.School;
import Bean.Subject;

public class SubjectDao extends Dao {

    /** 指定された科目コードと学校で科目を取得 */
    public Subject get(String cd, School school) {
        // 実際には DBアクセスで Subject を取得する
        Subject subject = new Subject();
        // subject.setCd(cd); subject.setSchool(school); etc.
        return subject;
    }

    /** 指定した学校の科目一覧を取得 */
    public List<Subject> filter(School school) {
        List<Subject> list = new ArrayList<>();
        // DBアクセスで filter 条件に合うリストをリターン
        return list;
    }

    /** 科目を保存または更新 */
    public boolean save(Subject subject) {
        boolean isSaved = false;
        // DB に保存し成功した場合 → isSaved = true
        return isSaved;
    }

    /** 科目を削除 */
    public boolean delete(Subject subject) {
        boolean isDeleted = false;
        // DB から削除し成功した場合 → isDeleted = true
        return isDeleted;
    }
}
