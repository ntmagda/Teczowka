package com.example.teczowka2.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Magda on 2014-06-03.
 */
public class Gallery implements View.OnClickListener {

    private Activity mContext;

    public Gallery(Activity activity){
        this.mContext = activity;
    }

    @Override
    public void onClick(View view) {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        mContext.startActivityForResult(Intent.createChooser(gallery, "Select Image"), 1); // here is a NULL exception
    }
}
