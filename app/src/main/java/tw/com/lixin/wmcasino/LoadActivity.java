package tw.com.lixin.wmcasino;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.CasinoRoad;
import tw.com.lixin.wmcasino.Tools.FourthRoad;
import tw.com.lixin.wmcasino.Tools.SecRoad;
import tw.com.lixin.wmcasino.Tools.ThirdRoad;
import tw.com.lixin.wmcasino.global.Url;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.Client35;
import tw.com.lixin.wmcasino.jsonData.LoginData;
import tw.com.lixin.wmcasino.jsonData.LoginResData;
import tw.com.lixin.wmcasino.jsonData.data.Game;
import tw.com.lixin.wmcasino.jsonData.data.TableStage;
import tw.com.lixin.wmcasino.models.Table;

public class LoadActivity extends RootActivity {

    private Game bacGame;
    private ImageView loadImg;
    private Map<String, Integer> loadings = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        loadings.put("loading1", R.drawable.loading1);
        loadings.put("loading2", R.drawable.loading2);
        loadings.put("loading3", R.drawable.loading3);
        loadings.put("loading4", R.drawable.loading4);
        loadings.put("loading5", R.drawable.loading5);
        loadings.put("loading6", R.drawable.loading6);
        loadings.put("loading7", R.drawable.loading7);
        loadings.put("loading8", R.drawable.loading8);
        loadings.put("loading9", R.drawable.loading9);
        loadings.put("loading10", R.drawable.loading10);
        loadings.put("loading11", R.drawable.loading11);
        loadings.put("loading12", R.drawable.loading12);
        loadings.put("loading13", R.drawable.loading13);

    }

    private void recurLoad(int loadI){
        loadImg.setImageResource(loadings.get("loading" + loadI));
        loadI++;
        if(loadI > 13) loadI = 1;
        int finalLoadI = loadI;
        delay(80, ()-> recurLoad(finalLoadI));
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadImg = findViewById(R.id.load_img);
        recurLoad(1);

        String pass = getPassedStr();
        LoginData loginData = new LoginData( User.account(), pass);
        App.socket.send(Json.to(loginData));
        App.tables = new ArrayList<>();

        App.socket.receive35(data -> {
            for(Game game: data.gameArr){
                if (game.gameID == 101)
                    bacGame = game;
            }
            setTables();
            App.cleanSocketCalls();
            toActivity(LobbyActivity.class);
        });
        App.socket.onLogin((mss)->{

            LoginResData logRespend = Json.from(mss, LoginResData.class);
            if(logRespend.protocol == 0){
                if(logRespend.data.bOk){
                    User.account(logRespend.data.account);
                    User.gameID(logRespend.data.gameID);
                    User.userName(logRespend.data.userName);
                    User.memberID(logRespend.data.memberID);
                    App.socket.send(Json.to(new Client35()));
                }else {
                    alert("Cannot login");
                    finish();
                }
            }

        });


    }

    private void setTables(){
        for(TableStage tableStage: bacGame.groupArr){
            if ( tableStage.gameStage != 4){
                CasinoRoad casinoRoad = new CasinoRoad(tableStage.historyArr);
                Table table = new Table();
                table.casinoRoad = casinoRoad;
                table.stage = tableStage.gameStage;
                table.groupID = tableStage.groupID;
                table.groupType = tableStage.groupType;
                table.score = tableStage.bankerScore;
                table.round = tableStage.gameNoRound;
                table.number = tableStage.gameNo;

                table.secRoad = new SecRoad(table.casinoRoad.sortedRoad);
                table.secRoadPreB = new SecRoad(table.casinoRoad.sortedRoadB);
                table.secRoadPreP = new SecRoad(table.casinoRoad.sortedRoadP);

                table.thirdRoad = new ThirdRoad(table.casinoRoad.sortedRoad);
                table.thirdRoadPreB = new ThirdRoad(table.casinoRoad.sortedRoadB);
                table.thirdRoadPreP = new ThirdRoad(table.casinoRoad.sortedRoadP);

                table.fourthRoad = new FourthRoad(table.casinoRoad.sortedRoad);
                table.fourthRoadPreB = new FourthRoad(table.casinoRoad.sortedRoadB);
                table.fourthRoadPreP = new FourthRoad(table.casinoRoad.sortedRoadP);


                App.tables.add(table);
            }
        }
    }
}
