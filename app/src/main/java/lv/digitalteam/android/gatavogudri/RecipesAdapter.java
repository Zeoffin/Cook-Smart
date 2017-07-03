package lv.digitalteam.android.gatavogudri;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class RecipesAdapter extends BaseAdapter {

    ArrayList<String> recipeTitle;
    ArrayList<String> recipeDesc;
    ArrayList<Bitmap> recipeImage;
    Context context;

    public RecipesAdapter(Context context, ArrayList<String> recipeTitle, ArrayList<String> recipeDesc, ArrayList<Bitmap> recipeImage) {

        this.recipeTitle=recipeTitle;
        this.recipeDesc=recipeDesc;
        this.recipeImage=recipeImage;
        this.context=context;

    }

    @Override
    public int getCount() {
        return recipeTitle.size();
    }

    @Override
    public Object getItem(int i) {
        return recipeTitle.get(i);
    }

    @Override
    public long getItemId(int i) {
        return recipeTitle.indexOf(getItem(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_recipes, null);

        }

        TextView recipesTitleList = (TextView) view.findViewById(R.id.recipesTitle);
        TextView recipesDescList = (TextView) view.findViewById(R.id.recipesDesc);
        ImageView recipesImageList = (ImageView) view.findViewById(R.id.recipesImage);

        recipesTitleList.setText(recipeTitle.get(i));
        recipesDescList.setText(recipeDesc.get(i));
        recipesImageList.setImageBitmap(recipeImage.get(i));

        return view;
    }
}
