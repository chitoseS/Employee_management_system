package com.example.employee_management_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;

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
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // クラス変数
    private ListView mListView;
    private EditText mEditTextName;
    private EditText mEditTextId;
    private TextView mTextOperation;
    private TestOpenHelper mHelper;
    private SQLiteDatabase mDb;
    ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // リストのIDを取得
        mListView = findViewById(R.id.list_view);

        // オペレーションの内容表示用のTextView
        mTextOperation = findViewById(R.id.textOperation);

        // 追加ボタンの実装
        Button insertButton = findViewById(R.id.insert_button);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // インスタンスが存在しない場合？
                if (mHelper == null) {
                    // コンストラクター getApplicationContext() でインタフェース作成。
                    mHelper = new TestOpenHelper(getApplicationContext());
                }

                // dbが存在しない場合？？
                if (mDb == null) {
                    // データベースを読み書きするとき getWritableDatabase()
                    // 読み取り専用で良い場合 getReadableDatabase()
                    // インタフェースを読み書き権限で扱う。
                    mDb = mHelper.getWritableDatabase();
                }

                // EditTextの文字列を取得
                mEditTextName = findViewById(R.id.name_editText);
                mEditTextId = findViewById(R.id.employee_id_editText);

                // insertデータの準備
                String name = mEditTextName.getText().toString();
                String id = mEditTextId.getText().toString();

                // 入力チェック
                checkData(name, id, mTextOperation);

                // データの挿入
                insertData(mDb, name, id);
                mTextOperation.setText("データを追加しました。");
            }
        });

        // 検索ボタンの実装
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raedData();
            }
        });

        // 削除ボタンの実装
        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // インスタンスが存在しない場合？
                if (mHelper == null) {
                    // コンストラクター getApplicationContext() でインタフェース作成。
                    mHelper = new TestOpenHelper(getApplicationContext());
                }

                // dbが存在しない場合？？
                if (mDb == null) {
                    // データベースを読み書きするとき getWritableDatabase()
                    // 読み取り専用で良い場合 getReadableDatabase()
                    // インタフェースを読み書き権限で扱う。
                    mDb = mHelper.getWritableDatabase();
                }

                deleteData(mDb);
            }
        });
    }

    // IdとNameの入力チェック
    private void checkData(String name, String id, TextView mTextOperation) {
        if(name.isEmpty()){
            // setTextに文字列をそのまま記述すると、Use Android resources instead.と言われるため。
            mTextOperation.setText(R.string.check_name);
        }
        if(id.isEmpty()){
            mTextOperation.setText(R.string.check_id);
        }
    }


    // SQLiteに対するREADメソッド
    private void raedData() {
        // インスタンスが存在しない場合？
        if (mHelper == null) {
            // コンストラクター getApplicationContext() でインタフェース作成。
            mHelper = new TestOpenHelper(getApplicationContext());
        }

        // dbが存在しない場合？？
        if (mDb == null) {
            // データベースを読み書きするとき getWritableDatabase()
            // 読み取り専用で良い場合 getReadableDatabase()
            // インタフェースを読み書き権限で扱う。
            mDb = mHelper.getReadableDatabase();
        }
        Log.d("debug", "**********Cursor");

        // SQLiteDatabase.query()メソッドの場合
        // レコードの検索を行った後、検索結果は、Cursorというインスタンスとして返されてくる。
        // https://android.roof-balcony.com/shori/strage/select/
        Cursor cursor = mDb.query(
                "testdb",
                new String[]{"employee_name", "employee_id"},
                null,
                null,
                null,
                null,
                null
        );

        // SQLiteDatabase.rawQuery()メソッドの場合
        // 生のSQLを書く

        // 取得した結果に対するカーソルを先頭に移動させる？
        cursor.moveToFirst();

        // 文字列を作成
        StringBuilder stringBuilder = new StringBuilder();

        // 文字配列の準備
        String[] insertList;

        // StringBuilderにデータ読み込み
        for (int i = 0; i < cursor.getCount(); i++) {
            stringBuilder.append(cursor.getString(0));
            stringBuilder.append(": ");
            stringBuilder.append(cursor.getInt(1));
            stringBuilder.append("\n");
            cursor.moveToNext();
        }

        // StringBuilderを文字配列に変換
        insertList = stringBuilder.toString().split("\n");
        Log.d("debug", Arrays.toString(insertList));

        cursor.close();
        // Log.d("debug", "**********" + stringBuilder.toString());

        // ArrayAdapterの用意  android.R.layout.simple_list_item_1は、アンドロイドにあらかじめ用意されているレイアウトファイル。
        mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, insertList);

        // Adapterの指定
        mListView.setAdapter(mArrayAdapter);
    }

    // SQLiteに対するINSERTメソッド
    private void insertData(SQLiteDatabase mDb, String name, String id) {

        ContentValues values = new ContentValues();
        values.put("employee_name", name);
        values.put("employee_id", id);

        mDb.insert("testdb", null, values);
    }

    // SQLiteに対するDELETEメソッド
    private void deleteData(SQLiteDatabase mDb) {
        mDb.delete("testdb", null, null);
    }
}
