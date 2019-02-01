package tw.com.lixin.wmcasino;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.CmdStr;
import tw.com.lixin.wmcasino.Tools.SettingPopup;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.Client10;
import tw.com.lixin.wmcasino.jsonData.Server35;
import tw.com.lixin.wmcasino.jsonData.data.Game;
import tw.com.lixin.wmcasino.models.Table;

public class LobbyActivity extends SocketActivity {

    ItemsView itemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        setTextView(R.id.member_txt, User.account());

        clicked(R.id.setting_icon, v->{
            new SettingPopup(this).show();
        });

        itemsView = findViewById(R.id.itemsView);
        List<TableHolder> holders = new ArrayList<>();
        for(Table table: App.tables){
            holders.add(new TableHolder(table));
        }
        itemsView.add(holders);


        setTextView(R.id.table_txt, App.tables.size() + "");

        /*
        App.bacSocket.onReceive((mss, pro)->  {
            if(pro == 26) alert("kojkiji");
        });
        App.lobbySocket.onReceive((mss, pro)->  {
            if(pro == 10) alert("kojkiji");
        });
        Client10 client = new Client10(1);
        delay(2000, ()->{
            App.bacSocket.send(Json.to(client));
        });*/


    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...


        App.socket.receive30(data -> {
            if(User.memberID() == data.memberID){
                setTextView(R.id.player_money, data.balance + "");
            }
        });

        if(!App.socket.connected){
          //  App.logout();
            toActivity(LoginActivity.class);
        }

        App.socket.receive26(data->  {
            itemsView.refresh();
        });

        App.socket.receive34(data->  {
            setTextView(R.id.user_online_txt, data.onlinePeople + "");
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!justRecreated) App.logout();
    }

}
