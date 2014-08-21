package src.main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Diese Klasse enthaelt alle Funktionen fuer die Datenbank
 * 
 * @author michael
 *
 */
public class DatenbankController

{

	private Connection connection;
	// Pfad zur Datenbank
	private static final String DB_PATH = "src/videoDb.sqlite";

	/**
	 * Konstruktor Stellt die Verbindung zur Datenbank her und erstellt die
	 * Datenbank(Falls sie noch nicht existiert)
	 */
	public DatenbankController() {
		connectToDb();
		createTable();
	}

	/**
	 * Stellt eine Verbindung zur Datenbank her
	 */
	private void connectToDb() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
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
			//statement.executeUpdate("DROP TABLE IF EXISTS videos;"); //TODO
			// später entfernen!
			statement
					.executeUpdate("CREATE TABLE IF NOT EXISTS videos (id INT PRIMARY KEY, "
							+ "name VARCHAR(50) NOT NULL,"
							+ "pfad VARCHAR(50) NOT NULL, "
							+ "geraet VARCHAR(20) NOT NULL, "
							+ "beschreibung VARCHAR(200), "
							+ "schwierigkeitsgrad VARCHAR(10),"
							+ "elementgruppe VARCHAR(5));");
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
	public void addVideo(int id, String name, String pfad,String geraet, String beschreibung,
			String schwierigkeitsgrad, String elementgruppe) {

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
		ResultSet alleVideos = null;
		connectToDb();
		try {
			alleVideos = connection.createStatement().executeQuery(
					"SELECT * FROM videos;");
		} catch (SQLException e) {
			System.err.println("Fehler bei Datenbankabfrage!");
			e.printStackTrace();
		}
		return alleVideos;
	}
	
	public ResultSet getAllByGeraet(String geraet){
		ResultSet alleVideos = null;
		connectToDb();
		try {
			alleVideos = connection.createStatement().executeQuery(
					"SELECT * FROM videos WHERE geraet = '" + geraet + "'");
		} catch (SQLException e) {
			System.err.println("Fehler bei Datenbankabfrage!");
			e.printStackTrace();
		}
		return alleVideos;
	}

	public void deleteVideo(int id) {
		PreparedStatement statement = null;
		try {
			connectToDb();
			statement = connection
					.prepareStatement("DELETE FROM videos WHERE id = " + id);
			statement.executeUpdate();
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
		ResultSet video = null;
		connectToDb();
		try {
			video = connection.createStatement().executeQuery(
					"SELECT * FROM videos WHERE id =" + primaryKey);
		} catch (SQLException e) {
			System.err.println("Fehler bei Datenbankabfrage!");
			e.printStackTrace();
		}
		return video;
	}

}
