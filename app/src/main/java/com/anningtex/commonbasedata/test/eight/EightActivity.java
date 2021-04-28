package com.anningtex.commonbasedata.test.eight;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.data.api.ApiManager;
import com.anningtex.commonbasedata.data.base.BaseResponse;
import com.anningtex.commonbasedata.data.base.two.BaseActivity;
import com.anningtex.commonbasedata.data.rx.FileRequestBodyProgress;
import com.anningtex.commonbasedata.data.rx.RetrofitCallback;
import com.anningtex.commonbasedata.data.rx.RxDisposeManager;
import com.anningtex.commonbasedata.data.rx.RxNet;
import com.anningtex.commonbasedata.data.rx.RxNetCallBack;
import com.anningtex.commonbasedata.databinding.ActivityEightBinding;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Song
 * dedsc:上传文件(注解)
 */
public class EightActivity extends BaseActivity<ActivityEightBinding> {
    private final String IMAGE_TYPE = "image/*";
    private final int IMAGE_CODE = 0;

    @Override
    protected void initViews(Bundle savedInstanceState) {
//                ((ProgressBar) helper.getView(R.id.progress)).setProgress((int) (qrMangerBean.progress * 100));
//                helper.setText(R.id.currentProgress, "当前上传进度 : " + (((int) (qrMangerBean.progress * 100))) + "%");
    }

    @OnClick({R.id.btn_pic, R.id.btn_upload})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pic:
                setImage();
                break;
            case R.id.btn_upload:
                new UpLoadData().start();
                break;
            default:
                break;
        }
    }

    private void setImage() {
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(IMAGE_TYPE);
        startActivityForResult(getAlbum, IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getContentResolver();
        if (requestCode == IMAGE_CODE) {
            try {
                Uri originalUri = data.getData();
                Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                binding.ivPic.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传服务器(格式举例)
     */
    class UpLoadData extends Thread {
        @Override
        public void run() {
            super.run();
            RequestBody bodyOlid = RequestBody.create(MediaType.parse("multipart/form-data"), "123");
            RequestBody bodyFlowerId = RequestBody.create(MediaType.parse("multipart/form-data"), "456");
            RequestBody bodyBarCode = RequestBody.create(MediaType.parse("multipart/form-data"), "789");
            Map<String, RequestBody> paramMap = new HashMap<>();
            String qrCodePicPath = "sd卡上的路径附一个就好";
            File file = new File(qrCodePicPath);
            RequestBody bodyFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            FileRequestBodyProgress fileRequestBody = new FileRequestBodyProgress(bodyFile, new RetrofitCallback() {
                @Override
                public void onSuccess(Call call, Response response) {

                }

                @Override
                public void onLoading(long total, long progress) {
                    runOnUiThread(() -> {
                        Log.e("999UpLoadData", " total : " + total + "  progress : " + progress);
                        //所使用的实体类声明一个progress，不需要get和set方法。  public double progress;
                        double percent = progress / total;
//                                    qrMangerBean.progress = percent;
//                                    recycleAdapter.notifyDataSetChanged();
                    });
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
            paramMap.put("file\"; filename=\"" + file.getName(), fileRequestBody);

            Observable<BaseResponse<List>> observable = ApiManager.getInstance().getSaveBarCodeSingle(bodyOlid, bodyFlowerId, bodyBarCode, paramMap);
            RxDisposeManager.get().add(getClass().getName(), RxNet.request(observable, new RxNetCallBack<List>() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(String msg, List response) {
                    Log.e("999TAGs", "onSuccess: " + msg);
                    showToast(msg);
                }

                @Override
                public void onFailure(String msg) {
                    Log.e("999TAGf", "onFailure: " + msg);
                    showToast(msg);
                }
            }));
        }
    }
}