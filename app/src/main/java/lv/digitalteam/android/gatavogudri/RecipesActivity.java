package lv.digitalteam.android.gatavogudri;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {

    ImageView backRecipes;
    ImageView addRecipes;
    RecipesDBManager dbManager;
    AdView adView;

    BaseAdapter baseAdapter;
    ListView recipesList;

    ArrayList<String> recipeId = new ArrayList<>();
    ArrayList<String> recipeTitle = new ArrayList<>();
    ArrayList<String> recipeDesc = new ArrayList<>();
    ArrayList<String> recipeIngr = new ArrayList<>();
    ArrayList<String> recipePrep = new ArrayList<>();
    ArrayList<Bitmap> recipeImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        //Admob and firebase
        MobileAds.initialize(this, "ca-app-pub-9573430590084189~2808728113");
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        recipesList = (ListView) findViewById(R.id.recipesList);

        dbManager = new RecipesDBManager(this);

        final DbBitmapUtility dbBitmapUtility = new DbBitmapUtility();

        Cursor cursor = dbManager.getAll();

        //Adapter
        baseAdapter = new RecipesAdapter(this, recipeTitle, recipeDesc, recipeImage);
        recipesList.setAdapter(baseAdapter);

        addRecipes = (ImageView) findViewById(R.id.saveRecipes);
        backRecipes = (ImageView) findViewById(R.id.backAddRecipes);

        //Add from database
        while (cursor.moveToNext()) {

            recipeId.add(cursor.getString(cursor.getColumnIndex("id")));
            recipeTitle.add(cursor.getString(cursor.getColumnIndex("title")));
            recipeDesc.add(cursor.getString(cursor.getColumnIndex("description")));
            recipeIngr.add(cursor.getString(cursor.getColumnIndex("ingredients")));
            recipePrep.add(cursor.getString(cursor.getColumnIndex("preperations")));
            recipeImage.add(dbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndex("image"))));

        }

        //Update recipe
        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String idIntent = recipeId.get(i);
                String titleIntent = recipeTitle.get(i);
                String descIntent = recipeDesc.get(i);
                String ingrIntent = recipeIngr.get(i);
                String prepIntent = recipePrep.get(i);

                Bitmap imgIntent = recipeImage.get(i);
                byte[] imgArray = dbBitmapUtility.getBytes(imgIntent);

                Intent intent = new Intent(getApplicationContext(), AddRecipeActivity.class);

                intent.putExtra("id", idIntent);
                intent.putExtra("title", titleIntent);
                intent.putExtra("description", descIntent);
                intent.putExtra("ingredients", ingrIntent);
                intent.putExtra("preperations", prepIntent);
                intent.putExtra("image", imgArray);

                startActivity(intent);

            }
        });

        //Add a new recipe
        addRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddRecipeActivity.class);
                startActivity(intent);

            }
        });

        //Back button
        backRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

    }


    //Refresh listview un nonemt extras
    @Override
    protected void onRestart() {

        super.onRestart();

        getIntent().removeExtra("id");
        getIntent().removeExtra("title");
        getIntent().removeExtra("description");
        getIntent().removeExtra("ingredients");
        getIntent().removeExtra("preperations");
        getIntent().removeExtra("image");

        finish();
        startActivity(getIntent());

    }
}
