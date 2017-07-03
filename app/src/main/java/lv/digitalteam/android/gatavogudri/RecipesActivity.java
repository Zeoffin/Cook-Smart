package lv.digitalteam.android.gatavogudri;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {

    /*

    TODO: Pielikt button clicked efektu:
    https://stackoverflow.com/questions/17145615/using-image-in-a-android-button-with-effects
    vai
    GOOGLE: Android button clicked effect

    TODO: SATAISIT FILTRĒSANU

    */



    ImageView backRecipes;
    ImageView addRecipes;
    RecipesDBManager dbManager;

    BaseAdapter baseAdapter;
    ListView recipesList;

    ArrayList<String> recipeId = new ArrayList<>();
    ArrayList<String> recipeTitle = new ArrayList<>();
    ArrayList<String> recipeDesc = new ArrayList<>();
    ArrayList<Bitmap> recipeImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        recipesList = (ListView) findViewById(R.id.recipesList);

        dbManager = new RecipesDBManager(this);

        DbBitmapUtility dbBitmapUtility = new DbBitmapUtility();

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
            recipeImage.add(dbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndex("image"))));

        }

        //Update recipe
        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //TODO: UPDATE

                Intent intent = new Intent(getApplicationContext(), AddRecipeActivity.class);
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


    //Refresh listview
    @Override
    protected void onRestart() {

        super.onRestart();
        finish();
        startActivity(getIntent());

    }
}
