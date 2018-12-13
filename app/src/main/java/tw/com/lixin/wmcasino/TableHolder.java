package tw.com.lixin.wmcasino;

import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tw.com.atromoby.widgets.ItemHolder;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;
import tw.com.lixin.wmcasino.jsonData.data.TableStage;

public class TableHolder extends ItemHolder {

    private TableStage stage;

    public TableHolder(TableStage stage) {
        super(R.layout.table_item);
        this.stage = stage;
    }

    @Override
    public void onBind() {

        CasinoGrid grid = findView(R.id.road_grid);
        grid.setGrid(29, 6);
        grid.insertImage(5,2, R.drawable.casino_roadplay);
        TextView gyuTxt = findView(R.id.gyu_shu);
        gyuTxt.setText("局数  " + stage.gameNo + " -- " + stage.gameNoRound);
        TextView numTxt = findView(R.id.table_num);
        numTxt.setText("00" + stage.groupID);

    }

    //public static arrange

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

    @Override
    public void onClean() {


    }
}
