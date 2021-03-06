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
import tw.com.lixin.wmcasino.Tools.ReportPopup;
import tw.com.lixin.wmcasino.Tools.SettingPopup;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.interfaces.LobbyBridge;
import tw.com.lixin.wmcasino.jsonData.Client10;
import tw.com.lixin.wmcasino.jsonData.Client35;
import tw.com.lixin.wmcasino.jsonData.Server35;
import tw.com.lixin.wmcasino.jsonData.data.Game;

import tw.com.lixin.wmcasino.models.EmptyHolder;
import tw.com.lixin.wmcasino.models.Group;
import tw.com.lixin.wmcasino.models.Table;
import tw.com.lixin.wmcasino.models.VerticalEmptyHolder;
import tw.com.lixin.wmcasino.models.VerticalTableHolder;
import tw.com.lixin.wmcasino.websocketSource.LobbySource;

public class LobbyActivity extends WMActivity implements LobbyBridge {

    ItemsView itemsView;
    LobbySource source;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        source = LobbySource.getInstance();

        setTextView(R.id.member_txt, User.account());

        clicked(R.id.setting_icon, v->{
            new SettingPopup(this).show();
        });

        itemsView = findViewById(R.id.itemsView);
        List<ItemHolder> holders = new ArrayList<>();
        for(Table table: source.tables){
            if (!isPortrait()) {
                holders.add(new TableHolder(table));
            } else {
                holders.add(new VerticalTableHolder(table));
            }
        }

        int tRem = 10 - source.tables.size();
        if(tRem > 0){
            for (int g = 0; g<tRem;g++){
                if (!isPortrait()) {
                    holders.add(new EmptyHolder());
                } else {
                    holders.add(new VerticalEmptyHolder());
                }
            }
        }

        itemsView.add(holders);

        setTextView(R.id.table_txt, source.tables.size() + "");
    }

    @Override
    public void onResume(){
        super.onResume();

        source.bindLobby(this);
        if(source.isConnected()) return;
        loading();
        source.login(User.sid(),data->{
            unloading();
            alert("reconnected");
        }, fail->{
            source.close();
            toActivity(LoginActivity.class);
            alert(fail);
            unloading();
        });

        /*
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
        }*/

    }

    @Override
    public void onPause() {
        super.onPause();
        source.unbind();
    }


    @Override
    public void onBackPressed() {
     //   App.logout();
        super.onBackPressed();
    }

    @Override
    public void wholeDataUpdated() {

    }

    @Override
    public void balanceUpdated() {

    }

    @Override
    public void peopleOnlineUpdate(int number) {
        setTextView(R.id.user_online_txt, number + "");
    }

    @Override
    public void nineUpdate() {

    }
}
