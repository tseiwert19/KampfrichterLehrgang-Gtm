package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.main.DatenbankController;

/**
 * Testklasse fuer VideoDbController
 * @author michael
 *
 */
public class DatenbankCotrollerTest {
	private DatenbankController controller;
	/**
	 *Vor jedem Test wird ein Controller erstellt und es werden 2 Datensaetze in die Datenbank eingefuegt
	 */
	@Before
	public void init(){
		controller = new DatenbankController();
		//Falls schon Datensaetze vor dem Test vorhanden sind
		controller.deleteVideo(1);
	    controller.deleteVideo(2);
		controller.addVideo(1, "Salto", "/videos/salto", "boden" ,"salto", "3", "Boden");
		controller.addVideo(2, "Rolle", "/videos/rolle", "boden", "rolle", "1", "Boden");
		
	}
	/**
	 * Nach jedem Test werden die Datensaetze geloescht
	 */
	 @After
	 public void clear(){
	     controller.deleteVideo(1);
	     controller.deleteVideo(2);
	 }
	/**
	 * Prueft ob mit der Methode getEntry() die initialisierten Datensaetze abgerufen werden können
	 * @throws SQLException
	 */
	@Test
	public void testeGetEntry() throws SQLException {
		ResultSet salto = controller.getEntry(1);
		ResultSet rolle = controller.getEntry(2);
		assertEquals(salto.getString("name"), "Salto");
		assertEquals(rolle.getString("name"), "Rolle");
		salto.close();
		rolle.close();
	}
	/**
	 * Prueft ob die Methode getAllEntries alle Datensaetze liefert
	 * @throws SQLException
	 */
	@Test
	public void testeGetAllEntries() throws SQLException{
		ResultSet alleVideos = controller.getAllEntries();
		ArrayList<String> namen = new ArrayList<String>();
		while(alleVideos.next()){
			namen.add(alleVideos.getString("name"));
		}
		assertTrue(namen.contains("Salto"));
		assertTrue(namen.contains("Rolle"));
		alleVideos.close();
		
	}
	
}
