package tw.com.lixin.wmcasino;

import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import tw.com.atromoby.widgets.ItemHolder;

public class TableHolder extends ItemHolder {

    public TableHolder() {
        super(R.layout.table_item);
    }

    @Override
    public void onBind() {

        ImageView myImage;
        Resources res = getContex().getResources();

        TableLayout tableLayout = findView(R.id.road_grid);
        View view = null;

        for(int i=0; i<6; i++){
            TableRow tr_head = new TableRow(getContex());
            tr_head.setDividerDrawable(res.getDrawable(R.drawable.table_divider));
            tr_head.setShowDividers(TableRow.SHOW_DIVIDER_MIDDLE);
            tr_head.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.FILL_PARENT, 1.0f));

            for(int j=0; j<30; j++){
                 view = new View(getContex());
                 view.setLayoutParams(new TableRow.LayoutParams(
                         0,
                         TableLayout.LayoutParams.FILL_PARENT, 1.0f));
              //  view.setBackgroundResource(R.drawable.casino_roadplay);
                tr_head.addView(view);
            }
            tableLayout.addView(tr_head);
        }

        view.setBackgroundResource(R.drawable.casino_roadplay);

    }

    @Override
    public void onClean() {

    }
}
