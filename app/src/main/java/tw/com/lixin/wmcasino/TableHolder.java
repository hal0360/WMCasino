package tw.com.lixin.wmcasino;

import android.widget.TextView;

import java.util.Locale;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.utils.Kit;
import tw.com.atromoby.widgets.ItemHolder;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;
import tw.com.lixin.wmcasino.jsonData.Client10;
import tw.com.lixin.wmcasino.models.Table;
import tw.com.lixin.wmcasino.websocketSource.LobbySource;

public class TableHolder extends ItemHolder {

    private Table table;

    public TableHolder(Table table) {
        super(R.layout.table_item);
        this.table = table;
    }

    @Override
    public void onBind() {

        CasinoGrid grid = findViewById(R.id.road_grid);
        grid.setGrid(28, 6);

        TextView gyuTxt = findViewById(R.id.gyu_shu);
        gyuTxt.setText(getContex().getString(R.string.table_number) + "  " + table.number + " -- " + table.round);
        TextView numTxt = findViewById(R.id.table_num);
        numTxt.setText(String.format(Locale.US,"%02d", table.groupID));

        grid.drawRoad(table.firstGrid);

        clicked(R.id.table_grid,v->{
            WMActivity activity = (WMActivity) getContex();
            LobbySource source = LobbySource.getInstance();
            source.tableLogin(table,ok->{
                if(ok){
                    Kit.alert(activity,"Cannot go to this table ok");
                    activity.toActivity(BacActivity.class);

                }else{
                    Kit.alert(activity,"Cannot go to this table ");
                }
            });

        });

    }

    @Override
    public void onRecycle() {

    }
}
