package src.main;

import java.lang.NullPointerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import src.main.videoplayer.Video;

/**
 * Diese Klasse enthaelt alle Funktionen fuer die Datenbank
 * 
 * @author michael
 *
 */
public class DatenbankController

{
	// http://stackoverflow.com/questions/6499218/how-to-use-sqlite-database-inside-jar-file

	private Connection connection;
	// Pfad zur Datenbank
	// http://stackoverflow.com/questions/3209901/absolute-path-of-projects-folder-in-java
	// http://stackoverflow.com/questions/14739550/difference-between-getclass-getclassloader-getresource-and-getclass-getres
	private static final String DB_PATH = "/src/main/videoDb";

	/**
	 * Konstruktor Stellt die Verbindung zur Datenbank her und erstellt die
	 * Datenbank(Falls sie noch nicht existiert)
	 */
	public DatenbankController() {
		connectToDb();
		createTable();
	}

	public long ermittleAnzahlVideosInDatenbank() {

		long anzahlVideos = 0;

		ResultSet rs = findDatasets("SELECT count(*) FROM videos;");
		try {
			rs.next();
			anzahlVideos = rs.getLong(1);
			  rs.close();
			  		} catch (SQLException e) {
			e.printStackTrace();
		}
		return anzahlVideos;
	}

	/**
	 * Stellt eine Verbindung zur Datenbank her
	 */
	private void connectToDb() {
		String urlOfDatabaseFile = "";
		System.out.println("DatenbankController.java : " + DB_PATH);
		try {
			urlOfDatabaseFile = getClass().getResource(DB_PATH).toString();
		} catch (NullPointerException e) {
			System.err.println("Datenbank-Datei " + DB_PATH
					+ " nicht gefunden!");
			e.printStackTrace();
		}
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite::resource:"
					+ urlOfDatabaseFile);
		} catch (ClassNotFoundException e) {
			System.err.println("Fehler beim Laden des JDBC-Treibers");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Fehler bei Verbindung zur Datenbank!");
			e.printStackTrace();
		}

	}

	/**
	 * Erstellt die Tabelle Spalten: id, name, pfad, beschreibung,
	 * schwierigkeitsgrad, elementgruppe
	 */
	private void createTable() {
		connectToDb();
		try {
			Statement statement = connection.createStatement();
			statement
					.executeUpdate("CREATE TABLE IF NOT EXISTS videos (id INT PRIMARY KEY, "
							+ "name VARCHAR(50) NOT NULL,"
							+ "pfad VARCHAR(50) NOT NULL, "
							+ "geraet VARCHAR(20) NOT NULL, "
							+ "beschreibung VARCHAR(200), "
							+ "schwierigkeitsgrad VARCHAR(10),"
							+ "elementgruppe VARCHAR(5));");
			statement.close();
		} catch (SQLException e) {
			System.err.println("Fehler beim Erstellen der Datenbank!");
			e.printStackTrace();
		}
	}

	/**
	 * Fuegt ein Video in die Datenbank
	 * 
	 * @param id
	 *            = PrimaryKey
	 * @param name
	 *            = Name des Videos
	 * @param pfad
	 *            = Pfad zur Videodatei
	 * @param beschreibung
	 *            = Beschreibung zum Video
	 * @param schwierigkeitsgrad
	 *            = schwierigkeitsgradsgrad der Turnuebung
	 * @param elementgruppe
	 *            = elementgruppe des Videos
	 */
	public void addVideo(int id, String name, String pfad, String geraet,
			String beschreibung, String schwierigkeitsgrad, String elementgruppe) {

		connectToDb();
		PreparedStatement prepStatement;
		try {
			prepStatement = connection
					.prepareStatement("INSERT INTO videos VALUES ( ?, ?, ?, ?, ?, ?, ?)");

			prepStatement.setInt(1, id);
			prepStatement.setString(2, name);
			prepStatement.setString(3, pfad);
			prepStatement.setString(4, geraet);
			prepStatement.setString(5, beschreibung);
			prepStatement.setString(6, schwierigkeitsgrad);
			prepStatement.setString(7, elementgruppe);
			prepStatement.addBatch();

			connection.setAutoCommit(false);
			prepStatement.executeBatch();
			connection.setAutoCommit(true);

			prepStatement.close();
			connection.close();
		} catch (SQLException e) {
			System.err.println("Fehler beim Einfügen in die Datenbank!");
			e.printStackTrace();
		}
	}

	/**
	 * Liefert alle Eintraege aus der Datenbank
	 * 
	 * @return Alle Eintraege
	 * @throws SQLException
	 */
	public ResultSet getAllEntries() {
		return findDatasets("SELECT * FROM videos;");
	}
	/**
	 * Liefert alle Eintraege einer Ampelfarbe
	 * @param i 0=grün, 1 = gelb, 2 = rot
	 * @return Videos
	 */
	public ResultSet getAllByAmpel(int i){
		return findDatasets("SELECT * FROM videos WHERE ampel = " + i);
	}
	/**
	 * Liefert alle Eintraege aus der Datenbank, die dem uebergebenen geraet
	 * entsprechen
	 * 
	 * @param geraet
	 * @return Passende Eintraege
	 */
	public ResultSet getAllByGeraet(String geraet) {
		return findDatasets("SELECT * FROM videos WHERE geraet = '" + geraet
				+ "'");
	}

	/**
	 * Sucht alle Videos die den uebergebenen Teilstring enthalten. Die
	 * Ergebnisse werden nach Geraet sortiert.
	 * 
	 * @param name
	 *            Nach diesem Teilstring wird gesucht
	 * @return Liste der gefunden Videos
	 */
	public ResultSet getAllByName(String name) {
		return findDatasets("SELECT * FROM videos WHERE name LIKE '%" + name
				+ "%' ORDER BY geraet");
	}

	/**
	 * Liefert alle Eintraege aus der Datenbank die dem uergebenen Parametern
	 * entsprechen
	 * 
	 * @param geraet
	 * @param schwierigkeitsgrad
	 * @param elementgruppe
	 * @return Passende Eintraege
	 */
	public ResultSet getAllByGeraetSchwierigkeitsgradElementgruppe(
			String geraet, String schwierigkeitsgrad, String elementgruppe) {

		return findDatasets("SELECT * FROM videos WHERE geraet = '" + geraet
				+ "' AND schwierigkeitsgrad = '" + schwierigkeitsgrad
				+ "' AND elementgruppe = '" + elementgruppe + "'");
	}

	public ResultSet getAllByGeraetSchwierigkeitsgrad(String geraet,
			String schwierigkeitsgrad) {
		return findDatasets("SELECT * FROM videos WHERE geraet = '" + geraet
				+ "' AND schwierigkeitsgrad = '" + schwierigkeitsgrad + "'");
	}

	public ResultSet getAllByGeraetElementgruppe(String geraet,
			String elementgruppe) {
		return findDatasets("SELECT * FROM videos WHERE geraet = '" + geraet
				+ "'AND elementgruppe = '" + elementgruppe + "'");
	}

	/**
	 * Liefert alle Eintraege aus der Datenbank, die dem SQL-Statement
	 * entsprechen
	 * 
	 * @param sql
	 * @return
	 */
	private ResultSet findDatasets(String sql) {
		ResultSet alleVideos = null;
		connectToDb();
		try {
			alleVideos = connection.createStatement().executeQuery(sql);
		} catch (SQLException e) {
			System.err.println("Fehler bei Datenbankabfrage!");
			e.printStackTrace();
		}
		return alleVideos;
	}

	/**
	 * Loescht ein Video aus der Datenbank
	 * 
	 * @param id
	 *            Video mit dieser Id wird geloescht
	 */
	public void deleteVideo(int id) {
		PreparedStatement statement = null;
		try {
			connectToDb();
			statement = connection
					.prepareStatement("DELETE FROM videos WHERE id = " + id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.err.println("Fehler beim Loeschen!");
			e.printStackTrace();
		}
	}

	/**
	 * Liefert den Eintrag mit dem gesuchten PrimaryKey
	 * 
	 * @param primaryKey
	 *            Pimaerschluessel des Eintrags
	 * @return ResultSet mit id=PrimaryKey
	 * @throws SQLException
	 */
	public ResultSet getEntry(int primaryKey) {
		return findDatasets("SELECT * FROM videos WHERE id = '" + primaryKey
				+ "'");
	}

	/**
	 * Liefert alle Eintraege zu einer Elementgruppe
	 * 
	 * @param elementgruppe
	 * @return ResultSet
	 */
	public ResultSet getAllByElementgruppe(String elementgruppe) {
		return findDatasets("SELECT * FROM videos WHERE elementgruppe ="
				+ elementgruppe);
	}

	/**
	 * Liefert alle Eintraege die mit Name, Geraet und Elementgruppe
	 * uebereinstimmen
	 * 
	 * @param name
	 * @param geraet
	 * @param elementgruppe
	 * @return ResultSet
	 */
	public ResultSet getAllByNameGeraetElementgruppe(String name,
			String geraet, String elementgruppe) {
		return findDatasets("SELECT * FROM videos WHERE name LIKE '%" + name
				+ "%' AND geraet = '" + geraet + "' AND elementgruppe ='"
				+ elementgruppe + "'");
	}

	/**
	 * Liefert alle Eintraege die mit Name, Geraet uebereinstimmen
	 * 
	 * @param name
	 * @param geraet
	 * @return ResultSet
	 */
	public ResultSet getAllByNameGeraet(String name, String geraet) {
		return findDatasets("SELECT * FROM videos WHERE name LIKE '%" + name
				+ "%' AND geraet = '" + geraet + "'");
	}

	/**
	 * Liefert alle Eintraege die mit Name, Elementgrupope uebereinstimmen
	 * 
	 * @param name
	 * @param elementgruppe
	 * @return ResultSet
	 */
	public ResultSet getAllByNameElementgruppe(String name, String elementgruppe) {
		return findDatasets("SELECT * FROM videos WHERE name LIKE '%" + name
				+ "%' AND elementgruppe ='" + elementgruppe + "'");
	}
	/**
	 * Aktualisiert einen Datensatz (AKTUALISIERT NUR NAME + AMPEL!!!!)
	 * @param video
	 */
	public void updateEntry(Video video){
		String sql ="UPDATE videos SET name = '" + video.getName() + "', ampel = " + video.getAmpel() + " WHERE id = '" + video.getId() + "'";
		connectToDb();
		try {
			Statement statement = connection.createStatement();

			connection.setAutoCommit(false);
			statement.executeUpdate(sql);
			connection.commit();


			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.err.println("Fehler beim Einfügen in die Datenbank!");
			e.printStackTrace();
		}
		
	}

}
