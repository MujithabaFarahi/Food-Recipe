package com.example.coursework2

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class MealClassAdapter(private val meals:List<Meal>): RecyclerView.Adapter<MealClassAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.design_layout, parent, false)
        return MealViewHolder(itemView)
    }

    override fun getItemCount(): Int =meals.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal=meals[position]
        holder.bind(meal)
    }

    class MealViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val mealName=itemView.findViewById<TextView>(R.id.textMealName)
        private val mealDrink=itemView.findViewById<TextView>(R.id.textDrink)
        private val mealCategory=itemView.findViewById<TextView>(R.id.textCategory)
        private val mealArea=itemView.findViewById<TextView>(R.id.textArea)
        private val mealInstructions=itemView.findViewById<TextView>(R.id.textInstructions)
        private val mealTags=itemView.findViewById<TextView>(R.id.textTag)
        private val mealYoutube=itemView.findViewById<TextView>(R.id.textYoutube)
        private val mealIngredient=itemView.findViewById<TextView>(R.id.textIngredient)
        private val mealMeasure=itemView.findViewById<TextView>(R.id.textMeasure)
        private val imgMeal=itemView.findViewById<ImageView>(R.id.foodImage)
        lateinit var bitmap: Bitmap

        @SuppressLint("SetTextI18n")
        fun bind(meal:Meal){
            mealName.text=meal.Meal
            mealDrink.text="Drink Alternate - ${meal.DrinkAlternate}"
            mealCategory.text="Category - ${meal.Category}"
            mealArea.text="Area - ${meal.Area}"
            mealInstructions.text="Instructions - ${meal.Instructions}"
            mealTags.text="Tags - ${meal.Tags}"
            mealYoutube.text="YouTube- ${meal.Youtube}\n"

            val ingredients= meal.Ingredients?.split(",")
            val measures= meal.Measures?.split(",")

            mealIngredient.text="Ingredients - \n"
            if (ingredients != null) {
                for((index,ingredient) in ingredients.withIndex()){
                    mealIngredient.append("ingredient ${index+1} - $ingredient\n")
                }
            }

            mealMeasure.text="Measure - \n"
            if (measures != null) {
                for((index,measure) in measures.withIndex()){
                    mealMeasure.append("measure ${index+1} - $measure\n")
                }
            }

            val url=meal.MealThumb
            runBlocking {
                launch {
                    withContext(Dispatchers.IO) {
                        val urlConnection = URL(url).openConnection() as HttpURLConnection
                        val input = urlConnection.inputStream
                        bitmap = BitmapFactory.decodeStream(input)
                    }
                }
            }
            imgMeal.setImageBitmap(bitmap)

//            Toast.makeText(this,"${meal.mealThumb}",Toast.LENGTH_SHORT)
        }
    }
}