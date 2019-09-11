package com.example.employee_management_system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // クラス変数
    ListView mListView;

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

        // ArrayAdapterの用意  android.R.layout.simple_list_item_1は、アンドロイドにあらかじめ用意されているレイアウトファイル。
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeName);

        // Adapterの指定
        mListView.setAdapter(arrayAdapter);
    }
}
