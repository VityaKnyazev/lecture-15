package by.itacademy.javaenterprise.knyazev.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import by.itacademy.javaenterprise.knyazev.entities.Good;

@Repository
public class GoodsDAO implements DAO<Good>{

	private NamedParameterJdbcTemplate jdbcGoodsDAO;	
	private Logger logger;
	
	private final static String SELECT = "SELECT id, name, sort, description, category_id, producer_id FROM goods ";
	private final static String INSERT = "INSERT INTO goods (name, sort, description, category_id, producer_id) VALUES(:name, :sort, :description, :category_id, :producer_id)";
	private final static String UPDATE = "UPDATE goods SET name = :name, sort = :sort, description = :description, category_id = :category_id, producer_id = :producer_id WHERE id = :id";
	private final static String DELETE = "DELETE FROM goods WHERE id = :id";
	
	@Autowired
	public GoodsDAO(NamedParameterJdbcTemplate jdbcGoodsDAO, @Qualifier(value = "goodsDAOLogger") Logger logger) {
		this.jdbcGoodsDAO = jdbcGoodsDAO;
		this.logger = logger;
	}
	
	@Override
	public Good select(int id) {
		try {
			Good good = jdbcGoodsDAO.queryForObject(SELECT + "WHERE id = :id", Collections.singletonMap("id", id), new BeanPropertyRowMapper<>(Good.class));
			if (good == null)
				throw new RuntimeException(
						"Error, returned null from select(int id) An empty Good object will be returned");
			return good;
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			return new Good();
		}
	}

	@Override
	public List<Good> selectAll() {
		try {
		return jdbcGoodsDAO.query(SELECT, new BeanPropertyRowMapper<>(Good.class));
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return new ArrayList<Good>();
		}
	}

	@Override
	public int save(Good good) {
		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("name", good.getName());
		namedParameters.put("sort", good.getSort());
		namedParameters.put("description", good.getDescription());
		namedParameters.put("category_id", good.getCategoryId());
		namedParameters.put("producer_id", good.getProducerId());
		
		int id = good.getId();
		
		try {
			if (id == 0) {				
				return jdbcGoodsDAO.update(INSERT, namedParameters);
			} else if (id < 0) {
				throw new RuntimeException("Error can't save or update Good object: id < 0");
			} else {
				namedParameters.put("id", id);
				return jdbcGoodsDAO.update(UPDATE, namedParameters);
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Override
	public int delete(Good good) {
		try {
			int result = jdbcGoodsDAO.update(DELETE, Collections.singletonMap("id", good.getId()));
			good = null;
			return result;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

}
