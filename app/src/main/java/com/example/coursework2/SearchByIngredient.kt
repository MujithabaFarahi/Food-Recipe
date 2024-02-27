package com.example.coursework2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchByIngredient : AppCompatActivity() {

    //Initializing
    private lateinit var saveMealBtn: Button
    private lateinit var retrieveMealBtn: Button
    private lateinit var searchIngredientText: EditText
    private lateinit var resultText: TextView
    private var stringBuilder = StringBuilder()
    private var resultBuilder = StringBuilder()
    private var mealArray = mutableListOf<Meal>()
    private var ingredientArray = mutableListOf<String>()
    private var measureArray = mutableListOf<String>()
    private var ingredientString = "";
    private var measureString = "";
    private var builderIng = StringBuilder()
    private var builderResultName = mutableListOf<String?>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_ingredient)

        saveMealBtn = findViewById(R.id.saveMealBtn)
        retrieveMealBtn = findViewById(R.id.retrieveMealBtn)
        searchIngredientText = findViewById(R.id.searchIngredientText)
        resultText = findViewById(R.id.resultText)

        //Creating Database
        val db = Room.databaseBuilder(this, AppDatabase::class.java,
            "myDatabase").build()
        val mealDao =db.mealDao()


        saveMealBtn.setOnClickListener(){
            runBlocking {
                launch {
                    for (i in mealArray){
                        val existingFood = mealDao.getMealByName(i.Meal)
                        if (existingFood == null) {
                            mealDao.insertMeals(i)
                        }
                    }
                }
            }
            Toast.makeText(this,"Added to Database", Toast.LENGTH_SHORT).show()
        }

        //Getting Meal details by ingredient
        retrieveMealBtn.setOnClickListener(){
            if(searchIngredientText.text?.toString()!=null && searchIngredientText.text?.toString()!!.trim().isNotEmpty()){
                resultBuilder.clear()
                mealArray.clear()
                getMealNameByIngredient(searchIngredientText.text.toString())
                resultText.text = resultBuilder
            }
        }
    }

    //saving need
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("Text", searchIngredientText.text.toString())
        outState.putString("BUILDER_RESULTS", resultBuilder.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val getText=savedInstanceState.getString("Text")
        // Restore the state of the UI
        getMealNameByIngredient(getText.toString())
        resultText.text=savedInstanceState.getString("BUILDER_RESULTS")
    }

    private fun getMealNameByIngredient(ingredient:String) {
        val urlString = "https://www.themealdb.com/api/json/v1/1/filter.php?i=$ingredient"
        val url = URL(urlString)
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection

        builderIng.clear()

        runBlocking {
            launch {
                withContext(Dispatchers.IO) {
                    val bf = BufferedReader(InputStreamReader(con.inputStream))
                    var line: String? = bf.readLine()

                    while (line != null) {
                        builderIng.append(line)
                        line = bf.readLine()
                    }
                    parseJsonMealName(builderIng)
                }
            }
        }
    }

    private fun parseJsonMealName(builder:StringBuilder){
        builderResultName.clear()
        if (builder.isNotEmpty() ) {
            resultText.setTextColor(Color.WHITE)
            val jsonObject = JSONObject(builder.toString())
            if(!jsonObject.isNull("meals")) {

                val meals = jsonObject.getJSONArray("meals")
                for (i in 0 until meals.length()) {

                    val meal = meals.getJSONObject(i)

                    val name: String? = meal.getString("strMeal")
                    builderResultName.add(name)

                }
                getMealByName()
            }
            else{
                resultText.setTextColor(Color.RED)
                resultBuilder.append("\n\n\n\nThere are no meals with this ingredient")
            }
        }
    }



    private fun getMealByName(){
        resultBuilder.append("Meals are:\n\n")
        for (i in builderResultName){
            val urlString="https://www.themealdb.com/api/json/v1/1/search.php?s=$i"
            val url= URL(urlString)

            val con: HttpURLConnection =url.openConnection() as HttpURLConnection
            stringBuilder.clear()
            runBlocking {
                launch {
                    withContext(Dispatchers.IO){
                        val bufferedReader= BufferedReader(InputStreamReader(con.inputStream))
                        var line:String?=bufferedReader.readLine()
                        while (line!=null){
                            stringBuilder.append(line)
                            line=bufferedReader.readLine()
                        }
                        parseJSON(stringBuilder)
                    }
                }
            }
        }
    }

    private fun parseJSON(stb:StringBuilder) {

        val jsonObject = JSONObject(stb.toString())

        if (!jsonObject.isNull("meals")) {

            val meals = jsonObject.getJSONArray("meals")
            for (i in 0 until meals.length()) {
                val meal = meals.getJSONObject(i)
                val name: String? = meal.getString("strMeal")
                val drinkAlternate: String? = meal.getString("strDrinkAlternate")
                val category: String? = meal.getString("strCategory")
                val area: String? = meal.getString("strArea")
                val instructions: String? = meal.getString("strInstructions")
                val thumbImage: String? = meal.getString("strMealThumb")
                val tags: String? = meal.getString("strTags")
                val youTube: String? = meal.getString("strYoutube")
                val source: String? = meal.getString("strSource")
                val imageSource: String? = meal.getString("strImageSource")
                val creativeCommonsConfirmed: String? =
                    meal.getString("strCreativeCommonsConfirmed")
                val dateModified: String? = meal.getString("dateModified")

                resultBuilder.append("Meal- $name\n")
                resultBuilder.append("DrinkAlternate- $drinkAlternate\n")
                resultBuilder.append("Category- $category\n")
                resultBuilder.append("Area- $area\n")
                resultBuilder.append("Instructions- $instructions\n")
                resultBuilder.append("Tags- $tags\n")
                resultBuilder.append("YouTube- $youTube\n")

                for (i in 1..20) {
                    val ingredient: String? = meal.getString("strIngredient$i")
                    if (ingredient != null && ingredient.isNotEmpty() && ingredient.isNotBlank()) {
                        resultBuilder.append("Ingredient $i-  " + meal.getString("strIngredient$i") + "\n")
                        ingredientArray.add(ingredient)
                    }
                }

                for (i in 1..20) {
                    val measure: String? = meal.getString("strMeasure$i")
                    if (measure != null && measure.isNotEmpty() && measure.isNotBlank()) {
                        resultBuilder.append("Measure $i-  " + meal.getString("strMeasure$i") + "\n")
                        measureArray.add(measure)
                    }
                }

                for (j in ingredientArray.indices){
                    ingredientString += if (j==ingredientArray.size-1){
                        ingredientArray[j]
                    } else{
                        ingredientArray[j] + ","
                    }
                }

                for (j in measureArray.indices){
                    measureString += if (j==measureArray.size-1){
                        measureArray[j]
                    } else{
                        measureArray[j] + ","
                    }
                }

                val meal1= Meal(
                    Meal=name,
                    DrinkAlternate=drinkAlternate,
                    Category=category,
                    Area=area,
                    Instructions=instructions,
                    MealThumb=thumbImage,
                    Tags=tags,
                    Youtube=youTube,
                    Ingredients=ingredientString,
                    Measures=measureString,
                    Source=source,
                    ImageSource=imageSource,
                    CreativeCommonsConfirmed=creativeCommonsConfirmed,
                    dateModified=dateModified
                )

                mealArray.add(meal1)


                measureArray.clear()
                ingredientArray.clear()
                ingredientString="";
                measureString="";

                resultBuilder.append("\t\t\t\t\t\t\t\t\t*___________________________________*\n\n")

            }
        }
        else{
            resultText.setTextColor(Color.RED)
            resultBuilder.append("\n\n\n\nThere are no meals with this ingredient")
        }
    }
}