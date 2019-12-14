package com.ks.dynamodb.example;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.ks.dynamodb.configuration.DynamoDBConfig;
import com.ks.dynamodb.model.Product;
import com.ks.dynamodb.model.ProductIdentity;
import com.ks.dynamodb.repository.ProductRepository;


@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, // No JPA
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableDynamoDBRepositories(basePackageClasses = ProductRepository.class)
@Configuration
@Import(DynamoDBConfig.class)
public class ExampleApplication {
	private static final Logger log = LoggerFactory.getLogger(ExampleApplication.class);

	@Autowired
	DynamoDBMapper dynamoDBMapper;
	
	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	
	}
	
	@Bean
	public CommandLineRunner custom(ConfigurableApplicationContext ctx, ProductRepository productRepository,
			AmazonDynamoDB amazonDynamoDB, DynamoDBMapper dynamoDBMapper, DynamoDBMapperConfig config) {
		return args -> {
			CreateTableRequest ctr = dynamoDBMapper.generateCreateTableRequest(Product.class)
					.withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
			TableUtils.createTableIfNotExists(amazonDynamoDB, ctr);
			TableUtils.waitUntilActive(amazonDynamoDB, ctr.getTableName());

			demoCustomInterface(productRepository);
			

			ctx.close();
		};
	}
		

	
	
	  private void demoCustomInterface(ProductRepository productRepository) {
	  
	  // Create product & save it (creates Id) 
      Product product= new
	  Product("1234","123","{\"sd\" : \"short desc\", \"ld\" :\"long desc\"}");
	  
	  productRepository.save(product); 
	  
	  log.info("Created product: {}", product);
	  
	  
	  // Reload instance to ensure custom method worked 
	  Optional<Product>
	  reloadedProduct = productRepository.findById( new
	  ProductIdentity("1234","123"));
	  
	  assert reloadedProduct.isPresent();
	  
	  log.info("Comparison - Old entity: {}", product);
	  log.info("Comparison - New entity: {}", reloadedProduct.get()); }
	 


}
