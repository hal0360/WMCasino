package tw.com.lixin.wmcasino.Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tw.com.atromoby.utils.Kit;
import tw.com.lixin.wmcasino.CasinoActivity;
import tw.com.lixin.wmcasino.R;

@SuppressLint("SetTextI18n")
public class CoinStack extends ConstraintLayout  implements Animation.AnimationListener{

    private ImageView coin1, coin2, coin3, coin4;
    private Animation animeDwn, animeUp;
    private int hit = 0;
    private List<Integer> ids = new ArrayList<>();
    private TextView valTxt;
    public int value = 0;
    public int maxValue = 999;
    private CasinoActivity context;

    public CoinStack(Context context) {
        super(context);
        init(context);
    }

    public CoinStack(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = (CasinoActivity) context;
        View.inflate(context, R.layout.coin_stack_layout, this);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

        this.setClipChildren(false);
        this.setClipToPadding(false);
        coin1 = findViewById(R.id.coin1);
        coin2 = findViewById(R.id.coin2);
        coin3 = findViewById(R.id.coin3);
        coin4 = findViewById(R.id.coin4);
        valTxt = findViewById(R.id.stack_value);
        valTxt.setText(value + "");
        coin1.setVisibility(View.INVISIBLE);
        coin2.setVisibility(View.INVISIBLE);
        coin3.setVisibility(View.INVISIBLE);
        coin4.setVisibility(View.INVISIBLE);
        valTxt.setVisibility(View.INVISIBLE);

        animeDwn = AnimationUtils.loadAnimation(context, R.anim.coin_anime_down);
        animeDwn.setAnimationListener(this);
        animeUp = AnimationUtils.loadAnimation(context, R.anim.coin_anime_up);

    }

    public void reset(){
        value = 0;
        valTxt.setText(value + "");
        coin1.setVisibility(View.INVISIBLE);
        coin2.setVisibility(View.INVISIBLE);
        coin3.setVisibility(View.INVISIBLE);
        coin4.setVisibility(View.INVISIBLE);
        valTxt.setVisibility(View.INVISIBLE);
    }

    public boolean add(int rid, int val){
        if(!context.canBet){
            Kit.alert(context, "Please wait!");
            return false;
        }
        value = value + val;
        if(value > maxValue){
            value = value - val;
            Kit.alert(context, "Exceeded max value!");
            return false;
        }

        valTxt.setVisibility(View.VISIBLE);
        valTxt.setText(value + "");
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
            coin4.startAnimation(animeUp);
            coin1.startAnimation(animeDwn);
        }
        hit++;
        return true;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        coin1.setImageResource(ids.get(0));
        coin2.setImageResource(ids.get(1));
        coin3.setImageResource(ids.get(2));
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
