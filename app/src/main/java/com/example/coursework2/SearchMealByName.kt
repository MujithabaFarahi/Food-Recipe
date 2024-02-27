package com.example.coursework2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchMealByName : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var searchByName: EditText
    private var stringBuilder = StringBuilder()
    private var resultBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_meal_by_name)

        resultTextView = findViewById(R.id.resultTextView)
        searchByName = findViewById(R.id.searchByName)


        searchByName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                if (searchByName.text?.toString() != null && searchByName.text?.toString()!!.trim()
                        .isNotEmpty()
                ) {
                    getCocktailByIngredient(searchByName.text.toString())
                    resultTextView.text = resultBuilder
                }
            }
        })
    }

    private fun getCocktailByIngredient(ingredient:String){
        val urlString="https://www.themealdb.com/api/json/v1/1/search.php?s=$ingredient"
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

    private fun parseJSON(stb:StringBuilder) {
        resultBuilder.clear()

        val jsonObject = JSONObject(stb.toString())

        if (!jsonObject.isNull("meals")) {
            resultTextView.setTextColor(Color.WHITE)

            val meals = jsonObject.getJSONArray("meals")
            resultBuilder.append("Meals are:\n\n")

            for (i in 0 until meals.length()) {
                val meal = meals.getJSONObject(i)
                val name: String? = meal.getString("strMeal")

                resultBuilder.append("Meal- $name\n")

                resultBuilder.append("\t\t\t\t\t\t*___________________________________*\n\n")

            }
        }
        else{
            resultTextView.setTextColor(Color.RED)
            resultBuilder.append("\n\n\nThere are no meals with in this name")
        }
    }
}