package CourierPackage;

import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.*;

public class myrightarea {
	JPanel rightarea = new JPanel(new BorderLayout()); // Created a panel for
														// right side components

	public myrightarea() {
		JPanel buttonpanel = new JPanel();
		// declared a panel on the bottom for holding buttons
		// buttonpanel.setBackground(Color.BLACK);

		JButton prevButton = new JButton("<< Backward Page");
		// created and added Set of buttons to the button panel
		prevButton.setLocation(0, 10);
		prevButton.setSize(70, 20);
		// prevButton.setBackground(Color.white);
		prevButton.setFont(new Font("Arial", Font.BOLD, 14));
		buttonpanel.add(prevButton);

		JButton newButton = new JButton("New Page");
		newButton.setSize(70, 20);
		// newButton.setBackground(Color.white);
		newButton.setFont(new Font("Arial", Font.BOLD, 14));
		buttonpanel.add(newButton);

		JButton delButton = new JButton("Delete Page");
		delButton.setSize(70, 20);
		// delButton.setBackground(Color.white);
		delButton.setFont(new Font("Arial", Font.BOLD, 14));
		buttonpanel.add(delButton);

		JButton nextButton = new JButton("Forward Page >>");
		nextButton.setSize(70, 20);
		// nextButton.setBackground(Color.white);
		nextButton.setFont(new Font("Arial", Font.BOLD, 14));
		buttonpanel.add(nextButton);

		rightarea.add(buttonpanel, BorderLayout.SOUTH); // add the button panel
		// to the main panel

		final JRadioButton inc = new JRadioButton("Free-Form Ink"); // created
		// set of
		// radio buttons
		inc.setFont(new Font("Arial", Font.BOLD, 14));
		// inc.setBackground(Color.white);

		final JRadioButton rectangle = new JRadioButton("Rectangle");
		rectangle.setFont(new Font("Arial", Font.BOLD, 14));
		// rectangle.setBackground(Color.white);

		final JRadioButton oval = new JRadioButton("Oval");
		oval.setFont(new Font("Arial", Font.BOLD, 14));
		// oval.setBackground(Color.white);

		final JRadioButton text = new JRadioButton("Text");
		text.setFont(new Font("Arial", Font.BOLD, 14));
		// text.setBackground(Color.white);

		ButtonGroup buttonGroup = new ButtonGroup(); // created button group for
		// the radio buttons
		buttonGroup.add(oval);
		buttonGroup.add(rectangle);
		buttonGroup.add(inc);
        buttonGroup.add(text);
		JPanel tools = new JPanel(); // created panel to hold button group
		// tools.setBackground(Color.BLACK);

		tools.add(inc); // adding the set of radio buttons to the panel
		tools.add(rectangle);
		tools.add(oval);
		tools.add(text);
		tools.validate();
		final JLayeredPane showPage = new JLayeredPane();
		rightarea.add(showPage);
		final ArrayList<RightWorkArea> Page_new = new ArrayList<RightWorkArea>();
		final RightWorkArea workAreaPage = new RightWorkArea();
		Page_new.add(workAreaPage);
		showPage.add(workAreaPage, new Integer(1));
		showPage.setVisible(true);

		inc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((RightWorkArea) Page_new.get(Page_new.size() - 1)).type_draw = "Line";
			}
		});
		rectangle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((RightWorkArea) Page_new.get(Page_new.size() - 1)).type_draw = "Rectangle";
			}
		});
		oval.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((RightWorkArea) Page_new.get(Page_new.size() - 1)).type_draw = "Oval";
			}
		});
		text.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((RightWorkArea) Page_new.get(Page_new.size() - 1)).type_draw = "Text";

			}
		});
		
		
		delButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RightWorkArea toDelete = (RightWorkArea) Page_new.get(Page_new
						.size() - 1);
				Page_new.remove(Page_new.size() - 1);
				showPage.remove(toDelete);
				rightarea.revalidate();
				rightarea.repaint();
				if (Page_new.size() > 0) {
					((RightWorkArea) Page_new.get(Page_new.size() - 1))
							.setVisible(true);
					((RightWorkArea) Page_new.get(Page_new.size() - 1))
							.setEnabled(true);
				} else {
					RightWorkArea page_temp = new RightWorkArea();
					Page_new.add(page_temp);
					showPage.add(page_temp, new Integer(Page_new.size()));
					page_temp.setEnabled(true);
					page_temp.setVisible(true);
					if (inc.isSelected()) {
						page_temp.type_draw = "Line";
					}
					if (rectangle.isSelected()) {
						page_temp.type_draw = "Rectangle";
					}
					if (oval.isSelected()) {
						page_temp.type_draw = "oval";
					}
					if (text.isSelected()) {
						page_temp.type_draw = "Text";
					}

					rightarea.revalidate();
					rightarea.repaint();
				}
			}
		});

		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Page_new.size() > 1) {
					RightWorkArea page_temp = ((RightWorkArea) Page_new.get(0));
					RightWorkArea page_replace = ((RightWorkArea) Page_new
							.get(Page_new.size() - 1));
					Page_new.remove(0);
					Page_new.add(page_temp);
					
					page_temp.setEnabled(true);
					page_temp.setVisible(true);
					
					page_replace.setEnabled(false);
					page_replace.setVisible(false);
					rightarea.repaint();
				}
			}
		});

		prevButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Page_new.size() > 1) {
					RightWorkArea page_temp = ((RightWorkArea) Page_new
							.get(Page_new.size() - 1));
					RightWorkArea page_replace = ((RightWorkArea) Page_new
							.get(Page_new.size() - 2));
					Page_new.remove(Page_new.size() - 1);
					Page_new.add(0, page_temp);
					page_temp.setVisible(false);
					page_temp.setEnabled(false);
					page_replace.setEnabled(true);
					page_replace.setVisible(true);
					rightarea.repaint();
				}
			}
		});

		newButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RightWorkArea page_temp = new RightWorkArea();
				((RightWorkArea) Page_new.get(Page_new.size() - 1))
						.setVisible(false);
				((RightWorkArea) Page_new.get(Page_new.size() - 1))
						.setEnabled(false);
				
				page_temp.setVisible(true);
				page_temp.setEnabled(true);
				
				
				Page_new.add(page_temp);
				showPage.add(page_temp, new Integer(Page_new.size()));
				if (inc.isSelected()) {
					page_temp.type_draw = "Line";
				}
				if (rectangle.isSelected()) {
					page_temp.type_draw = "Rectangle";
				}
				if (oval.isSelected()) {
					page_temp.type_draw = "Oval";
				}
				if (text.isSelected()) {
					page_temp.type_draw = "Text";
				}
				rightarea.revalidate();
				rightarea.repaint();
			}
		});

		rightarea.add(tools, BorderLayout.NORTH); // adding the radio button
		// panel to the main panel

	}
}