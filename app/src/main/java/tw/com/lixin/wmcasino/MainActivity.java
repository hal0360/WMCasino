package tw.com.lixin.wmcasino;

import android.os.Bundle;

import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.global.Casino;

public class MainActivity extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Casino.user() == null){
            delay(1000,()->toActivity(LoginActivity.class));
        }else{
            delay(1000,()->toActivity(LobbyActivity.class));
        }

    }
}
