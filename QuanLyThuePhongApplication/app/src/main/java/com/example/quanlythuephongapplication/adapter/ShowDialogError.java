package com.example.quanlythuephongapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quanlythuephongapplication.LoginActivity;
import com.example.quanlythuephongapplication.R;

public class ShowDialogError {
    Context context;
    String tieuDe;
    String noiDung;

    public ShowDialogError(Context context, String tieuDe, String noiDung) {
        this.context = context;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
    }

    public void settingDialog(){
        Button btnCancel;
        TextView txtTieuDe;
        TextView txtNoiDung;
        Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_error_custom);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.custom_dialog_bg));
        dialog.setCancelable(false);

        btnCancel = dialog.findViewById(R.id.btnCancel);
        txtTieuDe = dialog.findViewById(R.id.textViewTieuDe);
        txtNoiDung = dialog.findViewById(R.id.textViewNoiDung);

        txtTieuDe.setText(this.tieuDe);
        txtNoiDung.setText(this.noiDung);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
