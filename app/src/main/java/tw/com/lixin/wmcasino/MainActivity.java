package tw.com.lixin.wmcasino;

import android.os.Bundle;

import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.global.Setting;
import tw.com.lixin.wmcasino.global.Url;

public class MainActivity extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.lobbySocket.start(Url.Lobby);

        App.lobbySocket.onSuccess(()->{
            App.lobbySocket.cleanCallbacks();
            delay(1000,()->toActivity(LoginActivity.class));
        });

        App.lobbySocket.onFail(()->{
            alert("Error! cannot connect to server;");
        });

        setLanguage(Setting.language());

    }
}
