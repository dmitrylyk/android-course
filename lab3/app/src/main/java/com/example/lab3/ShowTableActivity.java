package com.example.lab3;

import android.app.Activity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowTableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_table);

        ArrayList<String> allRecords = getIntent().getStringArrayListExtra("allRecords");

        TextView textViewTable = findViewById(R.id.text_view_table);

        StringBuilder result = new StringBuilder();
        if (allRecords.size() > 0) {
            for (String record: allRecords) {
                result.append(record);
            }
        } else {
            String empty_table_msg = getResources().getString(R.string.table_is_empty_msg);
            result.append(empty_table_msg);
        }

        textViewTable.setText(result);
    }
}
