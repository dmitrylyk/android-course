package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Spinner authorSpinner;
    private RadioGroup yearRadioGroup;
    private TextView textViewSelAuthor, textViewSelYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] authors = getResources().getStringArray(R.array.authors);
        final String[] years = getResources().getStringArray(R.array.years);

        authorSpinner = findViewById(R.id.authors);
        yearRadioGroup = findViewById(R.id.years);
        textViewSelAuthor = findViewById(R.id.text_view_sel_author);
        textViewSelYear = findViewById(R.id.text_view_sel_year);
        Button buttonOK = findViewById(R.id.button_ok);

        ArrayAdapter<String> authorAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, authors);
        authorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        authorSpinner.setAdapter(authorAdapter);

        for (String year : years) {
            RadioButton radioButtonView = new RadioButton(this);
            radioButtonView.setText(year);
            yearRadioGroup.addView(radioButtonView);
        }

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String warningMsg = "not selected";
                String authorText = "Author: ";
                String yearText = "Year: ";
                String selAuthor = authorSpinner.getSelectedItem().toString();
                if (selAuthor.equals("")) {
                    textViewSelAuthor.setText(authorText + warningMsg);
                } else {
                    textViewSelAuthor.setText(authorText + selAuthor);
                }

                int selYearID = yearRadioGroup.getCheckedRadioButtonId();
                if (selYearID == -1) {
                    textViewSelYear.setText(yearText + warningMsg);
                } else {
                    RadioButton selectedRadioButton = findViewById(selYearID);
                    String selYear = selectedRadioButton.getText().toString();
                    textViewSelYear.setText(yearText + selYear);
                }
            }
        });
    }
}
