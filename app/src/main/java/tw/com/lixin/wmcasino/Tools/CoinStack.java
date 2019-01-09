package tw.com.lixin.wmcasino.Tools;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import tw.com.lixin.wmcasino.R;


public class CoinStack extends ConstraintLayout {

    private ImageView coin1, coin2, coin3, coin4;

    public CoinStack(Context context) {
        super(context);
        init(context, null);
    }

    public CoinStack(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.coin_stack_layout, this);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

        coin1 = findViewById(R.id.coin1);
        coin2 = findViewById(R.id.coin2);
        coin3 = findViewById(R.id.coin3);
        coin4 = findViewById(R.id.coin4);
    }
}
