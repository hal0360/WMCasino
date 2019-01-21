package tw.com.lixin.wmcasino;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;

import tw.com.atromoby.widgets.CustomInput;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.SettingPopup;
import tw.com.lixin.wmcasino.Tools.TableSwitchPopup;
import tw.com.lixin.wmcasino.global.Setting;
import tw.com.lixin.wmcasino.global.User;

public class LoginActivity extends RootActivity {

    private CustomInput userIn, passIn;
    private SwitchCompat accountSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userIn = findViewById(R.id.userInput);
        passIn = findViewById(R.id.passInput);
        accountSwitch = findViewById(R.id.accountSwitch);
        accountSwitch.setChecked(Setting.savePassword());
        if(Setting.savePassword()) passIn.setText(Setting.remPass());

       clicked(R.id.loginBtn,v ->{
           String user = userIn.getRawText();
           String pass = passIn.getRawText();
           if(Setting.savePassword()) Setting.remPass(pass);
           User.account(user);
           toActivity(LoadActivity.class, pass);
       });

       clicked(R.id.questBtn, v->{
           User.account("ANONYMOUS");
           toActivity(LoadActivity.class, "1234");
       });

        clicked(R.id.setting_btn, v->{
       // new SettingPopup(this).show();
            new TableSwitchPopup(this).show();
       });

       clicked(accountSwitch, v -> Setting.savePassword(accountSwitch.isChecked()));

    }
}
