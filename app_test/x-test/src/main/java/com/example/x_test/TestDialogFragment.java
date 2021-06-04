package com.example.x_test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

public class TestDialogFragment extends DialogFragment {
    public static boolean show = false;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Dialog);
//        dialog.setContentView(R.layout.dialog_test);

        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_test, null);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(contentView)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = 500;
        window.setAttributes(layoutParams);

        return dialog;
    }


    public static void show(FragmentActivity activity){
        if (show)
            return;
        TestDialogFragment testDialogFragment = new TestDialogFragment();
        testDialogFragment.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_CREATE){
                    show = true;
                }else if (event == Lifecycle.Event.ON_DESTROY){
                    show = false;
                }
            }
        });

        testDialogFragment.show(activity.getSupportFragmentManager(), "");
    }
}
