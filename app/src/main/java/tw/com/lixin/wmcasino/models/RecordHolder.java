package tw.com.lixin.wmcasino.models;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;

import tw.com.atromoby.widgets.ItemHolder;
import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.jsonData.ServerReport;

public class RecordHolder extends ItemHolder {

    private ServerReport.Report report;

    public RecordHolder(ServerReport.Report re){
        super(R.layout.report_modal_item);
        report = re;
    }

    @Override
    public void onBind() {

        ConstraintLayout root = findViewById(R.id.root_view);
        if(position%2 == 0){
            root.setBackgroundColor(Color.parseColor("#e1e1e1"));
        }else{
            root.setBackgroundColor(Color.parseColor("#c0c0c0"));
        }


        if(report == null){
            return;
        }



        setTextView(R.id.bet_time, report.betTime);
        setTextView(R.id.game_name, report.gname);
        setTextView(R.id.event_child, report.event + "_" + report.eventChild);
        setTextView(R.id.bet_result, report.betId + ":" + report.betResult + "@" + report.bet);
        setTextView(R.id.game_result, report.gameResult);
        setTextView(R.id.win_result, report.bet);
    }

    @Override
    public void onRecycle() {

    }
}
