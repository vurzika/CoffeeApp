package com.viktorija.coffeelist.details;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.viktorija.coffeelist.R;
import com.viktorija.coffeelist.database.DrinkEntity;
import com.viktorija.coffeelist.editor.EditorActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_DRINK_ID = "EXTRA_DRINK_ID";

    private DetailsViewModel mDrinkDetailsViewModel;

    private DrinkEntity mSelectedDrink;

    @BindView(R.id.coffee_image)
    ImageView mImageView;

    @BindView(R.id.coffee_name)
    TextView mCoffeeName;

    @BindView(R.id.coffee_description)
    TextView mCoffeeDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        ButterKnife.bind(this);

        Intent intent = getIntent();
        int drinkId = intent.getIntExtra(EXTRA_DRINK_ID, 0);

        // get a ViewModel from the ViewModelProvider
        mDrinkDetailsViewModel = ViewModelProviders
                .of(DetailsActivity.this)
                .get(DetailsViewModel.class);

        // load data using view model
        mDrinkDetailsViewModel.loadDrinkById(drinkId).observe(this, selectedDrink -> {
            mSelectedDrink = selectedDrink;
            updateUI();
        });
    }

    private void updateUI() {
        if (mSelectedDrink != null) {
            getSupportActionBar().setTitle(mSelectedDrink.getName());
            mCoffeeName.setText(mSelectedDrink.getName());
            mCoffeeDescription.setText(mSelectedDrink.getDescription());
        }
    }

    // Menu related methods

    // Override onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Within onCreateOptionsMenu, use getMenuInflater().inflate to inflate the menu
        getMenuInflater().inflate(R.menu.menu_details, menu);
        // Return true to display your menu
        return true;
    }

    // Override onOptionsItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasSelected = item.getItemId();

        if (itemThatWasSelected == R.id.action_edit) {
            // Intent to open Editor Activity
            Intent intent = new Intent(DetailsActivity.this, EditorActivity.class);
            intent.putExtra(EXTRA_DRINK_ID, mSelectedDrink.getId());
            startActivity(intent);
            return true;
        } else if (itemThatWasSelected == R.id.home) {
            finish();
            return true;
        }

        return false;
    }
}
