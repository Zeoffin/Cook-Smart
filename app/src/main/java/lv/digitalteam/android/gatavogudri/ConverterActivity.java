package lv.digitalteam.android.gatavogudri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class ConverterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner productSpinner;
    TextView typeTablespoon;
    TextView typeGlass;
    TextView defineValues;
    TextView typeTeaspoon;
    EditText valueInput;
    Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        //Create
        typeGlass = (TextView) findViewById(R.id.typeGlass);
        typeTablespoon = (TextView) findViewById(R.id.typeTablespoon);
        typeTeaspoon = (TextView) findViewById(R.id.typeTeaspoon);
        valueInput = (EditText) findViewById(R.id.valueInput);
        defineValues = (TextView) findViewById(R.id.defineValue);

        //Create spinner
        productSpinner = (Spinner) findViewById(R.id.productSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.products_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSpinner.setAdapter(adapter);

        //Set listener
        productSpinner.setOnItemSelectedListener(this);

        convertButton = (Button) findViewById(R.id.convertButton);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // Log, which item user has selected
        String selected = parent.getItemAtPosition(position).toString();
        Log.i("Spinner listener", "Item selected " + selected);

        //Get position
        final int index = productSpinner.getSelectedItemPosition();
        final int[] tableSpoonList = getResources().getIntArray(R.array.tableSpoon);
        final int[] glassList = getResources().getIntArray(R.array.glass);
        final int[] teaSpoonList = getResources().getIntArray(R.array.teaSpoon);

        // Set the texts and defaults
        if (glassList[index] == 0) {
            typeGlass.setVisibility(View.INVISIBLE);
        } else {
            typeGlass.setText(getString(R.string.glass) + ": \n" + glassList[index] + getString(R.string.grams));
            typeGlass.setVisibility(View.VISIBLE);
            defineValues.setText(getString(R.string.default_value));
        }

        if (tableSpoonList[index] == 0) {
            typeTablespoon.setVisibility(View.INVISIBLE);
        } else {
            typeTablespoon.setText(getString(R.string.table_spoon) + ": \n" + tableSpoonList[index] + getString(R.string.grams));
            typeTablespoon.setVisibility(View.VISIBLE);
            defineValues.setText(getString(R.string.default_value));
        }

        if (teaSpoonList[index] == 0) {
            typeTeaspoon.setVisibility(View.INVISIBLE);
        } else {
            typeTeaspoon.setText(getString(R.string.tea_spoon) + ": \n" + teaSpoonList[index] + getString(R.string.grams));
            typeTeaspoon.setVisibility(View.VISIBLE);
            defineValues.setText(getString(R.string.default_value));
        }

        //The 'convert' button is clicked
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userInput = valueInput.getText().toString();

                if (userInput.equals("")) {

                    Toast.makeText(ConverterActivity.this, getString(R.string.enter_value), Toast.LENGTH_SHORT).show();

                } else {

                    //Convert EditText input to double
                    double userInputInteger = Integer.parseInt(valueInput.getText().toString());

                    //Set title
                    defineValues.setText(userInputInteger + getString(R.string.grams_are));

                    //Augstākā matemātika ar integrēšanu un trigonometriskajām robežām, lai aprēķinātu no gramiem
                    double customTableSpoon = userInputInteger / (double)tableSpoonList[index];
                    double customTeaSpoon = userInputInteger / (double)teaSpoonList[index];
                    double customGlass = userInputInteger / (double)glassList[index];

                    //Format and convert to string
                    String customTableSpoonString = String.format("%,.1f", customTableSpoon);
                    String customTeaSpoonString = String.format("%,.1f", customTeaSpoon);
                    String customGlassString = String.format("%,.1f", customGlass);

                    //Parādīt lietotājam
                    typeTablespoon.setText(customTableSpoonString + " " + getString(R.string.table_spoon));
                    typeTeaspoon.setText(customTeaSpoonString + " " + getString(R.string.tea_spoon));
                    typeGlass.setText(customGlassString + " " + getString(R.string.glass));

                }

            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("Spinner listener", "Nothing selected");
    }

}
