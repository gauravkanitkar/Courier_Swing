package CourierPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;




public class Courier {
    JFrame mainFrame;
	JPanel mainPanel = new JPanel(new BorderLayout());
	JLabel status = new JLabel("Status: ");
	JPanel statusBar;
	JPanel browserPanel;
	JTabbedPane leftArea = new JTabbedPane();
	JSplitPane splitScreen = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); //Define a split pane.

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				new Courier();    //Calling instance of Courier class.

			}
		});

	}

	public Courier() {

		mainFrame = new JFrame("My Courier Framework");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); //Initially set to maximum.

		myBrowser browser = new myBrowser();   // Instance of myBrowser class
		addressBook addbook = new addressBook(); // Instance of addressBook class
		myrightarea rightarea1 = new myrightarea();
        
		JPanel browserPanel = browser.panelLeft;  // Get the browser panel from myBrowser class
        browserPanel.setFocusable(false);
		browserPanel.setFont(new Font("Arial", Font.BOLD, 14));
		browserPanel.setBackground(Color.white);
		
		
		JPanel addbookPanel = addbook.addressBookPanel; // Get the browser panel from addressBook class
		addbookPanel.setFocusable(false);
		addbookPanel.setFont(new Font("Arial", Font.BOLD, 14));
		addbookPanel.setBackground(Color.white);
		
		
		JTabbedPane tab = new JTabbedPane();  // Tabbed pane for browser and addressbook
		tab.addTab("   WEB BROWSER   ", browserPanel);
		tab.addTab("   ADDRESS BOOK   ", addbookPanel);

		JPanel tabpanel = new JPanel(new BorderLayout());
        tabpanel.add(tab);
		
        leftArea.setBackground(Color.BLACK);
		leftArea.add(tabpanel);
	
		
		JPanel rightarea = rightarea1.rightarea;
		splitScreen.setRightComponent(rightarea);

		splitScreen.resetToPreferredSizes();
		splitScreen.setLeftComponent(leftArea);  // Set the left component of the split pane as the tabbed pane containing browser and addressbook.
		splitScreen.setFocusable(false);
		splitScreen.setResizeWeight(0.5);
		splitScreen.validate();

		statusBar = new JPanel();
		statusBar.setLayout(new BorderLayout());
		statusBar.add(status, BorderLayout.WEST);
		statusBar.setBackground(Color.white);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(splitScreen, BorderLayout.CENTER);
		mainPanel.add(statusBar, BorderLayout.SOUTH);

		
		
		mainFrame.addComponentListener(new ComponentAdapter() {
      // Component listener for the resizing of the window.
			public void componentResized(ComponentEvent event) {

				splitScreen.setDividerLocation((mainFrame.getWidth()) / 2);
				mainFrame.setSize(
					      Math.max(850, mainFrame.getWidth()),
					      Math.max(600, mainFrame.getHeight()));
					  

				if (mainFrame.getWidth() <= 850 || mainFrame.getHeight() <= 600) {

					mainFrame.setSize(850, 600);
					splitScreen.setDividerLocation((mainFrame.getWidth()) / 2);
					mainFrame.repaint();
				}

			}
		});
		
		

		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
	}

}