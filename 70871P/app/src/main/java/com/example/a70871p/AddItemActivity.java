package com.example.a70871p;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddItemActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText editName, editPhone, editDescription, editLocation;
    Spinner spinnerCategory;
    Button btnChooseImage, btnSave;
    ImageView imagePreview;
    RadioButton radioLost, radioFound;

    String selectedImageUri = "";

    ActivityResultLauncher<String[]> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        databaseHelper = new DatabaseHelper(this);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editDescription = findViewById(R.id.editDescription);
        editLocation = findViewById(R.id.editLocation);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSave = findViewById(R.id.btnSave);
        imagePreview = findViewById(R.id.imagePreview);
        radioLost = findViewById(R.id.radioLost);
        radioFound = findViewById(R.id.radioFound);

        String[] categories = {"Electronics", "Pets", "Clothes", "Books", "Other"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {

                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        getContentResolver().takePersistableUriPermission(uri, takeFlags);

                        selectedImageUri = uri.toString();
                        imagePreview.setImageURI(uri);
                    }
                }
        );

        btnChooseImage.setOnClickListener(v -> {
            imagePickerLauncher.launch(new String[]{"image/*"});
        });

        btnSave.setOnClickListener(v -> {
            String type;

            if (radioLost.isChecked()) {
                type = "Lost";
            } else {
                type = "Found";
            }

            String name = editName.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String description = editDescription.getText().toString().trim();
            String location = editLocation.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();

            String dateTime = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.getDefault()
            ).format(new Date());

            if (name.isEmpty() || phone.isEmpty() || description.isEmpty()
                    || location.isEmpty() || selectedImageUri.isEmpty()) {

                Toast.makeText(this, "Please fill all fields and choose an image", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = databaseHelper.insertItem(
                    type,
                    name,
                    phone,
                    description,
                    location,
                    category,
                    selectedImageUri,
                    dateTime
            );

            if (inserted) {
                Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save advert", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
