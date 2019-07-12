package tw.com.lixin.wmcasino;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import tw.com.atromoby.widgets.RootActivity;

public class MainActivity extends RootActivity {

    private static boolean setLoc = false;
    private ImageView loadImg;
    private Map<String, Integer> loadings = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadings.put("loading1", R.drawable.loading1);
        loadings.put("loading2", R.drawable.loading2);
        loadings.put("loading3", R.drawable.loading3);
        loadings.put("loading4", R.drawable.loading4);
        loadings.put("loading5", R.drawable.loading5);
        loadings.put("loading6", R.drawable.loading6);
        loadings.put("loading7", R.drawable.loading7);
        loadings.put("loading8", R.drawable.loading8);
        loadings.put("loading9", R.drawable.loading9);
        loadings.put("loading10", R.drawable.loading10);
        loadings.put("loading11", R.drawable.loading11);
        loadings.put("loading12", R.drawable.loading12);
        loadings.put("loading13", R.drawable.loading13);

        if(!setLoc){
            setLoc = true;
            switchLocale(Locale.TAIWAN);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(setLoc){
            loadImg = findViewById(R.id.load_img);
            recurLoad(1);
            delay(1500, ()->{
                toActivity(LoginActivity.class);
            });
        }

    }

    private void recurLoad(int loadI){
        loadImg.setImageResource(loadings.get("loading" + loadI));
        loadI++;
        if(loadI > 13) loadI = 1;
        int finalLoadI = loadI;
        delay(80, ()-> recurLoad(finalLoadI));
    }


}
