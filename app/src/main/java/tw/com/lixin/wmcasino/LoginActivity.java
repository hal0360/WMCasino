package tw.com.lixin.wmcasino;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;

import dmax.dialog.SpotsDialog;
import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.Animate;
import tw.com.atromoby.widgets.CustomInput;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.global.Setting;
import tw.com.lixin.wmcasino.global.Url;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.LoginData;
import tw.com.lixin.wmcasino.jsonData.LoginResData;

public class LoginActivity extends RootActivity {

    private CasinoSocket casinoSocket;
    private CustomInput userIn, passIn;
    private SpotsDialog dialog;
    private SwitchCompat accountSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userIn = findViewById(R.id.userInput);
        passIn = findViewById(R.id.passInput);
        dialog = new SpotsDialog(this,"Please wait...");
        casinoSocket = new CasinoSocket();
        accountSwitch = findViewById(R.id.accountSwitch);
        accountSwitch.setChecked(Setting.savePassword());
        if(Setting.savePassword()) passIn.setText(Setting.remPass());

       clicked(R.id.loginBtn,v ->{
           dialog.show();
           String user = userIn.getRawText();
           String pass = passIn.getRawText();
           LoginData loginData = new LoginData(user, pass);
           casinoSocket.send(Json.to(loginData));
           if(Setting.savePassword()) Setting.remPass(pass);
       });

       clicked(R.id.questBtn, v->{
           dialog.show();
           LoginData loginData = new LoginData("ANONYMOUS", "1234");
           casinoSocket.send(Json.to(loginData));
       });

        clicked(R.id.setting_btn, v->{
        new SettingPopup(this).show();
       });

       clicked(accountSwitch, v -> Setting.savePassword(accountSwitch.isChecked()));

        casinoSocket.onReceive((mss, pro)->{
            if(pro == 0){
                dialog.dismiss();
                LoginResData logRespend = Json.from(mss, LoginResData.class);
                if(logRespend.data.bOk){
                    User.account(logRespend.data.account);
                    User.gameID(logRespend.data.gameID);
                    User.userName(logRespend.data.userName);
                    User.memberID(logRespend.data.memberID);
                    toActivity(LobbyActivity.class, Animate.FADE);
                }else {
                    alert("Cannot login");
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        casinoSocket.start(Url.Lobby);
    }

    @Override
    public void onPause() {
        super.onPause();
        casinoSocket.close();
    }
}
