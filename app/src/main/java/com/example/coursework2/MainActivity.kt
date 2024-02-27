package com.example.coursework2

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var addMeal: Button
    private lateinit var searchIngredient: Button
    private lateinit var searchMeal: Button
    private lateinit var searchMealName: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val data: File = Environment.getDataDirectory()
//        val currentDBPath = "/data/com.example.coursework2/databases/myDatabase"
//        val currentDB = File(data, currentDBPath)
//        val deleted = SQLiteDatabase.deleteDatabase(currentDB)

        // create the database
        val db = Room.databaseBuilder(this, AppDatabase::class.java,
            "myDatabase").build()
        val mealDao =db.mealDao()

        addMeal = findViewById(R.id.addMeal)
        searchIngredient = findViewById(R.id.searchIngredient)
        searchMeal = findViewById(R.id.searchMeal)
        searchMealName = findViewById(R.id.searchMealName)

        //adding meals to database
        addMeal.setOnClickListener(){
            runBlocking {
                launch {
                    //meal Objects
                    val meal1 = Meal(
                        Meal="Sweet and Sour Pork",
                        DrinkAlternate=null,
                        Category="Pork",
                        Area="Chinese",
                        Instructions = "Preparation\r\n" +
                                "1. Crack the egg into a bowl. Separate the egg white and yolk.\r\n\r\nSweet and Sour Pork\r\n" +
                                "2. Slice the pork tenderloin into ips.\r\n\r\n" +
                                "3. Prepare the marinade using a pinch of salt, one teaspoon of starch, two teaspoons of light soy sauce, and an egg white.\r\n\r\n" +
                                "4. Marinade the pork ips for about 20 minutes.\r\n\r\n" +
                                "5. Put the remaining starch in a bowl. Add some water and vinegar to make a starchy sauce.\r\n\r\nSweet and Sour Pork\r\nCooking Inuctions\r\n" +
                                "1. Pour the cooking oil into a wok and heat to 190\u00b0C (375\u00b0F). Add the marinated pork ips and fry them until they turn brown. Remove the cooked pork from the wok and place on a plate.\r\n\r\n" +
                                "2. Leave some oil in the wok. Put the tomato sauce and white sugar into the wok, and heat until the oil and sauce are fully combined.\r\n\r\n" +
                                ". Add some water to the wok and thoroughly heat the sweet and sour sauce before adding the pork ips to it.\r\n\r\n" +
                                "4. Pour in the starchy sauce. Stir-fry all the ingredients until the pork and sauce are thoroughly mixed together.\r\n\r\n" +
                                "5. Serve on a plate and add some coriander for decoration.",
                        MealThumb="https://www.themealdb.com/images/media/meals/1529442316.jpg",
                        Tags="Sweet",
                        Youtube="https://www.youtube.com/watch?v=mdaBIhgEAMo",
                        Ingredients="Pork , Egg,Water,Salt Sugar,Soy Sauce,Starch,Tomato Puree,Pepper,Vinegar,Coriander",
                        Measures="200g,1 ,Dash,1/2 tsp,1 tsp,10g,10g,30g,10g,Dash",
                        Source="",
                        ImageSource=null,
                        CreativeCommonsConfirmed=null,
                        dateModified=null
                    )
                    val meal2 = Meal(
                        Meal = "Chicken Marengo",
                        DrinkAlternate = null,
                        Category = "Chicken",
                        Area = "French",
                        Instructions = "Heat the oil in a large flameproof casserole dish and stir-fry the mushrooms until they start to soften. " +
                                "Add the chicken legs and cook briefly on each side to colour them a little.\r\nPour in the passata, " +
                                "crumble in the stock cube and stir in the olives. Season with black pepper \u2013 you shouldn\u2019t need salt. " +
                                "Cover and simmer for 40 mins until the chicken is tender. Sprinkle with parsley and serve with pasta and a salad," +
                                " or mash and green veg, if you like.",
                        MealThumb = "https://www.themealdb.com/images/media/meals/qpxvuq1511798906.jpg",
                        Tags = null,
                        Youtube = "https://www.youtube.com/watch?v=U33HYUr-0Fw",
                        Ingredients = "Olive Oil,Mushrooms,Chicken Legs,Passata,Chicken Stock Cube,Black Olives,Parsley",
                        Measures = "1 tbs,300g,4,500g,1,100g,Chopped",
                        Source = "https://www.bbcgoodfood.com/recipes/3146682/chicken-marengo",
                        ImageSource = null,
                        CreativeCommonsConfirmed = null,
                        dateModified=null
                    )
                    val meal3= Meal(
                        Meal="Beef Banh Mi Bowls with Sriracha Mayo, Carrot & Pickled Cucumber",
                        DrinkAlternate=null,
                        Category="Beef",
                        Area="Vietnamese",
                        Instructions="Add'l ingredients: mayonnaise, siracha\r\n\r\n" +
                                "1\r\n\r\nPlace rice in a fine-mesh sieve and rinse until water runs clear. Add to a small pot with 1 cup water (2 cups for 4 servings) and a pinch of salt. Bring to a boil, then cover and reduce heat to low. Cook until rice is tender, 15 minutes. Keep covered off heat for at least 10 minutes or until ready to serve.\r\n\r\n" +
                                "2\r\n\r\nMeanwhile, wash and dry all produce. Peel and finely chop garlic. Zest and quarter lime (for 4 servings, zest 1 lime and quarter both). Trim and halve cucumber lengthwise; thinly slice crosswise into half-moons. Halve, peel, and medium dice onion. Trim, peel, and grate carrot.\r\n\r\n" +
                                "3\r\n\r\nIn a medium bowl, combine cucumber, juice from half the lime, \u00bc tsp sugar (\u00bd tsp for 4 servings), and a pinch of salt. In a small bowl, combine mayonnaise, a pinch of garlic, a squeeze of lime juice, and as much sriracha as you\u2019d like. Season with salt and pepper.\r\n\r\n" +
                                "4\r\n\r\nHeat a drizzle of oil in a large pan over medium-high heat. Add onion and cook, stirring, until softened, 4-5 minutes. Add beef, remaining garlic, and 2 tsp sugar (4 tsp for 4 servings). Cook, breaking up meat into pieces, until beef is browned and cooked through, 4-5 minutes. Stir in soy sauce. Turn off heat; taste and season with salt and pepper.\r\n\r\n" +
                                "5\r\n\r\nFluff rice with a fork; stir in lime zest and 1 TBSP butter. Divide rice between bowls. Arrange beef, grated carrot, and pickled cucumber on top. Top with a squeeze of lime juice. Drizzle with sriracha mayo.",
                        MealThumb="https://www.themealdb.com/images/media/meals/z0ageb1583189517.jpg",
                        Tags=null,
                        Youtube="",
                        Ingredients="Rice , Onion,Lime,Garlic Clove,Cucumber,Carrots,Ground Beef,Soy Sauce",
                        Measures="White,1 ,1,3,1 ,3 oz ,1 lb,2 oz",
                        Source="",
                        ImageSource=null,
                        CreativeCommonsConfirmed=null,
                        dateModified=null
                    )
                    val meal4 = Meal(
                        Meal="Leblebi Soup",
                        DrinkAlternate=null,
                        Category="Vegetarian",
                        Area="Tunisian",
                        Instructions = "Heat the oil in a large pot. Add the onion and cook until translucent.\r\n" +
                                "Drain the soaked chickpeas and add them to the pot together with the vegetable stock. Bring to the boil, then reduce the heat and cover. Simmer for 30 minutes.\r\n" +
                                "In the meantime toast the cumin in a small ungreased frying pan, then grind them in a mortar. Add the garlic and salt and pound to a fine paste.\r\n" +
                                "Add the paste and the harissa to the soup and simmer until the chickpeas are tender, about 30 minutes.\r\nSeason to taste with salt, pepper and lemon juice and serve hot.",
                        MealThumb="https://www.themealdb.com/images/media/meals/x2fw9e1560460636.jpg",
                        Tags="Soup",
                        Youtube="https://www.youtube.com/watch?v=BgRifcCwinY",
                        Ingredients="Olive Oil , Onion,Chickpeas,Vegetable Stock,Cumin,Garlic,Salt,Harissa Spice,Pepper,Lime",
                        Measures="2 tbs,1 medium finely diced,250g,1.5L,1 tsp,5 cloves,1/2 tsp,1 tsp,Pinch,1/2",
                        Source="http://allrecipes.co.uk/recipe/43419/leblebi--tunisian-chickpea-soup-.aspx",
                        ImageSource=null,
                        CreativeCommonsConfirmed=null,
                        dateModified=null
                    )
//                    mealDao.insertMeals(meal1,meal2,meal3,meal4)

                    //adding meal objects to an array
                    val mutableMeal= arrayOf(meal1,meal2,meal3,meal4)

                    //checking whether the meal is already on the database adding if its not there already
                    for (i in mutableMeal){
                        val existingFood = mealDao.getMealByName(i.Meal)
                        if (existingFood == null) {
                            mealDao.insertMeals(i)
                        }
                    }
                }
            }
            Toast.makeText(this,"Meals Added to the Database", Toast.LENGTH_SHORT).show()
        }

        //moving to the other activity on click of button using intent
        searchIngredient.setOnClickListener(){
            val ingredientIntent = Intent(this,SearchByIngredient:: class.java);
            startActivity(ingredientIntent)
        }

        searchMeal.setOnClickListener(){
            val mealIntent = Intent(this,SearchMeal:: class.java);
            startActivity(mealIntent)
        }

        searchMealName.setOnClickListener(){
            val nameIntent = Intent(this,SearchMealByName:: class.java);
            startActivity(nameIntent)
        }

    }
}