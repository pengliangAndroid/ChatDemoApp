package com.wstro.chatdemoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wstro.app.common.base.BaseAppToolbarActivity;
import com.wstro.app.common.utils.DeviceUtils;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorFragment;

public class SelectImageActivity extends BaseAppToolbarActivity implements MultiImageSelectorFragment.Callback{
    private static final String SINGLE = "single";
    private static final String SHOW_CAMERA = "showCamera";
    private static final String SELECT_COUNT = "selectCount";

    public static final String EXTRA_RESULT = "select_result";

    public static final int CODE_SINGLE = 100;
    public static final int CODE_MULTI = 101;


    private static final int DEFAULT_IMAGE_SIZE = 9;

    private ArrayList<String> resultList = new ArrayList<>();

    private int count = DEFAULT_IMAGE_SIZE;

    public static void startSingleSelect(Activity act, boolean isShowCamera) {
        Intent starter = new Intent(act, SelectImageActivity.class);
        starter.putExtra(SINGLE,true);
        starter.putExtra(SHOW_CAMERA,isShowCamera);
        starter.putExtra(SELECT_COUNT,1);
        act.startActivityForResult(starter,CODE_SINGLE);
    }

    public static void startMultiSelect(Activity act,boolean isShowCamera,int count) {
        Intent starter = new Intent(act, SelectImageActivity.class);
        starter.putExtra(SINGLE,false);
        starter.putExtra(SHOW_CAMERA,isShowCamera);
        starter.putExtra(SELECT_COUNT,count);
        act.startActivityForResult(starter,CODE_MULTI);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_image;
    }

    @Override
    protected void initViewsAndEvents(Bundle aBundle) {
        titleText.setText("选择图片");

        boolean isSingle = getIntent().getBooleanExtra(SINGLE, true);
        boolean isShowCamera = getIntent().getBooleanExtra(SHOW_CAMERA, true);
        count = getIntent().getIntExtra(SELECT_COUNT,DEFAULT_IMAGE_SIZE);

        if(isSingle){
            rightText.setVisibility(View.GONE);
        }else{
            rightText.setVisibility(View.VISIBLE);
            updateDoneText(resultList);
        }

        rightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resultList != null && resultList.size() >0){
                    // Notify success
                    Intent data = new Intent();
                    data.putStringArrayListExtra(EXTRA_RESULT, resultList);
                    setResult(RESULT_OK, data);
                }else{
                    setResult(RESULT_CANCELED);
                }
                finish();
            }
        });


        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, count);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, isSingle ? 0 : 1);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShowCamera);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(String path) {
        if(!resultList.contains(path)) {
            resultList.add(path);
        }
        updateDoneText(resultList);
    }

    @Override
    public void onImageUnselected(String path) {
        if(resultList.contains(path)){
            resultList.remove(path);
        }
        updateDoneText(resultList);
    }

    private void updateDoneText(ArrayList<String> resultList){
        int size = 0;
        if(resultList == null || resultList.size() <= 0){
            rightText.setText(R.string.action_done);
            rightText.setEnabled(false);
        }else{
            size = resultList.size();
            rightText.setEnabled(true);
        }
        rightText.setText(getString(R.string.action_button_string,
                getString(R.string.action_done), size, count));
    }

    @Override
    public void onCameraShot(File file) {
        if(file != null) {
            // notify system the image has change
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, DeviceUtils.getExternalFileUri(this,file)));

            Intent data = new Intent();
            resultList.add(file.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
