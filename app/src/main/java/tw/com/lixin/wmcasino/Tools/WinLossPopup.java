package tw.com.lixin.wmcasino.Tools;

import android.content.Context;
import android.widget.TextView;

import tw.com.atromoby.widgets.Popup;
import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.jsonData.Server31;

public class WinLossPopup extends Popup {
    public WinLossPopup(Context context, Server31 server31) {
        super(context, R.layout.win_loss_popup);

        TextView textView = findViewById(R.id.player_bet);
    }
}
