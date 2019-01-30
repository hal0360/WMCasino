package tw.com.lixin.wmcasino;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tw.com.atromoby.rtmplayer.IjkVideoView;
import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.Popup;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;
import tw.com.lixin.wmcasino.Tools.CasinoRoad;
import tw.com.lixin.wmcasino.Tools.CmdStr;
import tw.com.lixin.wmcasino.Tools.CoinStack;
import tw.com.lixin.wmcasino.Tools.FourthRoad;
import tw.com.lixin.wmcasino.Tools.LobbySocket;
import tw.com.lixin.wmcasino.Tools.Move;
import tw.com.lixin.wmcasino.Tools.SecRoad;
import tw.com.lixin.wmcasino.Tools.TableSwitchPopup;
import tw.com.lixin.wmcasino.Tools.ThirdRoad;
import tw.com.lixin.wmcasino.global.Poker;
import tw.com.lixin.wmcasino.global.Url;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.Client10;
import tw.com.lixin.wmcasino.jsonData.Client22;
import tw.com.lixin.wmcasino.jsonData.LoginData;
import tw.com.lixin.wmcasino.jsonData.Server10;
import tw.com.lixin.wmcasino.jsonData.Server20;
import tw.com.lixin.wmcasino.jsonData.Server22;
import tw.com.lixin.wmcasino.jsonData.Server24;
import tw.com.lixin.wmcasino.jsonData.Server25;
import tw.com.lixin.wmcasino.jsonData.Server26;
import tw.com.lixin.wmcasino.jsonData.Server31;
import tw.com.lixin.wmcasino.models.Table;

public class CasinoActivity extends RootActivity {


private int posX, posY;
private Animation fadeAnime;

    public ItemsView coinsView;
    private Popup winPopup;
    private int groupID, areaID;
    private TextView gameStageTxt, pokerBall, playerScreenScore, bankerScreenScore;
    private boolean videoIsLarge = false;
    public boolean canBet = false;
    private boolean loggedIn = false;
    private boolean comission = false;
    public CoinHolder curCoin;
    private CasinoGrid mainGrid, firstGrid, secGrid, thirdGrid, fourthGrid;
    private IjkVideoView video;
    private ImageView logo, playerPoker1, playerPoker2, playerPoker3, bankerPoker1, bankerPoker2, bankerPoker3, comissionBtn, cancelBtn, repeatBtn, confrimBtn;
    private ConstraintLayout videoContaner, pokerContainer, countdownBox;
    private CoinStack stackLeft, stackRight, stackTop, stackBTL, stackBTR;
    private Client22 client22;
    private ImageView bankSecondSym, bankThirdSym, bankFourthSym, playerSecondSym, playerThirdSym, playerFourthSym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino);

        fadeAnime = AnimationUtils.loadAnimation(this, R.anim.prediction_fade);
        groupID = getPassedInt();
        App.groupID = groupID;
        confrimBtn = findViewById(R.id.confirm_bet_btn);
        cancelBtn = findViewById(R.id.cancel_bet_btn);
        repeatBtn = findViewById(R.id.repeat_bet_btn);
        comissionBtn = findViewById(R.id.comission_btn);

        bankFourthSym = findViewById(R.id.bank_fourth_sym);
        bankThirdSym = findViewById(R.id.bank_third_sym);
        bankSecondSym = findViewById(R.id.bank_second_sym);
        playerSecondSym = findViewById(R.id.player_second_sym);
        playerThirdSym = findViewById(R.id.player_third_sym);
        playerFourthSym = findViewById(R.id.player_fourth_sym);

         countdownBox = findViewById(R.id.countdown);
        winPopup = new Popup(this,R.layout.win_loss_popup);
        playerScreenScore = findViewById(R.id.player_screen_score);
        bankerScreenScore = findViewById(R.id.banker_screen_score);
         gameStageTxt = findViewById(R.id.stage_info_txt);
         addAllCoins();
         logo = findViewById(R.id.lobby_logo);
         videoContaner = findViewById(R.id.videoContaner);
         mainGrid = findViewById(R.id.main_grid);
         firstGrid = findViewById(R.id.first_grid);
         secGrid = findViewById(R.id.second_grid);
         thirdGrid = findViewById(R.id.third_grid);
         fourthGrid = findViewById(R.id.fourth_grid);
         stackBTL = findViewById(R.id.table_bt_l_stack);
         stackBTR = findViewById(R.id.table_bt_r_stack);
         stackTop = findViewById(R.id.table_top_stack);
         stackLeft = findViewById(R.id.table_left_stack);
         stackRight = findViewById(R.id.table_right_stack);
         pokerContainer = findViewById(R.id.poker_layout);
         playerPoker1 = findViewById(R.id.player_poker1);
         playerPoker2 = findViewById(R.id.player_poker2);
         playerPoker3 = findViewById(R.id.player_poker3);
         bankerPoker1 = findViewById(R.id.banker_poker1);
         bankerPoker2 = findViewById(R.id.banker_poker2);
         bankerPoker3 = findViewById(R.id.banker_poker3);
         pokerBall = findViewById(R.id.poker_ball);
         resetPokers();
         setTextView(R.id.gyu_shu,getString(R.string.table_number) + " " + App.curTable.number + " -- " + App.curTable.round);

         treeObserve(mainGrid,v -> {
             double dim = mainGrid.getHeight() / 6;
             int wid = (int) Math.round(dim*14);
             mainGrid.setLayoutParams(new ConstraintLayout.LayoutParams(wid, ConstraintLayout.LayoutParams.MATCH_PARENT));
             mainGrid.setGrid(14,6);
             treeObserve(thirdGrid,vv -> {
                 double width = thirdGrid.getWidth();
                 double dim2 = thirdGrid.getHeight() / 3;
                 int wGrid = (int) Math.round(width/dim2);
                 firstGrid.setGrid(wGrid*2,6);
                 secGrid.setGridDouble(wGrid*2 , 3);
                 thirdGrid.setGridDouble(wGrid,3);
                 fourthGrid.setGridDouble(wGrid,3);
                 setMainGrid();
             });
         });

         clicked(R.id.table_left,v ->{
             if(stackLeft.add(curCoin.img_res, curCoin.value))
                 client22.addBet(2,curCoin.value);
         });
         clicked(R.id.table_right,v ->{
             if(stackRight.add(curCoin.img_res, curCoin.value))
                 client22.addBet(1,curCoin.value);
         });
         clicked(R.id.table_top,v ->{
             if(stackTop.add(curCoin.img_res, curCoin.value))
                 client22.addBet(3,curCoin.value);
         });
         clicked(R.id.table_bt_l,v ->{
             if(stackBTL.add(curCoin.img_res, curCoin.value))
                 client22.addBet(5,curCoin.value);
         });
         clicked(R.id.table_bt_r,v -> {
             if(stackBTR.add(curCoin.img_res, curCoin.value))
                 client22.addBet(4,curCoin.value);
         });

         clicked(R.id.fullscreen_btn,v -> {
             if(!videoIsLarge){
                 Move.toCenter(this, findViewById(R.id.root), videoContaner);
                 videoIsLarge = true;
             }else{
                 videoIsLarge = false;
                 Move.back(videoContaner);
                 logo.bringToFront();
             }
         });

         clicked(R.id.scroll_left_btn, v->{
            coinsView.smoothScrollToPosition(0);
        });

        clicked(R.id.scroll_right_btn, v->{
            coinsView.smoothScrollToPosition(15);
        });

clicked(R.id.back_btn, v->{
    finish();
});

         clicked(comissionBtn, v -> {
             if(canBet){
                 if(comission){
                     client22.data.commission = 0;
                     comission = false;
                     comissionBtn.setImageResource(R.drawable.casino_item_btn_super6);
                 }else{
                     client22.data.commission = 1;
                     comission = true;
                     comissionBtn.setImageResource(R.drawable.casino_item_btn_super6_a);
                 }
             }
         });

         clicked(R.id.switch_table_btn, v -> {
             new TableSwitchPopup(this).show();
         });

         clicked(confrimBtn, v -> {
             if(canBet) {
                 if (client22.data.betArr.size() > 0) App.socket.send(Json.to(client22));
                 else alert("You haven't put any money!");
             }
         });

         clicked(cancelBtn,v -> {
             if(canBet) resetCoinStacks();
         });

         clicked(R.id.bankBtn, v -> {
             if (App.curTable.secRoadPreB.posYY >= 0 && secGrid.width > App.curTable.secRoadPreB.posXX) {
                View secView = secGrid.insertImage(App.curTable.secRoadPreB.posXX, App.curTable.secRoadPreB.posYY, App.curTable.secRoadPreB.getLastRid());
                secView.startAnimation(fadeAnime);
             }
             if (App.curTable.thirdRoadPreB.posYY >= 0 && thirdGrid.width > App.curTable.thirdRoadPreB.posXX) {
                 View thiView = thirdGrid.insertImage(App.curTable.thirdRoadPreB.posXX, App.curTable.thirdRoadPreB.posYY, App.curTable.thirdRoadPreB.getLastRid());
                 thiView.startAnimation(fadeAnime);
             }
             if (App.curTable.fourthRoadPreB.posYY >= 0 && fourthGrid.width > App.curTable.fourthRoadPreB.posXX) {
                 View forView = fourthGrid.insertImage(App.curTable.fourthRoadPreB.posXX, App.curTable.fourthRoadPreB.posYY, App.curTable.fourthRoadPreB.getLastRid());
                 forView.startAnimation(fadeAnime);
             }
             View mainView = mainGrid.insertImage(posX,posY,R.drawable.casino_roadbank);
             mainView.startAnimation(fadeAnime);

             if(App.curTable.casinoRoad.posY >= 0){

             }

         });

        clicked(R.id.playBtn, v -> {
            if (App.curTable.secRoadPreP.posYY >= 0 && secGrid.width > App.curTable.secRoadPreP.posXX) {
                View secView = secGrid.insertImage(App.curTable.secRoadPreP.posXX, App.curTable.secRoadPreP.posYY, App.curTable.secRoadPreP.getLastRid());
                secView.startAnimation(fadeAnime);
            }
            if (App.curTable.thirdRoadPreP.posYY >= 0 && thirdGrid.width > App.curTable.thirdRoadPreP.posXX) {
                View thiView = thirdGrid.insertImage(App.curTable.thirdRoadPreP.posXX, App.curTable.thirdRoadPreP.posYY, App.curTable.thirdRoadPreP.getLastRid());
                thiView.startAnimation(fadeAnime);
            }
            if (App.curTable.fourthRoadPreP.posYY >= 0 && fourthGrid.width > App.curTable.fourthRoadPreP.posXX) {
                View forView = fourthGrid.insertImage(App.curTable.fourthRoadPreP.posXX, App.curTable.fourthRoadPreP.posYY, App.curTable.fourthRoadPreP.getLastRid());
                forView.startAnimation(fadeAnime);
            }
            View mainView = mainGrid.insertImage(posX,posY,R.drawable.casino_roadplay);
            mainView.startAnimation(fadeAnime);
        });

        App.socket.receive20(data -> {
            if(data.gameStage == 0){
                gameStageTxt.setText("洗牌中");
            }else if(data.gameStage == 1){
                gameStageTxt.setText("請下注");
                winPopup.dismiss();
                resetPokers();
                resetCoinStacks();
            }else if(data.gameStage == 2){
                stopAllBetBtn();
                gameStageTxt.setText("開牌中");
                pokerContainer.setVisibility(View.VISIBLE);
            }else if(data.gameStage == 3){
                gameStageTxt.setText("結算中");
            }else{
                gameStageTxt.setText("已關桌");
                finish();
            }
        });

        App.socket.receive24(data -> {
            Log.e("kknd", data.cardArea + "");
            if(data.cardArea == 1){
                playerPoker1.setImageResource(Poker.NUM(data.cardID));
                playerPoker1.setVisibility(View.VISIBLE);
            }else if(data.cardArea == 2){
                bankerPoker1.setImageResource(Poker.NUM(data.cardID));
                bankerPoker1.setVisibility(View.VISIBLE);
            }else if(data.cardArea == 4){
                bankerPoker2.setImageResource(Poker.NUM(data.cardID));
                bankerPoker2.setVisibility(View.VISIBLE);
            }else if(data.cardArea == 6){
                bankerPoker3.setImageResource(Poker.NUM(data.cardID));
                bankerPoker3.setVisibility(View.VISIBLE);
            }else if(data.cardArea == 3){
                playerPoker2.setImageResource(Poker.NUM(data.cardID));
                playerPoker2.setVisibility(View.VISIBLE);
            }else if(data.cardArea == 5){
                playerPoker3.setImageResource(Poker.NUM(data.cardID));
                playerPoker3.setVisibility(View.VISIBLE);
            }
        });

        App.socket.receive22(data -> {
            if(data.bOk){
                alert("Bet successful");
                stopAllBetBtn();
            }else alert("Error occurred when betting, try again");
        });

        App.socket.receive26(this::setMainGrid);

        App.socket.receive31(data -> {
            TextView mText = winPopup.findViewById(R.id.player_bet);
            mText.setText(stackLeft.value);
            mText = winPopup.findViewById(R.id.banker_bet);
            mText.setText(stackRight.value);
            mText = winPopup.findViewById(R.id.player_pair_bet);
            mText.setText(stackBTL.value);
            mText = winPopup.findViewById(R.id.banker_pair_bet);
            mText.setText(stackBTR.value);
            mText = winPopup.findViewById(R.id.tie_bet);
            mText.setText(stackTop.value);
            mText = winPopup.findViewById(R.id.player_win);
            mText.setText(data.dtMoneyWin.get(2));
            mText = winPopup.findViewById(R.id.banker_win);
            mText.setText(data.dtMoneyWin.get(1));
            mText = winPopup.findViewById(R.id.player_pair_win);
            mText.setText(data.dtMoneyWin.get(5));
            mText = winPopup.findViewById(R.id.banker_pair_win);
            mText.setText(data.dtMoneyWin.get(4));
            mText = winPopup.findViewById(R.id.tie_win);
            mText.setText(data.dtMoneyWin.get(3));
            mText = winPopup.findViewById(R.id.total_win_money);
            mText.setText(data.moneyWin);
            winPopup.show();
        });

        App.socket.receive25(data -> {
            Log.e("kknd", "receive25");
            countdownBox.setBackgroundResource(R.drawable.casino_countdown);
            int pokerWin = Move.divide(data.result);
            if(pokerWin == 1){
                pokerBall.setText(getString(R.string.banker_score));
                pokerBall.setBackgroundResource(R.drawable.casino_item_bt_bank);
            }else if(pokerWin == 2){
                pokerBall.setText(getString(R.string.player_score));
                pokerBall.setBackgroundResource(R.drawable.casino_item_bt_player);
            }else{
                pokerBall.setText(getString(R.string.tie_score));
                pokerBall.setBackgroundResource(R.drawable.casino_item_bt_bank);
            }
            playerScreenScore.setText(getString(R.string.player_score) + data.playerScore);
            bankerScreenScore.setText(getString(R.string.banker_score) + data.bankerScore);
            pokerBall.setVisibility(View.VISIBLE);
        });

        App.socket.receive38(data -> {
            recurSec(data.timeMillisecond/1000);
        });

        App.socket.receive10(data -> {
            Log.e("kknd10", data.areaID + "");
            if(data.bOk ){
                loggedIn = true;
                setTextView(R.id.table_left_score, data.dtOdds.get(2));
                setTextView(R.id.table_right_score, data.dtOdds.get(1));
                setTextView(R.id.table_bt_l_score, data.dtOdds.get(5));
                setTextView(R.id.table_bt_r_score, data.dtOdds.get(4));
                setTextView(R.id.table_top_score, data.dtOdds.get(3));
                stackLeft.maxValue = data.maxBet02;
                stackBTL.maxValue = data.maxBet04;
                stackRight.maxValue = data.maxBet01;
                stackBTR.maxValue = data.maxBet04;
                stackTop.maxValue = data.maxBet03;
                areaID = data.areaID;
                setTextView(R.id.player_money, data.balance + "");

                //String path = "rtmp://wmvdo.nicejj.cn/live" + (groupID > 100 ? groupID - 100 : groupID) + "/stream1";
                 String path = "rtmp://wmvdo.c2h6.cn/ytb" + String.format(Locale.US,"%02d", groupID) + "-1/stream1";
                 video = findViewById(R.id.player);
                 video.setVideoPath(path);
                 video.start();
            }else{
                alert("Access denied");
                finish();
            }
        });
        Client10 client = new Client10(groupID);
        App.socket.send(Json.to(client));
    }

    private void recurSec(int sec){
        gameStageTxt.setText("請下注" + sec);
        if(canBet && sec > 1){
            if(sec < 6) countdownBox.setBackgroundResource(R.drawable.casino_countdown2);
            delay(1000, ()-> recurSec(sec - 1));
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }

    @Override
    public void onResume(){
        super.onResume();
        Client10 client = new Client10(groupID);
        App.socket.send(Json.to(client));

    }

    private void setMainGrid(){
        int indexx = 0;

        bankSecondSym.setImageResource(App.curTable.secRoadPreB.getLastRid());
        bankThirdSym.setImageResource(App.curTable.thirdRoadPreB.getLastRid());
        bankFourthSym.setImageResource(App.curTable.fourthRoadPreB.getLastRid());
        playerSecondSym.setImageResource(App.curTable.secRoadPreP.getLastRid());
        playerThirdSym.setImageResource(App.curTable.thirdRoadPreP.getLastRid());
        playerFourthSym.setImageResource(App.curTable.fourthRoadPreP.getLastRid());

        firstGrid.drawRoad(App.curTable.casinoRoad);
        secGrid.drawSecRoad(App.curTable.secRoad);
        thirdGrid.drawThirdRoad(App.curTable.thirdRoad);
        fourthGrid.drawForthRoad(App.curTable.fourthRoad);
        for(int x = 0; x < mainGrid.width; x++){
            for(int y = 0; y < mainGrid.height; y++){
                if(indexx >= App.curTable.casinoRoad.bigRoad.size() ) return;
                mainGrid.insertImage(x,y,App.curTable.casinoRoad.bigRoad.get(indexx));
                indexx++;
                posX = x;
                posY = y;
            }
        }
    }

    private void stopAllBetBtn(){
        canBet = false;
        cancelBtn.setAlpha(0.5f);
        confrimBtn.setAlpha(0.5f);
        repeatBtn.setAlpha(0.5f);
        comissionBtn.setAlpha(0.5f);
    }

    private void resetCoinStacks(){
        canBet = true;
        stackTop.reset();
        stackBTR.reset();
        stackRight.reset();
        stackBTL.reset();
        stackLeft.reset();
        client22 = new Client22(groupID, areaID);
        if(comission) client22.data.commission = 1;
        else client22.data.commission = 0;
        cancelBtn.setAlpha(1f);
        confrimBtn.setAlpha(1f);
        repeatBtn.setAlpha(1f);
        comissionBtn.setAlpha(1f);
    }

    private void resetPokers(){
        playerPoker3.setVisibility(View.INVISIBLE);
        playerPoker2.setVisibility(View.INVISIBLE);
        playerPoker1.setVisibility(View.INVISIBLE);
        bankerPoker1.setVisibility(View.INVISIBLE);
        bankerPoker2.setVisibility(View.INVISIBLE);
        bankerPoker3.setVisibility(View.INVISIBLE);
        pokerContainer.setVisibility(View.GONE);
        pokerBall.setVisibility(View.INVISIBLE);
        playerScreenScore.setText("");
        bankerScreenScore.setText("");
    }

    private void addAllCoins(){
        coinsView = findViewById(R.id.coinsView);
        List<CoinHolder> coins = new ArrayList<>();
        coins.add(new CoinHolder(R.drawable.casino_item_chip_1, 1));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_5, 5));
        curCoin = new CoinHolder(R.drawable.casino_item_chip_10, 10);
        curCoin.selected = true;
        coins.add(curCoin);
        coins.add(new CoinHolder(R.drawable.casino_item_chip_20, 20));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_50, 50));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_100, 100));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_500, 500));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_1k, 1000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_5k, 5000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_10k, 10000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_20k, 20000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_50k, 50000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_100k, 100000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_200k, 200000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_1m, 1000000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_5m, 5000000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_10m, 10000000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_50m, 50000000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_100m, 100000000));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_200m, 200000000));
        coinsView.add(coins);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        video.stopPlayback();
        App.cleanSocketCalls();
    }
}
