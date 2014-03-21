package CourierPackage;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

public class myBrowser {

	String Url = "http://www.kedwards.com/classes/AY2014/cs4470_fall/";

	// Created necessary components to add for the browser

	JLabel urlLabel = new JLabel("URL:");
	JPanel addressBar = new JPanel();
	JTextField urlText = new JTextField(30);
	FlowLayout experimentLayout = new FlowLayout();
	JPanel panelLeft = new JPanel();

	public myBrowser() {

		JButton urlBrowse = new JButton("GO"); // created set of buttons to
		// operate the browser
		urlBrowse.setSize(40, 20);
		// urlBrowse.setBackground(Color.white);
		urlBrowse.setFont(new Font("Arial", Font.BOLD, 14));

		final JButton back = new JButton("BACK");
		back.setSize(20, 20);
		// back.setBackground(Color.white);
		back.setFont(new Font("Arial", Font.BOLD, 10));
		back.setEnabled(false);

		final JButton forward = new JButton("NEXT");
		forward.setSize(20, 20);
		// forward.setBackground(Color.white);
		forward.setFont(new Font("Arial", Font.BOLD, 10));
		forward.setEnabled(false);

		urlLabel.setForeground(Color.black); // label for url
		urlLabel.setFont(new Font("Arial", Font.BOLD, 12));
		urlText.setText(Url); // set address bar text to current page's URL
		urlText.setSize(130, 20);
		final JEditorPane page;

		addressBar.setLayout(experimentLayout); // adding buttons and url
		// components to addressbar
		// panel
		// addressBar.setBackground(Color.BLACK);
		addressBar.add(back);
		addressBar.add(forward);
		addressBar.add(urlLabel);
		addressBar.add(urlText);
		addressBar.add(urlBrowse);
		addressBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		addressBar.validate();

		final ArrayList<String> visitedPages = new ArrayList<String>(); // array
		// list
		// to
		// store
		// visited
		// url's
		visitedPages.add(Url);

		try {
			page = new JEditorPane(Url);
			page.setEditable(false);

			page.addHyperlinkListener(new HyperlinkListener() { // update
				// hyperlink

				public void hyperlinkUpdate(HyperlinkEvent e) {
					if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
						JEditorPane pane = (JEditorPane) e.getSource();
						if (e instanceof HTMLFrameHyperlinkEvent) {
							HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
							HTMLDocument doc = (HTMLDocument) pane
									.getDocument();
							doc.processHTMLFrameHyperlinkEvent(evt);
						} else {
							try {
								pane.setPage(e.getURL());

							} catch (Throwable t) {
								t.printStackTrace();
							}

						}

						urlText.setText(e.getURL().toExternalForm());
						visitedPages.add(urlText.getText());

						back.setEnabled(true);
					}
				}
			});

			urlBrowse.addActionListener(new ActionListener() { // action listner
						// for browse
						// button

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							try {
								page.setPage(urlText.getText().trim());
								back.setEnabled(true);

								visitedPages.add(urlText.getText());

							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					});

			urlText.addActionListener(new ActionListener() { // action listner
				// for urlText
				// :search on
				// pressing
				// "ENTER" key

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						page.setPage(urlText.getText().trim());
						back.setEnabled(true);

						visitedPages.add(urlText.getText());
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});

			back.addActionListener(new ActionListener() { // action listner for
				// page back button

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						URL curr = page.getPage();

						String Urlback;
						int index = visitedPages.indexOf(curr.toString());

						if (index == 1)
							back.setEnabled(false);
						if (index > 0) {
							Urlback = visitedPages.get(index - 1);
							forward.setEnabled(true);
						} else {
							Urlback = curr.toString();
							back.setEnabled(false);
						}
						page.setPage(Urlback);
						urlText.setText(Urlback);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});

			forward.addActionListener(new ActionListener() { // action listener
																// for page
																// forward
																// button

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						URL curr = page.getPage();
						String Urlfwd;
						int index = visitedPages.indexOf(curr.toString());
						int i = visitedPages.size() - 1;
						if (index == i - 1)
							forward.setEnabled(false);
						if (index < i) {
							Urlfwd = visitedPages.get(index + 1);
							back.setEnabled(true);
						} else {
							Urlfwd = curr.toString();
							forward.setEnabled(false);
						}
						page.setPage(Urlfwd);
						urlText.setText(Urlfwd);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});

			JScrollPane webpage = new JScrollPane(page);

			panelLeft.setLayout(new BorderLayout()); // adding components to the
														// left panel
			panelLeft.add(addressBar, BorderLayout.NORTH);
			panelLeft.add(webpage, BorderLayout.CENTER);
			panelLeft.setFocusable(false);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}