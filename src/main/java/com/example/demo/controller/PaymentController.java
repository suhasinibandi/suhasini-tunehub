package com.example.demo.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entities.Users;
import com.example.demo.services.UsersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	@Autowired
	UsersService service;
	
	@GetMapping("/pay")
	public String pay() {
		return "pay";
	}
	@GetMapping("/payment-success")
	public String paymentSuccess(HttpSession session)
	{
		String mail=(String)session.getAttribute("email");
		Users u=service.getUser(mail);
		u.setPremium(true);
		service.updateUser(u);
		return "customerHome";
	}
	
	@GetMapping("payment-failure")
	public String paymentFailure() {
		return "customerHome";
	}

	
	@SuppressWarnings("finally")
	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder()
	{
		int amount = 5000;
		Order order=null;
		try {
			RazorpayClient razorpay =new RazorpayClient("rzp_test_pvGIc9JvXWZTVS", "UlnLCUb8lRxvBKRyIYRYWrEO");
			
			JSONObject orderRequest=new JSONObject();
			orderRequest.put("amount", amount*100);//amount in the smallest currency unit
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "order_rcptid_11");
			
			order=razorpay.orders.create(orderRequest);
			
			String mail=(String) session.getAttribute("email");
			
			Users u=service.getUser("mail");
			u.setPremium(true);
			service.updateUser(u);			
			
		}catch(RazorpayException e){
			e.printStackTrace();
		}
		finally {
			return order.toString();
		}
	}
	@PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestParam String orderId, @RequestParam )
	try {
		RazorpayClient razorpayClient = new razorpayClient("rzp_test_pvGIc9JvXWZTVS");
		
		String verificationData=orderId+ "" +paymentId;
		boolean isValidSignature =  Utils.verifyPaymentSignature(verificationData, secret);
	    return isValidSignature;
	}catch (RazorpaayException e) {
		e.printStackTrace();
		return false;
	}
	
}
