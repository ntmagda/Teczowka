package com.example.teczowka2.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Magda on 2014-06-03.
 */

  public class Camera implements View.OnClickListener {
    private static final int CAMERA_REQUEST = 2;
    private Activity mContext;

    public Camera(Activity activity) {
        this.mContext = activity;
    }

    @Override
    public void onClick(View view) {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        mContext.startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
}