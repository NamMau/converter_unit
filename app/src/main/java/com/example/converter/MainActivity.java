package com.example.converter;

import android.os.Bundle;
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

        // Gắn kết Spinner cho các đơn vị
        Spinner spinnerFromUnit = findViewById(R.id.fromUnitSpinner);
        Spinner spinnerToUnit = findViewById(R.id.toUnitSpinner);

        // Dữ liệu cho Spinner
        String[] units = {"Meter", "Millimeter", "Mile", "Foot"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromUnit.setAdapter(adapter);
        spinnerToUnit.setAdapter(adapter);

        // Gắn kết các thành phần giao diện khác
        EditText editTextValue = findViewById(R.id.valueEditText);
        Button buttonConvert = findViewById(R.id.convertButton);
        TextView textViewResult = findViewById(R.id.resultTextView);

        // Xử lý sự kiện nhấn nút Convert
        buttonConvert.setOnClickListener(v -> {
            String inputValue = editTextValue.getText().toString();

            if (inputValue.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter the value!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double value = Double.parseDouble(inputValue);
                String fromUnit = spinnerFromUnit.getSelectedItem().toString();
                String toUnit = spinnerToUnit.getSelectedItem().toString();

                // Tính toán chuyển đổi
                double result = convertUnits(value, fromUnit, toUnit);

                // Hiển thị kết quả
                textViewResult.setText(String.format("Result: %.1f %s", result, toUnit));
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Invalid valid!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm chuyển đổi đơn vị
    private double convertUnits(double value, String fromUnit, String toUnit) {
        double valueInMeters = 0.0;

        // From some value to metter
        switch (fromUnit) {
            case "Meter":
                valueInMeters = value;
                break;
            case "Millimeter":
                valueInMeters = value / 1000.0;
                break;
            case "Mile":
                valueInMeters = value * 1609.34;
                break;
            case "Foot":
                valueInMeters = value * 0.3048;
                break;
        }

        // From metter to some values
        switch (toUnit) {
            case "Meter":
                return valueInMeters;
            case "Millimeter":
                return valueInMeters * 1000.0;
            case "Mile":
                return valueInMeters / 1609.34;
            case "Foot":
                return valueInMeters / 0.3048;
        }

        // Trường hợp không khớp đơn vị
        return 0.0;
    }
}
