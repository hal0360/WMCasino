package tw.com.lixin.wmcasino.Tools;

import android.view.Gravity;

import tw.com.atromoby.widgets.Language;
import tw.com.atromoby.widgets.Popup;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.global.Setting;

public class SettingPopup {

    private Popup popup;

    public SettingPopup(RootActivity context){
        popup = new Popup(context, R.layout.setting_popup, R.style.SettingCasDialog);
        popup.setGravity(Gravity.TOP|Gravity.END);

        popup.clicked(R.id.english_btn,v->{
            Setting.language(Language.ENGLISH);
          //  context.switchLanguage(Language.ENGLISH);
            dismiss();
        });

        popup.clicked(R.id.chinese_sim_btn,v->{
            Setting.language(Language.CHINESE_TW);
           // context.switchLanguage(Language.CHINESE_TW);
            dismiss();
        });


    }

    public void dismiss(){
        popup.dismiss();
    }

    public void show(){
        popup.show();
    }
}
