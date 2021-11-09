package by.itacademy.javaenterprise.knyazev;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import by.itacademy.javaenterprise.knyazev.config.AppConfig;
import by.itacademy.javaenterprise.knyazev.dto.Product;
import by.itacademy.javaenterprise.knyazev.services.IService;
import by.itacademy.javaenterprise.knyazev.services.ProductService;

	public class App {	
		private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		final static Logger logger = context.getBean("appLogger", Logger.class);
		
		public static void main(String[] args) {		
			IService<Product> productService = context.getBean(ProductService.class);
			Product product = productService.represent(3);
			List<Product> products = productService.represent();
					
					
			logger.info("User representation of product with id = 3:");
			logger.info(product.toString());
			
			logger.info("User representation for all products:");
			products.stream().forEach(p -> logger.info(p.toString()));
			
			((AnnotationConfigApplicationContext)  context).close();
		}
	
}
