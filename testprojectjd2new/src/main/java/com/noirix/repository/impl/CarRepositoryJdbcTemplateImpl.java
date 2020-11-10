package com.noirix.repository.impl;

import com.noirix.domain.Car;
import com.noirix.repository.CarColumns;
import com.noirix.repository.CarRepository;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//@Slf4j
@Repository
@Primary
public class CarRepositoryJdbcTemplateImpl implements CarRepository {

    private static final Logger log = Logger.getLogger(CarRepositoryJdbcTemplateImpl.class);

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CarRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Car> search(String query) {
        log.info("invoking search method");
        log.info(query);
        return jdbcTemplate.query("select * from m_cars where model like ?", new Object[]{query}, this::getCarRowMapper);
    }

    @Override
    public Car save(Car entity) {
        final String createQuery = "insert into m_cars (model, creation_year, user_id, price, color) " +
                "values (:model, :creationYear, :userId, :price, :color);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("model", entity.getModel());
        params.addValue("creationYear", entity.getCreationYear());
        params.addValue("userId", entity.getUserId());
        params.addValue("price", entity.getPrice());
        params.addValue("color", entity.getColor());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdCarId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(createdCarId);
    }

    @Override
    public List<Car> findAll() {
        return jdbcTemplate.query("select * from m_cars", this::getCarRowMapper);
    }

    private Car getCarRowMapper(ResultSet rs, int i) throws SQLException {
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

//        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
//        mapSqlParameterSource.addValue("userId", key);
//
//        return namedParameterJdbcTemplate.queryForObject("select * from m_users where id = :userId", mapSqlParameterSource, this::getUserRowMapper);

        return jdbcTemplate.queryForObject("select * from m_cars where id = ?", new Object[]{key}, this::getCarRowMapper);
    }

    @Override
    public Optional<Car> findOne(Long key) {
        return Optional.empty();
    }

    @Override
    public Car update(Car object) {
        return null;
    }

    @Override
    public Long delete(Car object) {
        return null;
    }
}
