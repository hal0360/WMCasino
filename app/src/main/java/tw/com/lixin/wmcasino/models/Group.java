package tw.com.lixin.wmcasino.models;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import tw.com.atromoby.utils.CountDown;
import tw.com.lixin.wmcasino.App;
import tw.com.lixin.wmcasino.CasinoActivity;
import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.Tools.CasinoGroupBridge;
import tw.com.lixin.wmcasino.Tools.CoinStackBack;
import tw.com.lixin.wmcasino.Tools.Move;
import tw.com.lixin.wmcasino.global.Poker;
import tw.com.lixin.wmcasino.jsonData.Server10;

public class Group {

    public boolean comission = false;

    public CasinoGroupBridge bridge;
    public boolean cardIsOpening = true;
    public boolean isBettingNow = true;
    public int groupID, areaID;
    public CountDown countDownTimer;
    public CoinStackBack leftBack, rightBack, topBack, lowRightbBack, lowLeftBack, superBack;

    public Server10.Data data10;

    public int[] pokers;

    public Group(){

        countDownTimer = new CountDown();
        leftBack = new CoinStackBack();
        rightBack = new CoinStackBack();
        topBack = new CoinStackBack();
        lowLeftBack = new CoinStackBack();
        lowRightbBack = new CoinStackBack();
        superBack = new CoinStackBack();
        pokers = new int[6];

        App.socket.receive20(data -> {
            isBettingNow = false;
            cardIsOpening = false;

            if (data.gameStage == 2) {
                cardIsOpening = true;
                countDownTimer.cancel();
            }else if (data.gameStage == 1) {
                isBettingNow = true;
            }

            if(bridge != null) bridge.CardStatus(data);
        });

        App.socket.receive24(data -> {

            if (data.cardArea == 3) {
                pokers[0] = Poker.NUM(data.cardID);

             //   playerPoker1.setImageResource(Poker.NUM(data.cardID));
               // playerPoker1.setVisibility(View.VISIBLE);
            } else if (data.cardArea == 2) {
                pokers[3] = Poker.NUM(data.cardID);

              //  bankerPoker1.setImageResource(Poker.NUM(data.cardID));
              //  bankerPoker1.setVisibility(View.VISIBLE);
            } else if (data.cardArea == 4) {
                pokers[4] = Poker.NUM(data.cardID);

             //  bankerPoker2.setImageResource(Poker.NUM(data.cardID));
               // bankerPoker2.setVisibility(View.VISIBLE);
            } else if (data.cardArea == 6) {
                pokers[5] = Poker.NUM(data.cardID);

              //  bankerPoker3.setImageResource(Poker.NUM(data.cardID));
              //  bankerPoker3.setVisibility(View.VISIBLE);
            } else if (data.cardArea == 1) {
                pokers[1] = Poker.NUM(data.cardID);
              //  playerPoker2.setImageResource(Poker.NUM(data.cardID));
               // playerPoker2.setVisibility(View.VISIBLE);
            } else if (data.cardArea == 5) {
                pokers[2] = Poker.NUM(data.cardID);
               // playerPoker3.setImageResource(Poker.NUM(data.cardID));
              //  playerPoker3.setVisibility(View.VISIBLE);
            }

            if(bridge != null) bridge.cardArea(data);
        });

        App.socket.receive23(data -> {
            if(bridge != null)  bridge.balance(data.balance);
        });


        App.socket.receive22(data -> {
            if (data.bOk) {
                if(bridge != null)  bridge.betOK();
            }

        });

        App.socket.receive26(data -> {
            if(bridge != null)  bridge.gridUpdate(data);
        });

        App.socket.receive31(data -> {
            if(bridge != null)  bridge.moneWon(data);
        });

        App.socket.receive25(data -> {
            if(bridge != null)  bridge.winLossResult(data);
        });

        App.socket.receive38(data ->{

            countDownTimer.start(data.timeMillisecond, i->{

                if(!cardIsOpening){
                    if(bridge != null)   bridge.betCountdown(i);


                }

            });

        });

    }

    public void setUp(CasinoGroupBridge bridge){
        this.bridge = bridge;
    }

}
