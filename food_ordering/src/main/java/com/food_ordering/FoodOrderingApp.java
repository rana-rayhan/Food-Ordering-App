package com.food_ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodOrderingApp {
	public static void main(String[] args) {
		try {
			SpringApplication.run(FoodOrderingApp.class, args);
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}
	}
}
