package edu.Roma42.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import edu.Roma42.models.Product;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {

    private ProductsRepository productsRepository;
	final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
		new Product(1L, "Product 1", 10.99),
		new Product(2L, "Product 2", 20.49),
		new Product(3L, "Product 3", 5.99),
		new Product(4L, "Product 4", 15.75),
		new Product(5L, "Product 5", 8.50));
	final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "Product 1", 10.99);
	final Product EXPECTED_UPDATED_PRODUCT = new Product(1L, "UPDATEDDD", 10.99);

    @BeforeEach
    public void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        this.productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    public void testSaveAndGetAllProducts() {
        Product newProduct = new Product(10L, "Test Product", 100.0);

        this.productsRepository.save(newProduct);

        List<Product> products = productsRepository.findAll();
		Product expected_Prod = this.productsRepository.findById(10L).get();

        assertEquals(EXPECTED_FIND_ALL_PRODUCTS.size() + 1, products.size());
        assertEquals(newProduct.toString(), expected_Prod.toString());
        assertEquals(products.get(products.size() - 1).toString(), expected_Prod.toString());
    }

	@Test
    public void testFindByIdAndUpdate() {

		Product expected_Prod = this.productsRepository.findById(1L).get();

		this.productsRepository.update(new Product(1L, "UPDATEDDD", 10.99));

		Product updated_Prod = this.productsRepository.findById(1L).get();

        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT.toString(), expected_Prod.toString());
        assertEquals(EXPECTED_UPDATED_PRODUCT.toString(), updated_Prod.toString());
    }
}
