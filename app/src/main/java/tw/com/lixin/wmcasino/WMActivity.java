package tw.com.lixin.wmcasino;

import android.os.Bundle;


import tw.com.atromoby.widgets.RootActivity;

public abstract class WMActivity extends RootActivity {

    private static int oreo = -9999;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        int orientation = getResources().getConfiguration().orientation;


    }


}
