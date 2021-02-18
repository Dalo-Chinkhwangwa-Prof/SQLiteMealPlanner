package com.britishbroadcast.sqlmealplanner.view;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.britishbroadcast.sqlmealplanner.R;
import com.britishbroadcast.sqlmealplanner.model.MealDBHelper;
import com.britishbroadcast.sqlmealplanner.model.data.Meal;
import com.britishbroadcast.sqlmealplanner.util.Day;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.britishbroadcast.sqlmealplanner.model.MealDBHelper.DAY_OF_WEEK_COLUMN;
import static com.britishbroadcast.sqlmealplanner.model.MealDBHelper.MEAL_ID_COLUMN;
import static com.britishbroadcast.sqlmealplanner.model.MealDBHelper.MEAL_NAME_COLUMN;
import static com.britishbroadcast.sqlmealplanner.model.MealDBHelper.MEAL_PRICE_COLUMN;

public class MainActivity extends AppCompatActivity {

    private MealDBHelper mealDBHelper;


    @BindView(R.id.day_radio_group)
    public RadioGroup radioGroup;
    
    @BindView(R.id.meal_name_edittext)
    public EditText mealNameEditText;
    
    @BindView(R.id.meal_price_edittext)
    public EditText mealPriceEditText;
    
    private Day day = Day.MONDAY;
    
    @OnClick(R.id.add_meal_button)
    public void onAddMeal(View view){
        String name = mealNameEditText.getText().toString().trim();
        mealNameEditText.setText("");
        
        double mealPrice = Double.parseDouble(mealPriceEditText.getText().toString());
        mealPriceEditText.setText("");

        Meal meal = new Meal(name, day, mealPrice);
        mealDBHelper.insertNewMeal(meal);
        //everytime I add the meal read from the database
        readAllMeals();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mealDBHelper = new MealDBHelper(this);
        readAllMeals();

        getCurrentDayOfWeek();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.radio_monday:
                        day = Day.MONDAY;
                        break;
                    case R.id.radio_tuesday:
                        day = Day.TUESDAY;
                        break;
                    case R.id.radio_wednesday:
                        day = Day.WEDNESDAY;
                        break;
                    case R.id.radio_thursday:
                        day = Day.THURSDAY;
                        break;
                    case R.id.radio_friday:
                        day = Day.FRIDAY;
                        break;
                    case R.id.radio_saturday:
                        day = Day.SATURDAY;
                        break;
                    case R.id.radio_sunday:
                        day = Day.SUNDAY;
                        break;
                }
            }
        });
    }

    private void getCurrentDayOfWeek() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.US);
        String dayString = simpleDateFormat.format(new Date()).toUpperCase();

        switch (dayString){
            case "SUNDAY":
                day = Day.SUNDAY;
                radioGroup.check(R.id.radio_sunday);
                break;
            case "MONDAY":
                day = Day.MONDAY;
                radioGroup.check(R.id.radio_monday);
                break;
            case "TUESDAY":
                day = Day.TUESDAY;
                radioGroup.check(R.id.radio_tuesday);
                break;
            case "WEDNESDAY":
                day = Day.WEDNESDAY;
                radioGroup.check(R.id.radio_wednesday);
                break;
            case "THURSDAY":
                day = Day.THURSDAY;
                radioGroup.check(R.id.radio_thursday);
                break;
            case "FRIDAY":
                day = Day.FRIDAY;
                radioGroup.check(R.id.radio_friday);
                break;
            case "SATURDAY":
                day = Day.SATURDAY;
                radioGroup.check(R.id.radio_saturday);
                break;
        }


    }

    //    private void readAllMeals() {
//        Cursor allMeals = mealDBHelper.getAllMeals();
//        allMeals.move(-1);
//
//        while(allMeals.moveToNext()){
//            int mealID = allMeals.getInt(allMeals.getColumnIndex(MEAL_ID_COLUMN));
//            String name = allMeals.getString(allMeals.getColumnIndex(MEAL_NAME_COLUMN));
//            double price = allMeals.getDouble(allMeals.getColumnIndex(MEAL_PRICE_COLUMN));
//            Day day  = Enum.valueOf(Day.class,  allMeals.getString(allMeals.getColumnIndex(DAY_OF_WEEK_COLUMN)));
//            Meal meal  = new Meal(mealID, name, day, price);
//            Log.d("TAG_X", meal.toString());
//        }
//    }
    private void readAllMeals() {
        List<Meal> meals = new ArrayList<>();
        meals = mealDBHelper.getAllMeals();
        meals.forEach(new Consumer<Meal>() {
            @Override
            public void accept(Meal meal) {
                Log.d("TAG_X", meal.toString());
            }
        });
    }
}