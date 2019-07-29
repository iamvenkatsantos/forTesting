package com.onedata.sunexchange.logintask.bean;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.onedata.sunexchange.logintask.R;

@Animate(Animation.CARD_LEFT_IN_DESC)
@NonReusable
@Layout(R.layout.activity_kyc_gallery_view)
public class GalleryItem {
    ImageView[] imageViews = new ImageView[3];
 //imageViews[0].setImageDrawable(mDrawable);
    @View(R.id.myImage)
    private ImageView imageView;

    private Drawable mDrawable;

    private Bitmap bitmap;

    public GalleryItem(Bitmap bitmap) {
       this.bitmap = bitmap;
    }

    @Resolve
    private void onResolved() {
        imageView.setImageBitmap(bitmap);
    }

}
