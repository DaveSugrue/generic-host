package ie.sugrue.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ie.sugrue.domain.Product;

public class ProductMapper implements RowMapper<Product> {
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new Product();
		product.setId(rs.getLong("id"));
		product.setCategoryId(rs.getString("category_id"));
		product.setFormat(rs.getString("format"));
		product.setName(rs.getString("name"));
		product.setDescription(rs.getString("description"));
		product.setPrice(rs.getBigDecimal("price"));
		product.setStock(rs.getInt("stock"));
		product.setIconUrl(rs.getString("icon_url"));
		product.setImageUrl(rs.getString("image_url"));
		product.setRating(rs.getInt("rating"));
		return product;
	}
}