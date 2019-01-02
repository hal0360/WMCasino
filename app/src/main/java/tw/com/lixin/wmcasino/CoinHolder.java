package tw.com.lixin.wmcasino;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import tw.com.atromoby.widgets.ItemHolder;

public class CoinHolder extends ItemHolder {

    private int value, img_res;

    public CoinHolder(int img_id, int value) {
        super(R.layout.coin_item);
        this.value = value;
        img_res = img_id;
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
        coin.setBackgroundResource(img_res);


        Animation anime = AnimationUtils.loadAnimation(getContex(), R.anim.bounce);

        /*
        coin.startAnimation(anime);
        anime.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                coin.startAnimation(anime);

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });
        */
    }
}
