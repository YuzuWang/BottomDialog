package com.sicon.bottomdialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.weshowbuy.bottomdialog.BottomDialog;
import com.weshowbuy.bottomdialog.BottomTextEditDialog;

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
                BottomTextEditDialog.onCreate(getSupportFragmentManager())
                        .show();
            }
        });
    }
}
