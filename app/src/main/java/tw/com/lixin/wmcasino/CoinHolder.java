package tw.com.lixin.wmcasino;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import tw.com.atromoby.widgets.ItemHolder;

public class CoinHolder extends ItemHolder {

    public CoinHolder() {
        super(R.layout.coin_item);
    }

    @Override
    public void onBind() {

    }

    @Override
    public void onRecycle() {

    }

    @Override
    public void onCreate() {

        View coin = findView(R.id.coin);
         Animation anime = AnimationUtils.loadAnimation(getContex(), R.anim.bounce);
        coin.startAnimation(anime);
    }
}
