package com.example.quanlythuephongapplication.adapter;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.quanlythuephongapplication.LoginActivity;
import com.example.quanlythuephongapplication.R;

public class ShowDialog {
    Dialog dialog;

    public ShowDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public void settingDialog(){
        Button btnCancel,btnLogout;

        dialog.setContentView(R.layout.dialog_custom);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //dialog.getWindow().setBackgroundDrawable(R.drawable.custom_dialog_bg);
        dialog.setCancelable(false);

        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnLogout = dialog.findViewById(R.id.btnLogout);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
