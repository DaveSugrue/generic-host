package ie.sugrue.repository;

import java.util.ArrayList;

import ie.sugrue.domain.User;

public interface UserRepository {

	/**
	 * This is the method to be used to create a User in the user table.
	 */
	public void createUser(User user);

	/**
	 * This is the method to be used to retrieve a single User based on Id.
	 */
	public User getUser(long id);

	/**
	 * This is the method to be used to retrieve a single User based on Email address.
	 */
	public User getUser(String email);

	/**
	 * This is the method to be used to list down all the records from the user table.
	 */
	public ArrayList<User> getUsers();

	/**
	 * This is the method to be used to delete a record from the user table corresponding to a passed user id.
	 */
	public void deleteUser(long id);

	/**
	 * This is the method to be used to delete a record from the user table corresponding to a passed email address.
	 */
	public void deleteUser(String email);

	/**
	 * This is the method to be used to update a record on the user table.
	 */
	public User updateUser(User user);
}