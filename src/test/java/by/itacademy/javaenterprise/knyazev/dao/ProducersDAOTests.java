package by.itacademy.javaenterprise.knyazev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import by.itacademy.javaenterprise.knyazev.entities.Producer;

public class ProducersDAOTests {
	private static JdbcTemplate jdbcMock;
	private static Logger logger;
	private static ProducersDAO producersDAO;

	@BeforeAll
	public static void setUp() {
		logger = LoggerFactory.getLogger(ProducersDAO.class);
	}

	@BeforeEach
	public void beforeEachTestSettings() {
		jdbcMock = Mockito.mock(JdbcTemplate.class);
		producersDAO = new ProducersDAO(jdbcMock, logger);
	}

	@Test
	public void whenSelectProducerOnId() {
		Producer producer = new Producer();
		producer.setId(3);

		Mockito.when(jdbcMock.queryForObject(Mockito.anyString(), Mockito.<RowMapper<Producer>>any(),
				Mockito.any())).thenReturn(producer);

		int idForQuery = 3;
		assertEquals(idForQuery, producersDAO.select(idForQuery).getId());
	}

	@Test
	public void whenSelectProducerOnBadId() {
		Producer producer = null;
		Mockito.when(jdbcMock.queryForObject(Mockito.anyString(), Mockito.<RowMapper<Producer>>any(),
				Mockito.any())).thenReturn(producer);

		int badIdForQuery = -25;

		assertEquals(0, producersDAO.select(badIdForQuery).getId());
	}

	@Test
	public void whenSelecProducerOnIdThrowDataAccessException() {
		int badIdForQuery = 1255;

		Mockito.when(jdbcMock.queryForObject(Mockito.anyString(), Mockito.<RowMapper<Producer>>any(),
				Mockito.any())).thenThrow(RecoverableDataAccessException.class);

		assertEquals(0, producersDAO.select(badIdForQuery).getId());
	}

	@Test
	public void whenSelectAll() {
		List<Producer> producers = new ArrayList<>();
		producers.add(new Producer(18, "ОАО Тест", "212056", "РБ", "Брестская обл.", "Брест", "ул. Зимняя", "12"));

		Mockito.when(jdbcMock.query(Mockito.anyString(), Mockito.<RowMapper<Producer>>any()))
				.thenReturn(producers);

		assertEquals(18, producersDAO.selectAll().get(0).getId());
	}

	@Test
	public void whenSelectAllEmpty() {
		Mockito.when(jdbcMock.query(Mockito.anyString(), Mockito.<RowMapper<Producer>>any()))
				.thenThrow(RecoverableDataAccessException.class);

		assertEquals(true, producersDAO.selectAll().isEmpty());
	}

	@Test
	public void whenSaveProducer() {
		Producer producer = new Producer(0, "ОАО Тест", "212056", "РБ", "Брестская обл.", "Брест", "ул. Зимняя", "12");

		Mockito.when(jdbcMock.update(Mockito.anyString(), Mockito.<Object[]>any())).thenReturn(1);

		assertEquals(1, producersDAO.save(producer));
	}

	@Test
	public void whenUpdateProducer() {
		Producer producer = new Producer(5, "ОАО Тест", "212056", "РБ", "Брестская обл.", "Брест", "ул. Зимняя", "12");

		Mockito.when(jdbcMock.update(Mockito.anyString(), Mockito.<Object[]>any())).thenReturn(1);

		assertEquals(1, producersDAO.save(producer));
	}

	@Test
	public void whenSaveOrUpdateProducerWithBadId() {
		Producer producer = new Producer(-1, "ОАО Тест", "212056", "РБ", "Брестская обл.", "Брест", "ул. Зимняя", "12");

		Mockito.when(jdbcMock.update(Mockito.anyString(), Mockito.<Object[]>any())).thenReturn(0);

		assertEquals(0, producersDAO.save(producer));
	}

	@Test
	public void whenDeleteProducer() {
		Producer producer = new Producer(2, "ОАО Тест", "212056", "РБ", "Брестская обл.", "Брест", "ул. Зимняя", "12");

		Mockito.when(jdbcMock.update(Mockito.anyString(), Mockito.<Object[]>any())).thenReturn(1);

		assertEquals(1, producersDAO.delete(producer));
	}
	
	@Test
	public void whenDeleteProducerDataAccessExceptio() {
		Producer producer = new Producer(2, "ОАО Тест", "212056", "РБ", "Брестская обл.", "Брест", "ул. Зимняя", "12");
		
		Mockito.when(jdbcMock.update(Mockito.anyString(), Mockito.<Object[]>any())).thenThrow(RecoverableDataAccessException.class);

		assertEquals(0, producersDAO.delete(producer));
	}
}
