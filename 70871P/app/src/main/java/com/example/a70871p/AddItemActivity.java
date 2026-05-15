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

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import android.location.Location;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;

public class AddItemActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText editName, editPhone, editDescription, editLocation;
    Spinner spinnerCategory;
    Button btnChooseImage, btnSave, btnCurrentLocation;
    FusedLocationProviderClient fusedLocationClient;
    ImageView imagePreview;
    RadioButton radioLost, radioFound;
    String selectedImageUri = "";
    double selectedLatitude = -37.8136;
    double selectedLongitude = 144.9631;

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
        btnCurrentLocation = findViewById(R.id.btnCurrentLocation);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
                    dateTime,
                    selectedLatitude,
                    selectedLongitude
            );

            if (inserted) {
                Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save advert", Toast.LENGTH_SHORT).show();
            }
        });

        btnCurrentLocation.setOnClickListener(v -> {
            getCurrentLocation();
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100
            );
            return;
        }

        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();

        fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.getToken()
        ).addOnSuccessListener(location -> {
            if (location != null) {
                selectedLatitude = location.getLatitude();
                selectedLongitude = location.getLongitude();

                editLocation.setText(selectedLatitude + ", " + selectedLongitude);

                Toast.makeText(this, "Current location saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cannot get location. Please set emulator location first.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
