package com.example.a70871p;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    TextView detailType, detailName, detailPhone, detailDescription, detailLocation, detailCategory, detailDateTime;
    ImageView detailImage;
    Button btnDelete;

    DatabaseHelper databaseHelper;
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        detailType = findViewById(R.id.detailType);
        detailName = findViewById(R.id.detailName);
        detailPhone = findViewById(R.id.detailPhone);
        detailDescription = findViewById(R.id.detailDescription);
        detailLocation = findViewById(R.id.detailLocation);
        detailCategory = findViewById(R.id.detailCategory);
        detailDateTime = findViewById(R.id.detailDateTime);
        detailImage = findViewById(R.id.detailImage);
        btnDelete = findViewById(R.id.btnDelete);

        databaseHelper = new DatabaseHelper(this);

        int itemId = getIntent().getIntExtra("itemId", -1);

        item = databaseHelper.getItemById(itemId);

        if (item != null) {
            detailType.setText(item.type);
            detailName.setText("Name: " + item.name);
            detailPhone.setText("Phone: " + item.phone);
            detailDescription.setText("Description: " + item.description);
            detailLocation.setText("Location: " + item.location);
            detailCategory.setText("Category: " + item.category);
            detailDateTime.setText("Posted: " + item.dateTime);
            detailImage.setImageURI(Uri.parse(item.image));
        }

        btnDelete.setOnClickListener(v -> {
            if (item != null) {
                databaseHelper.deleteItem(item.id);
            }
            finish();
        });
    }
}
