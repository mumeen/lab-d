package co.grandcircus.movies.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import co.grandcircus.movies.exception.NotFoundException;
import co.grandcircus.movies.model.User;
@Repository
@Primary
public class UserDaoJdbcImpl implements UserDao {
	@Autowired
	JdbcConnectionFactory connectionFactory;

	@Override
	public List<User> getAllUsers() {
		String sql = "SELECT * FROM labd.users";
		try (Connection connection = connectionFactory.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql)) {

			List<User> users = new ArrayList<User>();
			while (result.next()) {
				Integer id = result.getInt("id");
				String firstName = result.getString("firstName");
				String lastName = result.getString("lastName");
				String email = result.getString("email");
				String password = result.getString("password");

				users.add(new User(id, firstName, lastName, email, password));
			}

			return users;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	@Override
	public List<User> getAllUsersSortedBy(String sort) {
		String sql = "SELECT * FROM users order by" + sort;
		try (Connection connection = connectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, sort);
			ResultSet result = statement.executeQuery();

			List<User> users = new ArrayList<User>();
			while (result.next()) {
				Integer id = result.getInt("id");
				String firstName = result.getString("firstName");
				String lastName = result.getString("lastName");
				String email = result.getString("email");
				String password = result.getString("password");

				users.add(new User(id, firstName, lastName, email, password));
			}

			return users;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	@Override
	public User getUser(int id) throws NotFoundException {
	String sql = "SELECT * FROM labd WHERE id = ?";
	try (Connection connection = connectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql)) {
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			String firstName = result.getString("firstName");
			String lastName = result.getString("lastName");
			String email = result.getString("email");
			String password = result.getString("password");

			return new User(id, firstName, lastName, email, password);
		} else {
			throw new NotFoundException("No such movie.");
		}
	} catch (SQLException ex) {
		throw new RuntimeException(ex);
	}
}


@Override
public int addUser(User user) {
	String sql = "INSERT INTO Labd (firstName, lastName, email, password) VALUES (?, ?, ?, ?)";
	try (Connection connection = connectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

		statement.setString(1, user.getFirstName());
		statement.setString(2, user.getLastName());
		statement.setString(3, user.getEmail());
		statement.setString(4, user.getPassword());

		int affectedRows = statement.executeUpdate();
		if (affectedRows == 0) {
			throw new SQLException("Creating user failed, no rows affected.");
		}

		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				user.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
		}

		return user.getId();
	} catch (SQLException ex) {
		throw new RuntimeException(ex);
	}
}
@Override
public void updateUser(int id, User user) throws NotFoundException {
	String sql = "UPDATE Labd SET firstName = ?, lastName = ?, email = ?, password = ? WHERE id = ?";
	try (Connection conn = connectionFactory.getConnection();
			PreparedStatement statement = conn
					.prepareStatement(sql)) {
		statement.setString(1, user.getFirstName());
		statement.setString(2, user.getLastName());
		statement.setString(3, user.getEmail());
		statement.setString(4, user.getPassword());
		statement.setInt(5, user.getId());

		int rowsUpdated = statement.executeUpdate();
		if (rowsUpdated != 1) {
			throw new NotFoundException("No such user");
		}
	} catch (SQLException ex) {
		throw new RuntimeException(ex);
	}
}
@Override
public void deleteUser(int id) throws NotFoundException {
	String sql = "DELETE FROM Labd WHERE id = ?";
	try (Connection conn = connectionFactory.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql)) {
		statement.setInt(1, id);

		int rowsUpdated = statement.executeUpdate();
		if (rowsUpdated != 1) {
			throw new NotFoundException("No such user");
		}
	} catch (SQLException ex) {
		throw new RuntimeException(ex);
	}
}

	



//	@Override
	//public List<User> getAllUsersSortedBy(String sortOrder) throws IllegalArgumentException {
		// TODO Auto-generated method stub
	//	return null;
	//}

//	@Override
//	public User getUser(int id) throws NotFoundException {
		// TODO Auto-generated method stub
	//	return null;
//	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}



	//@Override
//	public int addUser(User user) {
		// TODO Auto-generated method stub
	//	return 0;
	//}

	//@Override
//	public void updateUser(int id, User user) throws NotFoundException {
		// TODO Auto-generated method stub
		
	//}

	
		
	}


