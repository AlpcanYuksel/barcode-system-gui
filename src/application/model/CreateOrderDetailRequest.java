package application.model;

import java.util.UUID;

public class CreateOrderDetailRequest {

	private UUID productId;

	private double quantity;

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

}
