/*
 * Source Material (c) 2016 GCD
 * All rights reserved
 */
package co.grandcircus.movies.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import co.grandcircus.movies.exception.NotFoundException;
import co.grandcircus.movies.model.User;

/**
 * Responsibility: To provide access to all the users.
 */
@Repository
public class UserDaoImpl implements UserDao {
	
	private int nextId = 1;

	private List<User> users = new ArrayList<User>();

	// Constructor
	public UserDaoImpl() {
		
		users.add(new User(1, "Ulysses", "Grant", "ulysses@example.com", "union"));
		users.add(new User(2, "Mark", "Twain", "mark@example.com", "sawyer"));
		users.add(new User(3, "Lame", "Duck", "lame@example.com", "quack"));
		
		for (User user : users) {
			assignId(user);
		}
	}
	
	/**
	 * Get all the users from the array list... every single one.
	 */
	@Override
	public List<User> getAllUsers() {
		return users;
	}
	
	@Override
	public List<User> getAllUsersSortedBy(String sortOrder) throws IllegalArgumentException {
		List<User> sorted = new ArrayList<>(users);
		switch (sortOrder) {
		case "firstName":
			Collections.sort(sorted, new Comparator<User>() {
				@Override
				public int compare(User o1, User o2) {
					return o1.getFirstName().compareTo(o2.getFirstName());
				}
			});
			return sorted;
		case "lastName":
			Collections.sort(sorted, new Comparator<User>() {
				@Override
				public int compare(User o1, User o2) {
					return o1.getLastName().compareTo(o2.getLastName());
				}
			});
			return sorted;
		case "email":
			Collections.sort(sorted, new Comparator<User>() {
				@Override
				public int compare(User o1, User o2) {
					return o1.getEmail().compareTo(o2.getEmail());
				}
			});
			return sorted;
		default:
			throw new IllegalArgumentException("users sortOrder cannot be " + sortOrder);	
		}
	}

	@Override
	public User getUser(int id) throws NotFoundException {
		int index = findUser(id);
		return users.get(index);
	}
	
	
	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		for (User user : users) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return user;
			}
		}
		throw new NotFoundException("No user " + email + " with given password");
	}

	@Override
	public int addUser(User user) {
		assignId(user);
		users.add(user);
		return user.getId();
	}

	@Override
	public void updateUser(int id, User user) throws NotFoundException {
		int index = findUser(id);
		user.setId(id);
		users.set(index, user);
	}
	
	@Override
	public void deleteUser(int id) throws NotFoundException {
		int index = findUser(id);
		users.remove(index);
	}
	
	/**
	 * Returns the list index of the user with the given ID.
	 * 
	 * @return zero-based index
	 * @throws NotFoundException if no user found with given ID
	 */
	private int findUser(int id) throws NotFoundException {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == id) {
				return i;
			}
		}
		throw new NotFoundException("No user " + id);
	}
	
	private void assignId(User user) {
		user.setId(nextId++);
	}
	
	
}
