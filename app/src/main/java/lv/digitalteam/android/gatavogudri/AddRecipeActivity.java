package lv.digitalteam.android.gatavogudri;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class AddRecipeActivity extends AppCompatActivity {

    final int RequestPermissionCode = 1;

    Uri uri;
    Intent GalIntent, CropIntent;
    RecipesDBManager recipesDBManager;

    ImageView backRecipes;
    ImageView shareRecipes;
    ImageView saveRecipes;
    ImageView deleteRecipes;
    ImageView recipeImage;

    EditText addRecipeTitle;
    EditText addRecipeDesc;
    EditText addRecipeIngr;
    EditText addRecipePrep;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipesDBManager = new RecipesDBManager(this);

        //Find and beyond (⊙⊙)(☉_☉)(⊙⊙)
        backRecipes = (ImageView) findViewById(R.id.backAddRecipes);
        shareRecipes = (ImageView) findViewById(R.id.shareRecipes);
        saveRecipes = (ImageView) findViewById(R.id.saveRecipes);
        deleteRecipes = (ImageView) findViewById(R.id.deleteRecipes);
        recipeImage = (ImageView) findViewById(R.id.addRecipeImage);

        addRecipeTitle = (EditText) findViewById(R.id.addRecipeTitle);
        addRecipeDesc = (EditText) findViewById(R.id.addRecipeDesc);
        addRecipeIngr = (EditText) findViewById(R.id.addRecipeIngr);
        addRecipePrep = (EditText) findViewById(R.id.addRecipePrep);


                //Back button
        backRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        //Add image buttõnõ
        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GalIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(GalIntent, getString(R.string.select_image)), 2);

            }
        });


        //Save
        saveRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titleField = addRecipeTitle.getText().toString();
                String descField = addRecipeDesc.getText().toString();
                String ingrField = addRecipeIngr.getText().toString();
                String prepField = addRecipePrep.getText().toString();

                // convert image to bitmap
                recipeImage.buildDrawingCache();
                Bitmap img = recipeImage.getDrawingCache();

                DbBitmapUtility dbBitmapUtility = new DbBitmapUtility();

                if (titleField.equals("") || descField.equals("") || ingrField.equals("") || prepField.equals("")) {

                    Toast.makeText(AddRecipeActivity.this, getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show();

                } else {

                    boolean inserted = recipesDBManager.newData(titleField, descField, ingrField, prepField, dbBitmapUtility.getBytes(img));

                    if (inserted) {

                        Toast.makeText(AddRecipeActivity.this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {

                        Toast.makeText(AddRecipeActivity.this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0 && resultCode == RESULT_OK) {

            CropImage();

        } else if(requestCode == 2) {

            if(data != null) {

                uri = data.getData();
                CropImage();
            }

        } else if(requestCode == 1) {

            if(data != null) {

                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                recipeImage.setImageBitmap(bitmap);


            }

        }
    }

    private void CropImage() {

        try {

            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 1);
            CropIntent.putExtra("aspectY", 1);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

            Toast.makeText(this, getString(R.string.error_occured), Toast.LENGTH_SHORT).show();

        }

    }

}
