package com.kamila.food.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DataSource dataSource;

	private Connection connection;

	public void clearTables() {
		try (Connection connection = dataSource.getConnection()) {
			this.connection = connection;

			checkTestDatabase();
			tryToClearTables();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.connection = null;
		}
	}

	private void checkTestDatabase() throws SQLException {
		String catalog = connection.getCatalog();
		// Verifica se o nome do banco termina com "test" por motivo de segurança
		if (catalog == null || !catalog.endsWith("test")) {
			throw new RuntimeException("Cannot clear database tables because '" + catalog
					+ "' is not a test database (suffix 'test' not found).");
		}
	}

	private void tryToClearTables() throws SQLException {
		List<String> tableNames = getTableNames();
		clear(tableNames);
	}

	private List<String> getTableNames() throws SQLException {
		List<String> tableNames = new ArrayList<>();

		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet rs = metaData.getTables(connection.getCatalog(), null, null, new String[] { "TABLE" });

		while (rs.next()) {
			tableNames.add(rs.getString("TABLE_NAME"));
		}

		// Não remove dados de tabela de histórico do Flyway, possivelmente ocorreria erro ao executar migrações
		tableNames.remove("flyway_schema_history");

		return tableNames;
	}

	private void clear(List<String> tableNames) throws SQLException {
		Statement statement = buildSqlStatement(tableNames);

		logger.debug("Executing SQL");
		statement.executeBatch();
	}

	private Statement buildSqlStatement(List<String> tableNames) throws SQLException {
		Statement statement = connection.createStatement();

		statement.addBatch(sql("SET FOREIGN_KEY_CHECKS = 0")); // Para Postgresql comente
		addTruncateStatements(tableNames, statement);
		statement.addBatch(sql("SET FOREIGN_KEY_CHECKS = 1")); // Para Postgresql comente

		return statement;
	}

	private void addTruncateStatements(List<String> tableNames, Statement statement) {
		tableNames.forEach(tableName -> {
			try {
				statement.addBatch(sql("TRUNCATE TABLE " + tableName));
				// Para Postgresql troque por: statement.addBatch(sql("TRUNCATE TABLE " + tableName + " RESTART IDENTITY CASCADE"));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}

	private String sql(String sql) {
		logger.debug("Adding SQL: {}", sql);
		return sql;
	}

}