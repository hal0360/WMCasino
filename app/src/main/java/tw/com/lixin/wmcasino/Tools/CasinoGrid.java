package tw.com.lixin.wmcasino.Tools;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import tw.com.lixin.wmcasino.R;

public class CasinoGrid extends TableLayout {

    private View[][] viewGrid;
    public int width, height;
    private Context context;

    public CasinoGrid(Context context)
    {
        super(context);
        this.context = context;
    }

    public CasinoGrid(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
    }

    public View insertImage(int x, int y, int image_res){
        viewGrid[x][y].setBackgroundResource(image_res);
        return viewGrid[x][y];
    }

    public void setGrid(int x, int y){

        width = x;
        height = y;

        this.removeAllViews();
        viewGrid = new View[x][y];
        View view;

        for(int i=0; i<y; i++){

            Resources res = context.getResources();
            TableRow tr_head = new TableRow(context);
            tr_head.setDividerDrawable(res.getDrawable(R.drawable.table_divider));
            tr_head.setShowDividers(TableRow.SHOW_DIVIDER_MIDDLE);
            tr_head.setLayoutParams(new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, 1.0f));

            for(int j=0; j<x; j++){
                view = new View(context);
                view.setLayoutParams(new TableRow.LayoutParams(
                        0,
                        LayoutParams.MATCH_PARENT, 1.0f));

                tr_head.addView(view);
                viewGrid[j][i] = view;
            }
            this.addView(tr_head);

        }

    }

}
