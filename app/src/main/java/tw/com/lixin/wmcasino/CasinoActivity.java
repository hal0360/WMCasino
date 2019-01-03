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
import tw.com.lixin.wmcasino.jsonData.CasinoData;

public class CasinoActivity extends RootActivity {

    private boolean videoIsLarge = false;
    public CoinHolder curCoin;
    private CasinoGrid mainGrid, firstGrid, secGrid, thirdGrid, fourthGrid;
    private IjkVideoView mVideoView;
    private ImageView logo;
    private ConstraintLayout videoContaner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino);

         //String path = "rtmp://demo-stream.wm77.asia/live1/stream1";
        // mVideoView = findViewById(R.id.player);
        // mVideoView.setVideoPath(path);
        // mVideoView.start();

         logo = findViewById(R.id.lobby_logo);
         videoContaner = findViewById(R.id.videoContaner);
         mainGrid = findViewById(R.id.main_grid);
         firstGrid = findViewById(R.id.first_grid);
         secGrid = findViewById(R.id.second_grid);
         thirdGrid = findViewById(R.id.third_grid);
         fourthGrid = findViewById(R.id.fourth_grid);

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

    }

    private void addAllCoins(){
        ItemsView coinsView = findViewById(R.id.coinsView);
        List<CoinHolder> coins = new ArrayList<>();
        coins.add(new CoinHolder(R.drawable.casino_item_chip_1, 1));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_5, 5));
        coins.add(new CoinHolder(R.drawable.casino_item_chip_10, 10));
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
    protected void onStart() {
        super.onStart();

      //  ImageView img = findViewById(R.id.imageView6);

       // Animation anime = AnimationUtils.loadAnimation(this, R.anim.bounce);
       // img.startAnimation(anime);


       // disableClipOnParents(coinsView);


         clicked(R.id.fullscreen_btn,v -> {
             videoContaner.animate().scaleX(1.0f).scaleY(1.0f).translationX(0).translationY(0).setDuration(700).start();
         });

         delay(6000, ()->{
           //  videoContaner.bringToFront();
           //  dfd.animate().scaleX(1.5f).scaleY(1.5f).translationX(300).setDuration(700).start();
          //   moveViewToScreenCenter(videoContaner);
           //  dfd.startAnimation(anime);
         });


        delay(9000, ()->{
          //  dfd.clearAnimation();
          //  logo.bringToFront();
          //  gameContainer.bringToFront();
        });


         /*
        anime.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                Animation anim = AnimationUtils.loadAnimation(BuzzFinderActivity.this, R.anim.crosshair_focusing);
                anim.setAnimationListener(this);
                brackets.startAnimation(anim);

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });*/

    }

    private void moveViewToScreenCenter( View view )
    {
        ConstraintLayout root =  findViewById( R.id.root );
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics( dm );
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();
        int originalPos[] = new int[2];
        view.getLocationOnScreen( originalPos );
        int xDest = dm.widthPixels/2;
        xDest -= (view.getMeasuredWidth()/2);
        int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;

        view.bringToFront();
        view.animate().scaleX(1.5f).scaleY(1.5f).translationX(xDest - originalPos[0]).translationY(yDest - originalPos[1]).setDuration(700).start();

    }


    public void disableClipOnParents(View v) {
        if (v.getParent() == null) {
            return;
        }

        if (v instanceof ViewGroup) {
            ((ViewGroup) v).setClipChildren(false);
        }

        if (v.getParent() instanceof View) {
            disableClipOnParents((View) v.getParent());
        }
    }
}
