package com.sound.mobileapptestnative;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.sound.mobileapptestnative.Models.Bills;

public class TipsManaging extends AppCompatActivity {

    public static float tipsPercent;

    Integer[] validNumber= new Integer[]{1,2,3,4,5,6,7,8,9,10};
    int selectedVal =0;
    Bills bills = new Bills();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_managing);

        Spinner spinner =findViewById(R.id.nopSpinner);
        Slider slider = findViewById(R.id.tipsSlider);
        EditText tips = findViewById(R.id.tipsEditText);
        TextView personVal = findViewById(R.id.valPerperson);

        Intent intent= getIntent();
        bills.setTotal(intent.getFloatExtra("Total",0.0F));

        ArrayAdapter<Integer> spArray = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item ,validNumber);
        spinner.setAdapter(spArray);
        spinner.setSelection(selectedVal);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVal= Integer.parseInt( parent.getSelectedItem().toString());
                personVal.setText(setPersonAmount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        slider.addOnChangeListener((slider1, value, fromUser) -> {
            if(fromUser) {

                float ratio = Float.parseFloat(String.valueOf(value));
                float total = bills.getTotal();
                float tip_val = total * ratio;
                bills.setTips(tip_val);
                tips.setText(String.valueOf(tip_val));
            }
        });


        tips.setOnKeyListener((v, keyCode, event) -> {
            String tipVal =tips.getText().toString();
            if(tipVal.isEmpty())
            {
                tips.setError("Fill the Field");
                return false;
            }
            float tip_val = Float.parseFloat(tipVal);
            float total =bills.getTotal();
            tipsPercent = tip_val / total;
            if(tipsPercent<=1) {

                slider.setValue(tipsPercent);
            }
            return false;
        });
    }



    String setPersonAmount()
    {
        float splitAmount= bills.getTotal()/selectedVal;

        return String.valueOf(splitAmount);
    }





}