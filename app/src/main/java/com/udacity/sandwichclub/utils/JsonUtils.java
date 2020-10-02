package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        try {
            JSONObject sandwichJson = new JSONObject(json);


            JSONObject nameJson = sandwichJson.getJSONObject("name");
            sandwich.setMainName(nameJson.getString("mainName"));


            sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichJson.getString("description"));
            sandwich.setImage(sandwichJson.getString("image"));

            JSONArray alsoKnownAsJsonArray = nameJson.getJSONArray("alsoKnownAs");
            List alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownAs);

            JSONArray ingredientsJsonArray = sandwichJson.getJSONArray("ingredients");
            List ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                ingredients.add(ingredientsJsonArray.getString(i));
            }
            sandwich.setIngredients(ingredients);
            sandwich.setImage(sandwichJson.getString("image"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
