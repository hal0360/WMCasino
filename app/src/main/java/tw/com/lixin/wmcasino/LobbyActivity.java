package tw.com.lixin.wmcasino;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.Animate;
import tw.com.atromoby.widgets.ItemHolder;
import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.global.Url;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.Client35;
import tw.com.lixin.wmcasino.jsonData.LoginData;
import tw.com.lixin.wmcasino.jsonData.LoginResData;
import tw.com.lixin.wmcasino.jsonData.Server35;
import tw.com.lixin.wmcasino.jsonData.data.Game;
import tw.com.lixin.wmcasino.jsonData.data.TableStage;

public class LobbyActivity extends RootActivity {

    private Server35 server35;
    private Game bacGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        TextView mTxt = findViewById(R.id.member_txt);
        mTxt.setText(User.account());

        App.lobbySocket.onReceive((mss, pro)->{
            if(pro == 35){
                server35 = Json.from(mss, Server35.class);
                //App.games = server35.data.gameArr;
                for(Game game: server35.data.gameArr){
                    if (game.gameID == 101)
                        bacGame = game;
                }
                setTables();
            }
        });

        clicked(R.id.setting_icon, v->{
       //     alert(Json.to(server35.data.gameArr));
        });

        App.lobbySocket.send(Json.to(new Client35()));

    }

    private void setTables(){
        ItemsView itemsView = findViewById(R.id.itemsView);
        List<TableHolder> holders = new ArrayList<>();
        for(TableStage tableStage: bacGame.groupArr){
            if (tableStage.groupID != 3 && tableStage.gameStage != 4)
                holders.add(new TableHolder(tableStage));
        }
        itemsView.add(holders);
    }

}
