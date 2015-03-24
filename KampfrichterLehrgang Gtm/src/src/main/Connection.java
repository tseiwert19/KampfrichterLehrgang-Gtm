package src.main;

import java.io.File;
import java.rmi.Naming;
import java.util.List;

import src.main.videoplayer.Video;

public class Connection {
    public static void testConnection() {
          try {
            IServer iserver = (IServer)Naming.lookup("Server");
            String result = iserver.insert("test");
            System.out.println(result);
          } catch(Exception ex) {
            ex.printStackTrace();
          }
        }
    
    public static void sendVideoToServer(String name, int ampel, String geraet,
			String beschreibung, String schwierigkeitsgrad,
			String elementgruppe, File video, String sprache) {
    	try {
    		IServer iserver = (IServer)Naming.lookup("Server");
    		iserver.insertNewVideo(name, ampel, geraet, beschreibung, schwierigkeitsgrad, elementgruppe, video, sprache);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static List<Video> findVideo(String suchname){
    	try {
    		IServer iserver = (IServer)Naming.lookup("Server");
    		iserver.findVideo(suchname);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    }
    
}
