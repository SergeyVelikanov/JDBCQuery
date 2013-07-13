package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

class JDBCQuery {

	private final static String connector = "jdbc:mysql://localhost/DB_name?user=DB_userName&password=DB_UserPassword";

	public static void main(String args[]) {
		try {
			// Load DB driver
			Class.forName("com.mysql.jdbc.Driver");

			// Set up the connection to DB
			Connection connection = DriverManager.getConnection(connector);

			// Print warnings
			for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn
					.getNextWarning()) {
				System.out.println("SQL Warning:");
				System.out.println("State  : " + warn.getSQLState());
				System.out.println("Message: " + warn.getMessage());
				System.out.println("Error  : " + warn.getErrorCode());
			}

			// Get a statement from the connection
			Statement statement1 = connection.createStatement();
			Statement statement2 = connection.createStatement();

			// Execute query
			ResultSet resultSet1 = statement1.executeQuery("SELECT EQUIPOS.nombre as nomEq,"
					        + "JUGADORES.nombre as nomJug,"
							+ "apellido ,numero FROM EQUIPOS INNER JOIN "
							+ "JUGADORES ON EQUIPOS.id=JUGADORES.idEq ORDER BY nomEq,nomJug");
			ResultSetMetaData resultSetMetaData = resultSet1.getMetaData();
			int column = resultSetMetaData.getColumnCount();
			System.out.println(column);
			resultSet1.next();// put the pointer "resultSet1" to the first query record
			System.out.println(resultSet1.getString(1));
			ResultSet resultSet2 = statement2.executeQuery("SELECT id,pais,nomLiga FROM LIGA");
			resultSet2.next();// put the pointer "resultSet2" to the first query record
			System.out.println(resultSet2.getString("pais"));
			// Rows iteration
			while (resultSet1.next()) {
				int i = 1;
				// Columns iteration
				while (i <= column) {
					System.out.print(resultSet1.getString(i) + " ");
					i++;
				}
				System.out.println();
			}

			// Close the result set, statement and the connection
			resultSet1.close();
			statement1.close();
			connection.close();
		} catch (SQLException sqlException) {
			System.out.println("SQL Exception:");

			// SQL Exceptions iteration
			while (sqlException != null) {
				System.out.println("State  : " + sqlException.getSQLState());
				System.out.println("Message: " + sqlException.getMessage());
				System.out.println("Error  : " + sqlException.getErrorCode());

				sqlException = sqlException.getNextException();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}