package tw.com.lixin.wmcasino.Tools;

import android.content.Context;
import android.util.Log;
;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.Popup;
import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.ServerReport;
import tw.com.lixin.wmcasino.models.RecordHolder;

public class ReportPopup extends Popup {

    private ItemsView recordView;

    public ReportPopup(Context context) {
        super(context, R.layout.report_popup);


        recordView = findViewById(R.id.record_view);
        recordView.add(new RecordHolder(null));
        recordView.add(new RecordHolder(null));
        recordView.add(new RecordHolder(null));
        recordView.add(new RecordHolder(null));
        recordView.add(new RecordHolder(null));
        recordView.add(new RecordHolder(null));
        recordView.add(new RecordHolder(null));
        recordView.add(new RecordHolder(null));
        recordView.add(new RecordHolder(null));
        recordView.add(new RecordHolder(null));


        RequestTask requestTask = new RequestTask(context, "http://p36-api.chenkui102.cn/api/web/Gateway.php", null);
        requestTask.onSuccess(r->{


            Log.e("money", r.content);

          //  ServerReport serReport = Json.from(r.content, ServerReport.class);

          //  for(ServerReport.Report report: serReport.result.report){
           //     recordView.add(new RecordHolder(report));
          //  }

        });

        requestTask.send("cmd=GetMemberMoneyReport&lang=cn&sid=" + User.sid() + "&startTime=2018-11-22 00:00:00&endTime=2019-02-20 23:59:59");


        /*
        RequestTask requestTask = new RequestTask(context, "http://p36-api.chenkui102.cn/api/web/Gateway.php", null);
        requestTask.onSuccess(r->{


            ServerReport serReport = Json.from(r.content, ServerReport.class);

            for(ServerReport.Report report: serReport.result.report){
                recordView.add(new RecordHolder(report));
            }

        });
        requestTask.send("cmd=GetMemberReport&lang=cn&sid=" + User.sid() + "&startTime=2018-11-22 00:00:00&endTime=2019-02-20 23:59:59");
        */


        clicked(R.id.close_btn,v -> dismiss());

    }
}

