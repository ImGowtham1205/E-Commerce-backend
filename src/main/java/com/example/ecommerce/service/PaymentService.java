package com.example.ecommerce.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.Products;
import com.example.ecommerce.model.Users;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
public class PaymentService {

	private OrderService orderService;
	private ProductService productService;
	private UsersService userService;

	public PaymentService(OrderService orderService, ProductService productService, UsersService userService) {
		this.orderService = orderService;
		this.productService = productService;
		this.userService = userService;
	}

	@Value("${razorpay.client.id}")
	private String clientid;
	@Value("${razorpay.client.secret}")
	private String clientsecret;

	public Map<String, String> createOrder(int amonut, long userid) throws RazorpayException {

		Users user = userService.getUserById(userid);
		String email = user.getEmail();

		RazorpayClient client = new RazorpayClient(clientid, clientsecret);

		JSONObject obj = new JSONObject();
		obj.put("amount", amonut * 100);
		obj.put("currency", "INR");
		obj.put("receipt", email + System.currentTimeMillis());

		Order order = client.orders.create(obj);

		Map<String, String> response = new HashMap<>();
		response.put("id", order.get("id").toString());
		response.put("amount", order.get("amount").toString());
		return response;
	}

	public String verifyPayment(Map<String, String> data) {
		try {
			long productid = Long.parseLong(data.get("productid"));
			long userid = Long.parseLong(data.get("userid"));

			String razorpayOrderId = data.get("razorpay_order_id");
			String razorpayPaymentId = data.get("razorpay_payment_id");
			String razorpaySignature = data.get("razorpay_signature");

			String payload = razorpayOrderId + "|" + razorpayPaymentId;
			boolean isValid = Utils.verifySignature(payload, razorpaySignature, clientsecret);
			
			if(!isValid)
	            return "Payment verification failed";
	        			
			Products product = productService.getProductById(productid);
			Users user = userService.getUserById(userid);

			Orders order = orderService.buildOrder(productid, user, "RAZORPAY", "PAID",
					data.get("razorpay_payment_id"));
			product.setStock(product.getStock() - 1);
			orderService.placeOrder(order, product, user);
			return "Payment successful";
		} catch (RazorpayException e) {
			e.printStackTrace();
			return "Payment failed";
		}

	}
}