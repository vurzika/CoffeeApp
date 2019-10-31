package com.viktorija.coffeelist.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viktorija.coffeelist.R;
import com.viktorija.coffeelist.database.DrinkEntity;
import com.viktorija.coffeelist.details.DetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.viktorija.coffeelist.details.DetailsActivity.EXTRA_DRINK_ID;

// Adapter responsible for returning individual views in lists
// In the adapter we also add functionality, that shows item's value (or details),
// allows to edit item and save changes to the database
// ViewHolder object describes and provides access to all the views within each item row

public class DrinkRecyclerViewAdapter extends RecyclerView.Adapter<DrinkRecyclerViewAdapter.ViewHolder> {

    private final List<DrinkEntity> mDrinkList;

    private Context mContext;

    // constructor
    public DrinkRecyclerViewAdapter(Context context, List<DrinkEntity> drinkList) {
        this.mDrinkList = drinkList;
        this.mContext = context;
    }

    // onCreateViewHolder responsible for inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.coffee_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    // Using inner class ViewHolder, populates data (text) into the item (xml element)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        DrinkEntity drink = mDrinkList.get(position);
        viewHolder.mTextView.setText(drink.getName());

        // Intent to open details Activity
        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra(EXTRA_DRINK_ID, drink.getId());
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mDrinkList.size();
    }

    // Inner class. Represents each item, connects with xml elements.
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.coffee)
        TextView mTextView;

        // constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}