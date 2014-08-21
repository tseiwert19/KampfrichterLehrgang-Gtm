package src.main.videoplayer;
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
	
	public Video (int id, String name, String pfad, String geraet, String beschreibung, String schwierigkeitsgrad, String elementgruppe){
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
	
	
	
}
