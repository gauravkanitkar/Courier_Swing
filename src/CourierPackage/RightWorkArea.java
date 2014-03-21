package CourierPackage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.color.*;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

class figRegion { // Class which holds rectangle, line and oval objects
	int x1, y1, x2, y2;
	String type;

	public figRegion() {
	}

	public figRegion(int xo, int yo, int xn, int yn, String tp) {
		x1 = xo;
		y1 = yo;
		x2 = xn;
		y2 = yn;
		type = tp;
	}
}

class textRegion { // Class which holds post-its

	int x2 = 0, y2 = 0;
	int x1, y1;
	int t_font;
	String text = new String();

	public textRegion() {
		text = "_";
	}

	public textRegion(int x1, int y1) {
		x2 = x1;
		y2 = y1;
		text = "_";
	}

	public textRegion(int xo, int yo, int xn, int yn, int font_text) {
		x1 = xo;
		y1 = yo;
		x2 = xn;
		y2 = yn;
		t_font = font_text;
		text = "_";
	}
}

class RightWorkArea extends Component {

	private static final long serialVersionUID = 1L;
	ArrayList<textRegion> post_it;
	ArrayList<figRegion> Figures;

	static boolean start_Draw;
	static boolean is_on_postit; // Checks if the cursor is on a post-it

	static int textobj_id = 0;
	static int startX = 0, startY = 0;

	String type_draw = "Line";

	private String[] font_type = { "Calibri", "Times New Roman", "Arial" };
	private int fontchoose = 0;

	public RightWorkArea() {
		
		post_it = new ArrayList<textRegion>(); //Arraylist for post-it objects
		Figures = new ArrayList<figRegion>(); // Arraylist for all other figures
		
		start_Draw = false;
		startX = 0; // Initial X and Y co-ordinates.
		startY = 0;
		
		type_draw = "Line";
		setLocation(0, 0);
		setSize(new Dimension(800, 800));
	
		
		this.addMouseListener(new MouseListener() {

			

			@Override
			public void mousePressed(MouseEvent e) {
				start_Draw = true;
				startX = e.getX(); // Get initial X and Y positions
				startY = e.getY();
				textRegion text_obj;
				is_on_postit = false;

				for (int i = 0; i < post_it.size(); i++) {
					text_obj = (textRegion) post_it.get(i);
					if (startX >= text_obj.x1 && startX <= text_obj.x2
							&& startY >= text_obj.y1 && startY <= text_obj.y2) {
						is_on_postit = true;  // Check if a post-it is clicked.
						textobj_id = i;       // ID of the clicked post-it in arraylist
						break;
					}

				}

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				start_Draw = true;
          
			// Right-click is used to change fonts.
				if (e.getButton() == MouseEvent.BUTTON3) {
					fontchoose++;
					if (fontchoose == 3) {
						fontchoose = 0;
					}
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				is_on_postit = false;
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {

				requestFocus();
				textRegion showText_obj;
				if (start_Draw == false) {
					if (post_it.size() > 0) {
						showText_obj = (textRegion) post_it.get(post_it.size() - 1);
						showText_obj.text = showText_obj.text.substring(0,
								showText_obj.text.length() - 1);
					}
					start_Draw = true;
				}
				
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (start_Draw && is_on_postit == true) // Used for dragging the post-it
				{
					textRegion text_obj;
					int i = 0;
					for (i = 0; i < post_it.size(); i++) {
						text_obj = (textRegion) post_it.get(i);
						if (startX >= text_obj.x1 && startX <= text_obj.x2
								&& startY >= text_obj.y1 && startY <= text_obj.y2) {
							is_on_postit = true;

							break;
						}
					}

					text_obj = (textRegion) post_it.get(textobj_id);
					text_obj.x1 += e.getX() - startX;

					text_obj.y1 += e.getY() - startY;

					text_obj.x2 = text_obj.x2 + e.getX() - startX;
					text_obj.y2 = text_obj.y2 + e.getY() - startY;
					startX = e.getX();
					startY = e.getY();
					
				}
				if (start_Draw && (type_draw == "Line") // Draw line
						&& is_on_postit == false) {
					Figures.add(new figRegion(startX, startY, e.getX(), e.getY(),
							type_draw));
					startX = e.getX();
					startY = e.getY();
					repaint();
				}
				if (start_Draw
						&& (type_draw == "Oval" || type_draw == "Rectangle")
						&& is_on_postit == false) {
					if (Figures.size() > 0) {
						figRegion dispFig = Figures.get(Figures.size() - 1);
						if (dispFig.x1 == startX && (dispFig.y1 == startY)) {
							dispFig.x2 = e.getX();
							dispFig.y2 = e.getY();
						}

						else {
							Figures.add(new figRegion(startX, startY, e.getX(), e
									.getY(), type_draw));
						}
					} else {
						Figures.add(new figRegion(startX, startY, e.getX(), e
								.getY(), type_draw));
					}
					repaint();
				}
				if (start_Draw && (type_draw == "Text") // New post-it
						&& is_on_postit == false) {
					if (post_it.size() > 0) {
						textRegion dispTxt = post_it.get(post_it.size() - 1);
						if (dispTxt.x1 == startX && (dispTxt.y1 == startY)) {
							dispTxt.x2 = e.getX();
							dispTxt.y2 = e.getY();
						}

						else {
							post_it.add(new textRegion(startX, startY, e.getX(), e
									.getY(), fontchoose));
						}
					} else {
						post_it.add(new textRegion(startX, startY, e.getX(), e
								.getY(), fontchoose));
					}
					repaint();
				}
			}
		});
		
		
		
		this.addKeyListener(new KeyListener() { //For text input.

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == 8) { //Backspace functionality
					textRegion showText_obj = (textRegion) post_it.get(post_it.size() - 1);
					if (showText_obj.text.length() > 1) {
						showText_obj.text = showText_obj.text.substring(0,
								showText_obj.text.length() - 2);
						showText_obj.text = showText_obj.text + "_";
						repaint();
					}
				} else if (post_it.size() > 0 && e.getKeyCode() >= 32
						&& e.getKeyCode() <= 126) {
					textRegion showText_obj = (textRegion) post_it.get(post_it.size() - 1);
					showText_obj.text = showText_obj.text.substring(0,
							showText_obj.text.length() - 1);
					showText_obj.text = showText_obj.text + e.getKeyChar()
							+ "_";
					repaint();
				}
			}
		});
	}

	public void drawString(int i, Graphics g, String s, int x, int y, int maxY,
			int width, int height, int txt_font) { // Used to draw string with word-wrapping
		//around the post-it.
		
		g.setFont(new Font(font_type[txt_font], Font.BOLD, 19));
		FontMetrics fm = g.getFontMetrics();
		int wordHeight = fm.getHeight() + 5;
		textRegion temptxt;
		temptxt = (textRegion) post_it.get(i);
		int width2 = width + x;
		int newx = x + 10;
		int newy = y + 25;
		int curX = newx;
		int curY = newy;
		if (s.contains(" ")) {
			String[] words = s.split(" ");
			for (String word : words) {
				int wordWidth = fm.stringWidth(word + " ");

				if (wordWidth >= width) { // If the word is larger than width, insert a hyphen.

					String[] words2 = word.split("");
					for (String word2 : words2) {
						int wordWidth2 = fm.stringWidth(word2);
						String temp = "-";
						int hyphenWidth = fm.stringWidth(temp);
						if (curX + wordWidth2 + hyphenWidth + 5 >= width2) {
							g.setColor(Color.black);
							g.drawString(temp, curX, curY);
							curY += wordHeight;
							if (curY > maxY) {
								
								temptxt.y2 = curY + wordHeight; // Rectangle enlargement.
							}
							curX = newx;
						}
						g.setColor(Color.black);
						g.drawString(word2, curX, curY);
						curX += wordWidth2;
					}
					curX += 8;
				} else if (curX + wordWidth >= width2) { // Take entire word to new line.

					curY += wordHeight;

					if (curY > maxY) {

						temptxt.y2 = curY + wordHeight; // Rectangle enlargement
					}
					curX = newx;

					g.setColor(Color.black);
					g.drawString(word, curX, curY);
					curX += wordWidth;
				} else {
					g.setColor(Color.black);
					g.drawString(word, curX, curY);
					curX += wordWidth;
				}

			}
		} else if (x + fm.stringWidth(s) > width2) {
			String[] words = s.split("");
			for (String word : words) {
				int wordWidth = fm.stringWidth(word);
				if (curX + wordWidth >= width2) {
					curY += wordHeight;
					if (curY > maxY) {

						temptxt.y2 = curY + wordHeight;
					}
					curX = newx;
				}
				g.setColor(Color.black);
				g.drawString(word, curX, curY);
				curX += wordWidth;
			}
		} else {
			g.setColor(Color.black);
			g.drawString(s, curX, curY);
			curX = curX + 8;
		}
	}

	
	@Override
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g);
		BufferedImage img = null;
		try {
			
			img = (BufferedImage) ImageIO.read(Courier.class
					.getResource("paper1.jpg"));

		} catch (Exception e) {
			System.out.println("Unable to load background image.");
		}
		g.drawImage(img, 0, 0, null);
		textRegion showText_obj;

		figRegion tempFig;
		for (int i = 0; i < Figures.size(); i++) { // Display figures.
			tempFig = (figRegion) Figures.get(i);
			if (tempFig.type == "Line") {
				g.setColor(Color.BLACK);
				g.drawLine(tempFig.x1, tempFig.y1, tempFig.x2, tempFig.y2);
			}
			if (tempFig.type == "Rectangle") {
				int x = Math.min(tempFig.x1, tempFig.x2);
				int y = Math.min(tempFig.y1, tempFig.y2);
				int width = Math.abs(tempFig.x2 - tempFig.x1);
				int height = Math.abs(tempFig.y2 - tempFig.y1);
				g.setColor(Color.black);
				g.drawRect(x, y, width, height);

			}
			if (tempFig.type == "Oval") {
				int x = Math.min(tempFig.x1, tempFig.x2);
				int y = Math.min(tempFig.y1, tempFig.y2);
				int width = Math.abs(tempFig.x2 - tempFig.x1);
				int height = Math.abs(tempFig.y2 - tempFig.y1);
				g.setColor(Color.BLACK);
				g.drawOval(x, y, width, height);
			}

		}
		for (int i = 0; i < post_it.size(); i++) { // Display post-it
			showText_obj = (textRegion) post_it.get(i);
			int x = Math.min(showText_obj.x1, showText_obj.x2);
			int y = Math.min(showText_obj.y1, showText_obj.y2);
			int maxY = Math.max(showText_obj.y1, showText_obj.y2);
			int width = Math.abs(showText_obj.x2 - showText_obj.x1);
			int height = Math.abs(showText_obj.y2 - showText_obj.y1);

			g.setColor(new Color(255, 255, 51));
			g.drawRect(x, y, width, height);
			g.fillRect(x, y, width, height);
			drawString(i, g, showText_obj.text, x, y, maxY, width, height,
					showText_obj.t_font);
			repaint();

		}

	}

}

