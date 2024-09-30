package application.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import application.model.ShortcutMenuItem;

public class ShortcutMenuItemController {

	public List<ShortcutMenuItem> getAllShortcutMenuItems(UUID shortcutMenuId) {

		List<ShortcutMenuItem> shortcutMenuItems = new ArrayList<>();
		try {
			URL url = new URL(ApiConfig.BASE_URL + "/shortcutmenuitem/getShortcutMenuId?id=" + shortcutMenuId.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
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
					JSONObject shortcutMenuItemObject = jsonArray.getJSONObject(i);
					UUID id = UUID.fromString(shortcutMenuItemObject.getString("id"));
					String productName = shortcutMenuItemObject.getString("productName");
					String barcode = shortcutMenuItemObject.getString("barcode");
					shortcutMenuItems.add(new ShortcutMenuItem(id, shortcutMenuId, productName, barcode));
				}
			} else {
				System.err.println("HTTP request failed with response code: " + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return shortcutMenuItems;
	}

	public void createShortcutMenuItem(UUID shortcutMenuId, UUID productId) {
		try {
			URL url = new URL(ApiConfig.BASE_URL + "/shortcutmenuitem/create");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; utf-8");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);

			// Create the JSON string with the shortcutMenuId and productId
			String jsonInputString = String.format("{\"shortcutMenuId\": \"%s\", \"productId\": \"%s\"}",
					shortcutMenuId.toString(), productId.toString());

			// Send the JSON as the request body
			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			// Read and process the response from the server if needed
			try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				// Handle the response if needed
				System.out.println("Response: " + response.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
