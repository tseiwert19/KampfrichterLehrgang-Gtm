package test;

import src.main.DatenbankController;

public class ExampleVideos
{
    private static DatenbankController controller;

    public static void main(String[] args)
    {

        controller = new DatenbankController();

        
        for(int i = 1; i <= 18; i++){
            controller.deleteVideo(i);    
        }
        
        //Boden
        controller.addVideo(1, "Manna heben", "videos/1 Boden/10 Manna heben i. s. Hdst.wmv", "Boden", "-", "A", "C");
        controller.addVideo(2, "Morandi", "videos/1 Boden/10 Morandi.wmv", "Boden", "-", "B", "C");
        controller.addVideo(3, "Twist", "/videos/1 Boden/10 Twist m. Dosa. geh.wmv", "Boden", "-", "C", "C");
//Noch mehr Videos von Thomas
//        controller.addVideo(4, "Manna heben", "videos/1 Boden/10 Manna heben i. s. Hdst.wmv", "Boden", "-", "A", "C");
//        controller.addVideo(5, "Morandi", "videos/1 Boden/10 Morandi.wmv", "Boden", "-", "B", "C");
//        controller.addVideo(6, "Twist", "/videos/1 Boden/10 Twist m. Dosa. geh.wmv", "Boden", "-", "C", "C");
//        controller.addVideo(7, "Manna heben", "videos/1 Boden/10 Manna heben i. s. Hdst.wmv", "Boden", "-", "A", "C");
//        controller.addVideo(8, "Morandi", "videos/1 Boden/10 Morandi.wmv", "Boden", "-", "B", "C");
//        controller.addVideo(9, "Twist", "/videos/1 Boden/10 Twist m. Dosa. geh.wmv", "Boden", "-", "C", "C");
//        controller.addVideo(10, "Manna heben", "videos/1 Boden/10 Manna heben i. s. Hdst.wmv", "Boden", "-", "A", "C");
//        controller.addVideo(12, "Morandi", "videos/1 Boden/10 Morandi.wmv", "Boden", "-", "B", "C");
//        controller.addVideo(13, "Twist", "/videos/1 Boden/10 Twist m. Dosa. geh.wmv", "Boden", "-", "C", "C");
//        controller.addVideo(14, "Manna heben", "videos/1 Boden/10 Manna heben i. s. Hdst.wmv", "Boden", "-", "A", "C");
//        controller.addVideo(15, "Morandi", "videos/1 Boden/10 Morandi.wmv", "Boden", "-", "B", "C");
//        controller.addVideo(11, "Twist", "/videos/1 Boden/10 Twist m. Dosa. geh.wmv", "Boden", "-", "C", "C");
//        controller.addVideo(16, "Manna heben", "videos/1 Boden/10 Manna heben i. s. Hdst.wmv", "Boden", "-", "A", "C");
//        controller.addVideo(17, "Morandi", "videos/1 Boden/10 Morandi.wmv", "Boden", "-", "B", "C");
//        controller.addVideo(18, "Twist", "/videos/1 Boden/10 Twist m. Dosa. geh.wmv", "Boden", "-", "C", "C");
//        
        //Pauschenpferd
        controller.addVideo(4, "Wandern", "/videos/2 Pauschenpferd/01 1-3 Wandern Seitst.wmv", "Pauschenpferd", "-", "A", "C");
        controller.addVideo(5, "Kreis", "/videos/2 Pauschenpferd/1 Alle Kreisfl. im Seitst.wmv", "Pauschenpferd", "-", "B", "C");
        controller.addVideo(6, "Schere", "/videos/2 Pauschenpferd/1 Schere vw.wmv", "Pauschenpferd", "-", "C", "C");
        
        //Ringe
        controller.addVideo(7, "Winkelstuetz", "/videos/3 Ringe/1 Winkelstütz.wmv", "Ringe", "-", "A", "C");
        controller.addVideo(8, "Li Ning", "/videos/3 Ringe/10 Li Ning i.d.wmv", "Ringe", "-", "B", "C");
        controller.addVideo(9, "Schwalbe", "/videos/3 Ringe/10 Schwalbe.wmv", "Ringe", "-", "C", "C");
       
        //Sprung
        controller.addVideo(10, "Ueberschlag", "/videos/4 Sprung/15 Überschlag u. Salto vw. geh. m-1_1 Dr..wmv", "Sprung", "-", "A", "C");
        controller.addVideo(11, "Yurchenko", "/videos/4 Sprung/19 Yurchenko gestr.wmv", "Sprung", "-", "B", "C");
        controller.addVideo(12, "Yurchenko 2", "/videos/4 Sprung/21 Yurchenko gestr. mit 1_1 Dr.wmv", "Sprung", "-", "C", "C");
        
        //Barren
        controller.addVideo(13, "Alle Handstaende", "/videos/5 Barren/1 Alle Handstäde.wmv", "Barren", "-", "A", "C");
        controller.addVideo(14, "Stuetz", "/videos/5 Barren/1 Ste. vw. i. d. Stütz.wmv", "Barren", "-", "B", "C");
        controller.addVideo(15, "Oberarme", "/videos/5 Barren/1 Unterschw. a. d. Oberarme.wmv", "Barren", "-", "C", "C");
        
        //Reck
        controller.addVideo(16, "Kippe", "/videos/6 Reck/1 Kippe d. d. Hdst o. m 1_2 Dr.wmv", "Reck", "-", "A", "C");
        controller.addVideo(17, "Ste. rw.", "/videos/6 Reck/1 Ste. rw. i. d. Hdst.wmv", "Reck", "-", "B", "C");
        controller.addVideo(18, "Yamawaki", "/videos/6 Reck/10 Yamawaki.wmv", "Reck", "-", "C", "C");
        
        System.out.println("Alle Datensaetze wurden eingefuegt!");

    }
}
