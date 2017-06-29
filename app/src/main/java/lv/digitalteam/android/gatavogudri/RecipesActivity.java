package lv.digitalteam.android.gatavogudri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    */

    ImageView backRecipes;
    ImageView filterRecipes;
    ImageView addRecipes;

    BaseAdapter baseAdapter;
    ListView recipesList;

    ArrayList<String> recipeTitle = new ArrayList<>();
    ArrayList<String> recipeDesc = new ArrayList<>();
    ArrayList<Integer> recipeImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        recipesList = (ListView) findViewById(R.id.recipesList);

        //TODO: REMOVE TEST
        recipeTitle.add("Zemeņu kūka");
        recipeTitle.add("Torte 'Cielaviņa'");

        recipeDesc.add("dfgsdsafgfds gsdf  dfsgsdfgsdfgser g43 gse5r gr gser gsfdg ");
        recipeDesc.add("dfgsdsafgfds gsew 4t ert ser srdt43 gerdg  as e5r gr gser gsfdg ");

        recipeImage.add(R.drawable.kuka1);
        recipeImage.add(R.drawable.kuka2);

        //--------------------------------UPPER TESTING------------------------------------------

        //Adapter
        baseAdapter = new RecipesAdapter(this, recipeTitle, recipeDesc, recipeImage);
        recipesList.setAdapter(baseAdapter);

        addRecipes = (ImageView) findViewById(R.id.addRecipes);
        backRecipes = (ImageView) findViewById(R.id.backRecipes);
        filterRecipes = (ImageView) findViewById(R.id.filterRecipes);

        addRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddRecipeActivity.class);
                startActivity(intent);

            }
        });

        backRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

    }

}
