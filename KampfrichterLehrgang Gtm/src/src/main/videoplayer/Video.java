package src.main.videoplayer;

import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;

/**
 * Klasse Video
 * Repraesentiert einen Datensatz aus der Datenbank.
 * Enthaelt alle wichtigen Daten zu einem Video.
 * @author michael
 *
 */
public class Video {
		
	private int id;
	private String name;
	private String pfad;
	private String geraet;
	private String beschreibung;
	private String schwierigkeitsgrad;
	private String elementgruppe;

	private static final String videoLocationPrefix="";
	
	public Video (int id, String name, String pfad, String geraet, String beschreibung, String schwierigkeitsgrad, String elementgruppe){
		this.id = id;
		this.name = name;
		this.pfad = detectAbsolutePath(pfad);
		this.geraet = geraet;
		this.beschreibung = beschreibung;
		this.schwierigkeitsgrad = schwierigkeitsgrad;
		this.elementgruppe = elementgruppe;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPfad() {
		return pfad;
	}

	public String getGeraet(){
		return geraet;
	}
	public String getBeschreibung() {
		return beschreibung;
	}

	public String getSchwierigkeitsgrad() {
		return schwierigkeitsgrad;
	}

	public String getElementgruppe() {
		return elementgruppe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beschreibung == null) ? 0 : beschreibung.hashCode());
		result = prime * result
				+ ((elementgruppe == null) ? 0 : elementgruppe.hashCode());
		result = prime * result + ((geraet == null) ? 0 : geraet.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pfad == null) ? 0 : pfad.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Video other = (Video) obj;
		if (beschreibung == null) {
			if (other.beschreibung != null)
				return false;
		} else if (!beschreibung.equals(other.beschreibung))
			return false;
		if (elementgruppe == null) {
			if (other.elementgruppe != null)
				return false;
		} else if (!elementgruppe.equals(other.elementgruppe))
			return false;
		if (geraet == null) {
			if (other.geraet != null)
				return false;
		} else if (!geraet.equals(other.geraet))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pfad == null) {
			if (other.pfad != null)
				return false;
		} else if (!pfad.equals(other.pfad))
			return false;
		if (schwierigkeitsgrad != other.schwierigkeitsgrad)
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "Video [id=" + id + ", name=" + name + ", pfad=" + pfad
				+ ", beschreibung=" + beschreibung + ", schwierigkeitsgrad="
				+ schwierigkeitsgrad + ", elementgruppe=" + elementgruppe + "]";
	}
	
	
	
	private String detectAbsolutePath(String pfad)
	{
		if (pfad == null || pfad.isEmpty())
		{
			System.err.println("Video: No path for video in database!");
			return null;
		}
		pfad=videoLocationPrefix + pfad;
		pfad=pfad.replaceFirst("[.]wmv$", ".mkv");
		URL	urlOfVideoFile = getClass().getResource(pfad);
		if (urlOfVideoFile == null)
		{
			System.err.println("Video: Video " + pfad + " not found!");
			return null;
		}
		URI uriOfVideoFile;
		try
		{
			uriOfVideoFile=urlOfVideoFile.toURI();
		}
		catch (URISyntaxException e)
		{
			System.err.println("Video: URL of video " + pfad + " couldn't be converted to URI!");
			return null;
		}
		File videoPathFileObject;
		try
		{
			videoPathFileObject=new File(uriOfVideoFile);
		}
		catch (Exception e)
		{
			System.err.println("Video: URI of video " + pfad + " couldn't be converted to File!");
			return null;
		}

		String pfadFertig = null;
		pfadFertig = videoPathFileObject.getPath().replace("bin", "src");
		return pfadFertig;
	}
}
