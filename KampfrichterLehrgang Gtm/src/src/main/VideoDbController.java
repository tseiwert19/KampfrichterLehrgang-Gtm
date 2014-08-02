package src.main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Diese Klasse enthaelt alle Funktionen fuer die Datenbank
 * @author michael
 *
 */
public class VideoDbController

{
	
    private Connection connection;
    //Pfad zur Datenbank
    private static final String DB_PATH = "src/videoDb";
    /**
     * Konstruktor
     * Stellt die Verbindung zur Datenbank her und erstellt
     * die Datenbank(Falls sie noch nicht existiert)
     */
    public VideoDbController(){
        connectToDb();
        createTable();
    }
    /**
     * Stellt eine Verbindung zur Datenbank her
     */
    private void connectToDb(){
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
     * Erstellt die Tabelle
     * Spalten:
     * id, name, pfad, beschreibung, schwierigkeit, kategorie
     */
   private void createTable(){
            connectToDb();
            try {
            Statement statement = connection.createStatement();
            //statement.executeUpdate("DROP TABLE IF EXISTS videos;"); //TODO später entfernen!
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS videos (id INT PRIMARY KEY, "
				        + "name VARCHAR(50) NOT NULL,"
				        + "pfad VARCHAR(50) NOT NULL, "
				        + "beschreibung VARCHAR(200), "
				        + "schwierigkeit INT,"
				        + "kategorie VARCHAR(15) NOT NULL);");
			} catch (SQLException e) {
				System.err.println("Fehler beim Erstellen der Datenbank!");
				e.printStackTrace();
			}
    }
    /**
     * Fuegt ein Video in die Datenbank
     * @param id = PrimaryKey
     * @param name = Name des Videos
     * @param pfad = Pfad zur Videodatei
     * @param beschreibung = Beschreibung zum Video
     * @param schwierigkeit = Schwierigkeitsgrad der Turnuebung
     * @param kategorie = Kategorie des Videos
     */
    public void addVideo(int id, String name, String pfad, String beschreibung, int schwierigkeit, String kategorie){
        
        connectToDb();
        PreparedStatement prepStatement;
		try {
			prepStatement = connection.prepareStatement("INSERT INTO videos VALUES ( ?, ?, ?, ?, ?, ?)");
	
        prepStatement.setInt(1, id);
        prepStatement.setString(2, name);
        prepStatement.setString(3, pfad);
        prepStatement.setString(4, beschreibung);
        prepStatement.setInt(5, schwierigkeit);
        prepStatement.setString(6, kategorie);
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
     * @return Alle Eintraege
     * @throws SQLException
     */
    public ResultSet getAllEntries(){
    	ResultSet alleVideos = null;
    	connectToDb();
        try {
			alleVideos = connection.createStatement().executeQuery("SELECT * FROM videos;");
		} catch (SQLException e) {
			System.err.println("Fehler bei Datenbankabfrage!");
			e.printStackTrace();
		}
        return alleVideos;
    }
    public void deleteVideo(int id){
    	PreparedStatement statement;
		try {
			statement = connection.prepareStatement("DELETE FROM videos WHERE id=" + id);
			 connection.setAutoCommit(false);
	            statement.executeBatch();
	            connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.err.println("Fehler beim Loeschen!");
			e.printStackTrace();
		}
    }
    /**
     * Liefert den Eintrag mit dem gesuchten PrimaryKey
     * @param primaryKey Pimaerschluessel des Eintrags
     * @return ResultSet mit id=PrimaryKey
     * @throws SQLException
     */
    public ResultSet getEntry(int primaryKey){
    	ResultSet video = null;
    	connectToDb();
         try {
    	   video = connection.createStatement().executeQuery("SELECT * FROM videos WHERE id =" + primaryKey);
         } catch (SQLException e) {
    	   System.err.println("Fehler bei Datenbankabfrage!");
    	   e.printStackTrace();
       }
       return video;
    }

}
