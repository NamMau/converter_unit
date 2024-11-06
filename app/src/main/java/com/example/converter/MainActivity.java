package com.example.converter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the spinners
        Spinner fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        Spinner toUnitSpinner = findViewById(R.id.toUnitSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"Metre", "Millimetre", "Mile", "Foot"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);

        // Get references to UI components
        EditText valueEditText = findViewById(R.id.valueEditText);
        Button convertButton = findViewById(R.id.convertButton);
        TextView resultTextView = findViewById(R.id.resultTextView);

        // Set up button click listener
        // Set up button click listener
        convertButton.setOnClickListener(v -> {
            // Get input value and selected units
            String valueString = valueEditText.getText().toString();
            if (valueString.isEmpty()) {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }

            double value = Double.parseDouble(valueString);
            String fromUnit = fromUnitSpinner.getSelectedItem().toString();
            String toUnit = toUnitSpinner.getSelectedItem().toString();

            // Perform conversion
            double result = convertLength(value, fromUnit, toUnit);

            // Display result with 6 decimal places
            resultTextView.setText(String.format("Result: %.6f %s", result, toUnit));
        });

    }

    private double convertLength(double value, String fromUnit, String toUnit) {
        double meters = 0;

        // Convert from input unit to meters
        switch (fromUnit) {
            case "Metre":
                meters = value;
                break;
            case "Millimetre":
                meters = value / 1000;
                break;
            case "Mile":
                meters = value * 1609.34;
                break;
            case "Foot":
                meters = value * 0.3048;
                break;
        }

        // Convert from meters to output unit
        switch (toUnit) {
            case "Metre":
                return meters;
            case "Millimetre":
                return meters * 1000;
            case "Mile":
                return meters / 1609.34;
            case "Foot":
                return meters / 0.3048;
        }
        return 0;
    }
}
