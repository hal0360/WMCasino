package tw.com.lixin.wmcasino;


import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.ItemHolder;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;
import tw.com.lixin.wmcasino.models.Table;

public class RoadHolder extends ItemHolder {

    private Table table;
    private RootActivity activity;

    public RoadHolder(Table table, RootActivity activity) {
        super(R.layout.road_modal_item);
        this.table = table;
        this.activity=activity;
    }

    @Override
    public void onBind() {

        CasinoGrid grid = findViewById(R.id.road_modal_grid);
        grid.setGrid(18, 6);
        setTextView(R.id.road_num, table.groupID + "");
        grid.drawRoad(table.firstGrid);

        clicked(R.id.road_pop_grid,v->{

         //   App.groupID = table.groupID;
       //     App.curTable = table;

           // Client10 client = new Client10(table.groupID);
           // App.socket.send(Json.to(client));
          //  App.cleanSocketCalls();

            activity.toActivity(CasinoActivity.class);
        });


    }

    @Override
    public void onRecycle() {

    }
}
