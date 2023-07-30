package edu.school21.tank.app;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.sql.DataSource;

import org.postgresql.core.SqlCommand;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnection {
    Connection connection = null;
    String urlConnection;
    String user;
    String password;

    public DBConnection(String url, String u, String pw) {
        this.urlConnection = url;
        this.user = u;
        this.password = pw;
    }

    public void connect() {
        try {
            connection = createDataSource().getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void process(String sqlUrl) {
        Reader inputstream = new InputStreamReader(DBConnection.class.getClassLoader().getResourceAsStream(sqlUrl));
        Scanner input = new Scanner(inputstream).useDelimiter(";");
        try {
            Statement statement = connection.createStatement();
            while (input.hasNext()) {
                statement.execute(input.next());
            }
            System.out.printf("%s successful executed\n", sqlUrl);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        input.close();
    }

    private DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(urlConnection);
        config.setUsername(user);
        config.setPassword(password);

        return (new HikariDataSource(config));
    }

    public Connection getConnection() {
        return connection;
    }
}
