package com.example.employee_management_system;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/// [Android] データベース SQLite の簡単作成
/// https://akira-watson.com/android/sqlite.html
public class TestOpenHelper extends SQLiteOpenHelper {

    // データベースのバージョン
    private static final int DATABASE_VERSION = 1;
    // データーベース名
    private static final String DATABASE_NAME = "TestDB.db";

    // テーブル名
    private static final String TABLE_NAME = "testdb";
    private static final String _ID = "_id";
    private static final String COLUMN_NAME_TITLE = "employee_name";
    private static final String COLUMN_NAME_SUBTITLE = "employee_id";

    // SQL文
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_SUBTITLE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    // コンストラクター
    TestOpenHelper(Context context) {
        // 親クラスのコンストラクタを呼び出している。http://individualmemo.blog104.fc2.com/blog-entry-47.html
        // SQLiteOpenHelperが親クラス（スーパークラス、基底クラス）
        // super() で 自分でコーディングすることで親クラスのコンストラクタを呼べる。
        // 第1引数：DBを利用するアプリのContent（インタフェース？）。このクラスを呼ぶときにthisか、getApplication()を指定する。
        // 第3引数：特に理由が無ければ、nullでOK.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * http://yan-note.blogspot.com/2010/09/android-sqlite.html
     * onCreateメソッド
     * データベースが作成された時に呼ばれます。
     * テーブルの作成などを行います。
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブル作成
        // SQLiteファイルがなければSQLiteファイルが作成される
        // データベースファイルがある場合は、呼ばれない。
        db.execSQL(
                SQL_CREATE_ENTRIES
        );
    }

    /*
     * onUpgradeメソッド
     * onUpgrade()メソッドはデータベースをバージョンアップした時に呼ばれます。
     * 現在のレコードを退避し、テーブルを再作成した後、退避したレコードを戻すなどの処理を行います。
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // アップデートの判別、古いバージョンは削除して新規作成
        db.execSQL(
                SQL_DELETE_ENTRIES
        );
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
