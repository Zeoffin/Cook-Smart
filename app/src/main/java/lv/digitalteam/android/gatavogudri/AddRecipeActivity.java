package lv.digitalteam.android.gatavogudri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AddRecipeActivity extends AppCompatActivity {

    ImageView backRecipes;
    ImageView shareRecipes;
    ImageView saveRecipes;
    ImageView deleteRecipes;
    ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //Find and beyond (⊙⊙)(☉_☉)(⊙⊙)
        backRecipes = (ImageView) findViewById(R.id.backAddRecipes);
        shareRecipes = (ImageView) findViewById(R.id.shareRecipes);
        saveRecipes = (ImageView) findViewById(R.id.saveRecipes);
        deleteRecipes = (ImageView) findViewById(R.id.deleteRecipes);
        recipeImage = (ImageView) findViewById(R.id.addRecipeImage);

        backRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: pievienot bildi

            }
        });

    }
}
