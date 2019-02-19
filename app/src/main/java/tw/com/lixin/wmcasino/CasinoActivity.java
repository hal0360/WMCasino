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
import tw.com.lixin.wmcasino.Tools.SettingPopup;
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

public class CasinoActivity extends SocketActivity {


private int posX, posY;
private Animation fadeAnimeB;

private Move move;
    public ItemsView coinsView;
    private Popup winPopup;
    private int groupID, areaID;
    private TextView gameStageTxt, pokerBall, playerScreenScore, bankerScreenScore;
    public boolean canBet = false;
    private boolean comission = false;
    public CoinHolder curCoin;
    private CasinoGrid mainGrid, firstGrid, secGrid, thirdGrid, fourthGrid;
    private IjkVideoView video;
    private ImageView logo, playerPoker1, playerPoker2, playerPoker3, bankerPoker1, bankerPoker2, bankerPoker3, comissionBtn, cancelBtn, repeatBtn, confrimBtn;
    private ConstraintLayout videoContaner, pokerContainer, countdownBox, gameContainer, root, tableRight, tableSuper, tableTop, tableLeft;
    private CoinStack stackLeft, stackRight, stackTop, stackBTL, stackBTR, stackSuper;
    private ImageView bankSecondSym, bankThirdSym, bankFourthSym, playerSecondSym, playerThirdSym, playerFourthSym;
    private View fullscreenBlanket;
    private List<CoinHolder> tempCoinAdd;

    private boolean canCancelBet = false;
    private boolean cardOpening = false;
    private boolean canRepeatBet = false;


    private boolean viewIsZoomed = false;
    public void viewZoomOut(View view){
        if(viewIsZoomed){
            move.back();
            viewIsZoomed = false;
            logo.bringToFront();
        }else{

            move.toCenter(view);
            viewIsZoomed = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino_two);

        fadeAnimeB = AnimationUtils.loadAnimation(this, R.anim.prediction_fade);

        groupID = App.groupID;
        String path = "rtmp://wmvdo.c2h6.cn/ytb" + String.format(Locale.US,"%02d", groupID) + "-1/stream1";
        video = findViewById(R.id.player);
        video.setVideoPath(path);
        video.start();


        root = findViewById(R.id.root);
        confrimBtn = findViewById(R.id.confirm_bet_btn);
        cancelBtn = findViewById(R.id.cancel_bet_btn);
        repeatBtn = findViewById(R.id.repeat_bet_btn);
        comissionBtn = findViewById(R.id.comission_btn);
        gameContainer = findViewById(R.id.game_container);
        setTextView(R.id.table_num, groupID + "");
        bankFourthSym = findViewById(R.id.bank_fourth_sym);
        bankThirdSym = findViewById(R.id.bank_third_sym);
        bankSecondSym = findViewById(R.id.bank_second_sym);
        playerSecondSym = findViewById(R.id.player_second_sym);
        playerThirdSym = findViewById(R.id.player_third_sym);
        playerFourthSym = findViewById(R.id.player_fourth_sym);
        tableRight = findViewById(R.id.table_right);
        tableSuper = findViewById(R.id.table_super);
        tableTop = findViewById(R.id.table_top);
        tableLeft = findViewById(R.id.table_left);

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

       treeObserve(root,v->{
            move = new Move(this, root);
       });

         treeObserve(mainGrid,v -> {
             double dim = mainGrid.getHeight() / 6;
             int wid = (int) Math.round(dim*14);
             mainGrid.getLayoutParams().width = wid;
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
             if(stackLeft.add(curCoin)){
                 cancelBtn.setAlpha(1f);
                 canCancelBet = true;
             }
         });
         clicked(tableRight,v ->{
             if(stackRight.add(curCoin)){
                 cancelBtn.setAlpha(1f);
                 canCancelBet = true;
             }
         });
         clicked(R.id.table_top,v ->{
             if(stackTop.add(curCoin)){
                 cancelBtn.setAlpha(1f);
                 canCancelBet = true;
             }
         });
         clicked(R.id.table_bt_l,v ->{
             if(stackBTL.add(curCoin)){
                 cancelBtn.setAlpha(1f);
                 canCancelBet = true;
             }
         });
         clicked(R.id.table_bt_r,v -> {
             if(stackBTR.add(curCoin)){
                 cancelBtn.setAlpha(1f);
                 canCancelBet = true;
             }
         });

         clicked(R.id.fullscreen_btn,v -> viewZoomOut(videoContaner));

         clicked(R.id.scroll_left_btn, v->{
            coinsView.smoothScrollToPosition(0);
        });

        clicked(R.id.scroll_right_btn, v->{
            coinsView.smoothScrollToPosition(18);
        });

clicked(R.id.back_btn, v->{
    onBackPressed();
});

         clicked(comissionBtn, v -> {
                 if(comission){
                     comission = false;
                     comissionBtn.setImageResource(R.drawable.casino_item_btn_super6);
                     tableRight.getLayoutParams().height = tableLeft.getHeight();
                     tableSuper.setVisibility(View.GONE);
                 }else{
                     comission = true;
                     tableRight.getLayoutParams().height = tableTop.getHeight();
                     comissionBtn.setImageResource(R.drawable.casino_item_btn_super6_a);
                     tableSuper.setVisibility(View.VISIBLE);
                 }


         });

         clicked(R.id.switch_table_btn, v -> {
             new TableSwitchPopup(this).show();
         });

         clicked(confrimBtn, v -> {
             if(canBet) {
                 Client22 client22 = new Client22(groupID, areaID);
                 for(CoinHolder coin: stackLeft.addedCoin){
                     client22.addBet(2,coin.value);
                 }
                 for(CoinHolder coin: stackRight.addedCoin){
                     client22.addBet(1,coin.value);
                 }
                 for(CoinHolder coin: stackTop.addedCoin){
                     client22.addBet(3,coin.value);
                 }
                 for(CoinHolder coin: stackBTL.addedCoin){
                     client22.addBet(5,coin.value);
                 }
                 for(CoinHolder coin: stackBTR.addedCoin){
                     client22.addBet(4,coin.value);
                 }
                 if(comission){
                     client22.data.commission = 1;
                     for(CoinHolder coin: stackSuper.addedCoin){
                         client22.addBet(8,coin.value);
                     }
                 }
                 if (client22.data.betArr.size() > 0) App.socket.send(Json.to(client22));
                 else alert("You haven't put any money!");
             }
         });

         clicked(cancelBtn,v -> {
             if(canBet && canCancelBet) resetCoinStacks();
         });


         clicked(R.id.repeat_bet_btn,v -> {
             /*
             for(CoinHolder coin: stackLeft.addedCoin){

                 if(stackLeft.add(coin))
                     client22.addBet(2,coin.value);

             }
             for(CoinHolder coin: stackRight.addedCoin){
                 if(stackRight.add(coin))
                     client22.addBet(1,coin.value);
             }
             for(CoinHolder coin: stackTop.addedCoin){
                 if(stackTop.add(coin))
                     client22.addBet(3,coin.value);
             }
             for(CoinHolder coin: stackBTL.addedCoin){
                 if(stackBTL.add(coin))
                     client22.addBet(5,coin.value);
             }
             for(CoinHolder coin: stackBTR.addedCoin){
                 if(stackBTR.add(coin))
                     client22.addBet(4,coin.value);
             }*/
         });


         clicked(R.id.bankBtn, v -> askRoad(1));

        clicked(R.id.playBtn, v -> askRoad(2));


        clicked(R.id.setting_btn,v -> {
            new SettingPopup(this).show();
        });


        App.socket.receive10(data -> {
            if(data.bOk ){
                App.data10 = data;
                setTextView(R.id.table_left_score, App.data10.dtOdds.get(2));
                setTextView(R.id.table_right_score, App.data10.dtOdds.get(1));
                setTextView(R.id.table_bt_l_score, App.data10.dtOdds.get(5));
                setTextView(R.id.table_bt_r_score, App.data10.dtOdds.get(4));
                setTextView(R.id.table_top_score, App.data10.dtOdds.get(3));
                stackLeft.maxValue = App.data10.maxBet02;
                stackBTL.maxValue = App.data10.maxBet04;
                stackRight.maxValue = App.data10.maxBet01;
                stackBTR.maxValue = App.data10.maxBet04;
                stackTop.maxValue = App.data10.maxBet03;
                stackSuper.maxValue = App.data10.maxBet04;
                areaID = App.data10.areaID;
                setTextView(R.id.player_money, App.data10.balance + "");
                comissionBtn.setAlpha(1f);
            }else{
                alert("Access denied");
                onBackPressed();
            }
        });

        App.socket.receive20(data -> {
            if(data.gameStage == 0){
                gameStageTxt.setText("洗牌中");
            }else if(data.gameStage == 1){
                gameStageTxt.setText("請下注");
                winPopup.dismiss();
                resetPokers();
                canCancelBet = true;
                resetCoinStacks();
            }else if(data.gameStage == 2){
                stopAllBetBtn();
                gameStageTxt.setText("開牌中");
                cardOpening = true;
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

        App.socket.receive30(data -> {
            if(User.memberID() == data.memberID){
                setTextView(R.id.player_money, data.balance + "");
            }
        });

        App.socket.receive22(data -> {
            if(data.bOk){
                alert("Bet successful");
              //  client22 = new Client22(groupID, areaID);
                canCancelBet = false;
                cancelBtn.setAlpha(0.5f);
              //  stopAllBetBtn();
            }else alert("Error occurred when betting, try again");
        });

        App.socket.receive26(data -> {
            setMainGrid();
            setTextView(R.id.gyu_shu,getString(R.string.table_number) + " " + App.curTable.number + " -- " + App.curTable.round);
            setTextView(R.id.banker_count, data.historyData.bankerCount + "");
            setTextView(R.id.player_count, data.historyData.playerCount + "");
            setTextView(R.id.tie_count, data.historyData.tieCount + "");
            setTextView(R.id.bank_pair_count, data.historyData.bankerPairCount + "");
            setTextView(R.id.play_pair_count, data.historyData.playerPairCount + "");
        });

        App.socket.receive31(data -> {
            TextView mText = winPopup.findViewById(R.id.player_bet);
            mText.setText(stackLeft.value + "");
            mText = winPopup.findViewById(R.id.banker_bet);
            mText.setText(stackRight.value + "");
            mText = winPopup.findViewById(R.id.player_pair_bet);
            mText.setText(stackBTL.value + "");
            mText = winPopup.findViewById(R.id.banker_pair_bet);
            mText.setText(stackBTR.value + "");
            mText = winPopup.findViewById(R.id.tie_bet);
            mText.setText(stackTop.value + "");
            mText = winPopup.findViewById(R.id.player_win);
            if(data.dtMoneyWin.get(2) == null){
                mText.setText("");
            }else{
                mText.setText(data.dtMoneyWin.get(2) + "");
            }
            mText = winPopup.findViewById(R.id.banker_win);
            if(data.dtMoneyWin.get(1) == null){
                mText.setText("");
            }else{
                mText.setText(data.dtMoneyWin.get(1) + "");
            }
            mText = winPopup.findViewById(R.id.player_pair_win);
            if(data.dtMoneyWin.get(5) == null){
                mText.setText("");
            }else{
                mText.setText(data.dtMoneyWin.get(5) + "");
            }
            mText = winPopup.findViewById(R.id.banker_pair_win);
            if(data.dtMoneyWin.get(4) == null){
                mText.setText("");
            }else{
                mText.setText(data.dtMoneyWin.get(4) + "");
            }
            mText = winPopup.findViewById(R.id.tie_win);
            if(data.dtMoneyWin.get(3) == null){
                mText.setText("");
            }else{
                mText.setText(data.dtMoneyWin.get(3) + "");
            }
            mText = winPopup.findViewById(R.id.total_win_money);
            mText.setText(data.moneyWin + "");
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
            cardOpening = false;

            recurSec(data.timeMillisecond/1000);
        });

    }

    @Override
    public void onResume(){
        super.onResume();
         Client10 client = new Client10(groupID);
         App.socket.send(Json.to(client));
    }

    private void askRoad(int win){
        App.curTable.askRoadThird(win);
        App.curTable.askRoadSec(win);
        App.curTable.askRoadFirst(win);
        App.curTable.askRoadFourth(win);
        if (firstGrid.width > App.curTable.firstGrid.posXX){
            View secView = firstGrid.insertImage(App.curTable.firstGrid.posXX, App.curTable.firstGrid.posYY, App.curTable.firstGrid.resX);
            secView.startAnimation(fadeAnimeB);
        }
        if (secGrid.width > App.curTable.secGrid.posXX){
            View secView = secGrid.insertImage(App.curTable.secGrid.posXX, App.curTable.secGrid.posYY, App.curTable.secGrid.resX);
            secView.startAnimation(fadeAnimeB);
        }
        if (thirdGrid.width > App.curTable.thirdGrid.posXX){
            View secView = thirdGrid.insertImage(App.curTable.thirdGrid.posXX, App.curTable.thirdGrid.posYY, App.curTable.thirdGrid.resX);
            secView.startAnimation(fadeAnimeB);
        }
        if (fourthGrid.width > App.curTable.fourthGrid.posXX){
            View secView = fourthGrid.insertImage(App.curTable.fourthGrid.posXX, App.curTable.fourthGrid.posYY, App.curTable.fourthGrid.resX);
            secView.startAnimation(fadeAnimeB);
        }
    }

    private void recurSec(int sec){
        gameStageTxt.setText("請下注" + sec);
        if(sec > 1 && !cardOpening){
            if(sec < 6) countdownBox.setBackgroundResource(R.drawable.casino_countdown2);
            delay(1000, ()-> recurSec(sec - 1));
        }
    }

    @Override
    public void onPause(){
        super.onPause();
       // if(!justRecreated)finish();
        onBackPressed();
    }

    private void setMainGrid(){
        int indexx = 0;
        App.curTable.askRoadThird(1);
        App.curTable.askRoadSec(1);
        App.curTable.askRoadFirst(1);
        App.curTable.askRoadFourth(1);
        App.curTable.askRoadThird(2);
        App.curTable.askRoadSec(2);
        App.curTable.askRoadFirst(2);
        App.curTable.askRoadFourth(2);

        firstGrid.drawRoad(App.curTable.firstGrid);
        secGrid.drawRoad(App.curTable.secGrid);
        thirdGrid.drawRoad(App.curTable.thirdGrid);
        fourthGrid.drawRoad(App.curTable.fourthGrid);

        for(int x = 0; x < mainGrid.width; x++){
            for(int y = 0; y < mainGrid.height; y++){
                if(indexx >= App.curTable.mainRoad.size() ) return;
                mainGrid.insertImage(x,y,App.curTable.mainRoad.get(indexx));
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
        canCancelBet = false;
        canRepeatBet = false;
        stackTop.clearCoin();
        stackBTR.clearCoin();
        stackRight.clearCoin();
        stackBTL.clearCoin();
        stackLeft.clearCoin();
        stackSuper.clearCoin();
        cancelBtn.setAlpha(0.5f);
        confrimBtn.setAlpha(1f);
        repeatBtn.setAlpha(0.5f);
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
    public void onBackPressed() {
        App.cleanSocketCalls();
        video.stopPlayback();
        super.onBackPressed();
    }

}
