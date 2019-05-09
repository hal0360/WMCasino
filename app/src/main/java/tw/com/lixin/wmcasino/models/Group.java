package tw.com.lixin.wmcasino.models;

import android.util.Log;
import android.view.View;

import tw.com.lixin.wmcasino.App;
import tw.com.lixin.wmcasino.CasinoActivity;
import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.Tools.CasinoGroupBridge;
import tw.com.lixin.wmcasino.global.Poker;

public class Group {

    private CasinoGroupBridge bridge;
    private boolean cardIsOpening = true;
    private boolean isBettingNow = true;
    private int groupID, areaID;

    public Group(CasinoGroupBridge activity){

        bridge = activity;

        App.socket.receive20(data -> {
            isBettingNow = false;
            cardIsOpening = false;
            bridge.CardStatus(data);
        });

        App.socket.receive10(data -> {
            if (data.bOk) {
                App.data10 = data;
                int maxBetVal = App.data10.maxBet01;
                if(maxBetVal < App.data10.maxBet02) maxBetVal = App.data10.maxBet02;
                if(maxBetVal < App.data10.maxBet03) maxBetVal = App.data10.maxBet03;
                if(maxBetVal < App.data10.maxBet04) maxBetVal = App.data10.maxBet04;
                areaID = App.data10.areaID;
                bridge.loginStatus(data);
            } else {


               // alert("Access denied");
               // onBackPressed();
            }
        });

        App.socket.receive24(data -> {
            bridge.cardArea(data.cardArea);
        });

        App.socket.receive23(data -> {
            bridge.balance(data.balance);
        });


        App.socket.receive22(data -> {
            if (data.bOk) {
                bridge.betOK();
            }

        });

        App.socket.receive26(data -> {

            bridge.betOK();
/*
            clearAskViews();
            setMainGrid();
            setTextView(R.id.gyu_shu, getString(R.string.table_number) + " " + App.curTable.number + " -- " + App.curTable.round);
            setTextView(R.id.banker_count, data.historyData.bankerCount + "");
            setTextView(R.id.player_count, data.historyData.playerCount + "");
            setTextView(R.id.tie_count, data.historyData.tieCount + "");
            setTextView(R.id.bank_pair_count, data.historyData.bankerPairCount + "");
            setTextView(R.id.play_pair_count, data.historyData.playerPairCount + "");*/
        });

    }

}
