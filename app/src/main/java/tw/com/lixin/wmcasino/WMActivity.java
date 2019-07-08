package tw.com.lixin.wmcasino;

import android.content.res.Configuration;
import android.os.Bundle;


import tw.com.atromoby.widgets.RootActivity;

public abstract class WMActivity extends RootActivity {

    private static String preActName = "WMActivity";

    private Boolean actRecreated = false;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);



        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            alert("LANDSCAPE");
        } else {
            alert("PORTRAIT");
        }

    }

    @Override
    public void onResume() {
        super.onResume();


    }

   // public abstract void onWMResume(String text);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            alert("LANDSCAPE");
        } else {
            alert("PORTRAIT");
        }
    }
}
