package com.example.teczowka2.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

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
//                int reqWidth=200;
//                int reqHeigth=200;
//                Bitmap okobit = CompressImage.decodeSampledBitmapFromResource(getResources(),R.drawable.tecz1,reqWidth,reqHeigth);
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

        GreyScale greytemp = new GreyScale();
        Binarization binarizationtemp = new Binarization();
        FindIris findIristemp = new FindIris();
        Sobel sobeltemp = new Sobel();
        HoughWersjaElipsa hough = new HoughWersjaElipsa();


        switch (item.getItemId()) {

            case R.id.original:
                iv.setImageBitmap(original);
                break;
            case R.id.findIrisBow:
                Bitmap grey = greytemp.Grey(original);
                Bitmap BinarizationPupil = binarizationtemp.Binarize(grey,"Pupil");
                Bitmap BinarizationIris = binarizationtemp.Binarize(grey,"Iris");
                Bitmap sobelPupil = sobeltemp.Sobel(BinarizationPupil);
                Bitmap sobelIris = sobeltemp.Sobel(BinarizationIris);
                Circle pupil = hough.H(sobelPupil, grey,30,70);
                Circle iris = hough.H(sobelIris, grey, 40, 70 );
                Bitmap[] IRISBOW = hough.IrisBow(grey,pupil,iris);
                iv.setImageBitmap(IRISBOW[0]);
                ivNorm.setImageBitmap(IRISBOW[1]);
                break;/*
            case R.id.drawIrisBow:
                HoughDraw hough1 = new HoughDraw();
                Bitmap grey1 = greytemp.Grey(original);
                Bitmap BinarizationPupil1 = binarizationtemp.Binarize(grey1,"Pupil");
                Bitmap BinarizationIris1 = binarizationtemp.Binarize(grey1,"Iris");
                Bitmap sobelPupil1 = sobeltemp.Sobel(BinarizationPupil1);
                Bitmap sobelIris1 = sobeltemp.Sobel(BinarizationIris1);
                Bitmap pupil1 = hough1.H(sobelPupil1, grey1,20,70);
                Bitmap iris1 = hough1.H(sobelIris1, grey1, 40, 70 );
                iv.setImageBitmap(pupil1);
                break;*/
            case R.id.exit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}