package com.example.grouping;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class HomeSeeWriting extends AppCompatActivity {
    final LinearLayout linear = (LinearLayout) View.inflate(HomeSeeWriting.this, R.layout.home_apply_dialog, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_see_writing);

        new AlertDialog.Builder(HomeSeeWriting.this)
                .setView(linear)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText applycontent = (EditText) linear.findViewById(R.id.homeStudyApplyDialogEdittext);
                        String value = applycontent.getText().toString(); //GET suggest에 어떤 거..? 간단 지원 내용이 DB에도 들어가야할 듯
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}