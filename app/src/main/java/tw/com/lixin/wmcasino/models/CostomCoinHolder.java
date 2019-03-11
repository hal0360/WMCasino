package tw.com.lixin.wmcasino.models;

import android.view.View;

import tw.com.atromoby.widgets.ItemHolder;
import tw.com.lixin.wmcasino.CasinoActivity;
import tw.com.lixin.wmcasino.CoinHolder;
import tw.com.lixin.wmcasino.R;

public class CostomCoinHolder extends ItemHolder {

    public int value, img_res;
    public boolean selected = false;
    public CoinHolder coinHolder;

    public CostomCoinHolder() {
        super(R.layout.custom_coin_item);
        this.value = 10;
        img_res = R.drawable.casino_item_chip;
        coinHolder = new CoinHolder(img_res,10);
    }

    @Override
    public void onBind() {

        View coin = findViewById(R.id.coin);
        coin.setBackgroundResource(img_res);
        View chip = findViewById(R.id.chip);

        /*
        clicked(R.id.coin, v -> {
            if(!selected){

                selected = true;
                CasinoActivity act = (CasinoActivity) getContex();
                act.curCoin.selected = false;
                act.curCoin = this;
                act.coinsView.refresh();

            }
        });*/

        if(selected){
            chip.setBackgroundResource(R.drawable.outer_glow);
        }else {
            chip.setBackgroundResource(0);
        }

    }

    @Override
    public void onRecycle() {

    }




}
