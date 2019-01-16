package tw.com.lixin.wmcasino;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.util.SparseArray;
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
import tw.com.lixin.wmcasino.Tools.CmdStr;
import tw.com.lixin.wmcasino.Tools.CoinStack;
import tw.com.lixin.wmcasino.Tools.Move;
import tw.com.lixin.wmcasino.global.Poker;
import tw.com.lixin.wmcasino.jsonData.Client10;
import tw.com.lixin.wmcasino.jsonData.Server20;
import tw.com.lixin.wmcasino.jsonData.Server24;
import tw.com.lixin.wmcasino.jsonData.Server26;
import tw.com.lixin.wmcasino.models.Table;

public class CasinoActivity extends RootActivity {

    private Table table;
    private int groupID, cardArea;
    private TextView gameStageTxt;
    private boolean videoIsLarge = false;
    private boolean firstIsLarge = false;
    private boolean secIsLarge = false;
    private boolean thirdIsLarge = false;
    private boolean fourthIsLarge = false;
    public CoinHolder curCoin;
    private CasinoGrid mainGrid, firstGrid, secGrid, thirdGrid, fourthGrid;
    private IjkVideoView video;
    private ImageView logo, playerPoker1, playerPoker2, playerPoker3, bankerPoker1, bankerPoker2, bankerPoker3;
    private ConstraintLayout videoContaner, pokerContainer;
    private CoinStack stackLeft, stackRight, stackTop, stackBTL, stackBTR;
    private SparseArray<CmdStr> robots = new SparseArray<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino);

        groupID = getPassedInt();
        table = App.findTable(groupID);
        if(table == null) {
            alert("table error");
            return;
        }

        String path = "rtmp://wmvdo.nicejj.cn/live" + (groupID > 100 ? groupID - 100 : groupID) + "/stream1";
       //  String path = "rtmp://demo-stream.wm77.asia/live1/stream1";
         video = findViewById(R.id.player);
         video.setVideoPath(path);
         video.start();

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

                 secGrid.insertImage(5, 3, R.drawable.casino_roadplay);

                 setMainGrid(table.casinoRoad); });
         });

         clicked(R.id.cancel_btn,v -> {
             alert(thirdGrid.getWidth() + " " + fourthGrid.getWidth() + " " + secGrid.getWidth());
         });

         clicked(R.id.table_left,v -> stackLeft.add(curCoin.img_res, curCoin.value));
         clicked(R.id.table_right,v -> stackRight.add(curCoin.img_res, curCoin.value));
         clicked(R.id.table_top,v -> stackTop.add(curCoin.img_res, curCoin.value));
         clicked(R.id.table_bt_l,v -> stackBTL.add(curCoin.img_res, curCoin.value));
         clicked(R.id.table_bt_r,v -> stackBTR.add(curCoin.img_res, curCoin.value));

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

         clicked(firstGrid,v -> {
             Move.disableClipOnParents(firstGrid);
             Move.toCenter(this, findViewById(R.id.root), firstGrid);
         });

         /*
        robots.put(10, mss -> {
            alert("table enter successfulls");
            Server10 server10 = Json.from(mss,Server10.class);
            if(server10.data.bOk && server10.data.gameID == App.GAMEID && server10.data.groupID == groupID){
                // alert("table enter successfulls");
            }
        });*/
         setUpRobots();
        App.bacSocket.onReceive((mss, pro)->  {
            CmdStr tempMD = robots.get(pro);
            if(tempMD != null) tempMD.exec(mss);
        });
        App.lobbySocket.onReceive((mss, pro)->  {
            CmdStr tempMD = robots.get(pro);
            if(tempMD != null) tempMD.exec(mss);
        });
        Client10 client = new Client10(groupID);
        App.bacSocket.send(Json.to(client));
    }

    private void setUpRobots(){
        robots.put(20, mss -> {
            Server20 server20 = Json.from(mss,Server20.class);
            if(server20.data.groupID == groupID && server20.data.gameID == App.GAMEID){
                if(server20.data.gameStage == 0){
                    gameStageTxt.setText("洗牌中");
                    Log.e("kknd", "洗牌中");
                    resetPokers();
                }else if(server20.data.gameStage == 1){
                    gameStageTxt.setText("下注中");
                    Log.e("kknd", "下注中");
                    resetPokers();
                }else if(server20.data.gameStage == 2){
                    gameStageTxt.setText("開牌中");
                    Log.e("kknd", "開牌中");
                    pokerContainer.setVisibility(View.VISIBLE);
                }else if(server20.data.gameStage == 3){
                    gameStageTxt.setText("結算中");
                    Log.e("kknd", "結算中");
                }else{
                    gameStageTxt.setText("已關桌");
                }
            }
        });

        robots.put(24, mss -> {
            Server24 server24 = Json.from(mss,Server24.class);
            if(server24.data.groupID == groupID && server24.data.gameID == App.GAMEID){
                Log.e("kknd", server24.data.cardArea + "");
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
            }

        });

        robots.put(26, mss -> {
            Server26 server26 = Json.from(mss,Server26.class);
            if(server26.data.groupID == groupID && server26.data.gameID == App.GAMEID){
                if(server26.data.historyArr != null){
                     table.casinoRoad = new CasinoRoad(server26.data.historyArr);
                     setMainGrid(table.casinoRoad);
                }
            }
        });

    }

    private void setMainGrid(CasinoRoad road){
        int indexx = 0;
        firstGrid.drawRoad(road);
        for(int x = 0; x < mainGrid.width; x++){
            for(int y = 0; y < mainGrid.height; y++){
                if(indexx >= road.bigRoad.size() ) return;
                mainGrid.insertImage(x,y,road.bigRoad.get(indexx));
                indexx++;
            }
        }
    }

    private void resetPokers(){
        playerPoker3.setVisibility(View.GONE);
        playerPoker2.setVisibility(View.GONE);
        playerPoker1.setVisibility(View.GONE);
        bankerPoker1.setVisibility(View.GONE);
        bankerPoker2.setVisibility(View.GONE);
        bankerPoker3.setVisibility(View.GONE);
        pokerContainer.setVisibility(View.GONE);
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
}
