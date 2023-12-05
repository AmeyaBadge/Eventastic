package com.amykoder.eventastic;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class CustomProgressDialogClass {
    private final Activity activity;
    private AlertDialog alertDialog;

    CustomProgressDialogClass(Activity myActivity){
        activity = myActivity;
    }
    void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_progress_dialog,null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }
    void dismiss(){
        alertDialog.dismiss();
    }
}
