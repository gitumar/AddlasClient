package com.sonicworkflow.addlas.custom_adapters;

import android.graphics.Bitmap;

/**
 * Created by umarbradshaw on 10/2/15.
 */
public class ImageHolderModel {

    Bitmap image;

    public ImageHolderModel(Bitmap bitmap) {

        image = bitmap;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }


}



