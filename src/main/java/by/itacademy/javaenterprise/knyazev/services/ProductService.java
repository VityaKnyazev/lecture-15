package by.itacademy.javaenterprise.knyazev.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import by.itacademy.javaenterprise.knyazev.dao.DAO;
import by.itacademy.javaenterprise.knyazev.dto.Product;
import by.itacademy.javaenterprise.knyazev.entities.Category;
import by.itacademy.javaenterprise.knyazev.entities.Good;
import by.itacademy.javaenterprise.knyazev.entities.Producer;

@Service
public class ProductService implements IService<Product> {
	@Autowired
	DAO<Producer> producersDAO;
	@Autowired
	DAO<Category> categoriesDAO;
	@Autowired
	DAO<Good> goodsDAO;

	@Autowired
	@Qualifier(value = "productServiceLogger")
	private Logger logger;

	@Override
	public Product represent(int id) {
		Product product = new Product();
		
		Good good = goodsDAO.select(id);

		if (good.getId() == 0) {
			logger.error("Bad request. empty Product object will be returned");
			return product;
		}
		Category category = categoriesDAO.select(good.getCategoryId());
		Producer producer = producersDAO.select(good.getProducerId());

		if (category.getId() != 0 && producer.getId() != 0) {
			return new Product(good.getId(), good.getName(), good.getSort(), good.getDescription(), category, producer);
		} else {
			logger.error("Internal error. Category object or product object doesn't exist on given id's. Empty product will be returned");
		}
		
		return product;		
	}

	@Override
	public List<Product> represent() {
		List<Product> products = new ArrayList<>();
		
		List<Good> goods = goodsDAO.selectAll();
		
		if (goods.isEmpty()) {
			logger.error("No entities were found bind with Good object. empty Product List of objects will be returned");
			return products;
		}
		
		goods.stream().forEach(g -> {
			Category category = categoriesDAO.select(g.getCategoryId());
			Producer producer = producersDAO.select(g.getProducerId());
			
			if (category.getId() != 0 && producer.getId() != 0) {
				products.add(new Product(g.getId(), g.getName(), g.getSort(), g.getDescription(), category, producer));
			} else {
				logger.error("Internal error. Category object or product object doesn't exist on given id's. Product with good id will be skiped in products list.");
			}
		});
		
		return products;
	}

}
