package by.itacademy.javaenterprise.knyazev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import by.itacademy.javaenterprise.knyazev.entities.Producer;

public class ProducersDAOTests {
	private static JdbcTemplate jdbcMock;
	private static Logger logger;
	private ProducersDAO producersDAO;

	@BeforeAll
	public static void setUp() {
		logger = LoggerFactory.getLogger(ProducersDAO.class);
		jdbcMock = Mockito.mock(JdbcTemplate.class);

		Producer producer = new Producer();
		producer.setId(2);
		Mockito.when(jdbcMock.queryForObject(Mockito.anyString(),
				Mockito.eq(new BeanPropertyRowMapper<>(Producer.class)), Mockito.eq(new Object[] {  })))
				.thenReturn(producer);
	}

	@Test
	public void whenSelectProducerOnId() {

		int idForQuery = 2;
		producersDAO = new ProducersDAO(jdbcMock, logger);
		Producer producer = producersDAO.select(idForQuery);

		assertEquals(idForQuery, producer.getId());
	}

}
