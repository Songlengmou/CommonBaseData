package com.anningtex.commonbasedata.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anningtex.commonbasedata.R;


/**
 * @author Song
 * desc:每个界面加载的Dialog
 */
public class LoadingView extends ProgressDialog {
    private ProgressBar progressBar;
    private TextView tv_progress, tv_load_dialog;

    public LoadingView(Context context) {
        super(context, R.style.CustomDialog);
    }

    public LoadingView(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_loading);

        progressBar = findViewById(R.id.pb_load);
        tv_progress = findViewById(R.id.tv_progress);
        tv_load_dialog = findViewById(R.id.tv_load_dialog);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {//开启
        super.show();
    }

    @Override
    public void dismiss() {//关闭
        super.dismiss();
    }

    public void setMsg(String msg) {
        tv_load_dialog.setText(msg);
    }

    public void showProgressbar(int p) {
        tv_progress.setText(p + "%");
    }
}
