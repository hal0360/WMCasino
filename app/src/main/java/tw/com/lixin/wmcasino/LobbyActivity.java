package tw.com.lixin.wmcasino;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.ItemHolder;
import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.CmdStr;
import tw.com.lixin.wmcasino.Tools.ReportPopup;
import tw.com.lixin.wmcasino.Tools.SettingPopup;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.Client10;
import tw.com.lixin.wmcasino.jsonData.Server35;
import tw.com.lixin.wmcasino.jsonData.data.Game;
import tw.com.lixin.wmcasino.models.EmptyHolder;
import tw.com.lixin.wmcasino.models.Table;

public class LobbyActivity extends SocketActivity {

    ItemsView itemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        setTextView(R.id.member_txt, User.account());
       // setTextView(R.id.member_txt, "\u5e84:\u2666K\u26663\u26662\u95f2:\u2665K\u26633\u2660J");

        alert(User.sid());


        clicked(R.id.setting_icon, v->{
            new SettingPopup(this).show();
        });


        itemsView = findViewById(R.id.itemsView);
        List<ItemHolder> holders = new ArrayList<>();
        for(Table table: App.tables){
            holders.add(new TableHolder(table));
        }

        int tRem = 10 - App.tables.size();
        if(tRem > 0){
            for (int g = 0; g<tRem;g++){
                holders.add(new EmptyHolder());
            }
        }

        itemsView.add(holders);

        setTextView(R.id.table_txt, App.tables.size() + "");

    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

        if(!App.socket.connected){
            App.logout();
            toActivity(LoginActivity.class);
        }

        App.socket.receive26(data->  {
            itemsView.refresh();
        });

        App.socket.receive34(data->  {
            setTextView(R.id.user_online_txt, data.onlinePeople + "");
        });

        setTextView(R.id.player_money, User.balance() + "");



    }

}
