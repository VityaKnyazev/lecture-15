package by.itacademy.javaenterprise.knyazev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import by.itacademy.javaenterprise.knyazev.entities.Category;

public class CategoriesDAOTests {
	private static DataSource ds;
	private static JdbcTemplate jdbc;
	private static Logger logger;
	private static CategoriesDAO categoriesDAO;
	

	@BeforeAll
	public static void setUp() {
		ds = new DriverManagerDataSource("jdbc:postgresql://localhost/storage?useUnicode=true&characterEncoding=UTF-8",
				"admin", "admin");
		jdbc = new JdbcTemplate(ds);
		logger = LoggerFactory.getLogger(CategoriesDAO.class);
		categoriesDAO = new CategoriesDAO(jdbc, logger);
	}

	@Test
	public void whenSelectCategoryOnID() {
		int id = 2;

		Category category = categoriesDAO.select(id);

		assertEquals(id, category.getId());
	}

	@Test
	public void whenSelectCategorOnUnexistingId() {
		int id = 62;

		Category category = categoriesDAO.select(id);

		assertEquals(0, category.getId());
	}

	@Test
	public void whenSelectCategoryOnBadId() {
		int id = -2;

		CategoriesDAO categoriesDAO = new CategoriesDAO(jdbc, logger);
		Category category = categoriesDAO.select(id);

		assertEquals(0, category.getId());
	}

	@Test
	public void whenSelectEmptyListCategories() {
		List<Category> categories = categoriesDAO.selectAll();
		assertFalse(categories.isEmpty(), "Category is epty, List of Category objects expected");
	}

	@Test
	public void whenInsertCategory() {
		Category category = new Category(0, "Зарубежные плоды",
				"Зарубежные плоды - экзотические овощи и фрукты малоизвестные");
		int result = categoriesDAO.save(category);

		assertEquals(1, result);
	}

	@Test
	public void whenInsertCategoryWithExceedingFieldSize() {
		Category category = new Category(0, "Иностранные плоды",
				"Иностранные плоды - экзотические овощи и фрукты малоизвестные и неупотребляемые в нашей стране. Однако очень полезные для граждан нашей страны и без преувеличения необходимые для импорта. Например, папайя являются ..");
		int result = categoriesDAO.save(category);

		assertEquals(0, result);
	}
	
	@Test
	public void whenInsertWithBadId() {
		Category category = new Category(-2, "Тяжелая артилерия", "Не про еду");
		int result = categoriesDAO.save(category);

		assertEquals(0, result);
	}

	@Test
	public void whenInsertNull() {
		Category category = new Category(0, null, null);
		int result = categoriesDAO.save(category);

		assertEquals(0, result);
	}
	
	@Test
	public void whenUpdateCategory() {
		Category category = new Category(3, "Плоды", "Плоды - овощи и фрукты плодовых растений");
		int result = categoriesDAO.save(category);

		assertEquals(1, result);
	}

	@Test
	public void whenUpdateCategoryWithExceedingFieldSize() {
		Category category = new Category(3, "Зарубежные овощи",
				"Зарубежные овощи - экзотические овощи малоизвестные и неупотребляемые в нашей стране. Однако очень полезные для граждан нашей страны и без преувеличения необходимые для импорта. Например, папайя являются ..");
		int result = categoriesDAO.save(category);

		assertEquals(0, result);
	}
	
	@Test
	public void whenUpdateWithBadId() {
		Category category = new Category(-2, "Красные фрукты", "Определение не найдено, стоит поискать в учебниках истории");
		int result = categoriesDAO.save(category);

		assertEquals(0, result);
	}

	@Test
	public void whenUpdateNull() {
		Category category = new Category(1, "Не содержит", null);
		int result = categoriesDAO.save(category);

		assertEquals(0, result);
	}
	
	@Test
	public void whenDeleteLinkedValueInCategory() {
		int id = 3;
		
		Category category = categoriesDAO.select(3);
		
		int result = categoriesDAO.delete(category);
		assertNotNull(category);
		assertEquals(category.getId(), id);
		assertEquals(0, result);
	}
	
	@Test
	public void whenDeleteWithBadId() {
		int id = -5;
		
		Category category = categoriesDAO.select(id);
		
		int result = categoriesDAO.delete(category);
		assertEquals(0, result);
	}
}