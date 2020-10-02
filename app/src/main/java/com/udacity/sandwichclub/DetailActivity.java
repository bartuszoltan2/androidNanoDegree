package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.stream.Collectors;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final String UNKNOWN = "Unknown";
    private static final int DEFAULT_POSITION = -1;
    private ImageView ingredientsIv;
    private TextView mAlsoKnownTv;
    private TextView mIngredients;
    private TextView mDescriptionTv;
    private TextView mOriginTv;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownTv = (TextView) findViewById(R.id.also_known_tv);
        mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        mOriginTv = (TextView) findViewById(R.id.origin_tv);
        mDescriptionTv = (TextView) findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateUI(Sandwich sandwich) {

        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() != 0) {
            mAlsoKnownTv.setText(sandwich.getAlsoKnownAs().stream().collect(Collectors.joining(", ")));
        } else {
            mAlsoKnownTv.setText(UNKNOWN);
        }

        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() != 0) {
            mIngredients.setText(sandwich.getIngredients().stream().collect(Collectors.joining(", ")));
        } else {
            mIngredients.setText(UNKNOWN);
        }

        if (sandwich.getDescription() != null && sandwich.getDescription().length() != 0) {
            mDescriptionTv.setText(sandwich.getDescription());
        } else {
            mDescriptionTv.setText(UNKNOWN);
        }

        if (sandwich.getPlaceOfOrigin() != null && sandwich.getPlaceOfOrigin().length() != 0) {
            mOriginTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            mOriginTv.setText(UNKNOWN);
        }
    }
}
