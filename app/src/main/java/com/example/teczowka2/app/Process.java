package com.example.teczowka2.app;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Magda on 2014-06-19.
 */
public class Process {

    public static void DoIt(ImageView iv, ImageView ivNorm, Bitmap compressedImage, Bitmap NotCompressedImage)
    {
        HoughWersjaElipsa hough = new HoughWersjaElipsa();
        Bitmap grey = GreyScale.process(compressedImage);
        Bitmap greyGauss = GaussianFilter.process(grey);
        Bitmap BinarizationPupil = Binarization.process(greyGauss, "Pupil");
        Bitmap BinarizationIris = Binarization.process(greyGauss, "Iris");
        Bitmap sobelPupil = Sobel.process(BinarizationPupil);
        Bitmap sobelIris = Sobel.process(BinarizationIris);
        Circle pupil = hough.H(sobelPupil,30,70);
        Circle iris = hough.H(sobelIris, 40, 70 );
        if(pupil == null && iris !=null)
        {
            System.out.println("sama teczowka");
            pupil = new Circle(iris.getCenter(),iris.getR()/3);
        }
        else if(pupil != null && iris ==null)
        {
            System.out.println("sama źrenica");
            iris = new Circle(pupil.getCenter(),pupil.getR()*2);
        }
        else if(pupil==null && iris==null)
        {
            System.out.println("nie znaleniono tęczówki");
            iv.setImageBitmap(NotCompressedImage);
        }
        Bitmap[] IRISBOW = hough.IrisBow(grey,pupil,iris);
        iv.setImageBitmap(IRISBOW[0]);
        ivNorm.setImageBitmap(IRISBOW[1]);
    }
}
