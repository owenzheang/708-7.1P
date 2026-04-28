package com.example.a70871p;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    Spinner spinnerFilter;
    ListView listViewItems;

    DatabaseHelper databaseHelper;
    ArrayList<Item> allItems;
    ArrayList<Item> filteredItems;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        spinnerFilter = findViewById(R.id.spinnerFilter);
        listViewItems = findViewById(R.id.listViewItems);

        databaseHelper = new DatabaseHelper(this);

        String[] categories = {"All", "Electronics", "Pets", "Clothes", "Book", "Other"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(spinnerAdapter);
        loadItems();

        listViewItems.setOnItemClickListener((parent, view, position, id) -> {
            Item selectedItem = filteredItems.get(position);

            android.content.Intent intent = new android.content.Intent(ItemListActivity.this, ItemDetailActivity.class);
            intent.putExtra("itemId", selectedItem.id);
            startActivity(intent);
        });

        spinnerFilter.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {

                String selected = spinnerFilter.getSelectedItem().toString();

                filteredItems.clear();

                for (Item item : allItems) {
                    if (selected.equals("All") || item.category.equals(selected)) {
                        filteredItems.add(item);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {

            }
        });

    }
    private void loadItems() {
        allItems = databaseHelper.getAllItems();
        filteredItems = new ArrayList<>(allItems);

        adapter = new ItemAdapter(this, filteredItems);
        listViewItems.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }
}
