package tw.com.lixin.wmcasino;

import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import tw.com.atromoby.widgets.ItemHolder;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;

public class TableHolder extends ItemHolder {

    public TableHolder() {
        super(R.layout.table_item);
    }

    @Override
    public void onBind() {

        CasinoGrid grid = findView(R.id.road_grid);
        grid.setGrid(29, 6);
        grid.insertImage(5,2, R.drawable.casino_roadplay);
        TextView gyuTxt = findView(R.id.gyu_shu);

    }

    @Override
    public void onClean() {


    }
}
