package application.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import application.model.Category;

public class CategoryController {

	

	public List<Category> fetchCategories() {
		List<Category> categories = new ArrayList<>();
		try {
			URL url = new URL(ApiConfig.BASE_URL + "/category/get");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // HTTP 200 OK
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				String jsonResponse = response.toString();

				// Print response for debugging purposes

				// Parse the JSONObject
				JSONObject jsonObject = new JSONObject(jsonResponse);

				// Get the JSONArray from the "data" key
				JSONArray jsonArray = jsonObject.getJSONArray("data");

				// Loop through the JSONArray
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject categoryObject = jsonArray.getJSONObject(i);
					String name = categoryObject.getString("categoryName");
					UUID id = UUID.fromString(categoryObject.getString("id")); // Assuming categoryId is in UUID
																				// format
					categories.add(new Category(name, id));
				}
			} else {
				System.err.println("HTTP request failed with response code: " + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}

}
