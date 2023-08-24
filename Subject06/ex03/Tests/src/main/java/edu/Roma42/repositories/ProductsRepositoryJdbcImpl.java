package edu.Roma42.repositories;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import edu.Roma42.models.Product;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

	private Connection connection;

	public ProductsRepositoryJdbcImpl(DataSource dataSource) {
		try {
			this.connection = dataSource.getConnection();
		} catch (Exception e) {
			System.out.println("error during connection");
		}
	}

	@Override
	public List<Product> findAll() {
		List<Product> products = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM numbers.product");
			ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Product product = mapProduct(resultSet);
				products.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	public void save(Product product) {

		String query = "INSERT INTO numbers.product (identifier, name, price) VALUES (?, ?, ?)";

		try (PreparedStatement ps = this.connection.prepareStatement(query)) {

			ps.setLong(1, product.getId());
			ps.setString(2, product.getName());
			ps.setDouble(3, product.getPrice());
			ps.executeUpdate();

		} catch (SQLException ex) {
		}
	}

	@Override
    public void update(Product product) {

        String query = "UPDATE numbers.product SET " + "name = ?, price = ? WHERE identifier = ?";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);

			ps.setString(1, product.getName());
			ps.setDouble(2, product.getPrice());
			ps.setLong(3, product.getId());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("No rows matched the update criteria");
            }
        } catch (SQLException ex) {
        }
    }

	@Override
	public void delete(Long id) {
		String query = "DELETE FROM product WHERE identifier = ?";

		try {

			PreparedStatement ps = connection.prepareStatement(query);

			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Optional<Product> findById(Long id) {

		String query = "SELECT * FROM numbers.product WHERE identifier = ?";

		try {

		PreparedStatement ps = this.connection.prepareStatement(query);

		ps.setLong(1, id);

		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				Product product = mapProduct(rs);
				return (Optional.of(product));
			}
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Optional.empty());
	}


	private Product mapProduct(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("identifier");
		String name = resultSet.getString("name");
		double price = resultSet.getDouble("price");

		return new Product(id, name, price);
	}
}
