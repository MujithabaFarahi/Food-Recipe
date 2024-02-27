package com.example.coursework2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchMeal : AppCompatActivity() {

    //Initializing
    private lateinit var searchMealOnDB: Button
    private lateinit var searchMealText: EditText
    private lateinit var mealClassAdapter: MealClassAdapter
    private var meals: List<Meal> = listOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_meal)

        // create the database
        val db = Room.databaseBuilder(this, AppDatabase::class.java,
            "myDatabase").build()
        val mealDao =db.mealDao()

        searchMealOnDB = findViewById(R.id.searchMealOnDB)
        searchMealText = findViewById(R.id.searchMealText)

        //
        searchMealOnDB.setOnClickListener() {
            if (searchMealText.text?.toString() != null && searchMealText.text?.toString()!!.trim()
                    .isNotEmpty()
            ) {
                loadMeals("%${searchMealText.text}%", mealDao)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("TEXT",searchMealText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        var edtText=savedInstanceState.getString("TEXT")
        searchMealText.setText(edtText)
        edtText="%${edtText}%"


        // create the database
        val db = Room.databaseBuilder(this, AppDatabase::class.java,
            "myDatabase").build()
        val mealDao =db.mealDao()

//        Toast.makeText(this,"$edtText",Toast.LENGTH_SHORT).show()
        loadMeals(edtText,mealDao)

//        Toast.makeText(this,"${meals.size}",Toast.LENGTH_SHORT).show()
    }

    private fun loadMeals(edtText:String,mealDao:MealDao){
        runBlocking {
            launch {
//                    val meals:List<Meal> =mealDao.getMealsWithIngredient("%${edtMealIng.text}%")
                try {
                    meals = mealDao.getMealsWithIngredient(edtText)
//                        for (meal in meals) {
//                            txtResult.append("${meal.mealName}\n")
//                        }
                }
                catch (e: Exception) {
                    Log.e("Error", "Error accessing database: ${e.message}")
                }
            }
        }
        if(meals.isNotEmpty()) {
            mealClassAdapter = MealClassAdapter(meals)
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = mealClassAdapter
        }
        else{
            Toast.makeText(this,"No entries with this name",Toast.LENGTH_SHORT).show()
        }
    }
}