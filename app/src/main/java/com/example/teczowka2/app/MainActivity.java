package com.example.teczowka2.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

    Bitmap original;
    ImageView iv;
    ImageView ivNorm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button gallery = (Button) findViewById(R.id.gallery);
        gallery.setOnClickListener(new Gallery(this));

        Button camera = (Button) findViewById(R.id.camera);
        camera.setOnClickListener(new Camera(this));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                ImageView oko = (ImageView) findViewById(R.id.oko);
                oko.setImageURI(data.getData()); // pobranie z galerii
                Bitmap okobit = ((BitmapDrawable) oko.getDrawable()).getBitmap();
                original =((BitmapDrawable) oko.getDrawable()).getBitmap();
                ImageView iv = (ImageView) findViewById(R.id.oko);
                iv.setImageBitmap(okobit);


            }
            if (resultCode == RESULT_OK) {
                if (requestCode == 2) {
                    Bundle extras = data.getExtras();
                    Bitmap oko = (Bitmap) extras.get("data");
                    oko = Bitmap.createScaledBitmap(oko, 160, 160, true);
                    original = Bitmap.createScaledBitmap(oko, 160, 160, true);
                    ImageView iv = (ImageView) findViewById(R.id.oko);
                    iv.setImageBitmap(oko);

                    String path = Environment.getExternalStorageDirectory().toString();
                    OutputStream fOut = null;
                    File file = new File(path, "teczowka"+3+".jpg");
                    try {
                        fOut = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    oko.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    assert fOut != null;
                    try {
                        fOut.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fOut.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        iv = (ImageView) findViewById(R.id.oko);
        ivNorm = (ImageView) findViewById(R.id.normalized);
        original = ((BitmapDrawable) iv.getDrawable()).getBitmap();
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int reqWidth=200;
        int reqHeigth=200;
        HoughWersjaElipsa hough = new HoughWersjaElipsa();
        iv.setImageBitmap(original);
        Bitmap compressedImage = CompressImage.decodeSampledBitmapFromResource(getResources(),R.drawable.mojeoko,reqWidth,reqHeigth);

        switch (item.getItemId()) {

            case R.id.original:
                iv.setImageBitmap(original);
                break;
            case R.id.findIrisBow:
              Process.DoIt(iv,ivNorm,compressedImage,original);

                break;
            case R.id.drawIrisBow:
                HoughDraw hough1 = new HoughDraw();
                Bitmap grey1 = GreyScale.process(compressedImage);
                Bitmap greyGauss1 = GaussianFilter.process(grey1);
                Bitmap BinarizationPupil1 = Binarization.process(greyGauss1, "Pupil");
                Bitmap BinarizationIris1 = Binarization.process(greyGauss1, "Iris");
                Bitmap sobelPupil1 = Sobel.process(BinarizationPupil1);
                Bitmap sobelIris1 = Sobel.process(BinarizationIris1);
                Bitmap pupil1 = hough1.H(sobelPupil1, grey1,20,70);
                Bitmap iris1 = hough1.H(sobelIris1, grey1, 40, 70 );
                iv.setImageBitmap(iris1);
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}