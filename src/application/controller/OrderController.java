package application.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.model.SaleRequest;

public class OrderController {

	public void createOrder(SaleRequest saleRequest) {
		try {
			URL url = new URL(ApiConfig.BASE_URL + "/order/create");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; utf-8");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);

			// ObjectMapper ile SaleRequest nesnesini JSON formatına çeviriyoruz
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonInputString = objectMapper.writeValueAsString(saleRequest);

			// JSON verisini OutputStream ile yazıyoruz
			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			// İsteği gönderip yanıtı kontrol edebilirsiniz
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("Order created successfully.");
			} else {
				System.out.println("Failed to create order. Response code: " + responseCode);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
