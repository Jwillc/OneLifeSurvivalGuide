package us.flexswag.onehouronelifesurvivalguide

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_notes.*
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var previousDataId = ""
    var foodsMap = mutableMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        foodsMap[1] = R.drawable.obj_carrot
        foodsMap[2] = R.drawable.obj_wildcarrot
        foodsMap[3] = R.drawable.cookedrabbit

        val writeJson = WriteJson(this)
        writeJson.jsonWrite()

        val testData = jsonToMap("categories", "categoryName")

        list.layoutManager = LinearLayoutManager(this)
        list.hasFixedSize()
        list.adapter = NoteAdapter(testData, { partItem : NoteData -> partItemClicked(partItem) })

        fab.setOnClickListener { view ->
            when (previousDataId) {
                "main" -> {
                    val newData = jsonToMap("categories", "categoryName")
                    ivTest.visibility = View.GONE
                    //list.adapter = NoteAdapter(testData, { partItem : NoteData -> partItemClicked(partItem) })
                    resetAdapter(newData)
                }
                "foods" -> {
                    val newData = jsonToMap("foods", "foodType")
                    ivTest.visibility = View.GONE
                    //list.adapter = NoteAdapter(testData, { partItem : NoteData -> partItemClicked(partItem) })
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
        // Launch second activity, pass part ID as string parameter
        /*val showDetailActivityIntent = Intent(this, PartDetailActivity::class.java)
        showDetailActivityIntent.putExtra(Intent.EXTRA_TEXT, partItem.id)
        startActivity(showDetailActivityIntent)*/
        when {
            partItem.id == "001" -> {
                val newData = jsonToMap("foods", "foodType")
                //list.adapter = NoteAdapter(newData, { partItem : NoteData -> partItemClicked(partItem) })
                resetAdapter(newData)
                previousDataId = "main"
            }
            partItem.id == "food01" -> {
                launchDetailActivity(foodsMap[1]!!)
            }
            partItem.id == "food02" -> {
                launchDetailActivity(foodsMap[2]!!)
            }
            partItem.id == "food03" -> {
                launchDetailActivity(foodsMap[3]!!)
            }
            else -> Snackbar.make(this.currentFocus, "Clicked: " + partItem.id, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        //toast("Clicked: " + partItem.id)
    }

    private fun resetAdapter(data: List<NoteData>){
        list.adapter = NoteAdapter(data, { partItem : NoteData -> partItemClicked(partItem) })
    }

    private fun setDrawableIv(drawableId: Int){
        ivTest.visibility = View.VISIBLE
        ivTest.setImageDrawable(ContextCompat.getDrawable(this, drawableId))
    }

    private fun launchDetailActivity(drawableId: Int){
        val showDetailActivityIntent = Intent(this, PartDetailActivity::class.java)
        showDetailActivityIntent.putExtra("mDrawableId", drawableId)
        startActivity(showDetailActivityIntent)
    }

    private fun jsonToMap(jsonArray: String, jsonObj: String): List<NoteData>{
        val noteList = ArrayList<NoteData>()
        val jsonObject = JSONObject(readJSONFromAsset())
        val array = jsonObject.getJSONArray(jsonArray)

        for (i in 0 until array.length()) {
            val jo = array.getJSONObject(i)
            val entries = NoteData(jo.getString("objectId"), jo.getString(jsonObj))
            noteList.add(entries)
        }
        return noteList
    }

    private fun readJSONFromAsset(): String? {
        val json: String?
        try {
            val `is` = this.openFileInput("guideData.json")
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
