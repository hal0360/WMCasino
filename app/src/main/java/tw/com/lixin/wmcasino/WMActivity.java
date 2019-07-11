package tw.com.lixin.wmcasino;

import android.content.res.Configuration;
import android.os.Bundle;


import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.LoadDialog;
import tw.com.lixin.wmcasino.models.VerticalTableHolder;

public abstract class WMActivity extends RootActivity {

    private LoadDialog loadDialog;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        loadDialog = new LoadDialog(this);


    }

    public void loading(){
        loadDialog.show();
    }

    public void unloading(){
        loadDialog.dismiss();
    }

    public boolean isPortrait(){
        return getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE;
    }

    public int orientation(){
        return getResources().getConfiguration().orientation;
    }

}
