package server;


import java.io.File;
import java.rmi.Naming;
import java.util.List;

import server.IServer;

public class Connection {
    public static void testConnection() {
          try {
            IServer iserver = (IServer)Naming.lookup(Konstanten.SERVER_DEUTSCHLAND);
          } catch(Exception ex) {
            ex.printStackTrace();
          }
        }
    
    public static void sendVideoToServer(String name, int ampel, String geraet,
			String beschreibung, String schwierigkeitsgrad,
			String elementgruppe, byte[] video, String sprache) {
    	try {
    		IServer iserver = (IServer)Naming.lookup(Konstanten.SERVER_DEUTSCHLAND);
    		iserver.insertNewVideo(name, ampel, geraet, beschreibung, schwierigkeitsgrad, elementgruppe, video, sprache);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static List<Video> findVideo(String suchname){
    	List<Video> videos = null;
    	try {
    		IServer iserver = (IServer)Naming.lookup(Konstanten.SERVER_DEUTSCHLAND);
    		videos = iserver.findVideo(suchname, true);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return videos;
    	
    	
    	
    }
    
    public static void newTranslation(int id, String neueBezeichnung){
	    try {
			IServer iserver = (IServer)Naming.lookup(Konstanten.SERVER_DEUTSCHLAND);
			iserver.insertNewTranslation(id, neueBezeichnung, "deutsch", true);
	    }catch(Exception ex) {
			ex.printStackTrace();
		}
    }
}
