package com.example.employee_management_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // Menu画面からデータの受け取り
        Intent intent = getIntent();
        String receivedData = intent.getStringExtra("Message");
        TextView textView = findViewById(R.id.textView);
        textView.setText(receivedData);

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
}
