package src.main;

import java.awt.GridLayout;


public class ProtVideoGridLayout extends GridLayout {

private static final int COLUMNCOUNT = 3;
private static final int ROWCOUNT = 2;

  ProtVideoGridLayout() {
    this.setColumns(COLUMNCOUNT);
    this.setRows(ROWCOUNT);

    this.setHgap(20);
    this.setVgap(30);

  }
}
