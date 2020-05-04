package com.example.lab2;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


public class FragmentSelection extends Fragment {

    private Spinner authorSpinner;
    private RadioGroup yearRadioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection, container, false);

        final String[] authors = getResources().getStringArray(R.array.authors);
        final String[] years = getResources().getStringArray(R.array.years);

        authorSpinner = view.findViewById(R.id.authors);
        yearRadioGroup = view.findViewById(R.id.years);
        Button buttonOK = view.findViewById(R.id.button_ok);

        ArrayAdapter<String> authorAdapter = new ArrayAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, authors);
        authorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        authorSpinner.setAdapter(authorAdapter);

        for (String year : years) {
            RadioButton radioButtonView = new RadioButton(getActivity());
            radioButtonView.setText(year);
            yearRadioGroup.addView(radioButtonView);
        }

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selAuthor = authorSpinner.getSelectedItem().toString();
                String selYear = "";

                int selYearID = yearRadioGroup.getCheckedRadioButtonId();
                if (selYearID != -1) {
                    RadioButton selectedRadioButton = yearRadioGroup.findViewById(selYearID);
                    selYear = selectedRadioButton.getText().toString();
                }

                MainActivity m = (MainActivity) getActivity();

                m.transferToOutput(selAuthor, selYear);

            }
        });

        return view;
    }
}
