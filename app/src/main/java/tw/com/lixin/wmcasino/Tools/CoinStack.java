package tw.com.lixin.wmcasino.Tools;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tw.com.lixin.wmcasino.R;

public class CoinStack extends ConstraintLayout{

    private ImageView coin1, coin2, coin3, coin4;
    private Animation animeDwn, animeUp;
    private int hit = 0;
    private List<Integer> ids = new ArrayList<>();

    public CoinStack(Context context) {
        super(context);
        init(context);
    }

    public CoinStack(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.coin_stack_layout, this);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

        this.setClipChildren(false);
        this.setClipToPadding(false);
        coin1 = findViewById(R.id.coin1);
        coin2 = findViewById(R.id.coin2);
        coin3 = findViewById(R.id.coin3);
        coin4 = findViewById(R.id.coin4);
        coin1.setVisibility(View.INVISIBLE);
        coin2.setVisibility(View.INVISIBLE);
        coin3.setVisibility(View.INVISIBLE);
        coin4.setVisibility(View.INVISIBLE);

        animeDwn = AnimationUtils.loadAnimation(context, R.anim.coin_anime_down);
        animeUp = AnimationUtils.loadAnimation(context, R.anim.coin_anime_up);

    }

    public void add(int rid){

        ids.add(rid);
        if(hit == 0){
            coin1.setImageResource(rid);
            coin1.setVisibility(View.VISIBLE);
            coin1.startAnimation(animeUp);
        }else if(hit == 1){
            coin2.setImageResource(rid);
            coin2.setVisibility(View.VISIBLE);
            coin2.startAnimation(animeUp);
        }else if( hit == 2){
            coin3.setImageResource(rid);
            coin3.setVisibility(View.VISIBLE);
            coin3.startAnimation(animeUp);
        }else if(hit == 3){
            coin4.setImageResource(rid);
            coin4.setVisibility(View.VISIBLE);
            coin4.startAnimation(animeUp);
        }else{
            ids.remove(0);
            coin4.setImageResource(rid);
            coin1.setImageResource(ids.get(0));
            coin2.setImageResource(ids.get(1));
            coin3.setImageResource(ids.get(2));
            coin4.startAnimation(animeUp);
            coin1.startAnimation(animeDwn);
        }
        hit++;
    }
}
