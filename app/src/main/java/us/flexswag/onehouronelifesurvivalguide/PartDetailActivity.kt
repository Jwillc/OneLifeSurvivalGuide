package us.flexswag.onehouronelifesurvivalguide

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_part_detail.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_notes.*
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class PartDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_detail)

        var intentThatStartedThisActivity = intent
        if (intentThatStartedThisActivity.hasExtra("mDrawableId")) {
            var drawableId = intentThatStartedThisActivity.getIntExtra("mDrawableId", R.drawable.obj_carrot)
            ivGuide.setImageDrawable(ContextCompat.getDrawable(this, drawableId))
        }
    }
}
