package com.viktorija.coffeelist.editor;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viktorija.coffeelist.R;
import com.viktorija.coffeelist.database.DrinkEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity {

    // key to get intent with drink id from drink recycler view adapter(main activity) to editor activity
    public static final String EXTRA_DRINK_ID = "EXTRA_DRINK_ID";

    // Create a member variable for the ViewModel:
    private EditorViewModel mDrinkEditorViewModel;

    private DrinkEntity mSelectedDrink;

    @BindView(R.id.edit_drink_image)
    ImageView mEditDrinkImage;

    @BindView(R.id.edit_drink_title)
    TextView mEditDrinkTitle;

    @BindView(R.id.edit_drink_description)
    TextView mEditDrinkDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        int drinkId = intent.getIntExtra(EXTRA_DRINK_ID, 0);

        // In onCreate(), get a ViewModel from the ViewModelProvider
        mDrinkEditorViewModel = ViewModelProviders
                .of(this)
                .get(EditorViewModel.class);

        // If the intent DOES NOT contain drink id, then we know that we are
        // creating a new coffee drink.
        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(EXTRA_DRINK_ID)) {
            getSupportActionBar().setTitle("New coffee drink");
        } else {
            // Otherwise this is an existing coffee drink, so change app bar to say "Edit Coffee drink"
            getSupportActionBar().setTitle("Edit coffee drink");
            // Getting article id passed to Activity
            mDrinkEditorViewModel.loadDrinkById(drinkId).observe(this, drink -> {
                mSelectedDrink = drink;
                updateUI();
            });
        }
    }

    // Setting up the fields
    private void updateUI() {
        if (mSelectedDrink != null) {
            mEditDrinkTitle.setText(mSelectedDrink.getName());
            mEditDrinkDescription.setText(mSelectedDrink.getDescription());
        }
    }

    // Menu Related Methods

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Within onCreateOptionsMenu, use getMenuInflater().inflate to inflate the menu
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        // Return true to display your menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                boolean saved = saveDrink();
                if (saved) {
                    //Exit activity only if save was successful
                    finish();
                }
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the product hasn't changed, continue with navigating up to previous activity
                if (drinkDataWasNotChanged()) {
                    finish();
                } else {
                    // Show a dialog that notifies the user they have unsaved changes
                    showUnsavedChangesDialog();
                }
                return true;
        }
        return false;
    }

    // Method to check if the data has changed
    private boolean drinkDataWasNotChanged() {
        String drinkTitle = mEditDrinkTitle.getText().toString().trim();
        String drinkDescription = mEditDrinkDescription.getText().toString().trim();

        // Creating new drink
        if (mSelectedDrink == null) {
            return TextUtils.isEmpty(drinkTitle) && TextUtils.isEmpty(drinkDescription);
        }
        // Updating drink
        else {
            return TextUtils.equals(drinkTitle, mSelectedDrink.getName()) &&
                    TextUtils.equals(drinkDescription, mSelectedDrink.getDescription());
        }
    }

    //This method is called when the back button is pressed.
    @Override
    public void onBackPressed() {
        // If the product hasn't changed, continue with handling back button press
        if (drinkDataWasNotChanged()) {
            super.onBackPressed();
            return;
        }
        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog();
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     */
    private void showUnsavedChangesDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        builder.setPositiveButton("Discard", (dialog, which) -> finish());
        builder.setNegativeButton("Keep editing", (dialog, id) -> {
            // User clicked the "Keep editing" button, so dismiss the dialog
            // and continue editing the product.
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public boolean saveDrink() {
        String drinkTitle = mEditDrinkTitle.getText().toString().trim();
        String drinkDescription = mEditDrinkDescription.getText().toString().trim();
        if (TextUtils.isEmpty(drinkTitle)) {
            // Display Toast with result message
            Toast.makeText(this, "Error with updating the drink. Drink requires a name", Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (mSelectedDrink != null) {
                mSelectedDrink.setName(drinkTitle);
                mSelectedDrink.setDescription(drinkDescription);

                mDrinkEditorViewModel.update(mSelectedDrink);
            } else {

                DrinkEntity drink = new DrinkEntity(
                        drinkTitle,
                        drinkDescription);
                mDrinkEditorViewModel.saveDrink(drink);
            }
            return true;
        }
    }
}