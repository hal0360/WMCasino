package tw.com.lixin.wmcasino;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import tw.com.atromoby.widgets.ItemHolder;

public class CoinHolder extends ItemHolder implements Animation.AnimationListener{

    public int value, img_res;
    public boolean selected = false;
    private Animation bounce;
    private View coin;

    CoinHolder(int img_id, int value) {
        super(R.layout.coin_item);
        this.value = value;
        img_res = img_id;
    }

    @Override
    public void onBind() {

         bounce = AnimationUtils.loadAnimation(getContex(), R.anim.bounce);
         bounce.setAnimationListener(this);
         coin = findViewById(R.id.coin);
        coin.setBackgroundResource(img_res);

        clicked(R.id.coin, v -> {
            if(!selected){
                selected = true;
                coin.clearAnimation();
                coin.startAnimation(bounce);

                CasinoActivity act = (CasinoActivity) getContex();
                act.curCoin.selected = false;
                act.curCoin = this;
            }
        });

        if(selected){
            coin.clearAnimation();
            coin.startAnimation(bounce);
        }

    }

    @Override
    public void onRecycle() {
        coin = null;
    }



    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(selected && coin != null){
            coin.startAnimation(bounce);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
