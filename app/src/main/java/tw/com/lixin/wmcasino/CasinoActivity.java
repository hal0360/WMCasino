package tw.com.lixin.wmcasino;

import android.content.res.Resources;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import tw.com.atromoby.rtmplayer.IjkVideoView;
import java.util.ArrayList;
import java.util.List;

import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.Tools.CasinoGrid;
import tw.com.lixin.wmcasino.Tools.CoinStack;
import tw.com.lixin.wmcasino.Tools.Move;
import tw.com.lixin.wmcasino.jsonData.CasinoData;

public class CasinoActivity extends RootActivity {

    private boolean videoIsLarge = false;
    private boolean firstIsLarge = false;
    private boolean secIsLarge = false;
    private boolean thirdIsLarge = false;
    private boolean fourthIsLarge = false;
    public CoinHolder curCoin;
    private CasinoGrid mainGrid, firstGrid, secGrid, thirdGrid, fourthGrid;
    private IjkVideoView video;
    private ImageView logo;
    private ConstraintLayout videoContaner;
    private CoinStack stackLeft, stackRight, stackTop, stackBTL, stackBTR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino);

         String path = "rtmp://demo-stream.wm77.asia/live1/stream1";
         //video = findViewById(R.id.player);
        // video.setVideoPath(path);
        // video.start();

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

         treeObserve(mainGrid,v -> {
             double dim = mainGrid.getHeight() / 6;
             int wid = (int) Math.round(dim*14);
             mainGrid.setLayoutParams(new ConstraintLayout.LayoutParams(wid, ConstraintLayout.LayoutParams.MATCH_PARENT));
             mainGrid.setGrid(14,6);
         });
         treeObserve(thirdGrid,v -> {
             double width = thirdGrid.getWidth();
             double dim = thirdGrid.getHeight() / 3;
             int wGrid = (int) Math.round(width/dim);
             firstGrid.setGrid(wGrid*2,6);
             secGrid.setGrid(wGrid*2 , 3);
             thirdGrid.setGrid(wGrid,3);
             fourthGrid.setGrid(wGrid,3);
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
