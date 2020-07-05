package ua.itea;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DBWorkerTest {
	
	private static final String DB_NAME = "busdb";
	private static final String USER = "root";
	private static final int CITIES_COUNT = 9;
	private static final String THIRD_CITY_NAME = "Kayanza";
	
	@Spy
	DBWorker dbWorker;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testDBName() {		
		assertTrue(Objects.equals(DBWorker.getDbName(), DB_NAME));
	}	
	
	@Test
	public void testDBUser() {		
		assertTrue(Objects.equals(DBWorker.getUser(), USER));
	}
	
	@Test
	public void testCitiesCount() {		
		assertTrue(Objects.equals(dbWorker.getAllCitiesCnt(), CITIES_COUNT));
	}
	
	@Test
	public void testThirdCityName() {		
		List<City> cities = dbWorker.getAllCities();
		assertTrue(Objects.equals(cities.get(2).getName(), THIRD_CITY_NAME));
	}
}
