package ie.sugrue.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ie.sugrue.domain.Category;

public class CategoryMapper implements RowMapper<Category> {
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		Category category = new Category();
		category.setId(rs.getString("id"));
		category.setName(rs.getString("name"));
		category.setOrderSequence(rs.getInt("orderSequence"));
		return category;
	}
}