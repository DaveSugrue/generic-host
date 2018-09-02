package ie.sugrue.repository;

import java.util.ArrayList;

import ie.sugrue.domain.Category;

public interface CategoryRepository {
	/**
	 * This is the method to be used to initialize database resources ie. connection.
	 */
	// public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to create a record in the user table.
	 */
	public void createCategory(Category category);

	/**
	 * This is the method to be used to list down a record from the user table corresponding to a passed user id.
	 */
	public Category getCategory(String id);

	/**
	 * This is the method to be used to list down all the records from the user table.
	 */
	public ArrayList<Category> getCategories();

	/**
	 * This is the method to be used to delete a record from the user table corresponding to a passed student id.
	 */
	public void deleteCategory(String id);

	/**
	 * This is the method to be used to update a record into the user table.
	 */
	public Category updateCategory(Category category);
}