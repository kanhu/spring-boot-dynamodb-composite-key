package com.ks.dynamodb.model;



import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ps_products112")
/**
 * https://github.com/derjust/spring-data-dynamodb/wiki/Use-Hash-Range-keys
 * @author krudash
 *
 */
public class Product {
	
	@Id
	ProductIdentity productIdentity= new ProductIdentity();
	
	@DynamoDBAttribute(attributeName = "description")
	String description;
	
	@DynamoDBHashKey(attributeName = "entity_id")
	public String getProductId() {
		return productIdentity.getProductId();
	}

	
	@DynamoDBRangeKey(attributeName = "ev_id")
	public String getColorCode() {
		return productIdentity.getColorCode();
	}
	
	public void setProductId(String productId) {
		this.productIdentity.productId = productId;
	}

	

	public void setColorCode(String colorCode) {
		this.productIdentity.colorCode = colorCode;
	}

	
	public Product(String productId, String colorCode, String description) {
		super();
		this.productIdentity= new ProductIdentity(productId,colorCode); 
		this.description = description;
	}
	public Product() {
		super();
	}
	
	
	

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productIdentity.getProductId() + ", colorCode=" + productIdentity.getColorCode() + ", description=" + description + "]";
	}
	
}
