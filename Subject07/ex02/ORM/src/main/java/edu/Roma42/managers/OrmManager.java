package edu.Roma42.managers;

import edu.Roma42.Exceptions.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import edu.Roma42.annotations.OrmColumn;
import edu.Roma42.annotations.OrmColumnId;
import edu.Roma42.annotations.OrmEntity;
import javax.sql.ConnectionEvent;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class OrmManager {

    Connection connection;
    String schema = "orm";

    public OrmManager(HikariDataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (Exception e) {
            System.out.println("error during connection " + e.getMessage());
        }
        try {
            String query = "DROP SCHEMA IF EXISTS " + schema + " CASCADE";
            System.out.println(query);
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error during dropping " + e.getMessage());
        }
        try {
            String query = "CREATE SCHEMA IF NOT EXISTS " + schema;
            System.out.println(query);
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error during creation " + e.getMessage());
        }
    }

    public void createTable(Class<?> entityClass) {
        OrmEntity annotation = entityClass.getAnnotation(OrmEntity.class);

        if (annotation != null) {
            String table = annotation.table();
            String query = "CREATE TABLE IF NOT EXISTS " + this.schema + "." + table + " (";
            Field[] fields = entityClass.getDeclaredFields();
            String name = null;
            String type = null;
            int totAnnotatedFileds = 0;
            int i = 0;

            for (Field field : fields) {
                if (field.isAnnotationPresent(OrmColumn.class) || field.isAnnotationPresent(OrmColumnId.class)) {
                    totAnnotatedFileds++;
                }
            }
            for (Field field : fields) {
                if (field.isAnnotationPresent(OrmColumn.class) || field.isAnnotationPresent(OrmColumnId.class)) {
                    i++;
                    OrmColumn ormColumnAnnotation = field.getAnnotation(OrmColumn.class);
                    if (ormColumnAnnotation != null) {
                        type = field.getType().getName();
                        name = ormColumnAnnotation.name();
                        if (type.equals("java.lang.String")) {
                            int length = ormColumnAnnotation.length();
                            if (length == -1) {
                                type = "text";
                            }
                            else {
                                type = "varchar(" + length + ")";
                            }
                        }
                        if (type.equals("java.lang.Integer")) {
                            type = "INT";
                        }
                        if (type.equals("java.lang.Double")) {
                            type = "DOUBLE PRECISION";
                        }
                        if (type.equals("java.lang.Boolean")) {
                            type = "BOOLEAN";
                        }
                        if (type.equals("java.lang.Long")) {
                            type = "BIGINT";
                        }
                    }
                    OrmColumnId ormColumnIdAnnotation = field.getAnnotation(OrmColumnId.class);
                    if (ormColumnIdAnnotation != null) {
                        name = "id";
                        type = "BIGSERIAL NOT NULL PRIMARY KEY";
                    }
                    if (i < totAnnotatedFileds) {
                        query += name + " " + type + ", ";
                    }
                    else {
                        query += name + " " + type + ")";
                    }
                }
            }
            try {
                System.out.println(query);
                PreparedStatement ps = this.connection.prepareStatement(query);
                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("error during creation of table " + e.getMessage());
            }
        }
    }

    public void save(Object entity) {
        Class<?> entityClass = entity.getClass();
        OrmEntity ormEntityAnnotation = entityClass.getAnnotation(OrmEntity.class);

        if (ormEntityAnnotation != null) {
            String tableName = ormEntityAnnotation.table();
            Field[] fields = entityClass.getDeclaredFields();
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Field field : fields) {
                if (field.isAnnotationPresent(OrmColumn.class) || field.isAnnotationPresent(OrmColumnId.class)) {
                    OrmColumn ormColumnAnnotation = field.getAnnotation(OrmColumn.class);
                    if (ormColumnAnnotation != null) {

                        String columnName = ormColumnAnnotation.name();
                        Object columnValue = null;

                        try {
                            field.setAccessible(true);
                            columnValue = field.get(entity);
                        } catch (IllegalAccessException e) {
                            System.out.println("Error accessing field: " + e.getMessage());
                        }

                        if (columnValue != null) {
                            columns.append(columnName).append(", ");
                            values.append("'").append(columnValue).append("', ");
                        }
                    }
                }
            }

            if (columns.length() > 0 && values.length() > 0) {
                columns.setLength(columns.length() - 2);
                values.setLength(values.length() - 2);

                String query = "INSERT INTO " + schema + "." + tableName + " (" + columns + ") VALUES (" + values + ")";

                try {
                    System.out.println(query);
                    PreparedStatement ps = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.executeUpdate();
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            fields = entityClass.getDeclaredFields();
                            for (Field field : fields) {
                                OrmColumnId ormColumnIdAnnotation = field.getAnnotation(OrmColumnId.class);
                                if (ormColumnIdAnnotation != null) {
                                    field.setAccessible(true);
                                    field.setLong(entity, generatedKeys.getLong(1));
                                }
                            }
                        } else {
                            throw new SQLException("Creating player failed, no ID obtained.");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error during entity save: " + e.getMessage());
                }
            }
        }
    }

    public void update(Object entity) {
        Class<?> entityClass = entity.getClass();
        OrmEntity ormEntityAnnotation = entityClass.getAnnotation(OrmEntity.class);

        if (ormEntityAnnotation != null) {
            String tableName = ormEntityAnnotation.table();
            Field[] fields = entityClass.getDeclaredFields();
            StringBuilder setClause = new StringBuilder();
            StringBuilder whereClause = new StringBuilder();

            for (Field field : fields) {
                if (field.isAnnotationPresent(OrmColumn.class) || field.isAnnotationPresent(OrmColumnId.class)) {
                    OrmColumn ormColumnAnnotation = field.getAnnotation(OrmColumn.class);
                    if (ormColumnAnnotation != null) {

                        String columnName = ormColumnAnnotation.name();
                        Object columnValue = null;

                        try {
                            field.setAccessible(true);
                            columnValue = field.get(entity);
                        } catch (IllegalAccessException e) {
                            System.out.println("Error accessing field: " + e.getMessage());
                        }

                        if (columnValue != null) {
                            setClause.append(columnName).append(" = '").append(columnValue).append("', ");
                        }
                    }
                    OrmColumnId ormColumnIdAnnotation = field.getAnnotation(OrmColumnId.class);
                    if (ormColumnIdAnnotation != null) {
                        Object columnValue = null;
                        try {
                            field.setAccessible(true);
                            columnValue = field.get(entity);
                        } catch (IllegalAccessException e) {
                            System.out.println("Error accessing field: " + e.getMessage());
                        }

                        if (columnValue != null) {
                            whereClause.append("id").append(" = '").append(columnValue).append("'");
                        }
                    }
                }
            }

            if (setClause.length() > 0 && whereClause.length() > 0) {
                setClause.setLength(setClause.length() - 2);

                String query = "UPDATE " + schema + "." + tableName + " SET " + setClause + " WHERE " + whereClause;

                try {
                    System.out.println(query);
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Error during entity update: " + e.getMessage());
                }
            }
        }
    }

    public <T> T findById(Long id, Class<T> entityClass) {
        OrmEntity ormEntityAnnotation = entityClass.getAnnotation(OrmEntity.class);

        if (ormEntityAnnotation != null) {
            String tableName = ormEntityAnnotation.table();
            Field[] fields = entityClass.getDeclaredFields();
            StringBuilder whereClause = new StringBuilder();

            for (Field field : fields) {
                OrmColumnId ormColumnIdAnnotation = field.getAnnotation(OrmColumnId.class);
                if (ormColumnIdAnnotation != null) {
                    String columnName = "id";
                    whereClause.append(columnName).append(" = '").append(id).append("'");
                    break;
                }
            }

            if (whereClause.length() > 0) {
                String query = "SELECT * FROM " + schema + "." + tableName + " WHERE " + whereClause;

                try {
                    System.out.println(query);
                    PreparedStatement ps = connection.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        T entity = entityClass.getDeclaredConstructor().newInstance();
                        for (Field field : fields) {
                            OrmColumn ormColumnAnnotation = field.getAnnotation(OrmColumn.class);
                            if (ormColumnAnnotation != null) {
                                String columnName = ormColumnAnnotation.name();
                                Object columnValue = rs.getObject(columnName);
                                field.setAccessible(true);
                                field.set(entity, columnValue);
                            }
                            OrmColumnId ormColumnIdAnnotation = field.getAnnotation(OrmColumnId.class);
                            if (ormColumnIdAnnotation != null) {
                                String columnName = "id";
                                Object columnValue = rs.getObject(columnName);
                                field.setAccessible(true);
                                field.set(entity, columnValue);
                            }
                        }
                        return entity;
                    }
                } catch (Exception e) {
                    System.out.println("Error during entity retrieval: " + e.getMessage());
                }
            }
        }

        return null;
    }
    }
