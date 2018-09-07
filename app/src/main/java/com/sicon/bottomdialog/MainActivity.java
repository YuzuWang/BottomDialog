package com.sicon.bottomdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.weshowbuy.bottomdialog.BottomDialog;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.tv_show_dialog);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialog.onCreate(getSupportFragmentManager(), MainActivity.this)
                        .setTitle("显示")
                        .setItems(Arrays.asList("相机", "从相册选择"))
                        .setSubtitles(Arrays.asList("拍照或视频"))
                        .setDestoryTitle("删除")
                        .show();
            }
        });
    }
}
