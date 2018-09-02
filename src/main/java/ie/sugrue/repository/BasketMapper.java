package ie.sugrue.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ie.sugrue.domain.Basket;

public class BasketMapper implements RowMapper<Basket> {
	public Basket mapRow(ResultSet rs, int rowNum) throws SQLException {
		Basket basket = new Basket();
		basket.setId(rs.getLong("id"));
		basket.setUserId(rs.getLong("app_user_id"));
		basket.setStatus(rs.getString("status"));
		basket.setComplete(rs.getBoolean("complete"));
		return basket;
	}
}