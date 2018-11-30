package tw.com.lixin.wmcasino;

import android.os.Bundle;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.jsonData.ProData;
import tw.com.lixin.wmcasino.jsonData.Protocol;

public class LoginActivity extends RootActivity {

    CasinoSocket casinoSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Protocol protocol = new Protocol();
       clicked(R.id.loginBtn,v -> casinoSocket.send(Json.to(protocol)));


        casinoSocket = new CasinoSocket("ws://gameserver.a45.me:15109");
        casinoSocket.onReceive(m->{
            ProData proData = Json.from(m,ProData.class);
            if(proData.protocol == 0){
                alert("loged");
            }
        });
    }


}
