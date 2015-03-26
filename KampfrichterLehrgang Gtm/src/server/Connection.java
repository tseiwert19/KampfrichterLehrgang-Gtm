package server;


import java.io.File;
import java.rmi.Naming;
import java.util.List;

import server.IServer;

public class Connection {
    public static void testConnection() {
          try {
            IServer iserver = (IServer)Naming.lookup("Server");
          } catch(Exception ex) {
            ex.printStackTrace();
          }
        }
    
    public static void sendVideoToServer(String name, int ampel, String geraet,
			String beschreibung, String schwierigkeitsgrad,
			String elementgruppe, byte[] video, String sprache) {
    	try {
    		IServer iserver = (IServer)Naming.lookup("Server");
    		iserver.insertNewVideo(name, ampel, geraet, beschreibung, schwierigkeitsgrad, elementgruppe, video, sprache);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static List<Video> findVideo(String suchname){
    	List<Video> videos = null;
    	try {
    		IServer iserver = (IServer)Naming.lookup("Server");
    		videos = iserver.findVideo(suchname, true);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return videos;
    	
    	
    	
    }
    
//    public static newTranslation(String neueBezeichnung, String sprache, boolean insertOnOtherServers)
//    try {
//		IServer iserver = (IServer)Naming.lookup("Server");
//    iserver.insertNewTranslation(int id, String neueBezeichnung, String sprache, boolean insertOnOtherServers)
//    
}
