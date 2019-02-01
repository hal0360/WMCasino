package tw.com.lixin.wmcasino;

import android.os.Bundle;

import tw.com.atromoby.widgets.RootActivity;

public abstract class SocketActivity extends RootActivity {


public boolean justRecreated;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        justRecreated = false;
    }

}
