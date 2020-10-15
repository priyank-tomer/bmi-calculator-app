package com.priyank.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String KILOGRAMS = "Kilograms";
    public static final String POUNDS = "Pounds";
    public static final String CENTIMETERS = "Centimeters";
    public static final String METERS = "Meters";
    public static final String FEET = "Feet";
    public static final String INCHES = "Inches";
    public static final String UNDERWEIGHT = "Underweight";
    public static final String NORMAL = "Normal";
    public static final String OVERWEIGHT = "Overweight";
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String FIVE = "5";
    public static final String SIX = "6";
    public static final String SEVEN = "7";
    public static final String EIGHT = "8";
    public static final String NINE = "9";
    public static final String ZERO = "0";
    public static final String DECIMAL = ".";

    boolean focus_changed = true, max = false, flag_dec=false, flag_layout=false;
    Button button_one, button_two, button_three, button_four, button_five, button_six, button_seven, button_eight, button_nine, button_zero, button_decimal, button_all_clear, btn_go;
    EditText weight_value, height_value;
    ImageButton button_back;
    static int flag_input = 0, selected_weight_unit, selected_height_unit;
    LinearLayout buttons_layout, output_layout;
    Spinner spin_weight, spin_height;
    String[] weight_units = {KILOGRAMS, POUNDS};
    String[] height_units = {CENTIMETERS, METERS, FEET, INCHES};
    String weight_input, height_input;
    TextView weight_unit, height_unit, output_value, output_comment;

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState (savedInstanceState);
        weight_input = savedInstanceState.getString ("WEIGHT_INPUT");
        height_input = savedInstanceState.getString ("HEIGHT_INPUT");
        this.flag_dec = savedInstanceState.getBoolean ("FLAG_DEC");
        flag_layout = savedInstanceState.getBoolean ("FLAG_LAYOUT");
        if(flag_layout)
            HideButtonsLayout();
    }
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState (outState);
        outState.putString ("WEIGHT_INPUT", weight_input);
        outState.putString ("HEIGHT_INPUT", height_input);
        outState.putBoolean ("FLAG_DEC", flag_dec);
        outState.putBoolean ("FLAG_LAYOUT", flag_layout);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            Log.d("Priyank", Objects.requireNonNull(e.getMessage()));
        }

        SetView();
        Listeners();
        if(flag_layout)
            HideButtonsLayout();
    }

    public void SetView(){
        buttons_layout = findViewById(R.id.buttons);
        output_layout = findViewById(R.id.output);
        button_one= findViewById(R.id.btn_1);
        button_two = findViewById(R.id.btn_2);
        button_three = findViewById(R.id.btn_3);
        button_four = findViewById(R.id.btn_4);
        button_five = findViewById(R.id.btn_5);
        button_six = findViewById(R.id.btn_6);
        button_seven = findViewById(R.id.btn_7);
        button_eight = findViewById(R.id.btn_8);
        button_nine = findViewById(R.id.btn_9);
        button_nine = findViewById(R.id.btn_9);
        button_zero = findViewById(R.id.btn_0);
        button_decimal = findViewById(R.id.btn_decimal);
        button_all_clear = findViewById(R.id.btn_ac);
        button_back = findViewById(R.id.btn_back);
        btn_go = findViewById(R.id.btn_go);
        spin_weight = findViewById(R.id.wt_spin);
        spin_height = findViewById(R.id.ht_spin);
        weight_value = findViewById(R.id.wt_value);
        weight_unit = findViewById(R.id.wt_unit);
        height_value = findViewById(R.id.ht_value);
        height_unit = findViewById(R.id.ht_unit);
        output_value = findViewById(R.id.out_val);
        output_comment = findViewById(R.id.out_comment);

        output_layout.setVisibility(View.GONE);
        ArrayAdapter<String> wt_adapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, weight_units);

        ArrayAdapter<String> ht_adapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, height_units);

        spin_weight.setAdapter(wt_adapter);
        spin_weight.setOnItemSelectedListener(this);
        spin_weight.setPrompt("Weight");

        spin_height.setAdapter(ht_adapter);
        spin_height.setOnItemSelectedListener(this);
        spin_height.setPrompt("Height");
    }

    public void Listeners(){

        weight_value.setKeyListener(null);
        height_value.setKeyListener(null);

        button_one.setOnClickListener(this);
        button_two.setOnClickListener(this);
        button_three.setOnClickListener(this);
        button_four.setOnClickListener(this);
        button_five.setOnClickListener(this);
        button_six.setOnClickListener(this);
        button_seven.setOnClickListener(this);
        button_eight.setOnClickListener(this);
        button_nine.setOnClickListener(this);
        button_zero.setOnClickListener(this);
        button_decimal.setOnClickListener(this);
        button_back.setOnClickListener(this);
        button_all_clear.setOnClickListener(this);
        btn_go.setOnClickListener(this);
        height_value.setOnClickListener(this);
        weight_value.setOnClickListener(this);

        weight_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowButtonsLayout();
                focus_changed = true;
                flag_layout=false;
                flag_input = 0;
            }
        });
        height_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowButtonsLayout();
                focus_changed = true;
                flag_layout=false;
                flag_input = 1;
            }
        });

        height_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    flag_input = 1;
                    output_layout.setVisibility(View.GONE);
                    buttons_layout.setVisibility(View.VISIBLE);
                    focus_changed = true;
                } else {
                    flag_input = 0;
                    output_layout.setVisibility(View.GONE);
                    buttons_layout.setVisibility(View.VISIBLE);
                    focus_changed = true;
                }
            }
        });
    }

    void ShowButtonsLayout(){
        output_layout.setVisibility(View.GONE);
        buttons_layout.setVisibility(View.VISIBLE);
    }

    void HideButtonsLayout(){
        buttons_layout.setVisibility(View.GONE);
        output_layout.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        weight_input = String.valueOf(weight_value.getText());
        height_input = String.valueOf(height_value.getText());

        if (focus_changed) {
            weight_input = "";
            height_input = "";
            max = false;
            flag_dec=false;
            focus_changed = false;
        }

        if (flag_input == 0) {
            if (weight_input.equals(ZERO)) {
                weight_input = "";
            }
            max = (weight_input.length() == 3) && (!flag_dec);
            if (flag_dec) {
                try {
                    String[] parts = weight_input.split("\\.");
                    String part2 = parts[1];
                    max = part2.length() == 2;
                } catch (Exception ignored) {
                }
            }
        }

        if (flag_input == 1) {
            if (height_input.equals(ZERO)) {
                height_input = "";
            }
            max = (height_input.length() == 3) && (!flag_dec);
            if (flag_dec) {
                try {
                    String[] parts = height_input.split("\\.");
                    String part2 = parts[1];
                    max = part2.length() == 2;
                } catch (Exception e) {
                    Log.d("Priyank", Objects.requireNonNull(e.getMessage()));
                }
            }
        }

        switch (v.getId()) {
            case R.id.btn_1:
                if (flag_input == 0 && !max)
                    weight_value.setText(weight_input + ONE);
                else if (flag_input == 1 && !max)
                    height_value.setText(height_input + ONE);
                break;
            case R.id.btn_2:
                if (flag_input == 0 && !max)
                    weight_value.setText(weight_input + TWO);
                else if (flag_input == 1 && !max)
                    height_value.setText(height_input + TWO);
                break;
            case R.id.btn_3:
                if (flag_input == 0 && !max)
                    weight_value.setText(weight_input + THREE);
                else if (flag_input == 1 && !max)
                    height_value.setText(height_input + THREE);
                break;
            case R.id.btn_4:
                if (flag_input == 0 && !max)
                    weight_value.setText(weight_input + FOUR +
                            "");
                else if (flag_input == 1 && !max)
                    height_value.setText(height_input + FOUR);
                break;
            case R.id.btn_5:
                if (flag_input == 0 && !max)

                    weight_value.setText(weight_input + FIVE);
                else if (flag_input == 1 && !max)
                    height_value.setText(height_input + FIVE);
                break;
            case R.id.btn_6:
                if (flag_input == 0 && !max)
                    weight_value.setText(weight_input + SIX);
                else if (flag_input == 1 && !max)
                    height_value.setText(height_input + SIX);
                break;
            case R.id.btn_7:
                if (flag_input == 0 && !max)
                    weight_value.setText(weight_input + SEVEN);
                else if (flag_input == 1 && !max)
                    height_value.setText(height_input + SEVEN);
                break;
            case R.id.btn_8:
                if (flag_input == 0 && !max)
                    weight_value.setText(weight_input + EIGHT);
                else if (flag_input == 1 && !max)
                    height_value.setText(height_input + EIGHT);
                break;
            case R.id.btn_9:
                if (flag_input == 0 && !max)
                    weight_value.setText(weight_input + NINE);
                else if (flag_input == 1 && !max)
                    height_value.setText(height_input + NINE);
                break;
            case R.id.btn_0:
                if (flag_input == 0 && !max)
                    weight_value.setText(weight_input + ZERO);
                else if (flag_input == 1 && !max)
                    height_value.setText(height_input + ZERO);
                break;
            case R.id.btn_decimal:
                if (flag_input == 0 && !String.valueOf(weight_value.getText()).contains(".")){
                    weight_value.setText(weight_value.getText() + DECIMAL);
                }
                else if (flag_input == 1 && !String.valueOf(height_value.getText()).contains("."))
                    height_value.setText(height_value.getText() + DECIMAL);
                flag_dec=true;
                break;
            case R.id.btn_ac:
                if (flag_input == 0)
                    weight_value.setText(ZERO);
                else if (flag_input == 1)
                    height_value.setText(ZERO);
                flag_dec=false;
                break;
            case R.id.btn_back:
                if (flag_input == 0) {
                    String str = weight_input;
                    if (str.length() > 1) {
                        str = str.substring(0, str.length() - 1);
                        weight_value.setText(str);
                    } else {
                        weight_value.setText(ZERO);
                    }
                    if(!String.valueOf(height_value.getText()).contains("."))
                        flag_dec=false;
                } else if (flag_input == 1) {
                    String str = height_input;
                    if (str.length() > 1) {
                        str = str.substring(0, str.length() - 1);
                        height_value.setText(str);
                    } else {
                        height_value.setText(ZERO);
                    }
                }
                break;
            case R.id.btn_go:
                float weight = Float.parseFloat(String.valueOf(weight_value.getText()));
                float height = Float.parseFloat(String.valueOf(height_value.getText()));
                if (selected_weight_unit == 1)
                    weight *= 0.453592;
                if (selected_height_unit == 0)
                    height *= .01;
                if (selected_height_unit == 2)
                    height *= 0.3048;
                if (selected_height_unit == 3)
                    height *= 0.0254;
                double bmi = weight / (height * height);
                System.out.println(bmi);
                bmi = Math.round(bmi * 10) / 10.0;
                output_value.setText(String.valueOf(bmi));
                if (bmi < 18.5) {

                    output_comment.setText(UNDERWEIGHT);
                    output_comment.setTextColor(Color.parseColor("#2495D6"));
                }
                if (bmi >= 18.5 && bmi <= 25.0) {
                    output_comment.setText(NORMAL);
                    output_comment.setTextColor(Color.parseColor("#1EB115"));
                }
                if (bmi > 25) {
                    output_comment.setText(OVERWEIGHT);
                    output_comment.setTextColor(Color.parseColor("#EE7321"));
                }
                HideButtonsLayout();
                flag_layout=true;
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(parent);
        System.out.println(parent.getAdapter());
        System.out.println(parent.getParent());
        String temp = String.valueOf(parent);
        if (temp.endsWith("wt_spin}")) {
            weight_unit.setText(weight_units[position]);
            selected_weight_unit = position;
        }
        if (temp.endsWith("ht_spin}")) {
            height_unit.setText(height_units[position]);
            selected_height_unit = position;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
