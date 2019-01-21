package tw.com.lixin.wmcasino;

import android.widget.TextView;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.ItemHolder;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;
import tw.com.lixin.wmcasino.models.Table;

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
        gyuTxt.setText("局数  " + table.number + " -- " + table.round);
        TextView numTxt = findViewById(R.id.table_num);
        numTxt.setText("0" + table.groupID);

        grid.drawRoad(table.casinoRoad);

        clicked(R.id.table_grid,v->{
            RootActivity activity = (RootActivity) getContex();
            activity.pushActivity(CasinoActivity.class, table.groupID);
        });

    }

    @Override
    public void onRecycle() {

    }
}
