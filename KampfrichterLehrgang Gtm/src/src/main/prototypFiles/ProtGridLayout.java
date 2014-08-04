package src.main;

import java.awt.GridLayout;


/**
 * Layout des Prototypen
 * 
 * @author Thomas
 *
 */
public class ProtGridLayout extends GridLayout {

private static final int COLUMNCOUNT = 3;
private static final int ROWCOUNT = 2;

  ProtGridLayout() {
    this.setColumns(COLUMNCOUNT);
    this.setRows(ROWCOUNT);

    this.setHgap(20);
    this.setVgap(30);

  }
}
