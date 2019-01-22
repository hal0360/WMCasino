package tw.com.lixin.wmcasino;

import android.content.Context;

import tw.com.atromoby.widgets.ItemHolder;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;
import tw.com.lixin.wmcasino.models.Table;

public class RoadHolder extends ItemHolder {

    private Table table;
    private Context context;

    public RoadHolder(Context context, Table table) {
        super(R.layout.road_modal_item);
        this.table = table;
        this.context = context;
    }

    @Override
    public void onBind() {

        CasinoGrid grid = findViewById(R.id.road_modal_grid);
        grid.setGrid(18, 6);
        setTextView(R.id.road_num, table.groupID + "");
        grid.drawRoad(table.casinoRoad);

        clicked(R.id.road_pop_grid,v->{
            RootActivity activity = (RootActivity) context;
            activity.toActivity(CasinoActivity.class, table.groupID);
        });

    }

    @Override
    public void onRecycle() {

    }
}
