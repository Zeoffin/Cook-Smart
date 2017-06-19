package lv.digitalteam.android.gatavogudri;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashSet;

public class GroceryListActivity extends AppCompatActivity implements View.OnClickListener{

    //TODO: Pielikt pogu "clear all items"

    EditText editText;
    ArrayList<String> groceries = new ArrayList<>();
    SharedPreferences sharedPreferences;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    TextView list_content;

    //Save the list method
    public void saveGroceriesList() {
        sharedPreferences = this.getSharedPreferences("lv.digitalteam.android.gatavogudri", MODE_PRIVATE);
        HashSet<String> set = new HashSet(groceries);
        sharedPreferences.edit().putStringSet("Groceries", set).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        //Floating action button
        Button addNewItem = (Button) findViewById(R.id.addNewItem);
        addNewItem.setOnClickListener(this);

        //Edit Text
        editText = (EditText) findViewById(R.id.enterItemEditText);

        //list view
        listView = (ListView) findViewById(R.id.groceryListView);

        //Load on startup Groceries
        sharedPreferences = this.getSharedPreferences("lv.digitalteam.android.gatavogudri", MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("Groceries",null);
        if (set == null) {
            groceries.add(getString(R.string.hold_to_delete));
        } else {
            groceries = new ArrayList(set);
        }

        //arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, groceries);
        arrayAdapter = new GroceryListAdapter(this, groceries);
        listView.setAdapter(arrayAdapter);

        //list view click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                list_content = (TextView) view.findViewById(R.id.list_content);

                if (list_content.getCurrentTextColor() == Color.parseColor("#000000")){ //Check if item is checked or not

                    list_content.setTextColor(Color.parseColor("#a7a7a7"));
                    list_content.setPaintFlags(list_content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                } else {

                    list_content.setTextColor(Color.parseColor("#000000"));
                    list_content.setPaintFlags(list_content.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));

                }

            }
        });

        //List view long-click listener
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                groceries.remove(position);
                arrayAdapter.notifyDataSetChanged();
                saveGroceriesList();

                return true;
            }
        });

    }

    private void scrollListViewToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view..
                listView.setSelection(arrayAdapter.getCount() - 1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNewItem:

                String userInput = editText.getText().toString();

                if(userInput.equals("")) { //Check if the string is empty

                    Toast.makeText(GroceryListActivity.this, getString(R.string.enter_your_item), Toast.LENGTH_SHORT).show();

                } else if(groceries.contains(userInput)) { //Check if the item already exists

                    Toast.makeText(GroceryListActivity.this, getString(R.string.item_already_exists), Toast.LENGTH_SHORT).show();

                } else {
                    //Add to list
                    groceries.add(userInput);

                    //Refresh ListView
                    arrayAdapter.notifyDataSetChanged();

                    //Set blank text
                    editText.setText("");

                    //Scrolls to down
                    scrollListViewToBottom();

                    saveGroceriesList();
                }

                break;
        }
    }

}
