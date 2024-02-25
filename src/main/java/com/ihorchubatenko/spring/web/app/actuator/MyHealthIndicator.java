package com.ihorchubatenko.spring.web.app.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class MyHealthIndicator implements HealthIndicator {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            boolean isDbUp = check();
            connection.prepareCall("SELECT * FROM USERS");

            if (isDbUp) {
                return Health.up()
                        .withDetail("overall-status", "UP")
                        .withDetail("db-status", "UP")
                        .build();
            } else {
                return Health.down()
                        .withDetail("overall-status", "DOWN")
                        .withDetail("db-status", "DOWN")
                        .build();
            }

        } catch (SQLException e) {
            return Health.down()
                    .withDetail("overall-status", "DOWN")
                    .withDetail("db-status", "DOWN")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }

    private boolean check() {
        try {
            int rowCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
            return rowCount >= 0;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }
}

