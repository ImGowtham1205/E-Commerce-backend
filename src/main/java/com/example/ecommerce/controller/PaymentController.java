package com.example.ecommerce.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.service.PaymentService;
import com.razorpay.RazorpayException;

@RestController
public class PaymentController {
	
	private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
	
    @PostMapping("/api/user/create")
    public Map<String, String> createOrder(@RequestBody Map<String,Object> data) throws RazorpayException  {
    	int amount = Integer.parseInt(data.get("amount").toString());
    	long userid = Long.parseLong(data.get("userid").toString());
    	return paymentService.createOrder(amount,userid);
    }

    @PostMapping("/api/user/verify")
    public String verifyPayment(@RequestBody Map<String,String> data) {  	
        return paymentService.verifyPayment(data);
    }
    
}
