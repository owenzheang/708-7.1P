package com.example.a70871p;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnAddAdvert, btnShowItems, btnShowMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAddAdvert = findViewById(R.id.btnAddAdvert);
        btnShowItems = findViewById(R.id.btnShowItems);
        btnShowMap = findViewById(R.id.btnShowMap);

        btnAddAdvert.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivity(intent);
        });

        btnShowItems.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
            startActivity(intent);
        });

        btnShowMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}