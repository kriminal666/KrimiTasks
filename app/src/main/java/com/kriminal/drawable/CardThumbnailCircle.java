package com.kriminal.drawable;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.kriminal.main_activity.R;

import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * Created by Kriminal on 28/01/2016.
 */
public class CardThumbnailCircle extends CardThumbnail {

    private float mElevationCircle;
    private int mSizeCircle;

    public CardThumbnailCircle(Context context) {
        super(context);

        float density = getContext().getResources().getDisplayMetrics().density;
        int size = (int) (70*density);
        //setUrlResource("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s" + size + "/new%2520profile%2520%25282%2529.jpg");
        setErrorResource(R.drawable.error_center_x);
        this.mElevationCircle = context.getResources().getDimension(R.dimen.circle_elevation);
        this.mSizeCircle = context.getResources().getDimensionPixelSize(R.dimen.circle_size);

    }

    @Override

    public boolean applyBitmap(final View imageView, Bitmap bitmap) {

        CircleDrawable circle = new CircleDrawable(bitmap);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            imageView.setBackground(circle);
        else
            imageView.setBackgroundDrawable(circle);


        ViewCompat.setElevation(imageView, mElevationCircle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            imageView.setOutlineProvider(
                    new ViewOutlineProvider(){
                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void getOutline(View view, Outline outline) {
                            outline.setOval(0, 0, mSizeCircle, mSizeCircle);
                        }
                    });
            imageView.setClipToOutline(true);

        }


            /*imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                imageView.animate().setDuration(100).scaleX(1.1f).scaleY(1.1f).translationZ(10);
                            } else {
                                imageView.animate().setDuration(100).scaleX(1.1f).scaleY(1.1f);
                            }
                            return true;
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                imageView.animate().setDuration(100).scaleX(1).scaleY(1).translationZ(0);
                            } else {
                                imageView.animate().setDuration(100).scaleX(1).scaleY(1);
                            }
                            return true;
                    }
                    return false;
                }
            });*/


        return true;

    }
}
