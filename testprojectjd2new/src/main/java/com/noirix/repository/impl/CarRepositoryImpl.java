package com.noirix.repository.impl;

import com.noirix.domain.Car;

import com.noirix.exception.EntityNotFoundException;
import com.noirix.repository.CarColumns;
import com.noirix.repository.CarRepository;
import com.noirix.util.DatabasePropertiesReader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.noirix.util.DatabasePropertiesReader.*;
import static com.noirix.util.DatabasePropertiesReader.DATABASE_PASSWORD;

@Repository
public class CarRepositoryImpl implements CarRepository {

    private static final org.apache.log4j.Logger log = Logger.getLogger(CarRepositoryImpl.class);

    public static final DatabasePropertiesReader reader = DatabasePropertiesReader.getInstance();

    @Override
    public List<Car> search(String query) {
        return null;
    }

    @Override
    public Car save(Car car) {
        final String findByIdQuery = "insert into m_cars (model, creation_year, user_id, price, color) " +
                "values (?,?,?,?,?)";

        Connection connection;
        PreparedStatement statement;

        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            log.error("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        try {
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL), reader.getProperty(DATABASE_LOGIN), reader.getProperty(DATABASE_PASSWORD));
            statement = connection.prepareStatement(findByIdQuery);
            PreparedStatement lastInsertId = connection.prepareStatement("SELECT currval('m_cars_id_seq') as last_insert_id;");

            statement.setString(1, car.getModel());
            statement.setLong(2, car.getCreationYear());
            statement.setLong(3, car.getUserId());
            statement.setDouble(4, car.getPrice());
            statement.setString(5, car.getColor());

            statement.executeUpdate();

            Long insertedId;
            ResultSet lastIdResultSet = lastInsertId.executeQuery();
            if (lastIdResultSet.next()) {
                insertedId = lastIdResultSet.getLong("last_insert_id");
            } else {
                throw new RuntimeException("We cannot read sequence last value during Car creation!");
            }

            return findById(insertedId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public List<Car> findAll() {
        final String findAllQuery = "select * from m_cars order by id";

        List<Car> result = new ArrayList<>();

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        try {
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL), reader.getProperty(DATABASE_LOGIN), reader.getProperty(DATABASE_PASSWORD));
            statement = connection.createStatement();
            rs = statement.executeQuery(findAllQuery);

            while (rs.next()) {
                result.add(parseResultSet(rs));
            }

            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    private Car parseResultSet(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getLong(CarColumns.ID));
        car.setModel(rs.getString(CarColumns.MODEL));
        car.setCreationYear(rs.getLong(CarColumns.CREATION_YEAR));
        car.setUserId(rs.getLong(CarColumns.USER_ID));
        car.setPrice(rs.getDouble(CarColumns.PRICE));
        car.setColor(rs.getString(CarColumns.COLOR));
        return car;
    }

    @Override
    public Car findById(Long key) {
        final String findByIdQuery = "select * from m_cars where id = ?";

        Connection connection;
        PreparedStatement statement;
        ResultSet rs;

        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        try {
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL), reader.getProperty(DATABASE_LOGIN), reader.getProperty(DATABASE_PASSWORD));
            statement = connection.prepareStatement(findByIdQuery);
            statement.setLong(1, key);

            rs = statement.executeQuery();

            if (rs.next()) {
                return parseResultSet(rs);
            } else {
                throw new EntityNotFoundException("Car with ID:" + key + "not found");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Optional<Car> findOne(Long key) {
        return Optional.of(findById(key));
    }

    @Override
    public Car update(Car car) {
        final String findByIdQuery = "update m_cars " +
                "set " +
                "model = ?,  " +
                "creation_year = ?,  " +
                "user_id = ?,  " +
                "price = ?,  " +
                "color = ?,  " +
                "where id = ?";

        Connection connection;
        PreparedStatement statement;

        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        try {
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL), reader.getProperty(DATABASE_LOGIN), reader.getProperty(DATABASE_PASSWORD));
            statement = connection.prepareStatement(findByIdQuery);

            statement.setString(1, car.getModel());
            statement.setLong(2, car.getCreationYear());
            statement.setLong(3, car.getUserId());
            statement.setDouble(4, car.getPrice());
            statement.setString(5, car.getColor());

            statement.executeUpdate();
            return findById(car.getId());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Long delete(Car car) {
        final String findByIdQuery = "delete from m_cars where id = ?";

        Connection connection;
        PreparedStatement statement;

        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        try {
            connection = DriverManager.getConnection(reader.getProperty(DATABASE_URL), reader.getProperty(DATABASE_LOGIN), reader.getProperty(DATABASE_PASSWORD));
            statement = connection.prepareStatement(findByIdQuery);
            statement.setLong(1, car.getId());

            int deletedRows = statement.executeUpdate();
            return (long) deletedRows;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }
}