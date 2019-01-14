package tw.com.lixin.wmcasino.Tools;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import tw.com.lixin.wmcasino.R;

public class Move {

    public static void toCenter(Activity context, View root, View view){
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics( dm );
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();
        int originalPos[] = new int[2];
        view.getLocationOnScreen( originalPos );
        int xDest = dm.widthPixels/2;
        xDest -= (view.getMeasuredWidth()/2);
        int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;
        view.bringToFront();
        view.animate().scaleX(1.5f).scaleY(1.5f).translationX(xDest - originalPos[0]).translationY(yDest - originalPos[1]).setDuration(700).start();
    }

    public static void back(View view){
        view.animate().scaleX(1.0f).scaleY(1.0f).translationX(0).translationY(0).setDuration(700).start();
    }


    public static void disableClipOnParents(View v) {
        if (v.getParent() == null) {
            return;
        }

        if (v instanceof ViewGroup) {
            ((ViewGroup) v).setClipChildren(false);
        }

        if (v.getParent() instanceof View) {
            disableClipOnParents((View) v.getParent());
        }
    }
}
