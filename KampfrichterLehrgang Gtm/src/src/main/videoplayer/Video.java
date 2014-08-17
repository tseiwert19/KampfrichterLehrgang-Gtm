package src.main;
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
	private int schwierigkeitsgrad;
	private String elementgruppe;
	
	public Video (int id, String name, String pfad, String geraet, String beschreibung, int schwierigkeitsgrad, String elementgruppe){
		this.id = id;
		this.name = name;
		this.pfad = pfad;
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

	public int getSchwierigkeitsgrad() {
		return schwierigkeitsgrad;
	}

	public String getElementgruppe() {
		return elementgruppe;
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
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", name=" + name + ", pfad=" + pfad
				+ ", beschreibung=" + beschreibung + ", schwierigkeitsgrad="
				+ schwierigkeitsgrad + ", elementgruppe=" + elementgruppe + "]";
	}
	
	
	
}
