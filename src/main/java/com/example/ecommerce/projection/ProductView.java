package com.example.ecommerce.projection;

public interface ProductView {
	Long getId();
    String getProductname();
    String getDescription();
    Long getPrice();
    Long getStock();
    String getCategory();
}
