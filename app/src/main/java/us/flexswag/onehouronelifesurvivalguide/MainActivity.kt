package us.flexswag.onehouronelifesurvivalguide

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_notes.*
import org.json.JSONObject
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private var previousDataId = ""

    // Creating maps for each Category
    private var foodsMap = mutableMapOf<Int, Int>()
    private var vidMap = mutableMapOf<Int, String>()
    private var basicsMap = mutableMapOf<Int, Int>()
    private var clothingMap = mutableMapOf<Int, Int>()
    private var farmingMap = mutableMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Put guides into maps with keys
        vidMap[1] = "https://youtu.be/MRRNexVvK1E" // Forging Video

        basicsMap[1] = R.drawable.basket // Basket Guide
        basicsMap[2] = R.drawable.claybowl
        basicsMap[3] = R.drawable.clayplate
        basicsMap[4] = R.drawable.fire
        basicsMap[5] = R.drawable.firebowdrill
        basicsMap[6] = R.drawable.firebrand
        basicsMap[7] = R.drawable.hotflatrock
        basicsMap[8] = R.drawable.kiln
        basicsMap[9] = R.drawable.kindlingandtinder
        basicsMap[10] = R.drawable.leaf

        clothingMap[1] = R.drawable.rabbitfur
        clothingMap[2] = R.drawable.needlethread
        clothingMap[3] = R.drawable.rabbitfurclothing
        clothingMap[4] = R.drawable.rabbitbackpack
        clothingMap[5] = R.drawable.wolfhat
        clothingMap[6] = R.drawable.bowandarrow

        farmingMap[1] = R.drawable.bowlofwater
        farmingMap[2] = R.drawable.carrot
        farmingMap[3] = R.drawable.compost
        farmingMap[4] = R.drawable.deeptilledrow
        farmingMap[5] = R.drawable.farmingrows
        farmingMap[6] = R.drawable.gooseberrybush
        farmingMap[7] = R.drawable.milkweed
        farmingMap[8] = R.drawable.ripewheat
        farmingMap[9] = R.drawable.shovelofdung
        farmingMap[10] = R.drawable.stonehoe
        farmingMap[11] = R.drawable.straw

        foodsMap[1] = R.drawable.obj_carrot
        foodsMap[2] = R.drawable.obj_wildcarrot
        foodsMap[3] = R.drawable.cookedrabbit

        // Load the adapter with main category values
        val testData = jsonToMap("categories", "categoryName")

        // Set up the list view
        list.layoutManager = LinearLayoutManager(this)
        list.hasFixedSize()
        list.adapter = NoteAdapter(testData, { partItem : NoteData -> partItemClicked(partItem) })

        // This is the back button.
        // It goes back to the appropriate category
        // based on the previousDataId value
        fab.setOnClickListener {
            when (previousDataId) {
                "main" -> {
                    val newData = jsonToMap("categories", "categoryName")
                    resetAdapter(newData)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun partItemClicked(partItem : NoteData) {
        // Update Adapter based on what the user clicks
        // Receiving item id as param
        when {
            // Main Categories
            partItem.id == "vidCat" -> {
                val newData = jsonToMap("videos", "vidTitle")
                resetAdapter(newData)
                previousDataId = "main"
            }
            partItem.id == "foodCat" -> {
                val newData = jsonToMap("foods", "foodType")
                resetAdapter(newData)
                previousDataId = "main"
            }
            partItem.id == "basicsCat" -> {
                val newData = jsonToMap("basics", "basicsTitle")
                resetAdapter(newData)
                previousDataId = "main"
            }
            partItem.id == "clothingCat" -> {
                val newData = jsonToMap("clothing", "clothingTitle")
                resetAdapter(newData)
                previousDataId = "main"
            }
            partItem.id == "farmingCat" -> {
                val newData = jsonToMap("farming", "farmingTitle")
                resetAdapter(newData)
                previousDataId = "main"
            }

            // Videos
            partItem.id == "vidForge" -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(vidMap[1]!!)))
            }
            // Basics Category
            partItem.id == "basicsBasket" -> {
                launchDetailActivity(basicsMap[1]!!)
            }
            partItem.id == "basicsClayBowl" -> {
                launchDetailActivity(basicsMap[2]!!)
            }
            partItem.id == "basicsClayPlate" -> {
                launchDetailActivity(basicsMap[3]!!)
            }
            partItem.id == "basicsFire" -> {
                launchDetailActivity(basicsMap[4]!!)
            }
            partItem.id == "basicsFireBowDrill" -> {
                launchDetailActivity(basicsMap[5]!!)
            }
            partItem.id == "basicsFireBrand" -> {
                launchDetailActivity(basicsMap[6]!!)
            }
            partItem.id == "basicsHotFlatRock" -> {
                launchDetailActivity(basicsMap[7]!!)
            }
            partItem.id == "basicsAdobeKiln" -> {
                launchDetailActivity(basicsMap[8]!!)
            }
            partItem.id == "basicsKindlingAndTinder" -> {
                launchDetailActivity(basicsMap[9]!!)
            }
            partItem.id == "basicsLeaf" -> {
                launchDetailActivity(basicsMap[10]!!)
            }
            // Food Category
            partItem.id == "foodCarrot" -> {
                launchDetailActivity(foodsMap[1]!!)
            }
            partItem.id == "foodWildCarrot" -> {
                launchDetailActivity(foodsMap[2]!!)
            }
            partItem.id == "foodCookedRabbit" -> {
                launchDetailActivity(foodsMap[3]!!)
            }
            // Clothing Category
            partItem.id == "clothingRabbitFur" -> {
                launchDetailActivity(clothingMap[1]!!)
            }
            partItem.id == "clothingNeedleThread" -> {
                launchDetailActivity(clothingMap[2]!!)
            }
            partItem.id == "clothingRabbitFurClothes" -> {
                launchDetailActivity(clothingMap[3]!!)
            }
            partItem.id == "clothingRabbitBackpack" -> {
                launchDetailActivity(clothingMap[4]!!)
            }
            partItem.id == "clothingWolfHat" -> {
                launchDetailActivity(clothingMap[5]!!)
            }
            partItem.id == "clothingBowArrow" -> {
                launchDetailActivity(clothingMap[6]!!)
            }
            // Farming Category
            partItem.id == "farmingBowlOfWater" -> {
                launchDetailActivity(farmingMap[1]!!)
            }
            partItem.id == "farmingCarrot" -> {
                launchDetailActivity(farmingMap[2]!!)
            }
            partItem.id == "farmingCompost" -> {
                launchDetailActivity(farmingMap[3]!!)
            }
            partItem.id == "farmingDeepTilledRow" -> {
                launchDetailActivity(farmingMap[4]!!)
            }
            partItem.id == "farmingRows" -> {
                launchDetailActivity(farmingMap[5]!!)
            }
            partItem.id == "farmingGooseBerryBush" -> {
                launchDetailActivity(farmingMap[6]!!)
            }
            partItem.id == "farmingMilkweed" -> {
                launchDetailActivity(farmingMap[7]!!)
            }
            partItem.id == "farmingRipeWheat" -> {
                launchDetailActivity(farmingMap[8]!!)
            }
            partItem.id == "farmingShovelOfDung" -> {
                launchDetailActivity(farmingMap[9]!!)
            }
            partItem.id == "farmingStoneHoe" -> {
                launchDetailActivity(farmingMap[10]!!)
            }
            partItem.id == "farmingStraw" -> {
                launchDetailActivity(farmingMap[11]!!)
            }

            else -> Snackbar.make(this.currentFocus, "Clicked: " + partItem.id, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun resetAdapter(data: List<NoteData>){
        list.adapter = NoteAdapter(data, { partItem : NoteData -> partItemClicked(partItem) })
    }

    private fun launchDetailActivity(drawableId: Int){
        val showDetailActivityIntent = Intent(this, PartDetailActivity::class.java)
        showDetailActivityIntent.putExtra("mDrawableId", drawableId)
        startActivity(showDetailActivityIntent)
    }

    private fun jsonToMap(jsonArray: String, jsonObj: String): List<NoteData>{
        val noteList = ArrayList<NoteData>()
        val jsonObject = JSONObject(loadJSONFromAsset())
        val array = jsonObject.getJSONArray(jsonArray)

        for (i in 0 until array.length()) {
            val jo = array.getJSONObject(i)
            val entries = NoteData(jo.getString("objectId"), jo.getString(jsonObj))
            noteList.add(entries)
        }
        return noteList
    }

    private fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val `is` = this.assets.open("guideData.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }
}
