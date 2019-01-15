package tw.com.lixin.wmcasino;

import android.os.Bundle;

import java.util.ArrayList;

import tw.com.atromoby.utils.Json;
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
import tw.com.lixin.wmcasino.models.Table;

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
        App.tables = new ArrayList<>();
        App.bacSocket.start(Url.Bac);

        App.lobbySocket.onSuccess(()->{
            LoginData loginData = new LoginData( User.account(), pass);
            App.lobbySocket.send(Json.to(loginData));
        });
        App.bacSocket.onSuccess(()->{
            LoginData loginData = new LoginData( User.account(), pass);
            App.bacSocket.send(Json.to(loginData));
        });
        App.lobbySocket.onFail(()->{
            alert("connection error");
            finish();
        });

        App.lobbySocket.onReceive((mss, pro)->{
            if(pro == 0){
                LoginResData logRespend = Json.from(mss, LoginResData.class);
                if(logRespend.data.bOk){
                    User.account(logRespend.data.account);
                    User.gameID(logRespend.data.gameID);
                    User.userName(logRespend.data.userName);
                    User.memberID(logRespend.data.memberID);
                    App.lobbySocket.send(Json.to(new Client35()));
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
                App.cleanSocketCalls();
                toActivity(LobbyActivity.class);
            }

        });
    }

    private void setTables(){
        for(TableStage tableStage: bacGame.groupArr){
            if (tableStage.groupID != 3 && tableStage.gameStage != 4){
                CasinoRoad casinoRoad = new CasinoRoad();
                casinoRoad.update(tableStage.historyArr);

                Table table = new Table();
                table.casinoRoad = casinoRoad;
                table.stage = tableStage.gameStage;
                table.groupID = tableStage.groupID;
                table.groupType = tableStage.groupType;
                table.score = tableStage.bankerScore;
                table.round = tableStage.gameNoRound;
                table.number = tableStage.gameNo;
                App.tables.add(table);
            }
        }
    }
}
