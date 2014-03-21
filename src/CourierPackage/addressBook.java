package CourierPackage;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

public class addressBook {
	JPanel addressBookPanel; // main panel for holding the address book
	JTable table; // table for displaying the list of contacts
	JScrollPane scrollPane;
	JTextPane rowInfo; // text pane to display information of the selected
						// contact

	public addressBook() {
		final String[] columnNames = { "First Name", "Last Name",
				"Phone Number" }; // adding entries in the table

		final Object[][] data = {
				{ "Gaurav", "Kanitkar", "404-234-3691" }, // created an object
				{ "Yash", "Kshirsagar", "404-234-0955" },
				{ "Madhura", "Kamat", "324-234-0446" },
				{ "Hao", "Lu", "240-234-6879" },
				{ "Jing", "Lan", "201-234-4444" },
				{ "Nikita", "Prabhu", "201-261-3245" },
				{ "Pratik", "Patil", "231-244-4567" },
				{ "Chinmay", "Udali", "321-654-0987" },
				{ "Sheldon", "Cooper", "404-678-5678" },
				{ "Manva", "Naik", "201-234-8765" },
				{ "Java", "Swing", "404-345-1029" } };

		table = new JTable(data, columnNames); // added it to the table
		scrollPane = new JScrollPane(table);

		rowInfo = new JTextPane();
		rowInfo.setFont(new Font("Arial", Font.BOLD, 14));

		table.addMouseListener(new MouseAdapter() { // mouse listner class for
													// click evevt selection
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowNo = table.getSelectedRow();
				int i = 0;
				String rowDetails = " Information : ";

				for (i = 0; i < table.getColumnCount(); i++) {

					rowDetails = rowDetails + "\n\n " + columnNames[i]
							+ " :   " + data[rowNo][i] + "\n";

				}

				rowInfo.setText(rowDetails); // add info to the text area

				super.mouseClicked(e);
			}
		});
		JScrollPane textinfo = new JScrollPane(rowInfo); // adding table and
															// text pane to the
															// main address book
															// panel
		addressBookPanel = new JPanel();
		addressBookPanel.setLayout(new BorderLayout());
		addressBookPanel.add(scrollPane, BorderLayout.NORTH);
		addressBookPanel.add(textinfo, BorderLayout.CENTER);

	}

}
