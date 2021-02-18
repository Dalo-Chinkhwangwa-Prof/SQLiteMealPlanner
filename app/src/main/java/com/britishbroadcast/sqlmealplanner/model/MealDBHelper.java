package com.britishbroadcast.sqlmealplanner.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.britishbroadcast.sqlmealplanner.model.data.Meal;
import com.britishbroadcast.sqlmealplanner.util.Day;

import java.util.ArrayList;
import java.util.List;

public class MealDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "meal.db";
    public static int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "meals";

    public static final String MEAL_ID_COLUMN = "meal_id";
    public static final String DAY_OF_WEEK_COLUMN = "day_of_week";
    public static final String MEAL_NAME_COLUMN = "meal_name";
    public static final String MEAL_PRICE_COLUMN = "meal_price";

    public MealDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createCommand = "CREATE TABLE " + TABLE_NAME + " (" +
                MEAL_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DAY_OF_WEEK_COLUMN + " TEXT, " +
                MEAL_NAME_COLUMN + " TEXT, " +
                MEAL_PRICE_COLUMN + " REAL);";
        sqLiteDatabase.execSQL(createCommand); //Table officially created in the database!
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String upgradeCommand = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(upgradeCommand);
        DATABASE_VERSION++;
        onCreate(sqLiteDatabase);
    }

    public void insertNewMeal(Meal meal) {
//        String insert = "INSERT INTO ...."+meal.getName()///
        ContentValues values = new ContentValues();
        values.put(DAY_OF_WEEK_COLUMN, meal.getDay().name());
        values.put(MEAL_NAME_COLUMN, meal.getName());
        values.put(MEAL_PRICE_COLUMN, meal.getPrice());

        getWritableDatabase().insert(TABLE_NAME, null, values);
    }

//    public Cursor getAllMeals(){
//        String getAll = "SELECT * FROM "+TABLE_NAME;
//        return getReadableDatabase().rawQuery(getAll, null);
//    }

    public List<Meal> getAllMeals() {
        String getAll = "SELECT * FROM " + TABLE_NAME;
        Cursor allMeals = getReadableDatabase().rawQuery(getAll, null);
        List<Meal> mealsList = new ArrayList<>();

        while (allMeals.moveToNext()) {
            int mealID = allMeals.getInt(allMeals.getColumnIndex(MEAL_ID_COLUMN));
            String name = allMeals.getString(allMeals.getColumnIndex(MEAL_NAME_COLUMN));
            double price = allMeals.getDouble(allMeals.getColumnIndex(MEAL_PRICE_COLUMN));
            Day day = Enum.valueOf(Day.class, allMeals.getString(allMeals.getColumnIndex(DAY_OF_WEEK_COLUMN)));
            Meal meal = new Meal(mealID, name, day, price);
            mealsList.add(meal);
        }
        allMeals.close();
        return mealsList;
    }
}
