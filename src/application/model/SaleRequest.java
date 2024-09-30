package application.model;

import java.util.List;
import java.util.UUID;

public class SaleRequest {

	private List<CreateOrderDetailRequest> productItems;

	private UUID customerId;

	private PaymentMethod paymentMethod;

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public List<CreateOrderDetailRequest> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<CreateOrderDetailRequest> productItems) {
		this.productItems = productItems;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}

}
