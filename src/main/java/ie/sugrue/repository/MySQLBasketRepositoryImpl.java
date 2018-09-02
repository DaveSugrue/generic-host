package ie.sugrue.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ie.sugrue.domain.Basket;

@Repository
public class MySQLBasketRepositoryImpl implements BasketRepository {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected JdbcTemplate	jdbc;

	public void createBasket(Basket basket) {
		String SQL = "insert into basket ( id, app_user_id, status, complete ) values (?, ?, ?, ?)";

		jdbc.update(SQL, basket.getId(), basket.getUserId(), basket.getStatus(), basket.isComplete());
		log.debug("Created Record Name = " + basket.getId() + " " + basket.getStatus());
		return;
	}

	public Basket getBasket(long id) {
		String SQL = "select * from basket where id = ?";
		Basket basket = jdbc.queryForObject(SQL, new Object[] { id }, new BasketMapper());
		return basket;
	}

	public Basket getBasketForUser(long userId) {
		String SQL = "select * from basket where complete = 0 and app_user_id = ?";
		Basket basket = jdbc.queryForObject(SQL, new Object[] { userId }, new BasketMapper());
		return basket;
	}

	public void deleteBasket(long id) {
		String SQL = "delete from basket where id = ?";
		int result = jdbc.update(SQL, id);
		if (result == 0) {
			throw new EmptyResultDataAccessException(1);
		}
		log.debug("Deleted Record with ID = " + id);
		return;
	}

	public void deleteBasketForUser(long userId) {
		String SQL = "delete from basket where complete = 0 and app_user_id = ?";
		int result = jdbc.update(SQL, userId);
		if (result == 0) {
			throw new EmptyResultDataAccessException(1);
		}
		log.debug("Deleted Records with User ID = " + userId);
		return;
	}

	public Basket updateBasket(Basket basket) {
		String SQL = "update basket set app_user_id = ?, status = ?, complete = ? where id = ?";
		jdbc.update(SQL, basket.getUserId(), basket.getStatus(), basket.isComplete(), basket.getId());
		basket = getBasket(basket.getId());
		log.debug("Updated Record with ID = " + basket.getId());
		return basket;
	}

}