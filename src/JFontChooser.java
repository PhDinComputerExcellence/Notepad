// 
//    Name:  Lee, Kevin
//    Project: 3
//    Due:       3/12/2018 
//    Course: CS-245-01-w18 
// 
//    Description: 
//     A Complete Notepad application replicated from java swing! 
//
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.text.AttributedString;

import javax.swing.text.StyleConstants;

import javax.swing.*;

public class JFontChooser extends JDialog implements ActionListener {
	/**
	 * 
	 */

	
	JPanel topPanel;
	JPanel botPanel;
	
	JPanel topLeft;
	JPanel top;
	JPanel topRight;
	
	JLabel mainLabel;
	
	JTextField text1;
	JTextField text2;
	JTextField text3;
	JTextField maintext;
	
	JButton colorchooser;
	JButton exit;
	
	JList<String> list;
	JList<String> styleList;
	JList<String> size;
	
	JLabel label;
	
	String fontname;
	Font font;
	Color color;
	int style;
	boolean chosen;
	public JFontChooser() {
		chosen = false;
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		setSize(800, 550);
		setTitle("JFontChooser");
		color = Color.BLACK;
		
		setResizable(false);
		setLayout(new GridLayout(2,1));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		text1 = new JTextField();
		text2 = new JTextField();
		text3 = new JTextField();
		
		topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		topLeft = new JPanel();
		
		topLeft.setLayout(new BoxLayout(topLeft, BoxLayout.PAGE_AXIS));
		top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.PAGE_AXIS));
		topRight = new JPanel();
		topRight.setLayout(new BoxLayout(topRight, BoxLayout.PAGE_AXIS));
		
		
		botPanel = new JPanel();
		botPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.PAGE_AXIS));
		
		DefaultListModel<String> model = new DefaultListModel<>();
		for (int i = 0; i < fonts.length; i++) {
			model.addElement(fonts[i]);
		}
		list = new JList<>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		JScrollPane thelist = new JScrollPane(list);
		list.setSelectedIndex(0);
		
		DefaultListModel<String> model1 = new DefaultListModel<>();
		model1.addElement("Plain");
		model1.addElement("Bold");
		model1.addElement("Italics");
		model1.addElement("Italics+Bold");
		styleList = new JList<>(model1);
		styleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		styleList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		JScrollPane thelist2 = new JScrollPane(styleList);
		styleList.setSelectedIndex(0);
		
		DefaultListModel<String> model2 = new DefaultListModel<>();
		model2.addElement("8");
		model2.addElement("9");
		model2.addElement("10");
		model2.addElement("11");
		model2.addElement("12");
		model2.addElement("14");
		model2.addElement("16");
		model2.addElement("18");
		model2.addElement("20");
		model2.addElement("22");
		model2.addElement("24");
		model2.addElement("26");
		model2.addElement("28");
		model2.addElement("36");
		model2.addElement("48");
		model2.addElement("72");
		size = new JList<>(model2);
		size.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		size.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		JScrollPane thelist3 = new JScrollPane(size);
		size.setSelectedIndex(0);
		
		JLabel label = new JLabel("Font : ", JLabel.LEFT);
		label.setBorder(BorderFactory.createEmptyBorder(0,0,0,100));
		topLeft.add(label);
		text1.setMaximumSize(new Dimension(3000,10));;
		topLeft.add(thelist);
		
		top.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		JLabel label2 = new JLabel("Style : ", JLabel.LEFT);
		label2.setBorder(BorderFactory.createEmptyBorder(0,0,0,100));
		top.add(label2);
		text2.setMaximumSize(new Dimension(3000, 10));
		top.add(thelist2);
		
		
		JLabel label3 = new JLabel("Size : ", JLabel.LEFT);
		label3.setBorder(BorderFactory.createEmptyBorder(0,0,0,100));
		topRight.add(label3);
		text3.setMaximumSize(new Dimension(3000, 10));
		topRight.add(thelist3);
		
		
		mainLabel = new JLabel("", JLabel.CENTER);
		mainLabel.setMinimumSize(new Dimension(3000, 70));
		mainLabel.setMaximumSize(new Dimension(3000, 70));
		mainLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		
		maintext = new JTextField();
		maintext.setMaximumSize(new Dimension(3000, 20));
		
		
		topPanel.add(topLeft, BorderLayout.WEST);
		topPanel.add(top, BorderLayout.CENTER);
		topPanel.add(topRight, BorderLayout.EAST);
		botPanel.add(mainLabel);
		botPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		botPanel.add(maintext);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 10, 10, 10));
		colorchooser = new JButton("Color");
		colorchooser.addActionListener(this);
		exit = new JButton("Ok");
		exit.addActionListener(this);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(colorchooser);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(exit);
		
		botPanel.add(buttonPanel);
		
		add(topPanel);
		add(botPanel);
		
		new Timer(100, ae-> {
			String fontname = list.getSelectedValue();
			int choice = 0;
			switch (styleList.getSelectedValue()) {
			case "Bold":
				choice = 1;
				break;
			case "Italics":
				choice = 2;
				break;
			case "Plain":
				choice = 0;
				break;
			case "Italics+Bold":
				choice = 3;
				break;
			}
			style = choice;
			font = new Font(fontname,choice, Integer.parseInt(size.getSelectedValue()));
			mainLabel.setFont(font);
			mainLabel.setForeground(color);
			if (!maintext.getText().isEmpty()) {
			mainLabel.setText(maintext.getText());}
			else {
				mainLabel.setText("aAbBcC");
			}
		}).start();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				chosen = false;
			}
		});
		
		
	}
	
	public void setDefault(Font x) {
		font = x;
		switch (x.getStyle()) {
		case(0):
			styleList.setSelectedIndex(0);
			break;
		case(1):
			styleList.setSelectedIndex(1);
			break;
		case(2):
			styleList.setSelectedIndex(2);
			break;
		case(3):
			styleList.setSelectedIndex(3);
			break;
		}
		size.setSelectedIndex(sizeindexFinder(size, Integer.toString(font.getSize())));
		if (x.getName()=="Courier New") {
			list.setSelectedIndex(indexFinder(list, font.getName()));
		} else {
		list.setSelectedIndex(indexFinder(list, font.getName()));
		}
		
	}
	
	public int indexFinder(JList<String> x, String y) {
		int hold = 0;
		for (int i = 0; i < x.getModel().getSize(); i ++) {
			x.setSelectedIndex(i);
			if (x.getSelectedValue().matches(y)) {
				hold = i;
				break;
			}
		}
		return hold;
	}
	public int sizeindexFinder(JList<String> x, String y) {
		int hold = 0;
		for (int i = 0; i < x.getModel().getSize(); i ++) {
			x.setSelectedIndex(i);
			if (x.getSelectedValue().matches(y)) {
				hold = i;
				break;
			}
		}
		return hold;
	}
	
	public void setDefault(Color x) {
		color = x;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Font getFont() {
		return font;
	}
	
	public boolean showDialog(JFrame parent) {
		setLocationRelativeTo(parent);
		setModal(true);
		setVisible(true);
		if (chosen) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource()==colorchooser) {
			color = JColorChooser.showDialog(this,
		               "Font Colors", color);
		}else if (ae.getSource().equals(exit)) {
			chosen = true;
			dispose();
		}
		
	}

}
