package com.example.employee_management_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class SubActivity extends AppCompatActivity {
    // クラス変数
    private TestOpenHelper mHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // Menu画面からデータの受け取り
        Intent intent = getIntent();
        String receivedData = intent.getStringExtra("Message");
        TextView textView = findViewById(R.id.textView);
        textView.setText(receivedData);

        // 受取ったデータ（リストの番号）から対象のデータを取得し表示する。
        readData(receivedData, textView);

        // 戻るボタンの実装
        Button return_button = findViewById(R.id.return_button);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 遷移先の画面を準備
                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    /// DBからデータを検索し、TextViewに表示する。
    private void readData(String receivedData, TextView textView) {

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
                // where条件式  ex) "employee_id = 1"
                "employee_id = " + receivedData,
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
        textView.setText(Arrays.toString(insertList));

        cursor.close();
    }
}
