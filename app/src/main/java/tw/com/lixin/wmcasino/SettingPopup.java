package tw.com.lixin.wmcasino;

import android.view.Gravity;

import tw.com.atromoby.widgets.Language;
import tw.com.atromoby.widgets.Popup;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.global.Setting;

public class SettingPopup {

    private Popup popup;

    public SettingPopup(RootActivity context){
        popup = new Popup(context, R.layout.setting_popup);
        popup.setGravity(Gravity.TOP|Gravity.END);

        popup.clicked(R.id.englishBtn,v->{
            Setting.language(Language.ENGLISH);
            context.switchLanguage(Language.ENGLISH);
            dismiss();
        });

        popup.clicked(R.id.taiwaneseBtn,v->{
            Setting.language(Language.CHINESE_TW);
            context.switchLanguage(Language.CHINESE_TW);
            dismiss();
        });

        popup.clicked(R.id.chineseBtn,v->{
            Setting.language(Language.CHINESE_CH);
            context.switchLanguage(Language.CHINESE_CH);
            dismiss();
        });

        popup.clicked(R.id.logoutBtn,v->{
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
