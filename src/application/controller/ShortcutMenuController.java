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

import application.model.ShortcutMenu;

public class ShortcutMenuController {

	public List<ShortcutMenu> getAllShortcutMenus() {
		List<ShortcutMenu> shortcutMenus = new ArrayList<>();
		try {
			URL url = new URL(ApiConfig.BASE_URL + "/shortcutmenu/getAll");
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

				// Parse the JSONObject
				JSONObject jsonObject = new JSONObject(jsonResponse);

				// Get the JSONArray from the "data" key
				JSONArray jsonArray = jsonObject.getJSONArray("data");

				// Loop through the JSONArray
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject shortcutMenuObject = jsonArray.getJSONObject(i);
					String categoryName = shortcutMenuObject.getString("categoryName");
					UUID id = UUID.fromString(shortcutMenuObject.getString("id"));
					shortcutMenus.add(new ShortcutMenu(categoryName, id));
				}
			} else {
				System.err.println("HTTP request failed with response code: " + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return shortcutMenus;
	}

	public void createShortcutMenu(UUID categoryId) {
		try {
			URL url = new URL(ApiConfig.BASE_URL + "/shortcutmenu/create");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; utf-8");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);

			String jsonInputString = String.format("{\"categoryId\": \"%s\"}", categoryId.toString());

			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				// Handle the response if needed
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
