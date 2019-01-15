package tw.com.lixin.wmcasino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.Animate;
import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.CasinoRoad;
import tw.com.lixin.wmcasino.global.Url;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.Client35;
import tw.com.lixin.wmcasino.jsonData.LoginData;
import tw.com.lixin.wmcasino.jsonData.LoginResData;
import tw.com.lixin.wmcasino.jsonData.Server35;
import tw.com.lixin.wmcasino.jsonData.data.Game;
import tw.com.lixin.wmcasino.jsonData.data.TableStage;

public class LoadActivity extends RootActivity {

    private Server35 server35;
    private Game bacGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String pass = getPassedStr();
        App.lobbySocket.start(Url.Lobby);

        App.lobbySocket.onSuccess(()->{
            App.lobbySocket.cleanCallbacks();
            App.bacSocket.start(Url.Bac);
        });

        App.bacSocket.onSuccess(()->{
            App.bacSocket.cleanCallbacks();

            LoginData loginData = new LoginData( User.account(), pass);
            App.lobbySocket.send(Json.to(loginData));
        });

        App.lobbySocket.onReceive((mss, pro)->{
            if(pro == 0){
                LoginResData logRespend = Json.from(mss, LoginResData.class);
                if(logRespend.data.bOk){
                    User.account(logRespend.data.account);
                    User.gameID(logRespend.data.gameID);
                    User.userName(logRespend.data.userName);
                    User.memberID(logRespend.data.memberID);
                    LoginData loginData = new LoginData( User.account(), pass);
                    App.bacSocket.send(Json.to(loginData));
                }else {
                    alert("Cannot login");
                    finish();
                }
            }else if(pro == 35){
                server35 = Json.from(mss, Server35.class);
                //App.games = server35.data.gameArr;
                for(Game game: server35.data.gameArr){
                    if (game.gameID == 101)
                        bacGame = game;
                }
                setTables();
            }


        });

        App.lobbySocket.onReceive((mss, pro)->{
            if(pro == 0){
                LoginResData logRespend = Json.from(mss, LoginResData.class);
                if(logRespend.data.bOk){
                    App.lobbySocket.send(Json.to(new Client35()));



                    App.lobbySocket.cleanCallbacks();
                    App.bacSocket.cleanCallbacks();
                    toActivity(LobbyActivity.class);
                }else {
                    alert("Cannot login");
                    finish();
                }
            }
        });

    }

    private void setTables(){
        for(TableStage tableStage: bacGame.groupArr){
            if (tableStage.groupID != 3 && tableStage.gameStage != 4){
                CasinoRoad casinoRoad = new CasinoRoad();
                casinoRoad.update(tableStage.historyArr);

            }
        }
    }
}
