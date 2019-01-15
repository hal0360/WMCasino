package tw.com.lixin.wmcasino;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.global.Setting;
import tw.com.lixin.wmcasino.global.Url;
import tw.com.lixin.wmcasino.jsonData.Server35;
import tw.com.lixin.wmcasino.jsonData.data.Game;
import tw.com.lixin.wmcasino.jsonData.data.TableStage;

public class MainActivity extends RootActivity {

    private int mode = 0;
    private Game bacGame;

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
