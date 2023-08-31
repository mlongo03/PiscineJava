package edu.Roma42.app;

import edu.Roma42.classes.User;
import edu.Roma42.managers.OrmManager;
import javax.sql.ConnectionEvent;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;
import java.sql.Connection;

public class Program {
	public static void main(String[] args) {
		User mlongo = new User(0L, "manuele", "longo", 20);
		User zio = new User(0L, "zio", "bo", 44);
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("alessiolongo");
        config.setPassword("test");

        HikariDataSource dataSource = new HikariDataSource(config);
		OrmManager manager = new OrmManager(dataSource);
		manager.createTable(mlongo.getClass());
		manager.save(mlongo);
		manager.save(zio);
		mlongo.setFirstName("fcarlucc");
		manager.update(mlongo);
		System.out.println(manager.findById(1L, User.class));
		System.out.println(manager.findById(2L, User.class));
	}
}
