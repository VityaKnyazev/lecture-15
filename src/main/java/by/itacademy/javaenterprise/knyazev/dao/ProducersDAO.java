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

import by.itacademy.javaenterprise.knyazev.entities.Producer;

@Repository
public class ProducersDAO implements DAO<Producer>{

	
	private JdbcTemplate jdbcProducersDAO;
	private Logger logger;

	private final static String SELECT = "SELECT id, name, postal_code, country, region, locality, street, building FROM producers ";
	private final static String INSERT = "INSERT INTO producers (name, postal_code, country, region, locality, street, building) VALUES(?, ?, ?, ?, ?, ?, ?)";
	private final static String UPDATE = "UPDATE producers SET name = ?, postal_code = ?, country = ?, region = ?, locality = ?, street = ?, building = ?  WHERE id = ?";
	private final static String DELETE = "DELETE FROM producers WHERE id = ?";

	@Autowired
	public ProducersDAO(@Qualifier(value = "jdbcProducersDAO") JdbcTemplate jdbcProducersDAO, @Qualifier(value = "producersDAOLogger") Logger logger) {
		this.jdbcProducersDAO = jdbcProducersDAO;
		this.logger = logger;
	}
	
	@Override
	public Producer select(int id) {
		try {
			Producer producer = jdbcProducersDAO.queryForObject(SELECT + "WHERE id = ?",
					new BeanPropertyRowMapper<>(Producer.class), new Object[] { id });
			if (producer == null)
				throw new RuntimeException(
						"Error, returned null from select(int id) An empty Producer object will be returned");
			return producer;
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			return new Producer();
		}
	}	
	

	@Override
	public List<Producer> selectAll() {
		try {
			return jdbcProducersDAO.query(SELECT, new BeanPropertyRowMapper<>(Producer.class));
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return new ArrayList<Producer>();
		}
	}

	@Override
	public int save(Producer producer) {
		try {
			if (producer.getId() == 0) {
				return jdbcProducersDAO.update(INSERT,
						new Object[] { producer.getName(), producer.getPostalCode(), producer.getCountry(), producer.getRegion(), producer.getLocality(), producer.getStreet(), producer.getBuilding() });

			} else if (producer.getId() < 0) {
				throw new RuntimeException("Error can't save or update Category object: id < 0");
			} else {
				return jdbcProducersDAO.update(UPDATE,
						new Object[] { producer.getName(), producer.getPostalCode(), producer.getCountry(), producer.getRegion(), producer.getLocality(), producer.getStreet(), producer.getBuilding(), producer.getId() });
			}
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Override
	public int delete(Producer producer) {
		try {
			int result = jdbcProducersDAO.update(DELETE, producer.getId());
			producer = null;
			return result;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

}
