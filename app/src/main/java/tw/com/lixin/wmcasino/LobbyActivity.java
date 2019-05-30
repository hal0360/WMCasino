package tw.com.lixin.wmcasino;

import android.content.res.Configuration;
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
import tw.com.lixin.wmcasino.models.Group;
import tw.com.lixin.wmcasino.models.Table;
import tw.com.lixin.wmcasino.models.VerticalEmptyHolder;
import tw.com.lixin.wmcasino.models.VerticalTableHolder;

public class LobbyActivity extends SocketActivity {

    ItemsView itemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);


if(App.socket.cmdOpen == null){
    alert("confirmed");
}else{
    alert("negative");
}

        setTextView(R.id.member_txt, User.account());
       // setTextView(R.id.member_txt, "\u5e84:\u2666K\u26663\u26662\u95f2:\u2665K\u26633\u2660J");
        int orientation = getResources().getConfiguration().orientation;

        clicked(R.id.setting_icon, v->{
            new SettingPopup(this).show();
        });

        itemsView = findViewById(R.id.itemsView);
        List<ItemHolder> holders = new ArrayList<>();
        for(Table table: App.tables){
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                holders.add(new TableHolder(table));
            } else {
                holders.add(new VerticalTableHolder(table));
            }
        }

        int tRem = 10 - App.tables.size();
        if(tRem > 0){
            for (int g = 0; g<tRem;g++){
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    holders.add(new EmptyHolder());
                } else {
                    holders.add(new VerticalEmptyHolder());
                }
            }
        }

        itemsView.add(holders);

        setTextView(R.id.table_txt, App.tables.size() + "");

    }

    public void addHolders(){
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
        } else {
            // In portrait
        }
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

        App.socket.receive10(data -> {
            if (data.bOk) {

               // App.cleanSocketCalls();

                App.group.data10 = data;
                App.group.areaID = data.areaID;
                App.group.groupID = App.groupID;

                pushActivity(CasinoActivity.class);

            } else alert("Cannot login to this table");
        });

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setTextView(R.id.player_money, User.balance() + "");
        }

    }

    @Override
    public void onBackPressed() {
        App.logout();
        super.onBackPressed();
    }

}
