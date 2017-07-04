package lv.digitalteam.android.gatavogudri;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class AddRecipeActivity extends AppCompatActivity {

    final int RequestPermissionCode = 1;

    Uri uri;
    Intent GalIntent, CropIntent;
    RecipesDBManager recipesDBManager;
    Intent intent;
    DbBitmapUtility dbBitmapUtility;

    ImageView backRecipes;
    ImageView saveRecipes;
    ImageView deleteRecipes;
    ImageView recipeImage;

    EditText addRecipeTitle;
    EditText addRecipeDesc;
    EditText addRecipeIngr;
    EditText addRecipePrep;

    private static final int PICK_IMAGE_REQUEST = 1;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //Admob and firebase
        MobileAds.initialize(this, "ca-app-pub-9573430590084189~2808728113");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9573430590084189/5762194515");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //Datubāze
        recipesDBManager = new RecipesDBManager(this);

        //Find and beyond (⊙⊙)(☉_☉)(⊙⊙)
        backRecipes = (ImageView) findViewById(R.id.backAddRecipes);
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

        intent = getIntent();

        if (intent.hasExtra("id")) {

            //Update
            final String id = intent.getStringExtra("id");
            String title = intent.getStringExtra("title");
            String desc = intent.getStringExtra("description");
            String ingr = intent.getStringExtra("ingredients");
            String prep = intent.getStringExtra("preperations");

            byte[] img = intent.getByteArrayExtra("image");
            dbBitmapUtility = new DbBitmapUtility();
            Bitmap bitImg = dbBitmapUtility.getImage(img);

            deleteRecipes.setVisibility(1);

            addRecipeTitle.setText(title);
            addRecipeDesc.setText(desc);
            addRecipeIngr.setText(ingr);
            addRecipePrep.setText(prep);
            recipeImage.setImageBitmap(bitImg);

            saveRecipes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String newTitle = addRecipeTitle.getText().toString();
                    String newDesc = addRecipeDesc.getText().toString();
                    String newIngr = addRecipeIngr.getText().toString();
                    String newPrep = addRecipePrep.getText().toString();

                    recipeImage.buildDrawingCache();
                    Bitmap newImg = recipeImage.getDrawingCache();

                    boolean updated = recipesDBManager.update(id, newTitle, newDesc, newIngr, newPrep, dbBitmapUtility.getBytes(newImg));

                    if(updated) {

                        Toast.makeText(AddRecipeActivity.this, getString(R.string.updated), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {

                        Toast.makeText(AddRecipeActivity.this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();

                    }

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }

                }
            });

            deleteRecipes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int deletedRows = recipesDBManager.delete(id);

                    if(deletedRows > 0) {

                        Toast.makeText(AddRecipeActivity.this, getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {

                        Toast.makeText(AddRecipeActivity.this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();

                    }

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }

                }
            });

        } else {

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

                    dbBitmapUtility = new DbBitmapUtility();

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

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }

                }
            });

        }

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
