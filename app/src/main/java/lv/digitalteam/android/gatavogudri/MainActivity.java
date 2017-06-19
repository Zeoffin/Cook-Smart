package lv.digitalteam.android.gatavogudri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MainAdapter adapter;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Admob and firebase
        MobileAds.initialize(this, "ca-app-pub-9573430590084189~2808728113");
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //Start-menu
        String toolNames[] = {getString(R.string.converter), getString(R.string.grocery_list), getString(R.string.timer), getString(R.string.info)};
        int toolIcons[] = {R.drawable.ic_compare_arrows_black_24dp, R.drawable.ic_shopping_cart_black_24dp, R.drawable.ic_alarm_black_24dp, R.drawable.ic_info_black_24dp};

        listView = (ListView) findViewById(R.id.menuListView);

        adapter = new MainAdapter(this, toolIcons, toolNames);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                switch (position) {
                    case 0: Intent converter = new Intent(getApplicationContext(), ConverterActivity.class);
                        startActivity(converter);

                        break;

                    case 1: Intent groceries = new Intent(getApplicationContext(), GroceryListActivity.class);
                        startActivity(groceries);

                        break;

                    case 2: Intent timer = new Intent(getApplicationContext(), TimerActivity.class);
                        startActivity(timer);

                        break;

                    case 3: Intent info = new Intent(getApplicationContext(), InfoActivity.class);
                        startActivity(info);

                        break;
                }

            }
        });

    }
}
