package tw.com.lixin.wmcasino;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Locale;


import tw.com.atromoby.widgets.CustomInput;
import tw.com.atromoby.widgets.Popup;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.LoadDialog;
import tw.com.lixin.wmcasino.global.Setting;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.interfaces.LobbyBridge;
import tw.com.lixin.wmcasino.websocketSource.LobbySource;

public class LoginActivity extends RootActivity implements LobbyBridge {

    private CustomInput userIn, passIn;
    private SwitchCompat accountSwitch;
    private Popup popup;
    private LoadDialog dialog;
    private LobbySource source;

    private static int oreo = -999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        alert(getClass().getSimpleName());


        dialog = new LoadDialog(this);
        source = LobbySource.getInstance();
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
           User.userName(passIn.getRawText());
           User.account(userIn.getRawText());

          // toActivity(LoadActivity.class, pass);
       });

       clicked(R.id.questBtn, v->{
           User.account("ANONYMOUS");
           toActivity(LoadActivity.class, "1234");
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
        source.bind(this);
        Log.e("onclose", "resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onclose", "pause");
    }


    @Override
    public void wholeDataUpdated() {

    }

    @Override
    public void balanceUpdated() {

    }

    @Override
    public void peopleOnlineUpdate(int number) {

    }
}
