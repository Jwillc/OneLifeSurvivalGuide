package us.flexswag.onehouronelifesurvivalguide;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;
import java.util.Random;

public class WriteJson {

    private Activity ac;

    public WriteJson(Activity activity){
        ac = activity;
    }

    public void jsonWrite(){
        JSONObject obj = new JSONObject();
        JSONArray categories = new JSONArray();
        JSONArray foods = new JSONArray();
        JSONObject innerObj = new JSONObject();
        JSONObject innerObj2 = new JSONObject();
        JSONObject innerFoodObj = new JSONObject();
        JSONObject innerFoodObj2 = new JSONObject();
        JSONObject innerFoodObj3 = new JSONObject();
        File file = Objects.requireNonNull(ac).getBaseContext().getFileStreamPath("guideData.json");

        if(!file.exists()){
            try {
                innerObj.put("objectId", "001");
                innerObj.put("categoryName", "Food");

                innerObj2.put("objectId", "002");
                innerObj2.put("categoryName", "Clothing");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            categories.put(innerObj);
            categories.put(innerObj2);

            try {
                innerFoodObj.put("objectId", "food01");
                innerFoodObj.put("foodType", "Carrot");

                innerFoodObj2.put("objectId", "food02");
                innerFoodObj2.put("foodType", "Wild Carrot");

                innerFoodObj3.put("objectId", "food03");
                innerFoodObj3.put("foodType", "Cooked Rabbit");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            foods.put(innerFoodObj);
            foods.put(innerFoodObj2);
            foods.put(innerFoodObj3);

            try {
                obj.put("categories", categories);
                obj.put("foods", foods);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ac.openFileOutput("guideData.json", Context.MODE_PRIVATE));
                outputStreamWriter.write(obj.toString());
                outputStreamWriter.flush();
                outputStreamWriter.close();
            }
            catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }
    }

    public void jsonSimpleWrite(String data[]) {
        Random rand = new Random();
        Integer n = rand.nextInt(50) + 1;
        Integer r = rand.nextInt(75) + 1;
        //50 is the maximum and the 1 is our minimum for unique Log identifier
        try{
            JSONObject jsonObj = new JSONObject(readJSONFromAsset());
            // Getting JSON Array node
            JSONArray arr = jsonObj.getJSONArray("notes");
            //JSONArray list = new JSONArray();
            JSONObject innerObj = new JSONObject();
            String objId = Integer.toString(n + r * 3);

            if (data.length >= 6) {
                try {
                    innerObj.put("objectId", objId);
                    innerObj.put("timePosted", data[0]);
                    innerObj.put("expTime", data[1]);
                    innerObj.put("note", data[2]);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arr.put(innerObj);

                JSONObject newJsonObject = new JSONObject(jsonObj.toString());
                //Toast.makeText(this, newJsonObject.toString(), Toast.LENGTH_SHORT).show();
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ac.openFileOutput("guideData.json", Context.MODE_PRIVATE));
                    outputStreamWriter.write(newJsonObject.toString());
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                } catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }
            }

        } catch (final JSONException e) {
            Toast.makeText(ac.getApplicationContext(),
                    "Json parsing error: " + e.getMessage(),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    private String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = ac.openFileInput("guideData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
