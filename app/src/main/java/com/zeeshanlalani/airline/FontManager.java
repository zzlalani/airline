package com.zeeshanlalani.airline;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Hashtable;

/**
 * Created by zzlal on 12/4/2015.
 * Ref: http://code.tutsplus.com/tutorials/how-to-use-fontawesome-in-an-android-app--cms-24167
 */
public class FontManager {
    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface getTypeface(Context context, String font) {
        Typeface tf = fontCache.get(font);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), font);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(font, tf);
        }
        return tf;
    }

    // ...
    public static void markAsIconContainer(View v, Typeface typeface) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                markAsIconContainer(child, typeface);
            }
        } else if (v instanceof TextView) {
            ((TextView) v).setTypeface(typeface);
        }
    }
}
