package com.example.coursework2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meal(
    @PrimaryKey(true) val MealId:Int=0,
    val Meal:String?,
    val DrinkAlternate:String?,
    val Category:String?,
    val Area:String?,
    val Instructions:String?,
    val MealThumb:String?,
    val Tags:String?,
    val Youtube:String?,
    val Ingredients:String?,
    val Measures:String?,
    val Source:String?,
    val ImageSource:String?,
    val CreativeCommonsConfirmed:String?,
    var dateModified:String?
)
