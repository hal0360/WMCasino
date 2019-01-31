package tw.com.lixin.wmcasino.Tools;

import android.view.Gravity;

import java.util.Locale;

import tw.com.atromoby.widgets.Language;
import tw.com.atromoby.widgets.Popup;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.App;
import tw.com.lixin.wmcasino.LoginActivity;
import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.global.Setting;

public class SettingPopup {

    private Popup popup;

    public SettingPopup(RootActivity context){
        popup = new Popup(context, R.layout.setting_popup, R.style.SettingCasDialog);
        popup.setGravity(Gravity.TOP|Gravity.END);

        popup.clicked(R.id.english_btn,v->{
            Setting.language(Language.ENGLISH);
            context.switchLocale(Locale.US);
            dismiss();
        });

        popup.clicked(R.id.chinese_sim_btn,v->{
            Setting.language(Language.CHINESE_TW);
            context.switchLocale(Locale.CHINA);
            dismiss();
        });

        popup.clicked(R.id.logout_btn, v -> {
            App.logout();
            context.toActivity(LoginActivity.class);
        });

    }

    public void dismiss(){
        popup.dismiss();
    }

    public void show(){
        popup.show();
    }
}
