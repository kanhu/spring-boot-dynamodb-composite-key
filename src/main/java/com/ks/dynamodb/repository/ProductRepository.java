
package com.ks.dynamodb.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.ks.dynamodb.model.Product;
import com.ks.dynamodb.model.ProductIdentity;

@EnableScan
public interface ProductRepository extends CrudRepository<Product, ProductIdentity> {

}
