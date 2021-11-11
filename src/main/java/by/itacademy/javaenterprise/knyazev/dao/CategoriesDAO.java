package by.itacademy.javaenterprise.knyazev.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import by.itacademy.javaenterprise.knyazev.entities.Category;

@Repository
public class CategoriesDAO implements DAO<Category> {

	private JdbcTemplate jdbcCategoriesDAO;
	private Logger logger;

	private final static String SELECT = "SELECT id, name, description FROM categories ";
	private final static String INSERT = "INSERT INTO categories (name, description) VALUES(?, ?)";
	private final static String UPDATE = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
	private final static String DELETE = "DELETE FROM categories WHERE id = ?";

	@Autowired()
	public CategoriesDAO(@Qualifier(value = "jdbcCategoriesDAO") JdbcTemplate jdbcCategoriesDAO,
			@Qualifier(value = "categoriesDAOLogger") Logger logger) {
		this.jdbcCategoriesDAO = jdbcCategoriesDAO;
		this.logger = logger;
	}

	@Override
	public Category select(int id) {
		try {
			Category category = jdbcCategoriesDAO.queryForObject(SELECT + "WHERE id = ?",
					new BeanPropertyRowMapper<>(Category.class), new Object[] { id });
			if (category == null)
				throw new RuntimeException(
						"Error, returned null from select(int id) An empty Category object will be returned");
			return category;
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			return new Category();
		}
	}

	@Override
	public List<Category> selectAll() {
		try {
			return jdbcCategoriesDAO.query(SELECT, new BeanPropertyRowMapper<>(Category.class));
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return new ArrayList<Category>();
		}
	}

	@Override
	public int save(Category category) {
		try {
			if (category.getDescription() == null || category.getName() == null) {
				throw new RuntimeException("Error table Categories disallow NULL values");
			}
			
			if (category.getId() == 0) {
				return jdbcCategoriesDAO.update(INSERT, new Object[] { category.getName(), category.getDescription() });

			} else if (category.getId() < 0) {
				throw new RuntimeException("Error can't save or update Category object: id < 0");
			} else {
				return jdbcCategoriesDAO.update(UPDATE,
						new Object[] { category.getName(), category.getDescription(), category.getId() });
			}
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Override
	public int delete(Category category) {
		try {
			int result = jdbcCategoriesDAO.update(DELETE, category.getId());
			return result;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}
}