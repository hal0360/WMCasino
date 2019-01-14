package tw.com.lixin.wmcasino;

import android.os.Bundle;

import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.global.Setting;
import tw.com.lixin.wmcasino.global.Url;

public class MainActivity extends RootActivity {

    private int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int mode = getPassedInt();

        if(mode == 1){

        }else if(mode == 2){

        }else {
            App.lobbySocket.start(Url.Lobby);

            App.lobbySocket.onSuccess(()->{
                App.lobbySocket.cleanCallbacks();
                App.bacSocket.start(Url.Bac);
            });

            App.bacSocket.onSuccess(()->{
                App.bacSocket.cleanCallbacks();
                delay(600, () -> toActivity(LoginActivity.class));
            });
        }



    }
}
