package tw.com.lixin.wmcasino;

import tw.com.atromoby.widgets.ItemHolder;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;
import tw.com.lixin.wmcasino.models.Table;

public class RoadHolder extends ItemHolder {

    private Table table;

    public RoadHolder(Table table) {
        super(R.layout.road_modal_item);
    }

    @Override
    public void onBind() {

        CasinoGrid grid = findViewById(R.id.road_modal_grid);
        grid.setGrid(18, 6);
        setTextView(R.id.road_num, table.groupID + "");


    }

    @Override
    public void onRecycle() {

    }
}
