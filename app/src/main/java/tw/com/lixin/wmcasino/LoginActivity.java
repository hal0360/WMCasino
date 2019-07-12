package tw.com.lixin.wmcasino;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Locale;


import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.CustomInput;
import tw.com.atromoby.widgets.Popup;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.LoadDialog;
import tw.com.lixin.wmcasino.global.Setting;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.interfaces.LobbyBridge;
import tw.com.lixin.wmcasino.jsonData.Client35;
import tw.com.lixin.wmcasino.websocketSource.LobbySource;

public class LoginActivity extends WMActivity implements LobbyBridge {

    private CustomInput userIn, passIn;
    private SwitchCompat accountSwitch;
    private Popup popup;
    private LobbySource source;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        source = LobbySource.getInstance();
        source.bind(this);
        popup = new Popup(this,R.layout.login_setting_pop,R.style.SettingCasDialog);
        popup.setGravity(Gravity.TOP|Gravity.END);
        popup.clicked(R.id.english_btn,v -> {
            switchLocale(Locale.US);
            popup.dismiss();
        });
        popup.clicked(R.id.chinese_sim_btn,v -> {
            switchLocale(Locale.CHINA);
            popup.dismiss();
        });

        userIn = findViewById(R.id.userInput);
        passIn = findViewById(R.id.passInput);
        accountSwitch = findViewById(R.id.accountSwitch);
        accountSwitch.setChecked(Setting.savePassword());
        if(Setting.savePassword()) userIn.setText(User.userName());

       clicked(R.id.loginBtn,v ->{
           User.userName(userIn.getRawText());
           User.account(userIn.getRawText());
           loading();
           source.login(userIn.getRawText(),passIn.getRawText(),data->{
               User.account(data.account);
               User.gameID(data.gameID);
               User.userName(data.userName);
               User.memberID(data.memberID);
               User.sid(data.sid);
               toActivity(LobbyActivity.class);
           }, fail->{
               unloading();
                alert(fail);
           });


          // toActivity(LoadActivity.class, pass);
       });

       clicked(R.id.questBtn, v->{
           User.account("ANONYMOUS");
           User.userName("ANONYMOUS");
           loading();
           source.login("ANONYMOUS","1234",data->{
               unloading();
               User.account(data.account);
               User.gameID(data.gameID);
               User.userName(data.userName);
               User.memberID(data.memberID);
               User.sid(data.sid);
               toActivity(LobbyActivity.class);
           }, fail->{
               unloading();
               alert(fail);
           });
       });

        clicked(R.id.setting_btn, v->{
            popup.show();
       });

     //   setTextView(R.id.table_txt, 4 + "");

       clicked(accountSwitch, v -> Setting.savePassword(accountSwitch.isChecked()));

       // setTextView(R.id.user_online_txt, 4 + "");
    }

    @Override
    public void onResume() {
        super.onResume();

        if(source.isConnected()) return;
        loading();
        source.login("ANONYMOUS","1234",data->{
            User.account(data.account);
            User.gameID(data.gameID);
            User.userName(data.userName);
            User.memberID(data.memberID);
            User.sid(data.sid);
            source.send(Json.to(new Client35()));
        }, fail->{
            alert(fail);
            unloading();
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        source.unbind();

    }


    @Override
    public void wholeDataUpdated() {
        unloading();
        alert(source.tables.size()+"");
    }

    @Override
    public void balanceUpdated() {

    }

    @Override
    public void peopleOnlineUpdate(int number) {
        if(!isPortrait()){
            setTextView(R.id.user_online_txt, number+"");
        }
    }
}
