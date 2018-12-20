package tw.com.lixin.wmcasino;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.ItemHolder;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;
import tw.com.lixin.wmcasino.Tools.CasinoRoad;
import tw.com.lixin.wmcasino.jsonData.CasinoData;
import tw.com.lixin.wmcasino.jsonData.data.TableStage;

public class TableHolder extends ItemHolder {

    private TableStage stage;
    private int[][] gridNum;

    public TableHolder(TableStage stage) {
        super(R.layout.table_item);
        this.stage = stage;
        gridNum = new int[50][7];
    }

    @Override
    public void onBind() {

    }

    @Override
    public void onRecycle() {

    }

    @Override
    public void onCreate() {

        int xx = 0;
        int yy = 0;

        CasinoGrid grid = findView(R.id.road_grid);
        grid.setGrid(28, 6);

        TextView gyuTxt = findView(R.id.gyu_shu);
        gyuTxt.setText("局数  " + stage.gameNo + " -- " + stage.gameNoRound);
        TextView numTxt = findView(R.id.table_num);
        numTxt.setText("00" + stage.groupID);

        CasinoRoad road  = new CasinoRoad(grid);
        road.setGrid();


        clicked(R.id.table_img,v->{
            alert(Json.to(stage.historyArr));
        });

        clicked(R.id.table_grid,v->{
            Intent intent = new Intent(getContex(), CasinoActivity.class);
            getContex().startActivity(intent);
        });

        /*
        String asstxt = "";

        for(int his: stage.historyArr){

            if(xx < 28) {

                List<Integer> twoIns = CasinoData.divide(his);
                int val = twoIns.get(twoIns.size() - 1);
                asstxt = asstxt + val + ",";

                if(val == 1){
                    grid.insertImage(xx,yy, R.drawable.bank_1);
                }else if( val == 2){
                    grid.insertImage(xx,yy, R.drawable.play_1);
                }else if( val == 4){
                    grid.insertImage(xx,yy, R.drawable.play_5);
                }else if( val == 8){
                    grid.insertImage(xx,yy, R.drawable.bank_2);
                }else if( val == 16){
                    grid.insertImage(xx,yy, R.drawable.play_2);
                }
                xx++;
            }
        }
        gyuTxt.setText(asstxt);
        */
    }

    public static List<Integer> divide(int val){

        List<Integer> powers = new ArrayList<>();
        for(int i = 8; i >= 0; i-- ){
            int boss = (int) Math.pow(2,i);
            if(val >= boss){
                powers.add(boss);
                val = val - boss;
                if(val == 0){
                    return powers;
                }
            }
        }
        return powers;
    }

}
