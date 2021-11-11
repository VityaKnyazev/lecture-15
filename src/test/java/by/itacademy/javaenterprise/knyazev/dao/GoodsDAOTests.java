package by.itacademy.javaenterprise.knyazev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import by.itacademy.javaenterprise.knyazev.entities.Good;

public class GoodsDAOTests {
	private static NamedParameterJdbcTemplate jdbcMock;
	private static Logger logger;
	private static GoodsDAO goodsDAO;

	@BeforeAll
	public static void setUp() {
		logger = LoggerFactory.getLogger(GoodsDAO.class);
	}

	@BeforeEach
	public void beforeEachTestSettings() {
		jdbcMock = Mockito.mock(NamedParameterJdbcTemplate.class);
		goodsDAO = new GoodsDAO(jdbcMock, logger);
	}

	@Test
	public void whenSelectGoodOnId() {
		Good good = new Good();
		good.setId(5);

		Mockito.when(jdbcMock.queryForObject(Mockito.anyString(), Mockito.<Map<String, ?>>any(),
				ArgumentMatchers.<RowMapper<Good>>any())).thenReturn(good);

		int idForQuery = 5;
		assertEquals(idForQuery, goodsDAO.select(idForQuery).getId());
	}

	@Test
	public void whenSelectGoodOnBadId() {
		Good good = null;
		Mockito.when(jdbcMock.queryForObject(Mockito.anyString(), Mockito.<Map<String, ?>>any(),
				ArgumentMatchers.<RowMapper<Good>>any())).thenReturn(good);

		int badIdForQuery = -25;

		assertEquals(0, goodsDAO.select(badIdForQuery).getId());
	}

	@Test
	public void whenSelecGoodOnIdThrowDataAccessException() {
		int badIdForQuery = 1255;

		Mockito.when(jdbcMock.queryForObject(Mockito.anyString(), Mockito.<Map<String, ?>>any(),
				ArgumentMatchers.<RowMapper<Good>>any())).thenThrow(RecoverableDataAccessException.class);

		assertEquals(0, goodsDAO.select(badIdForQuery).getId());
	}

	@Test
	public void whenSelectAll() {
		List<Good> goods = new ArrayList<>();
		goods.add(new Good(12, "Черника", "Черный-красный", "Ягода черника", 3, 1));

		Mockito.when(jdbcMock.query(Mockito.anyString(), Mockito.<RowMapper<Good>>any()))
				.thenReturn(goods);

		assertEquals(12, goodsDAO.selectAll().get(0).getId());
	}

	@Test
	public void whenSelectAllEmpty() {
		Mockito.when(jdbcMock.query(Mockito.anyString(), Mockito.<RowMapper<Good>>any()))
				.thenThrow(RecoverableDataAccessException.class);

		assertEquals(true, goodsDAO.selectAll().isEmpty());
	}

	@Test
	public void whenSaveProducer() {
		Good good = new Good(0, "Черника", "Черный-красный", "Ягода черника", 3, 1);

		Mockito.when(jdbcMock.update(Mockito.anyString(), Mockito.<Map<String, ?>>any())).thenReturn(1);

		assertEquals(1, goodsDAO.save(good));
	}

	@Test
	public void whenUpdateGood() {
		Good good = new Good(8, "Черника", "Черный-красный", "Ягода черника", 3, 1);

		Mockito.when(jdbcMock.update(Mockito.anyString(), Mockito.<Map<String, ?>>any())).thenReturn(1);

		assertEquals(1, goodsDAO.save(good));
	}

	@Test
	public void whenSaveOrUpdateGoodWithBadId() {
		Good good = new Good(-21, "Черника", "Черный-красный", "Ягода черника", 3, 1);

		Mockito.when(jdbcMock.update(Mockito.anyString(), Mockito.<Map<String, ?>>any())).thenReturn(0);

		assertEquals(0, goodsDAO.save(good));
	}

	@Test
	public void whenDeleteGood() {
		Good good = new Good(5, "Черника", "Черный-красный", "Ягода черника", 3, 1);

		Mockito.when(jdbcMock.update(Mockito.anyString(), Mockito.<Map<String, ?>>any())).thenReturn(1);

		assertEquals(1, goodsDAO.delete(good));
	}
	
	@Test
	public void whenDeleteGoodDataAccessExceptio() {
		Good good = new Good(8, "Черника", "Черный-красный", "Ягода черника", 3, 1);
		
		Mockito.when(jdbcMock.update(Mockito.anyString(), Mockito.<Map<String, ?>>any())).thenThrow(RecoverableDataAccessException.class);

		assertEquals(0, goodsDAO.delete(good));
	}
}
