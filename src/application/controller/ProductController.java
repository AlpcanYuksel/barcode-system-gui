package application.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import application.model.Product;

public class ProductController {


	public static List<Product> fetchProducts() {
		List<Product> products = new ArrayList<>();
		try {
			URL url = new URL(ApiConfig.BASE_URL + "/product/getAll");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// JSON verilerini ayrıştır
			JSONArray jsonArray = new JSONObject(response.toString()).getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				UUID id = UUID.fromString(jsonObject.getString("id"));
				String productName = jsonObject.getString("productName");
				String barcode = jsonObject.getString("barcode");
				double quantity = jsonObject.getDouble("quantity");
				double unitPrice = jsonObject.getDouble("unitPrice");
				products.add(new Product(id, barcode, productName, quantity, unitPrice)); // JSON nesnesinden ürün adını
																							// al
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	public static JSONObject getProductByBarcode(String barcode) {
		try {
			URL url = new URL(ApiConfig.BASE_URL + "/product/getProductByBarcode?barcode=" + barcode);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// JSON objeyi döndür
			return new JSONObject(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	  public static void createProduct(JSONObject productJson) {
	        try {
	            // Define the API endpoint URL for creating a product
	            URL url = new URL(ApiConfig.BASE_URL + "/product/create");
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setDoOutput(true);

	            // Send the JSON data as the POST body
	            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	            wr.writeBytes(productJson.toString());
	            wr.flush();
	            wr.close();

	            // Read the API response
	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String inputLine;
	            StringBuilder response = new StringBuilder();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();

	            // Check response code for success
	            int responseCode = conn.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                System.out.println("Product created successfully!");
	            } else {
	                System.err.println("Failed to create product: " + response.toString());
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
