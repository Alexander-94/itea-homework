package ua.itea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBWorker {
	private static final String HOST = "jdbc:mysql://localhost/";
	private static final String DB_NAME = "busdb";
	private static final String USER = "root";
	private static final String PASS = "";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private final static String GET_ALL_CITIES = "SELECT * FROM CITIES ORDER BY ID";
	private final static String GET_ALL_CITIES_CNT = "SELECT COUNT(*) FROM CITIES";
	private Connection conn;
	private List<City> cities;
	private int citiesCnt;

	public DBWorker() {
		cities = new ArrayList<City>();
		try {
			Class.forName(DRIVER).newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			conn = DriverManager.getConnection(HOST + DB_NAME + "?" + "user=" + USER + "&" + "password=" + PASS);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return conn;
	}

	public List<City> getAllCities() {
		Statement stmt = null;
		ResultSet rs = null;
		cities = new ArrayList<City>();
		City city = null;
		Connection conn = getConnection();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(GET_ALL_CITIES);
			while (rs.next()) {
				city = new City(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
						rs.getInt("ridetime"), rs.getInt("stoptime"));
				cities.add(city);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cities;
	}

	public int getAllCitiesCnt() {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(GET_ALL_CITIES_CNT);
			while (rs.next()) {
				citiesCnt = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return citiesCnt;
	}
}
