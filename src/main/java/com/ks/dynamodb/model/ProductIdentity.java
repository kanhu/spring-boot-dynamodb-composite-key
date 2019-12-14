package com.ks.dynamodb.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class ProductIdentity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DynamoDBHashKey(attributeName = "entity_id")
	String productId;
	
	@DynamoDBRangeKey(attributeName = "ev_id")
	String colorCode;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public ProductIdentity(String productId, String colorCode) {
		super();
		this.productId = productId;
		this.colorCode = colorCode;
	}
	
	public ProductIdentity() {
		super();
	}
	
	
	
	
}
