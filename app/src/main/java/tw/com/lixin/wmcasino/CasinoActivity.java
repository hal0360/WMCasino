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
import tw.com.atromoby.widgets.ItemsView;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.jsonData.CasinoData;

public class CasinoActivity extends RootActivity {

    public TableLayout tableLayout;
    private IjkVideoView mVideoView;
    private ImageView logo, cancelBtn;
    private ConstraintLayout gameContainer, scoreContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino);

        logo = findViewById(R.id.lobby_logo);
        cancelBtn = findViewById(R.id.cancel_btn);
        gameContainer = findViewById(R.id.game_container);

        clicked(cancelBtn,v -> {
            alert("still here");
        });


        String path = "rtmp://demo-stream.wm77.asia/live1/stream1";
          mVideoView = findViewById(R.id.player);
        mVideoView.setVideoPath(path);
        mVideoView.start();

        alert(CasinoData.divide(58).toString());

        tableLayout = findViewById(R.id.road_grid);
        tableLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tableLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                double dim = tableLayout.getHeight() / 6;
                int wid = (int) dim *14;
                tableLayout.setLayoutParams(new ConstraintLayout.LayoutParams(wid, ConstraintLayout.LayoutParams.MATCH_PARENT));
              //  tableLayout.setLayoutParams(new ViewGroup.LayoutParams(wid, ViewGroup.LayoutParams.MATCH_PARENT));
                sett();
            }
        });
    }

    public void sett(){

        ImageView myImage;
        Resources res = getResources();
        View view = null;

        for(int i=0; i<6; i++){
            TableRow tr_head = new TableRow(this);
            tr_head.setDividerDrawable(res.getDrawable(R.drawable.table_divider));
            tr_head.setShowDividers(TableRow.SHOW_DIVIDER_MIDDLE);
            tr_head.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.FILL_PARENT, 1.0f));

            for(int j=0; j<14; j++){
                view = new View(this);
                view.setLayoutParams(new TableRow.LayoutParams(
                        0,
                        TableLayout.LayoutParams.FILL_PARENT, 1.0f));
                tr_head.addView(view);
            }
            tableLayout.addView(tr_head);
        }

        view.setBackgroundResource(R.drawable.casino_roadplay);
    }

    @Override
    protected void onStart() {
        super.onStart();

      //  ImageView img = findViewById(R.id.imageView6);

       // Animation anime = AnimationUtils.loadAnimation(this, R.anim.bounce);
       // img.startAnimation(anime);


        ItemsView coinsView = findViewById(R.id.coinsView);

       // disableClipOnParents(coinsView);


        coinsView.add(new CoinHolder());
        coinsView.add(new CoinHolder());
        coinsView.add(new CoinHolder());
        coinsView.add(new CoinHolder());
        coinsView.add(new CoinHolder());
        coinsView.add(new CoinHolder());
        coinsView.add(new CoinHolder());
        coinsView.add(new CoinHolder());
        coinsView.add(new CoinHolder());
        coinsView.add(new CoinHolder());
        coinsView.add(new CoinHolder());
        coinsView.add(new CoinHolder());

        View dfd = findViewById(R.id.playerContainer);
       // disableClipOnParents(dfd);
         Animation anime = AnimationUtils.loadAnimation(this, R.anim.zooming);

         clicked(R.id.fullscreen_btn,v -> {
             alert("full moon");
         });

         delay(5000, ()->{
           //  dfd.bringToFront();
           //  dfd.animate().scaleX(1.5f).scaleY(1.5f).translationX(300).setDuration(700).start();
           //  moveViewToScreenCenter(dfd);
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

        AnimationSet animSet = new AnimationSet(true);
        animSet.setFillAfter(true);
        animSet.setDuration(1000);
        TranslateAnimation trans = new TranslateAnimation( 0, xDest - originalPos[0] , 0, yDest - originalPos[1] );
        ScaleAnimation scale = new ScaleAnimation(1f, 1.5f, 1f, 1.5f, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
        animSet.addAnimation(scale);
        animSet.addAnimation(trans);

        view.startAnimation(animSet);
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
