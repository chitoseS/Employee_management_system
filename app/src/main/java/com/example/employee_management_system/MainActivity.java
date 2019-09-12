package com.example.employee_management_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    // クラス変数
    private ListView mListView;
    private EditText mEditTextName;
    private EditText mEditTextId;
    private TestOpenHelper mHelper;
    private SQLiteDatabase mDb;

    // リストに表示する仮のデータを用意 https://qiita.com/Tsumugi/items/47f31bb7351979a45653
    private static final String[] employeeName = {
            "上野", "栗田", "津川"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // リストのIDを取得
        mListView = findViewById(R.id.list_view);

        // EditTextの文字列を取得
        mEditTextName = findViewById(R.id.name_editText);
        mEditTextId = findViewById(R.id.employee_id_editText);

        // ArrayAdapterの用意  android.R.layout.simple_list_item_1は、アンドロイドにあらかじめ用意されているレイアウトファイル。
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeName);

        // Adapterの指定
        mListView.setAdapter(arrayAdapter);

        // 追加ボタンの実装
        Button insertButton = findViewById(R.id.insert_button);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("log","__________");

                // インスタンスが存在しない場合？
                if(mHelper == null){
                    // コンストラクター getApplicationContext() でインタフェース作成。
                    mHelper = new TestOpenHelper(getApplicationContext());
                }

                // dbが存在しない場合？？
                if(mDb == null){
                    // データベースを読み書きするとき getWritableDatabase()
                    // 読み取り専用で良い場合 getReadableDatabase()
                    // インタフェースを読み書き権限で扱う。
                    mDb = mHelper.getWritableDatabase();
                }

                // insertデータの準備
                String name = mEditTextName.getText().toString();
                String id = mEditTextId.getText().toString();

                insertData(mDb, name, id);
            }
        });

        // 検索ボタンの実装
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("log","----------");

                raedData();
            }
        });
    }

    // SQLiteに対するREADメソッド
    private void raedData() {
        // インスタンスが存在しない場合？
        if(mHelper == null){
            // コンストラクター getApplicationContext() でインタフェース作成。
            mHelper = new TestOpenHelper(getApplicationContext());
        }

        // dbが存在しない場合？？
        if(mDb == null){
            // データベースを読み書きするとき getWritableDatabase()
            // 読み取り専用で良い場合 getReadableDatabase()
            // インタフェースを読み書き権限で扱う。
            mDb = mHelper.getReadableDatabase();
        }
        Log.d("debug","**********Cursor");

        // SQLiteDatabase.query()メソッドの場合
        // レコードの検索を行った後、検索結果は、Cursorというインスタンスとして返されてくる。
        // https://android.roof-balcony.com/shori/strage/select/
        Cursor cursor = mDb.query(
                "testdb",
                new String[] { "employee_name", "employee_id" },
                null,
                null,
                null,
                null,
                null
        );

        // SQLiteDatabase.rawQuery()メソッドの場合
        // 生のSQLを書く

        //
        cursor.moveToFirst();
    }

    // SQLiteに対するINSERTメソッド
    private void insertData(SQLiteDatabase mDb, String name, String id) {

        ContentValues values = new ContentValues();
        values.put("employee_name", name);
        values.put("employee_id", id);

        mDb.insert("testdb", null, values);
    }
}
