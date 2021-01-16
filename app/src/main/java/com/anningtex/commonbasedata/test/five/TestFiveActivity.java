package com.anningtex.commonbasedata.test.five;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.data.base.one.BaseActivity;
import com.anningtex.commonbasedata.data.manger.MainApplication;
import com.anningtex.commonbasedata.weight.actionbar.TitleBar;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Song
 * desc:5. 获取动态权限
 */
public class TestFiveActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.ivShowPicture)
    ImageView ivShowPicture;

    private MainApplication mainApplication;
    private static int REQUEST_CAMERA = 1;
    private static int REQUEST_CALENDAR = 2;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_test_five;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        title.setLeftListener(v -> {
            onBackPressed();
        });
        mainApplication = new MainApplication();
    }

    @OnClick({R.id.btn_one, R.id.btn_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                cameraData();
                break;
            case R.id.btn_two:
                microData();
                break;
            default:
                break;
        }
    }

    /**
     * 摄像头权限
     */
    @SuppressLint("WrongConstant")
    private void cameraData() {
        AndPermission.with(TestFiveActivity.this)
                .runtime()
                .permission(Permission.Group.CAMERA)
                .rationale((Context context, List<String> data, RequestExecutor executor) -> {
                            new AlertDialog.Builder(context)
                                    .setTitle("权限申请")
                                    .setMessage("这里需要申请" + data.get(0))
                                    .setPositiveButton("好的", (dialog, which) -> executor.execute())
                                    .setNegativeButton("取消", null)
                                    .create()
                                    .show();
                        }
                )
                .onGranted(permission -> {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
                            startActivityForResult(intent, REQUEST_CAMERA);
                        }
                )
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(TestFiveActivity.this, permissions)) {
                        new AlertDialog.Builder(TestFiveActivity.this)
                                .setTitle("权限获取失败")
                                .setMessage("没有权限该功能不能使用，是否进入应用设置中进行权限中设置!")
                                .setPositiveButton("好的", (DialogInterface dialog, int which) -> {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            String applicationName = mainApplication.getApplicationName();
                                            Uri uri = Uri.fromParts("package", applicationName, null);
                                            intent.setData(uri);
                                            startActivity(intent);
                                        }
                                )
                                .setNegativeButton("取消", null)
                                .create()
                                .show();
                    } else {
                        showToast("开启权限失败");
                    }
                })
                .start();
    }

    /**
     * 日历权限
     */
    @SuppressLint("WrongConstant")
    private void microData() {
        AndPermission.with(TestFiveActivity.this)
                .runtime()
                .permission(Permission.Group.CALENDAR)
                .rationale((Context context, List<String> data, RequestExecutor executor) -> {
                            new AlertDialog.Builder(context)
                                    .setTitle("权限申请")
                                    .setMessage("这里需要申请" + data.get(0))
                                    .setPositiveButton("好的", (dialog, which) -> executor.execute())
                                    .setNegativeButton("取消", null)
                                    .create()
                                    .show();
                        }
                )
                //权限调用成功后的回调
                .onGranted(permission -> {
                            // 1.日历里面添加事件 TODO
//                            Intent intent = new Intent(Intent.ACTION_INSERT);
//                            intent.setData(CalendarContract.Events.CONTENT_URI);
//                            intent.putExtra(CalendarContract.Events.TITLE, "开会");
//                            startActivityForResult(intent, REQUEST_CALENDAR);

                            //2.
                            try {
                                Intent intent = new Intent();
                                ComponentName cn;
                                if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
                                    cn = new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity");
                                } else {
                                    cn = new ComponentName("com.google.android.calendar", "com.android.calendar.LaunchActivity");
                                }
                                intent.setComponent(cn);
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Log.e("Exception", e.toString());
                            }
                        }
                )
                //权限调用失败后的回调
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(TestFiveActivity.this, permissions)) {
                        new AlertDialog.Builder(TestFiveActivity.this)
                                .setTitle("权限获取失败")
                                .setMessage("没有权限该功能不能使用，是否进入应用设置中进行权限中设置!")
                                .setPositiveButton("好的", (DialogInterface dialog, int which) -> {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            String applicationName = mainApplication.getApplicationName();
                                            Uri uri = Uri.fromParts("package", applicationName, null);
                                            intent.setData(uri);
                                            startActivity(intent);
                                        }
                                )
                                .setNegativeButton("取消", null)
                                .create()
                                .show();
                    } else {
                        showToast("开启权限失败");
                    }
                })
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                ivShowPicture.setImageBitmap(bitmap);
            } else if (requestCode == REQUEST_CALENDAR) {

            }
        }
    }
}