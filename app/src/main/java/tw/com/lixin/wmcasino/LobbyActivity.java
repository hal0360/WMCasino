package tw.com.lixin.wmcasino;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tw.com.atromoby.widgets.ItemsView;

public class LobbyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        ItemsView itemsView = findViewById(R.id.itemsView);
        itemsView.add(new TableHolder());
        itemsView.add(new TableHolder());
        itemsView.add(new TableHolder());
        itemsView.add(new TableHolder());
        itemsView.add(new TableHolder());
    }


}
