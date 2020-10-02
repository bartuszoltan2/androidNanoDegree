package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView mAlsoKnownTv;
    TextView mIngredients;
    TextView mDescriptionTv;
    TextView mOriginTv;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageView ingredientsIv = findViewById(R.id.image_iv);

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

        if (sandwich.getAlsoKnownAs() != null) {
            sandwich.getAlsoKnownAs().stream().forEach(t -> mAlsoKnownTv.append(t + "\n"));
        }
        if(sandwich.getIngredients()!=null){
            sandwich.getIngredients().stream().forEach(t -> mIngredients.append(t + "\n"));
        }
        mDescriptionTv.setText(sandwich.getDescription());
        mOriginTv.setText(sandwich.getPlaceOfOrigin());
    }
}
