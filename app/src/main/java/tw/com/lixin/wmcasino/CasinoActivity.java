package tw.com.lixin.wmcasino;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tw.com.atromoby.rtmplayer.IjkVideoView;
import tw.com.atromoby.utils.CountDown;
import tw.com.atromoby.utils.Json;
import tw.com.atromoby.utils.TimeTask;
import tw.com.atromoby.widgets.Cmd;
import tw.com.atromoby.widgets.ItemHolder;
import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.Popup;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;
import tw.com.lixin.wmcasino.Tools.CoinStack;
import tw.com.lixin.wmcasino.Tools.GoldenButton;
import tw.com.lixin.wmcasino.Tools.Move;
import tw.com.lixin.wmcasino.Tools.PayPopup;
import tw.com.lixin.wmcasino.Tools.Screenshot;
import tw.com.lixin.wmcasino.Tools.SettingPopup;
import tw.com.lixin.wmcasino.Tools.TableSwitchPopup;
import tw.com.lixin.wmcasino.global.Poker;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.Client10;
import tw.com.lixin.wmcasino.jsonData.Client22;
import tw.com.lixin.wmcasino.models.CostomCoinHolder;
import tw.com.lixin.wmcasino.models.VerticalTableHolder;

public class CasinoActivity extends SocketActivity implements TextureView.SurfaceTextureListener{
    private int posX, posY;
    private Animation fadeAnimeB;
    private Move move;
    public ItemsView coinsView;
    private Popup winPopup;
    private TextView gameStageTxt, pokerBall, playerScreenScore, bankerScreenScore;
    private boolean comission = false;
    public CoinHolder curCoin;
    private CasinoGrid mainGrid, firstGrid, secGrid, thirdGrid, fourthGrid;
    private View logo;
    private ImageView playerPoker1, playerPoker2, playerPoker3, bankerPoker1, bankerPoker2, bankerPoker3;
    private ConstraintLayout videoContaner, pokerContainer, countdownBox, tableBetContainer, root, tableRight, tableSuper, tableTop, tableLeft;
    private CoinStack stackLeft, stackRight, stackTop, stackBTL, stackBTR, stackSuper;
    private ImageView bankSecondSym, bankThirdSym, bankFourthSym, playerSecondSym, playerThirdSym, playerFourthSym;
    private GoldenButton repeatBtn, cancelBtn, comissionBtn;
    public GoldenButton confirmBtn;
    private boolean viewIsZoomed = false;
    private CountDown countDownTimer;
    private String bankTableScore;
    private TimeTask timeTask;

    private View mainV, firstV, secV, thirdV, fourthV;

    private boolean cardIsOpening = true;
    private boolean isBettingNow = true;
    private int groupID, areaID;

    private IjkVideoView video;

    public void viewZoomOut(View view) {
        if(!isBettingNow) return;
        if (viewIsZoomed) {
            move.back(300);
            viewIsZoomed = false;
            logo.bringToFront();
        } else {
            if(view.getId() ==  R.id.first_grid || view.getId() ==  R.id.main_grid || view.getId() ==  R.id.videoContaner ){
                move.toCenter(view,1.5f,300);
            }else{
                move.toCenter(view,1.8f,300);
            }
            viewIsZoomed = true;
        }
    }

    public void calledByCoin(View chip){
        treeObserve(chip, v -> {
            alert(chip.getHeight() + " wewz");
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino_two);

        int orientation = getResources().getConfiguration().orientation;

        fadeAnimeB = AnimationUtils.loadAnimation(this, R.anim.prediction_fade);
        timeTask = new TimeTask();

        groupID = App.groupID;
        String path = "rtmp://wmvdo.c2h6.cn/ytb" + String.format(Locale.US, "%02d", groupID) + "-1/stream1";


            video = findViewById(R.id.player);

            video.setVideoPath(path);
            video.start();

  

        countDownTimer = new CountDown();

        root = findViewById(R.id.root);
        confirmBtn = findViewById(R.id.confirm_bet_btn);
        cancelBtn = findViewById(R.id.cancel_bet_btn);
        repeatBtn = findViewById(R.id.repeat_bet_btn);
        comissionBtn = findViewById(R.id.comission_btn);
        tableBetContainer = findViewById(R.id.table_bet_container);
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
        winPopup = new Popup(this, R.layout.win_loss_popup);
        playerScreenScore = findViewById(R.id.player_screen_score);
        bankerScreenScore = findViewById(R.id.banker_screen_score);
        gameStageTxt = findViewById(R.id.stage_info_txt);
        coinsView = findViewById(R.id.coinsView);
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
        stackSuper = findViewById(R.id.table_bt_super_stack);
        pokerContainer = findViewById(R.id.poker_layout);
        playerPoker1 = findViewById(R.id.player_poker1);
        playerPoker2 = findViewById(R.id.player_poker2);
        playerPoker3 = findViewById(R.id.player_poker3);
        bankerPoker1 = findViewById(R.id.banker_poker1);
        bankerPoker2 = findViewById(R.id.banker_poker2);
        bankerPoker3 = findViewById(R.id.banker_poker3);
        pokerBall = findViewById(R.id.poker_ball);
        resetPokers();

        setTextView(R.id.gyu_shu, getString(R.string.table_number) + " " + App.curTable.number + " -- " + App.curTable.round);
        setTextView(R.id.banker_count, App.curTable.bankCount + "");
        setTextView(R.id.player_count, App.curTable.playCount + "");
        setTextView(R.id.tie_count, App.curTable.tieCount + "");
        setTextView(R.id.bank_pair_count, App.curTable.bankPairCount + "");
        setTextView(R.id.play_pair_count, App.curTable.playPairCount + "");

        treeObserve(root, v -> move = new Move(this, root));

        treeObserve(coinsView, v->{
            int coinWidth = coinsView.getHeight();
            int coinListWidth = coinsView.getWidth();
            coinsView.getLayoutParams().width = coinListWidth - (coinListWidth % coinWidth);
        });
        coinsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Log.e("SCROLL", "stopped.");
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Log.e("SCROLL", "drag.");
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Log.e("SCROLL", "settling.");
                        break;

                }
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    final int curPos = coinsView.findScroll();
                    delay(200, new Cmd() {
                        @Override
                        public void exec() {
                            coinsView.scrollTo(curPos);
                        }
                    });
                }

            }
        });

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            clicked(R.id.cash_btn,v->{
                new PayPopup(this).show();
            });
        }

        clicked(R.id.table_left, v -> {
            stackLeft.add(curCoin);
            checkStackEmpty();
        });
        clicked(tableRight, v -> {
            stackRight.add(curCoin);
            checkStackEmpty();
        });
        clicked(R.id.table_top, v -> {
            stackTop.add(curCoin);
            checkStackEmpty();
        });
        clicked(R.id.table_bt_l, v -> {
            stackBTL.add(curCoin);
            checkStackEmpty();
        });
        clicked(R.id.table_bt_r, v -> {
            stackBTR.add(curCoin);
            checkStackEmpty();
        });
        clicked(tableSuper, v -> {
            if (comission) {
                stackSuper.add(curCoin);
                checkStackEmpty();
            }
        });

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            clicked(R.id.fullscreen_btn, v -> viewZoomOut(videoContaner));
        }

        clicked(R.id.fullscreen_btn, v ->{

            videoContaner.removeView(video);

            delay(3000,()->{
                videoContaner.addView(video);
            });

            //viewZoomOut(videoContaner);
        });

        clicked(R.id.scroll_left_btn, v -> {
            coinsView.smoothScrollToPosition(0);
        });

        clicked(R.id.scroll_right_btn, v -> {
            coinsView.smoothScrollToPosition(18);
        });

        clicked(R.id.back_btn, v -> {
            onBackPressed();
        });

        comissionBtn.clicked(v -> {
            if (comission) {
                comission = false;
                tableBetContainer.setBackgroundResource(R.drawable.table_bt);
                comissionBtn.setImageResource(R.drawable.casino_item_btn_super6);
                tableRight.getLayoutParams().height = tableLeft.getHeight();
                tableSuper.setVisibility(View.GONE);
                stackSuper.cancelBet();
                setTextView(R.id.table_right_score, bankTableScore);
            } else {
                comission = true;
                tableBetContainer.setBackgroundResource(R.drawable.table_bt_super6);
                tableRight.getLayoutParams().height = tableTop.getHeight();
                comissionBtn.setImageResource(R.drawable.casino_item_btn_super6_a);
                tableSuper.setVisibility(View.VISIBLE);
                setTextView(R.id.table_right_score, "1(0.5)");
            }
            checkStackEmpty();
        });

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            clicked(R.id.switch_table_btn, v -> {
                new TableSwitchPopup(this).show();
            });

        }

        confirmBtn.clicked(v -> {
            Client22 client22 = new Client22(groupID, areaID);
            if (comission) {
                client22.data.commission = 1;
                stackSuper.addCoinToClient(client22, 8);
            }
            stackBTR.addCoinToClient(client22, 4);
            stackTop.addCoinToClient(client22, 3);
            stackRight.addCoinToClient(client22, 1);
            stackBTL.addCoinToClient(client22, 5);
            stackLeft.addCoinToClient(client22, 2);
            if (client22.data.betArr.size() > 0) App.socket.send(Json.to(client22));
            else alert("You haven't put any money!");
        });

        cancelBtn.clicked(v -> {
            stackSuper.cancelBet();
            stackBTR.cancelBet();
            stackTop.cancelBet();
            stackRight.cancelBet();
            stackBTL.cancelBet();
            stackLeft.cancelBet();
            checkStackEmpty();
        });

        repeatBtn.clicked(v -> {
            stackSuper.repeatBet();
            stackBTR.repeatBet();
            stackTop.repeatBet();
            stackRight.repeatBet();
            stackBTL.repeatBet();
            stackLeft.repeatBet();
        });

        clicked(R.id.bankBtn, v -> {
            askRoad(1);
            bankSecondSym.setImageResource(App.curTable.secGrid.resX);
            bankThirdSym.setImageResource(App.curTable.thirdGrid.resX);
            bankFourthSym.setImageResource(App.curTable.fourthGrid.resX);
        });

        clicked(R.id.playBtn, v -> {
            askRoad(2);
            playerSecondSym.setImageResource(App.curTable.secGrid.resX);
            playerThirdSym.setImageResource(App.curTable.thirdGrid.resX);
            playerFourthSym.setImageResource(App.curTable.fourthGrid.resX);
        });

        clicked(R.id.setting_btn, v -> {
            new SettingPopup(this).show();
        });

        App.socket.receive10(data -> {
            timeTask.clear();
            if (data.bOk) {
                App.data10 = data;
                setTextView(R.id.table_left_score, App.data10.dtOdds.get(2));
                setTextView(R.id.table_right_score, App.data10.dtOdds.get(1));
                setTextView(R.id.table_bt_l_score, App.data10.dtOdds.get(5));
                setTextView(R.id.table_bt_r_score, App.data10.dtOdds.get(4));
                setTextView(R.id.table_top_score, App.data10.dtOdds.get(3));
                bankTableScore = App.data10.dtOdds.get(1);
                setTextView(R.id.table_bt_super_score, "12");
                stackLeft.maxValue = App.data10.maxBet02;
                stackBTL.maxValue = App.data10.maxBet04;
                stackRight.maxValue = App.data10.maxBet01;
                stackBTR.maxValue = App.data10.maxBet04;
                stackTop.maxValue = App.data10.maxBet03;
                stackSuper.maxValue = App.data10.maxBet04;
                int maxBetVal = App.data10.maxBet01;
                if(maxBetVal < App.data10.maxBet02) maxBetVal = App.data10.maxBet02;
                if(maxBetVal < App.data10.maxBet03) maxBetVal = App.data10.maxBet03;
                if(maxBetVal < App.data10.maxBet04) maxBetVal = App.data10.maxBet04;
                setTextView(R.id.gyu_shu2, "1 - " + maxBetVal);

                areaID = App.data10.areaID;
                setTextView(R.id.player_money, App.data10.balance + "");
                comissionBtn.disable(false);

                double dim = mainGrid.getHeight() / 6;
                mainGrid.getLayoutParams().width = (int) Math.round(dim * 14);
                mainGrid.setGrid(14, 6);
                double width = thirdGrid.getWidth();
                double dim2 = thirdGrid.getHeight() / 3;
                int wGrid = (int) Math.round(width / dim2);

                double width2 = firstGrid.getWidth();
                double dim3 = firstGrid.getHeight() / 6;
                int wGrid2 = (int) Math.round(width2 / dim3);
                firstGrid.setGrid(wGrid2, 6);


                secGrid.setGridDouble(wGrid * 2, 3);
                thirdGrid.setGridDouble(wGrid, 3);
                fourthGrid.setGridDouble(wGrid, 3);
                setMainGrid();
            } else {
                alert("Access denied");
                onBackPressed();
            }
        });

        App.socket.receive20(data -> {
            isBettingNow = false;
            cardIsOpening = false;
            if (data.gameStage == 0) {
                gameStageTxt.setText("洗牌中");
            } else if (data.gameStage == 1) {
                isBettingNow = true;
                gameStageTxt.setText("請下注");
                winPopup.dismiss();
                resetPokers();
                confirmBtn.disable(false);
                resetCoinStacks();
            } else if (data.gameStage == 2) {
                cardIsOpening = true;
                countDownTimer.cancel();
                confirmBtn.disable(true);
                cancelBtn.disable(true);
                repeatBtn.disable(true);
                gameStageTxt.setText("開牌中");
                if(viewIsZoomed){
                    move.back(0);
                    viewIsZoomed = false;
                }
                pokerContainer.bringToFront();
                pokerContainer.setVisibility(View.VISIBLE);
            } else if (data.gameStage == 3) {
                gameStageTxt.setText("結算中");
            } else {
                gameStageTxt.setText("已關桌");
                onBackPressed();
            }
        });


        App.socket.receive24(data -> {
            Log.e("kknd", data.cardArea + "");
            if (data.cardArea == 3) {
                playerPoker1.setImageResource(Poker.NUM(data.cardID));
                playerPoker1.setVisibility(View.VISIBLE);
            } else if (data.cardArea == 2) {
                bankerPoker1.setImageResource(Poker.NUM(data.cardID));
                bankerPoker1.setVisibility(View.VISIBLE);
            } else if (data.cardArea == 4) {
                bankerPoker2.setImageResource(Poker.NUM(data.cardID));
                bankerPoker2.setVisibility(View.VISIBLE);
            } else if (data.cardArea == 6) {
                bankerPoker3.setImageResource(Poker.NUM(data.cardID));
                bankerPoker3.setVisibility(View.VISIBLE);
            } else if (data.cardArea == 1) {
                playerPoker2.setImageResource(Poker.NUM(data.cardID));
                playerPoker2.setVisibility(View.VISIBLE);
            } else if (data.cardArea == 5) {
                playerPoker3.setImageResource(Poker.NUM(data.cardID));
                playerPoker3.setVisibility(View.VISIBLE);
            }
        });

        App.socket.receive23(data -> {
            setTextView(R.id.player_money, data.balance + "");
        });

        App.socket.receive22(data -> {
            if (data.bOk) {
                alert("Bet successful");
                stackBTR.comfirmBet();
                stackSuper.comfirmBet();
                stackTop.comfirmBet();
                stackRight.comfirmBet();
                stackBTL.comfirmBet();
                stackLeft.comfirmBet();
                checkStackEmpty();
            } else alert("Error occurred when betting, try again");

        });

        App.socket.receive26(data -> {
            clearAskViews();
            setMainGrid();
            setTextView(R.id.gyu_shu, getString(R.string.table_number) + " " + App.curTable.number + " -- " + App.curTable.round);
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
            mText = winPopup.findViewById(R.id.super_bet);
            mText.setText(stackSuper.value + "");

            mText = winPopup.findViewById(R.id.player_win);
            if (data.dtMoneyWin.get(2) == null) {
                mText.setText("");
            } else {
                mText.setText(data.dtMoneyWin.get(2) + "");
            }
            mText = winPopup.findViewById(R.id.banker_win);
            if (data.dtMoneyWin.get(1) == null) {
                mText.setText("");
            } else {
                mText.setText(data.dtMoneyWin.get(1) + "");
            }
            mText = winPopup.findViewById(R.id.player_pair_win);
            if (data.dtMoneyWin.get(5) == null) {
                mText.setText("");
            } else {
                mText.setText(data.dtMoneyWin.get(5) + "");
            }
            mText = winPopup.findViewById(R.id.banker_pair_win);
            if (data.dtMoneyWin.get(4) == null) {
                mText.setText("");
            } else {
                mText.setText(data.dtMoneyWin.get(4) + "");
            }
            mText = winPopup.findViewById(R.id.tie_win);
            if (data.dtMoneyWin.get(3) == null) {
                mText.setText("");
            } else {
                mText.setText(data.dtMoneyWin.get(3) + "");
            }
            mText = winPopup.findViewById(R.id.super_win);
            if (data.dtMoneyWin.get(8) == null) {
                mText.setText("");
            } else {
                mText.setText(data.dtMoneyWin.get(8) + "");
            }

            mText = winPopup.findViewById(R.id.total_win_money);
            mText.setText(data.moneyWin + "");
            winPopup.show();
        });

        App.socket.receive25(data -> {
            Log.e("kknd", "receive25");
            countdownBox.setBackgroundResource(R.drawable.casino_countdown);
            int pokerWin = Move.divide(data.result);
            if (pokerWin == 1) {
                pokerBall.setText(getString(R.string.banker_score));
                pokerBall.setBackgroundResource(R.drawable.casino_item_bt_bank);
            } else if (pokerWin == 2) {
                pokerBall.setText(getString(R.string.player_score));
                pokerBall.setBackgroundResource(R.drawable.casino_item_bt_player);
            } else {
                pokerBall.setText(getString(R.string.tie_score));
                pokerBall.setBackgroundResource(R.drawable.casino_item_bt_bank);
            }
            playerScreenScore.setText(getString(R.string.player_score) + data.playerScore);
            bankerScreenScore.setText(getString(R.string.banker_score) + data.bankerScore);
            pokerBall.setVisibility(View.VISIBLE);
        });

        App.socket.receive38(data -> countDownTimer.start(data.timeMillisecond, i->{
            if(!cardIsOpening){
                gameStageTxt.setText("請下注" + i);
                if (i <= 5) countdownBox.setBackgroundResource(R.drawable.casino_countdown2);
            }
        }));
    }

    private void checkStackEmpty() {
        if (stackLeft.isEmpty() && stackBTL.isEmpty() && stackRight.isEmpty() && stackTop.isEmpty() && stackSuper.isEmpty() && stackBTR.isEmpty()) {
            cancelBtn.disable(true);
            repeatBtn.disable(true);
        } else {
            cancelBtn.disable(false);
            repeatBtn.disable(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Client10 client = new Client10(groupID);
        App.socket.send(Json.to(client));
        timeTask.delay(3000,()->{
            alert("table login timeout");
            onBackPressed();
        });
    }

    private void clearAskViews(){
        if(mainV == null) return;
        mainV.clearAnimation();
        mainV.setBackgroundResource(0);
        firstV.clearAnimation();
        firstV.setBackgroundResource(0);
        secV.clearAnimation();
        secV.setBackgroundResource(0);
        thirdV.clearAnimation();
        thirdV.setBackgroundResource(0);
        fourthV.clearAnimation();
        fourthV.setBackgroundResource(0);
    }

    private void askRoad(int win) {
        clearAskViews();
        App.curTable.askRoadThird(win);
        App.curTable.askRoadSec(win);
        App.curTable.askRoadFirst(win);
        App.curTable.askRoadFourth(win);
        if (firstGrid.width > App.curTable.firstGrid.posXX) {
            firstV = firstGrid.insertImage(App.curTable.firstGrid.posXX, App.curTable.firstGrid.posYY, App.curTable.firstGrid.resX);
            firstV.startAnimation(fadeAnimeB);
        }
        if (secGrid.width > App.curTable.secGrid.posXX) {
            secV = secGrid.insertImage(App.curTable.secGrid.posXX, App.curTable.secGrid.posYY, App.curTable.secGrid.resX);
            secV.startAnimation(fadeAnimeB);
        }
        if (thirdGrid.width > App.curTable.thirdGrid.posXX) {
            thirdV = thirdGrid.insertImage(App.curTable.thirdGrid.posXX, App.curTable.thirdGrid.posYY, App.curTable.thirdGrid.resX);
            thirdV.startAnimation(fadeAnimeB);
        }
        if (fourthGrid.width > App.curTable.fourthGrid.posXX) {
            fourthV = fourthGrid.insertImage(App.curTable.fourthGrid.posXX, App.curTable.fourthGrid.posYY, App.curTable.fourthGrid.resX);
            fourthV.startAnimation(fadeAnimeB);
        }
        if(win == 1){
            if(posY < 5){
                mainV = mainGrid.insertImage(posX, posY + 1, R.drawable.casino_roadbank);
                mainV.startAnimation(fadeAnimeB);
            }else{
                mainV =  mainGrid.insertImage(posX+1, 0, R.drawable.casino_roadbank);
                mainV.startAnimation(fadeAnimeB);
            }
        }else{
            if(posY < 5){
                mainV =  mainGrid.insertImage(posX, posY + 1, R.drawable.casino_roadplay);
                mainV.startAnimation(fadeAnimeB);
            }else{
                mainV =   mainGrid.insertImage(posX+1, 0, R.drawable.casino_roadplay);
                mainV.startAnimation(fadeAnimeB);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
       // onBackPressed();
    }

    private void setMainGrid() {
        int indexx = 0;
        firstGrid.drawRoad(App.curTable.firstGrid);
        secGrid.drawRoad(App.curTable.secGrid);
        thirdGrid.drawRoad(App.curTable.thirdGrid);
        fourthGrid.drawRoad(App.curTable.fourthGrid);
        for (int x = 0; x < mainGrid.width; x++) {
            for (int y = 0; y < mainGrid.height; y++) {
                if (indexx >= App.curTable.mainRoad.size()) return;
                mainGrid.insertImage(x, y, App.curTable.mainRoad.get(indexx));
                indexx++;
                posX = x;
                posY = y;
            }
        }
    }

    private void resetCoinStacks() {
        stackTop.clearCoin();
        stackBTR.clearCoin();
        stackRight.clearCoin();
        stackBTL.clearCoin();
        stackLeft.clearCoin();
        stackSuper.clearCoin();
        checkStackEmpty();
    }

    private void resetPokers() {
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

    private void addAllCoins() {
        List<ItemHolder> coins = new ArrayList<>();
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
        coins.add(new CostomCoinHolder());
        coinsView.add(coins);
    }

    @Override
    public void onBackPressed() {
        App.cleanSocketCalls();
        video.stopPlayback();
        timeTask.clear();
        countDownTimer.cancel();
        super.onBackPressed();
    }




    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
