package us.flexswag.onehouronelifesurvivalguide

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_part_detail.*
import kotlinx.android.synthetic.main.fragment_notes.*
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class PartDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_detail)

        val testData = jsonToMap()

        list.layoutManager = LinearLayoutManager(this)
        list.hasFixedSize()
        list.adapter = NoteAdapter(testData, { partItem : NoteData -> partItemClicked(partItem) })

        var intentThatStartedThisActivity = getIntent()
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            var partId = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT)
            //tv_item_id.text = partId
        }
    }

    private fun partItemClicked(partItem : NoteData) {
        // Launch second activity, pass part ID as string parameter
        //val showDetailActivityIntent = Intent(this, PartDetailActivity::class.java)
        //showDetailActivityIntent.putExtra(Intent.EXTRA_TEXT, partItem.id)
        //startActivity(showDetailActivityIntent)
        //toast("Clicked: " + partItem.id)
    }

    private fun jsonToMap(): List<NoteData>{
        val noteList = ArrayList<NoteData>()
        val jsonObject = JSONObject(readJSONFromAsset())
        val array = jsonObject.getJSONArray("foods")

        for (i in 0 until array.length()) {
            val jo = array.getJSONObject(i)
            val entries = NoteData(jo.getString("objectId"), jo.getString("foodType"))
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
