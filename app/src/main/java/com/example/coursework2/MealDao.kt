package com.example.coursework2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MealDao {
    @Query("Select * from meal")
    suspend fun getAll():List<Meal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(vararg meal: Meal)

    @Query("Select * From Meal WHERE Meal LIKE :food OR ingredients LIKE :food")
    suspend fun getMealsWithIngredient(food: String): List<Meal>

    @Query("Select * From Meal WHERE Meal = :mealName"  )
    suspend fun getMealByName(mealName:String?): Meal?
}