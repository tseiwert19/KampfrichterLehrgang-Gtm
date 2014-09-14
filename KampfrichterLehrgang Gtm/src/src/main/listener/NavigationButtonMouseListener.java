package src.main.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import src.main.components.KariButton;
/**
 * MouseListener für die Buttons im Navigationspanel.
 * Setzt den jeweligen Tooltip.
 * @author Michael
 *
 */
public class NavigationButtonMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		KariButton button = (KariButton) arg0.getSource();
		String command = button.getActionCommand();

		switch (command) {
		case "back":
			button.setToolTipText("Zurück");
			break;
		case "forward":
			button.setToolTipText("Vor");
			break;
		case "home":
			button.setToolTipText("Home");
			break;
		case "testMode":
			button.setToolTipText("Testmodus starten");
			break;
		}

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
