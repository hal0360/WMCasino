package tw.com.lixin.wmcasino.Tools;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.Popup;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.App;
import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.RoadHolder;
import tw.com.lixin.wmcasino.models.Table;
import tw.com.lixin.wmcasino.websocketSource.LobbySource;

public class TableSwitchPopup extends Popup {


    public TableSwitchPopup(Context context) {
        super(context, R.layout.table_switch_popup);

        ItemsView roadView = findViewById(R.id.road_item);
        List<RoadHolder> holders = new ArrayList<>();
        RootActivity activity = (RootActivity) context;

        LobbySource source = LobbySource.getInstance();

        for(Table table: source.tables){
            holders.add(new RoadHolder(table, activity));
        }
        roadView.add(holders);

        clicked(R.id.close_btn,v -> dismiss());
    }
}
