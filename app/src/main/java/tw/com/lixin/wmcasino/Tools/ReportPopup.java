package tw.com.lixin.wmcasino.Tools;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.Popup;
import tw.com.atromoby.widgets.SpinList;
import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.global.Url;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.ServerMoney;
import tw.com.lixin.wmcasino.jsonData.ServerReport;
import tw.com.lixin.wmcasino.models.QuotaHolder;
import tw.com.lixin.wmcasino.models.RecordHolder;

public class ReportPopup extends Popup {

    private ItemsView recordView;
    private ConstraintLayout quoteHeader, betHeader;
    private Context context;
    private boolean isBet = true;
    private TextView betBtn, quoteBtn;

    public ReportPopup(Context context) {
        super(context, R.layout.report_popup);

        this.context = context;
        recordView = findViewById(R.id.record_view);
        quoteHeader = findViewById(R.id.quote_header);
        betHeader = findViewById(R.id.bet_header);
        betBtn = findViewById(R.id.bet_record_btn);
        quoteBtn = findViewById(R.id.quote_record_btn);
        SpinList spinList = findViewById(R.id.spin_list);
        spinList.init(new String[]{"firstarg", "secondarg", "thirdarg"});
        spinList.setColor("#f8ce82");

       getBets();

        clicked(R.id.close_btn,v -> dismiss());

        clicked(R.id.bet_record_btn, v->{
            betBtn.setBackgroundResource(R.drawable.blue_grad_color);
            quoteBtn.setBackgroundResource(0);
            isBet = true;
            getBets();
        });
        clicked(R.id.quote_record_btn, v->{
            betBtn.setBackgroundResource(0);
            quoteBtn.setBackgroundResource(R.drawable.blue_grad_color);
            isBet = false;
            getBets();
        });

    }

    private void getBets(){
        recordView.delete();
        RequestTask requestTask = new RequestTask(context, Url.Report, null);
        if(isBet) {
            quoteHeader.setVisibility(View.GONE);
            betHeader.setVisibility(View.VISIBLE);
            requestTask.send("cmd=GetMemberReport&lang=cn&sid=" + User.sid() + "&startTime=2018-11-22 00:00:00&endTime=2019-02-20 23:59:59");
            requestTask.onSuccess(r->{
                ServerReport serReport = Json.from(r.content, ServerReport.class);

                for(ServerReport.Report report: serReport.result.report){
                    recordView.add(new RecordHolder(report));
                }

            });
        }else{
            quoteHeader.setVisibility(View.VISIBLE);
            betHeader.setVisibility(View.GONE);
            requestTask.send("cmd=GetMemberMoneyReport&lang=cn&sid=" + User.sid() + "&startTime=2018-11-22 00:00:00&endTime=2019-02-20 23:59:59");
            requestTask.onSuccess(r->{
                ServerMoney serverMoney = Json.from(r.content, ServerMoney.class);

                for(ServerMoney.Report report: serverMoney.result.report){
                    recordView.add(new QuotaHolder(report));
                }

            });
        }

    }
}

