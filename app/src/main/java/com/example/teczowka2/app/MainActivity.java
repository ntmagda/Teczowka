package com.example.teczowka2.app;

import android.content.Intent;
import android.graphics.Bitmap;
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
                original = ((BitmapDrawable) oko.getDrawable()).getBitmap();
                ImageView iv = (ImageView) findViewById(R.id.oko);
                iv.setImageBitmap(okobit);

            }
            if (resultCode == RESULT_OK) {
                if (requestCode == 2) {
                    Bundle extras = data.getExtras();
                    Bitmap oko = (Bitmap) extras.get("data");
                    original = (Bitmap) extras.get("data");
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
                Bitmap IRISBOW = hough.IrisBow(grey,pupil,iris);
                iv.setImageBitmap(IRISBOW);
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}