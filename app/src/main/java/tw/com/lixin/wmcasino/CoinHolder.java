package tw.com.lixin.wmcasino;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import tw.com.atromoby.widgets.ItemHolder;

public class CoinHolder extends ItemHolder {

    public int value, img_res;
    public boolean selected = false;
    private View coin;

    CoinHolder(int img_id, int value) {
        super(R.layout.coin_item);
        this.value = value;
        img_res = img_id;
    }

    @Override
    public void onBind() {


         coin = findViewById(R.id.coin);
        coin.setBackgroundResource(img_res);

        clicked(R.id.coin, v -> {
            if(!selected){
                selected = true;
                coin.clearAnimation();

                CasinoActivity act = (CasinoActivity) getContex();
                act.curCoin.selected = false;
                act.curCoin = this;
            }
        });

        if(selected){
            coin.clearAnimation();
        }

    }

    @Override
    public void onRecycle() {
        coin = null;
    }




}
