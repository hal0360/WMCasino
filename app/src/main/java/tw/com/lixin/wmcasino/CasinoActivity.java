package tw.com.lixin.wmcasino;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tw.com.atromoby.rtmplayer.IjkVideoView;
import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;
import tw.com.lixin.wmcasino.Tools.CasinoRoad;
import tw.com.lixin.wmcasino.Tools.CoinStack;
import tw.com.lixin.wmcasino.Tools.FourthRoad;
import tw.com.lixin.wmcasino.Tools.Move;
import tw.com.lixin.wmcasino.Tools.SecRoad;
import tw.com.lixin.wmcasino.Tools.ThirdRoad;
import tw.com.lixin.wmcasino.global.Poker;
import tw.com.lixin.wmcasino.jsonData.Client10;
import tw.com.lixin.wmcasino.jsonData.Client22;
import tw.com.lixin.wmcasino.jsonData.Server10;
import tw.com.lixin.wmcasino.jsonData.Server20;
import tw.com.lixin.wmcasino.jsonData.Server22;
import tw.com.lixin.wmcasino.jsonData.Server24;
import tw.com.lixin.wmcasino.jsonData.Server25;
import tw.com.lixin.wmcasino.jsonData.Server26;
import tw.com.lixin.wmcasino.jsonData.Server31;
import tw.com.lixin.wmcasino.models.Table;

public class CasinoActivity extends RootActivity {

    private Table table;
    private int groupID, areaID;
    private TextView gameStageTxt, pokerBall, playerScreenScore, bankerScreenScore;
    private boolean videoIsLarge = false;
    public boolean canBet = false;
    private boolean loggedIn = false;
    public CoinHolder curCoin;
    private CasinoGrid mainGrid, firstGrid, secGrid, thirdGrid, fourthGrid;
    private IjkVideoView video;
    private ImageView logo, playerPoker1, playerPoker2, playerPoker3, bankerPoker1, bankerPoker2, bankerPoker3;
    private ConstraintLayout videoContaner, pokerContainer;
    private CoinStack stackLeft, stackRight, stackTop, stackBTL, stackBTR;
    private Client22 client22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino);

        groupID = getPassedInt();
        table = App.findTable(groupID);

        String path = "rtmp://wmvdo.nicejj.cn/live" + (groupID > 100 ? groupID - 100 : groupID) + "/stream1";
         //String path = "rtmp://demo-stream.wm77.asia/live1/stream1";
         video = findViewById(R.id.player);
         video.setVideoPath(path);
         video.start();

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
                 setMainGrid(table.casinoRoad);
             });
         });

         clicked(R.id.cancel_bet_btn, v -> {
             alert(thirdGrid.getWidth() + " " + fourthGrid.getWidth() + " " + secGrid.getWidth());
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

         /*
         clicked(firstGrid,v -> {
             Move.disableClipOnParents(firstGrid);
             Move.toCenter(this, findViewById(R.id.root), firstGrid);
         });*/

         clicked(R.id.confirm_bet_btn, v -> {
             if(canBet) {
                 if (client22.data.betArr.size() > 0) {
                     App.bacSocket.send(Json.to(client22));
                 } else {
                     alert("You haven't put any money!");
                 }
             }
         });

         clicked(R.id.cancel_bet_btn,v -> {
             if(canBet){
                 resetCoinStacks();
             }
         });

        App.bacSocket.onReceive(this::setUpRobots);
        App.lobbySocket.onReceive(this::setUpRobots);
        Client10 client = new Client10(groupID);
        App.bacSocket.send(Json.to(client));
    }

    @Override
    public void onPause(){
        super.onPause();
        exitTable();
    }

    private void resetCoinStacks(){
        stackTop.reset();
        stackBTR.reset();
        stackRight.reset();
        stackBTL.reset();
        stackLeft.reset();
        client22 = new Client22(groupID, areaID);
    }


    private void setUpRobots(String mss){

        Server20 server20 = Json.from(mss,Server20.class);
        if(server20.data.groupID == groupID && server20.data.gameID == App.GAMEID && server20.protocol == 20){
            canBet = false;
            resetCoinStacks();
            if(server20.data.gameStage == 0){
                gameStageTxt.setText("洗牌中");
                Log.e("kknd", "洗牌中");
                resetPokers();
            }else if(server20.data.gameStage == 1){
                gameStageTxt.setText("請下注");
                Log.e("kknd", "請下注");
                resetPokers();
                canBet = true;
            }else if(server20.data.gameStage == 2){
                gameStageTxt.setText("開牌中");
                Log.e("kknd", "開牌中");
                pokerContainer.setVisibility(View.VISIBLE);
            }else if(server20.data.gameStage == 3){
                gameStageTxt.setText("結算中");
                Log.e("kknd", "結算中");
                resetPokers();
            }else{
                gameStageTxt.setText("已關桌");
                exitTable();
            }
            return;
        }

        Server24 server24 = Json.from(mss,Server24.class);
        if(server24.data.groupID == groupID && server24.data.gameID == App.GAMEID && server20.protocol == 24){
            if(server24.data.cardArea == 1){
                playerPoker1.setImageResource(Poker.NUM(server24.data.cardID));
                playerPoker1.setVisibility(View.VISIBLE);
            }else if(server24.data.cardArea == 2){
                bankerPoker1.setImageResource(Poker.NUM(server24.data.cardID));
                bankerPoker1.setVisibility(View.VISIBLE);
            }else if(server24.data.cardArea == 4){
                bankerPoker2.setImageResource(Poker.NUM(server24.data.cardID));
                bankerPoker2.setVisibility(View.VISIBLE);
            }else if(server24.data.cardArea == 6){
                bankerPoker3.setImageResource(Poker.NUM(server24.data.cardID));
                bankerPoker3.setVisibility(View.VISIBLE);
            }else if(server24.data.cardArea == 3){
                playerPoker2.setImageResource(Poker.NUM(server24.data.cardID));
                playerPoker2.setVisibility(View.VISIBLE);
            }else if(server24.data.cardArea == 5){
                playerPoker3.setImageResource(Poker.NUM(server24.data.cardID));
                playerPoker3.setVisibility(View.VISIBLE);
            }
            return;
        }

        Server22 server22 = Json.from(mss,Server22.class);
        if(server22.data.groupID == groupID && server22.data.gameID == App.GAMEID && server22.protocol == 22){
            if(server22.data.bOk){
                alert("Bet successful");
                canBet = false;
            }else{
                alert("Error occurred when betting, try again");
            }
            return;
        }

        Server26 server26 = Json.from(mss,Server26.class);
        if(server26.data.groupID == groupID && server26.data.gameID == App.GAMEID && server26.protocol == 26){
            table.casinoRoad = new CasinoRoad(server26.data.historyArr);
            setMainGrid(table.casinoRoad);
            return;
        }

        Server31 server31 = Json.from(mss,Server31.class);
        if(server31.data.groupID == groupID && server31.data.gameID == App.GAMEID && server31.protocol == 31){
            alert(mss);
            return;
        }

        Server25 server25 = Json.from(mss,Server25.class);
        if(server25.data.groupID == groupID && server25.data.gameID == App.GAMEID && server25.protocol == 25){
            int pokerWin = Move.divide(server25.data.result);
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
            playerScreenScore.setText(getString(R.string.player_score) + server25.data.playerScore);
            bankerScreenScore.setText(getString(R.string.banker_score) + server25.data.bankerScore);
            pokerBall.setVisibility(View.VISIBLE);
            return;
        }

        Server10 server10 = Json.from(mss,Server10.class);
        if(server10.protocol == 10){
            if(server10.data.bOk ){
                loggedIn = true;
                setTextView(R.id.table_left_score, server10.data.dtOdds.get(2));
                setTextView(R.id.table_right_score, server10.data.dtOdds.get(1));
                setTextView(R.id.table_bt_l_score, server10.data.dtOdds.get(5));
                setTextView(R.id.table_bt_r_score, server10.data.dtOdds.get(4));
                setTextView(R.id.table_top_score, server10.data.dtOdds.get(3));
                stackLeft.maxValue = server10.data.maxBet02;
                stackBTL.maxValue = server10.data.maxBet04;
                stackRight.maxValue = server10.data.maxBet01;
                stackBTR.maxValue = server10.data.maxBet04;
                stackTop.maxValue = server10.data.maxBet03;
                areaID = server10.data.areaID;
                setTextView(R.id.player_money, server10.data.balance + "");
            }else{
                alert("Access denied");
                exitTable();
            }
        }

    }

    private void setMainGrid(CasinoRoad road){
        int indexx = 0;
        firstGrid.drawRoad(road);

        secGrid.drawSecRoad(new SecRoad(table.casinoRoad.sortedRoad));
        thirdGrid.drawThirdRoad(new ThirdRoad(table.casinoRoad.sortedRoad));
        fourthGrid.drawForthRoad(new FourthRoad(table.casinoRoad.sortedRoad));

        for(int x = 0; x < mainGrid.width; x++){
            for(int y = 0; y < mainGrid.height; y++){
                if(indexx >= road.bigRoad.size() ) return;
                mainGrid.insertImage(x,y,road.bigRoad.get(indexx));
                indexx++;
            }
        }


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
        ItemsView coinsView = findViewById(R.id.coinsView);
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

    public void exitTable(){

    }
}
