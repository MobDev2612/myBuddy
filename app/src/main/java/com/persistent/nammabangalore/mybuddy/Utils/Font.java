package com.persistent.nammabangalore.mybuddy.Utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by swamy_hariharan on 3/12/2016.
 */
public class Font {
    public static final String ROOT = "",
            FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }


}
