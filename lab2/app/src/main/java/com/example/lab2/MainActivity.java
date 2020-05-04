package com.example.lab2;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

    private FragmentOutput output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (FragmentOutput) getFragmentManager().findFragmentById(R.id.fragment_output);
    }

    public void transferToOutput(String author, String year) {

        output.outputSelectedItems(author, year);
    }
}
