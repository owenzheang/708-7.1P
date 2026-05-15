# Lost and Found Map App

## Main Features
- Create a new lost or found advert
- Upload an image for each advert
- Automatically save date and time stamp
- Save advert information into SQLite local database
- Show all saved lost and found items
- Filter adverts by category
- View advert details
- Remove advert from the list
- Google Maps integration
- Show all lost and found items on map
- Save latitude and longitude coordinates
- Get current location using GPS
- Radius-based search for nearby items

---

## Technologies Used
- Java
- Android Studio
- SQLite Database
- Google Maps SDK
- Fused Location Provider API

---

## How to Run the App
1. Open the project in Android Studio.
2. Allow Gradle sync to finish.
3. Connect an Android emulator or physical Android device.
4. Enable internet connection and location permission.
5. Click the Run button to install and launch the application.

---

## How to Use the App

### 1. Create a New Advert
- On the main page, click **Create a New Advert**.
- Select Lost or Found type.
- Enter name, phone number, description, and location.
- Choose a category.
- Upload an image.
- Click **Get Current Location** to save GPS coordinates.
- Click **Save** to store the advert.

### 2. View All Lost and Found Items
- On the main page, click **Show All Lost and Found Items**.
- All saved adverts will be displayed in a list.

### 3. Filter by Category
- Use the filter spinner at the top of the item list page.
- Select a category such as Electronics, Pets, Clothes, Books, or Other.

### 4. View Item Detail
- Click any advert in the list.
- The app will open the detail page showing the full advert information.

### 5. Remove an Advert
- On the detail page, click **Remove This Advert**.
- The advert will be deleted from the database and removed from the list.

### 6. Show Items on Map
- On the main page, click **Show on Map**.
- The app will display nearby lost and found items on Google Maps.

### 7. Radius-Based Search
- The map only displays items within a selected radius from the user's current location.

---

## Notes
- Google Maps API key is required for map features.
- Emulator location can be manually changed for testing.
- Internet connection is required for Google Maps.

---

## Author
Haowen Zheng
