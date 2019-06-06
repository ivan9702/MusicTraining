package com.example.musictraining;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {

    EditText tvTotalAward, tvTotalHalfAward, tvErrors;
    EditText etCars, etShield, etTV, etToy, etPark,etClock;

    EditText etMaxQuest;
    SharedPreferences sharedata;
    SharedPreferences.Editor editor;

//    Button btnAddBasic, btnAddMedium, btnAddHigh;
//    Button btnSubtrBasic, btnSubtrMedium, btnSubtrHigh;
//    Button btnMultiBasic, btnMultiMedium, btnMultiHigh;
//    Button btnDivBasic, btnDivMedium,btnDivHigh;
    Button btnCommit, btnCancel;
//
//    int AddBasic, AddMedium, AddHigh;
//    int SubtrBasic, SubtrMedium, SubtrHigh;
//    int  MultiBasic, MultiMedium, MultiHigh;


    int Cars, Shield, TV, Toy, Park, Clock, MaxQuest;
    int stars, halfstars, error;

    Boolean Entry_Cars = true;
    Boolean Entry_Shield = true;
    Boolean Entry_TV = true;
    Boolean Entry_Toy = true;
    Boolean Entry_Park = true;
    Boolean Entry_Clock = true;
    Boolean Entry_MaxQuest = true;

    Boolean Entry_stars = true;
    Boolean Entry_halfstar = true;
    Boolean Entry_error = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedata = getSharedPreferences("award", MODE_PRIVATE);
        editor = sharedata.edit();//获取Editor

        Log.d("Setting onCreate", "stars:"+sharedata.getInt("stars",0));


        tvTotalAward = findViewById(R.id.tvTotalAward);
        tvTotalHalfAward = findViewById(R.id.tvTotalHalfAward);
        tvErrors = findViewById(R.id.tvErrors);

        etCars = findViewById(R.id.etCars);
        etShield = findViewById(R.id.etShield);
        etTV = findViewById(R.id.etTV);
        etToy = findViewById(R.id.etToy);
        etPark = findViewById(R.id.etPark);

        etClock = findViewById(R.id.etClock);
        etMaxQuest = findViewById(R.id.etMaxQuest);

        btnCommit = findViewById(R.id.btnCommit);
        btnCancel = findViewById(R.id.btnCancel);



        etCars.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Entry_Cars) {
                    etCars.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                Entry_Cars = false;
            }
        });

        etShield.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Entry_Shield) {
                    etShield.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                Entry_Shield = false;
            }
        });

        etTV.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Entry_TV) {
                    etTV.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                Entry_TV = false;
            }
        });

        etToy.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Entry_Toy) {
                    etToy.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                Entry_Toy = false;
            }
        });

        etPark.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Entry_Park) {
                    etPark.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                Entry_Park = false;
            }
        });

        etClock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Entry_Clock) {
                    etClock.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                Entry_Clock = false;
            }
        });

        etMaxQuest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Entry_MaxQuest) {
                    etMaxQuest.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                Entry_MaxQuest = false;
            }
        });


        tvTotalAward.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Entry_stars) {
                    tvTotalAward.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                Entry_stars = false;
            }
        });

        tvTotalHalfAward.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Entry_halfstar) {
                    tvTotalHalfAward.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                Entry_halfstar = false;
            }
        });

        tvErrors.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Entry_error) {
                    tvErrors.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                Entry_error = false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedata = getSharedPreferences("award", MODE_PRIVATE);
        editor = sharedata.edit();//获取Editor

        Log.d("Setting onResume", "stars:"+sharedata.getInt("stars",0));


        Log.d("Setting onResume", "date:"+sharedata.getString("date", "0"));

        Log.d("Setting onResume", "errImgNum:"+sharedata.getInt("errImgNum",0));

        Log.d("Setting onResume", "CarNum:"+sharedata.getInt("CarNum",0));
        Log.d("Setting onResume", "ShieldNum:"+sharedata.getInt("ShieldNum",0));
        Log.d("Setting onResume", "TVNum:"+sharedata.getInt("TVNum",0));

        Log.d("Setting onResume", "ToyNum:"+sharedata.getInt("ToyNum", 0));

        Log.d("Setting onResume", "ParkNum:"+sharedata.getInt("ParkNum",0));

//==========================================================================================================
//              Get Data from SharePreference
//==========================================================================================================




        Cars= sharedata.getInt("CarNum",0);
        Shield = sharedata.getInt("ShieldNum",0);
        TV = sharedata.getInt("TVNum",0);
        Toy = sharedata.getInt("ToyNum", 0);
        Park = sharedata.getInt("ParkNum",0);

        Clock = sharedata.getInt("ClockTimer", 20);
        MaxQuest = sharedata.getInt("maxquest", 20);

        stars = sharedata.getInt("stars",0);
        halfstars = (sharedata.getBoolean("starHalf",false)? 1:0);
        error = sharedata.getInt("errorCount",0);

         //=====================================================================================================
        //                      Show SharedPreferences Data
        //=====================================================================================================


        if(sharedata.getBoolean("starHalf",false) == false)
            tvTotalHalfAward.setText("0");
        else
            tvTotalHalfAward.setText("1");

        tvTotalAward.setText(Integer.toString(sharedata.getInt("stars",0)));

        tvErrors.setText(Integer.toString(sharedata.getInt("errorCount",0)));



//
//        Str = etCars.getText().toString()+Integer.valueOf(Cars);
//        etCars.setText(Str);
//        Str = etShield.getText().toString()+Integer.valueOf(Shield);
//        etShield.setText(Str);
//        Str = etTV.getText().toString()+Integer.valueOf(TV);
//        etTV.setText(Str);
//        Str = etToy.getText().toString()+Integer.valueOf(Toy);
//        etToy.setText(Str);
//        Str = etPark.getText().toString()+Integer.valueOf(Park);
//        etPark.setText(Str);
        etCars.setText(Integer.toString(Cars));
        etShield.setText(Integer.toString(Shield));
        etTV.setText(Integer.toString(TV));
        etToy.setText(Integer.toString(Toy));
        etPark.setText(Integer.toString(Park));
        etClock.setText(Integer.toString(Clock));
        etMaxQuest.setText(Integer.toString(MaxQuest));
    }

    public void ButtonClick(View view) {

        int id= view.getId();
        String Str;
        int len;
        switch(id)
        {
            case R.id.btnCommit:
                commitFunc();
                Log.d("BTN", "Commit");
                break;

            case R.id.btnCancel:
                Log.d("BTN", "Cancel");
                cancelFunc();
                break;


        }


    }

    void commitFunc() {
        sharedata = getSharedPreferences("award", MODE_PRIVATE);
        editor = sharedata.edit();//获取Editor




        Cars = Integer.valueOf(etCars.getText().toString());
        Shield = Integer.valueOf(etShield.getText().toString());
        TV = Integer.valueOf(etTV.getText().toString());
        Toy = Integer.valueOf(etToy.getText().toString());
        Park = Integer.valueOf(etPark.getText().toString());

        Clock = Integer.valueOf(etClock.getText().toString());
        MaxQuest = Integer.valueOf(etMaxQuest.getText().toString());

        stars = Integer.valueOf(tvTotalAward.getText().toString());
        halfstars = Integer.valueOf(tvTotalHalfAward.getText().toString());
        error = Integer.valueOf(tvErrors.getText().toString());


        editor.putInt("CarNum", Cars);
        editor.putInt("ShieldNum", Shield);
        editor.putInt("TVNum", TV);
        editor.putInt("ToyNum", Toy);
        editor.putInt("ParkNum", Park);
        editor.putInt("ClockTimer", Clock);
        editor.putInt("maxquest", MaxQuest);

        editor.putInt("stars", stars);
        if(halfstars == 0)
            editor.putBoolean("starHalf", false);
        else
            editor.putBoolean("starHalf", true);

        editor.putInt("errorCount", error);

        editor.commit();
        cancelFunc();
    }

    void cancelFunc()
    {
        super.onBackPressed();
    }

}
