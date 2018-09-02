package ie.sugrue.repository;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ie.sugrue.domain.Category;

@Repository
public class MySQLCategoryRepositoryImpl implements CategoryRepository {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected JdbcTemplate	jdbc;

	public void createCategory(Category category) {
		String SQL = "insert into category ( id, name, orderSequence ) values (?, ?, ?)";

		jdbc.update(SQL, category.getId(), category.getName(), category.getOrderSequence());
		log.debug("Created Record Name = " + category.getId() + " " + category.getName());
		return;
	}

	public Category getCategory(String id) {
		String SQL = "select * from category where id = ?";
		Category category = jdbc.queryForObject(SQL, new Object[] { id }, new CategoryMapper());
		return category;
	}

	public ArrayList<Category> getCategories() {
		String SQL = "select * from category order by orderSequence";
		ArrayList<Category> categories = new ArrayList<Category>();
		categories.addAll(jdbc.query(SQL, new CategoryMapper()));
		return categories;
	}

	public void deleteCategory(String id) {
		String SQL = "delete from category where id = ?";
		int result = jdbc.update(SQL, id);
		if (result == 0) {
			throw new EmptyResultDataAccessException(1);
		}
		log.debug("Deleted Record with ID = " + id);
		return;
	}

	public Category updateCategory(Category category) {
		// user = populateUserNullsWithdefaults(user);

		String SQL = "update category set name = ?, orderSequence = ? where id = ?";
		jdbc.update(SQL, category.getName(), category.getOrderSequence(), category.getId());
		category = getCategory(category.getId());
		log.debug("Updated Record with ID = " + category.getId());
		return category;
	}

}