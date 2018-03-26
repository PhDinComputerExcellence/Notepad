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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Utilities;
import javax.swing.undo.UndoManager;
public class JNotepad {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Notepad();
			}
		});
	}

}

class Notepad implements ActionListener{
	JFrame frame;
	JMenuItem New, open, save, saveas, exit, PageSetup, Print, Undo, Cut, Copy, Paste, Delete,
	Find, FindNext, Replace, Goto, SelectAll, TimeDate, Font1, ViewHelp, About;
	JTextArea text;
	JCheckBox WordWrap, StatusBar;
	PrinterJob pj;
	UndoManager un;
	boolean modified = false;
	String dir;
	Highlighter h;
	String curstring;
	int cur;
	public Notepad() {
		dir = "";
		curstring = "";
		
		frame= new JFrame();
		frame.setTitle("Untitled - Notepad");
		ImageIcon img = new ImageIcon("JNotepad.png");
		frame.setIconImage(img.getImage());
		text = new JTextArea();
		cur = 0;
		text.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	modified = true;
	        	curstring = "";
	        }
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	modified = true;
	        	curstring = "";
	        }
	        @Override
	        public void changedUpdate(DocumentEvent arg0) {
	        	modified = true;
	        	curstring = "";
	        }
		});
		JPopupMenu popup = new JPopupMenu();
		Action cut = new DefaultEditorKit.CutAction();
		cut.putValue(Action.NAME, "Cut");
		Action copy = new DefaultEditorKit.CopyAction();
		copy.putValue(Action.NAME, "Copy");
		Action paste = new DefaultEditorKit.PasteAction();
		paste.putValue(Action.NAME, "Paste");
		popup.add(cut);
		popup.add(copy);
		popup.add(paste);
		text.setComponentPopupMenu(popup);
		Highlighter h = text.getHighlighter();
		JScrollPane textscroll = new JScrollPane(text);
		PrinterJob pj = PrinterJob.getPrinterJob();
		un = new UndoManager();
		text.getDocument().addUndoableEditListener(un);
		text.setFont(new Font("Courier New", Font.PLAIN, 12));
		JLabel label = new JLabel("Testing", JLabel.LEFT);
		frame.setSize(500, 500);
		frame.add(textscroll);
		frame.add(label,BorderLayout.SOUTH);
		label.setVisible(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu Help = new JMenu("Help");

		New = new JMenuItem("New");
		New.addActionListener(this);
		open = new JMenuItem("Open");
		open.addActionListener(this);
		save = new JMenuItem("Save");
		save.addActionListener(this);
		saveas = new JMenuItem("Save As");
		saveas.addActionListener(this);
		PageSetup = new JMenuItem("Page Setup");
		PageSetup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				PageFormat pf = pj.pageDialog(pj.defaultPage());
			}
		});
		Print = new JMenuItem("Print");
		Print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (pj.printDialog()) {
			        try {pj.print();}
			        catch (PrinterException exc) {
			           
			         }
			     }   
			}
		}
		);
		Undo = new JMenuItem("Undo");
		Undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				un.undo();
			}
		});
		Cut = new JMenuItem("Cut");
		Cut.addActionListener(this);
		Copy = new JMenuItem("Copy");
		Copy.addActionListener(this);
		Paste = new JMenuItem("Paste");
		Paste.addActionListener(this);
		Delete = new JMenuItem("Delete");
		Delete.addActionListener(this);
		Find = new JMenuItem("Find...");
		Find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JDialog dialog = new JDialog(frame, "Find");
			    dialog.setBounds(200, 200, 450, 125);
			    dialog.setResizable(false);
			    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			    JPanel main = new JPanel();
			    main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
			    JPanel panel1 = new JPanel();
			    panel1.setLayout(new BoxLayout(panel1, BoxLayout.LINE_AXIS));
			    panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
			    JLabel dialogLabel = new JLabel("Find : ");
			    JTextField textfield = new JTextField(1);
	//		    textfield.setMinimumSize(new Dimension(100, 20));
			    textfield.setMaximumSize(new Dimension(400, 20));
			    panel1.add(dialogLabel);
			    panel1.add(textfield);
			    main.add(panel1);
			    JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
				buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 5, 5, 5));
				JButton Find = new JButton("Find Next");
				JCheckBox box = new JCheckBox("Match Case");
				JRadioButton up = new JRadioButton("Up");
				JRadioButton down = new JRadioButton("Down");
				ButtonGroup group = new ButtonGroup();
				group.add(up);
				group.add(down);
				up.setSelected(true);
				Find.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String find = textfield.getText();
						String holder = text.getText();
						boolean thechoice;
						if (up.isSelected()) {
							thechoice = true;
						} else {
							thechoice = false;
						}
							curstring = textfield.getText();
							cur = 0;

							findNext(curstring,thechoice, box.isSelected() );
						
						
						
					}
				});
				JButton Cancel = new JButton("Cancel");
				Cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						h.removeAllHighlights();
						dialog.dispose();
					}
				});
				
				
				buttonPanel.add(Box.createHorizontalGlue());
				buttonPanel.add(box);
				buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
				buttonPanel.add(up);
				buttonPanel.add(down);
				buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
				buttonPanel.add(Find);
				buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
				buttonPanel.add(Cancel);
				
				
				
				main.add(buttonPanel);
				dialog.add(main);
				dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				dialog.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent ae) {
						h.removeAllHighlights();
						dialog.dispose();
					}
				});
			    dialog.setVisible(true);
			}
			
		});
		FindNext = new JMenuItem("Find Next");
		FindNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (curstring.equals("")) {
					JOptionPane.showMessageDialog(frame, "Word not specified! Use Find... instead!");
				} else {
					findNext(curstring, false, false);
				}
			}
		});
		Replace = new JMenuItem("Replace");
		Replace.addActionListener(this);
		Goto = new JMenuItem("Go To...");
		Goto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JDialog going = new JDialog(frame, "Go To Line Number");
				going.setBounds(0, 0, 450, 125);
			    going.setResizable(false);
				JPanel panel2 = new JPanel();
				panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
				panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
				JLabel Line = new JLabel("Line Number:");
				JTextField newfield = new JTextField();
				JPanel extra = new JPanel();
				extra.setLayout(new BoxLayout(extra, BoxLayout.LINE_AXIS));
				JButton OK = new JButton("OK");
				OK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						try {
						String holder = newfield.getText();
						int index = Integer.parseInt(holder);
						text.setCaretPosition(text.getDocument().getDefaultRootElement().getElement(index-1).getStartOffset());
						text.requestFocusInWindow();
						going.dispose();}
						catch (NullPointerException e) {
							JOptionPane.showMessageDialog(going, "Line Number doesn't exist!", "WARNING!", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				JButton Canceler = new JButton("Cancel");
				Canceler.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						going.dispose();
					}
				});
				going.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				extra.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
				extra.add(Box.createHorizontalGlue());
				extra.add(OK);
				extra.add(Box.createRigidArea(new Dimension(10, 0)));
				extra.add(Canceler);
				panel2.add(Line);
				panel2.add(newfield);
				panel2.add(extra);
				going.add(panel2);
				newfield.setMaximumSize(new Dimension(700, 20));
				going.setVisible(true);
				
				
				
			}
		});
		SelectAll = new JMenuItem("Select All");
		SelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				text.selectAll();
			}
		});
		TimeDate = new JMenuItem("Time and Date");
		TimeDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				Calendar currentdate = Calendar.getInstance();
				Date currenttime = currentdate.getTime();
				SimpleDateFormat date = new SimpleDateFormat("MM/d/yyyy");
		        SimpleDateFormat time = new SimpleDateFormat("h:mm a");
		        String x = date.format(currenttime);
		        String y = time.format(currenttime);
		        text.insert(y + " " + x, text.getCaretPosition());
		        
			}
		});
		WordWrap = new JCheckBox("Word Wrap");
		WordWrap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (WordWrap.isSelected()) {
					text.setLineWrap(true);
					text.setWrapStyleWord(true);
					Goto.setEnabled(false);
				} else {
					text.setLineWrap(false);
					text.setWrapStyleWord(false);
					Goto.setEnabled(true);
				}
			}
		});
		Font1 = new JMenuItem("Font...");
		Font1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				JFontChooser choose = new JFontChooser();
				choose.setDefault(text.getFont()); 
		  		choose.setDefault(text.getForeground());
		  		if (choose.showDialog(frame)) { // user selected ok 
		  			text.setFont(choose.getFont()); 
		  			text.setForeground(choose.getColor());
		  		}
		  		choose.dispose();
			}
			
		});
		JMenuItem background = new JMenuItem("Background Color");
		background.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				JColorChooser stuff = new JColorChooser();
				Color holder = JColorChooser.showDialog(frame,
			               "Font Colors", text.getBackground());
				if(holder != null){
                    frame.getContentPane().setBackground(holder);
                }
				text.setBackground(holder);
			}
		});
		StatusBar = new JCheckBox("Status Bar");
		ViewHelp = new JMenuItem("View Help");
		ViewHelp.addActionListener(this);
		About = new JMenuItem("About JNotepad");
		About.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "(C) Kevin Lee", "About JNotePad",JOptionPane.INFORMATION_MESSAGE);
			}
		});
 



		file.add(New);
		New.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_N, 
		        java.awt.Event.CTRL_MASK));
		New.setMnemonic('N');
		file.add(open);
		open.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_O, 
		        java.awt.Event.CTRL_MASK));
		file.add(save);
		save.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_S, 
		        java.awt.Event.CTRL_MASK));
		file.add(saveas);
		file.addSeparator();
		file.add(PageSetup);
		PageSetup.setMnemonic('u');
		file.add(Print);
		Print.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_P, 
		        java.awt.Event.CTRL_MASK));
		file.addSeparator();
		exit = new JMenuItem("Exit");
		exit.setMnemonic('x');
		exit.addActionListener(this);
		file.add(exit);

		JMenu edit = new JMenu("Edit");
		edit.add(Undo);
		Undo.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_Z, 
		        java.awt.Event.CTRL_MASK));
		edit.addSeparator();
		edit.add(Cut);
		Cut.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_X, 
		        java.awt.Event.CTRL_MASK));
		edit.add(Copy);
		Copy.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_C, 
		        java.awt.Event.CTRL_MASK));
		edit.add(Paste);
		Paste.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_V, 
		        java.awt.Event.CTRL_MASK));
		edit.add(Delete);
		Delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		edit.addSeparator();
		edit.add(Find);
		Find.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_F, 
		        java.awt.Event.CTRL_MASK));
		edit.add(FindNext);
		edit.add(Replace);
		Replace.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_H, 
		        java.awt.Event.CTRL_MASK));
		edit.add(Goto);
		Goto.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_G, 
		        java.awt.Event.CTRL_MASK));
		edit.addSeparator();
		edit.add(SelectAll);
		SelectAll.setAccelerator(KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_A, 
		        java.awt.Event.CTRL_MASK));
		edit.add(TimeDate);
		TimeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));


		JMenu format = new JMenu("Format");
		format.add(WordWrap);
		WordWrap.setMnemonic('W');
		format.add(Font1);
		Font1.setMnemonic('F');
		format.addSeparator();
		format.add(background);

		JMenu view = new JMenu("View");
		view.add(StatusBar);
		
		Help.add(ViewHelp);
		ViewHelp.setMnemonic('H');
		Help.addSeparator();
		Help.add(About);

		menubar.add(file);
		file.setMnemonic('F');
		menubar.add(edit);
		edit.setMnemonic('E');
		menubar.add(format);
		format.setMnemonic('o');
		menubar.add(view);
		view.setMnemonic('V');
		menubar.add(Help);
		Help.setMnemonic('H');
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (modified) {
					int choice =JOptionPane.showConfirmDialog(frame, "It seems that your Document has been modified. Do you want to Save?", "WARNING!", JOptionPane.YES_NO_CANCEL_OPTION);
					switch (choice) {
					case JOptionPane.YES_OPTION:
						if (dir!="") {
							try {
								save(true);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							frame.dispose();
							break;
						} else {
							try {
								save(false);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							System.exit(1);
							break;
						}
					case JOptionPane.NO_OPTION:
						System.exit(1);
						break;
					case JOptionPane.CANCEL_OPTION:
						break;
					};
				} else {
					System.exit(1);
				}
			}
		});


		frame.setJMenuBar(menubar);
		new Timer(200, ae->{
			int carot = text.getCaretPosition();
			int row = 1;
			int col = 1;
			try {
				row = text.getLineOfOffset(carot);
				col =text.getLineStartOffset(row);
			} catch (BadLocationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			carot = text.getCaretPosition();
			row = (carot == 0) ? 1 : 0;
			for (int offset = carot; offset > 0;) {
			    try {
					offset = Utilities.getRowStart(text, offset) - 1;
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    row++;
			}
			int offset = 0;
			try {
				offset = Utilities.getRowStart(text, carot);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			col = carot - offset + 1; 
			label.setText("Line:" + (row) + " Col:" + (col) );
			
			
		}
		).start();
		StatusBar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (StatusBar.isSelected()) {
					label.setVisible(true);
				} else {
					label.setVisible(false);
				}
			}
		});




		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae){
		if (ae.getSource()==New) {
			if (modified) {
				int opt =JOptionPane.showConfirmDialog(frame, "Do you want to saved your current file?", "Warning!",JOptionPane.YES_NO_CANCEL_OPTION);
				switch(opt){
                case JOptionPane.YES_OPTION:
                    if (dir=="") {
                    	try {
							save(false);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    } else {
                    	try {
							save(true);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                    frame.setTitle("Untitled - Notepad");
    				text.setText("");
    				modified = false;
    				dir = "";
    				dir = "";
                    break;
                case JOptionPane.NO_OPTION:
                	frame.setTitle("Untitled - Notepad");
    				text.setText("");
    				modified = false;
    				dir = "";
                    break;
                case JOptionPane.CLOSED_OPTION:
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;

            }
			} else {
				frame.setTitle("Untitled - Notepad");
				text.setText("");
				modified = false;
				dir = "";
			}
		} else if (ae.getSource()==exit) {
			frame.dispose();
			System.exit(1);
		} else if(ae.getSource()==open) {
			if (modified) {
				int opt =JOptionPane.showConfirmDialog(frame, "Do you want to saved your current file?", "Warning!",JOptionPane.YES_NO_CANCEL_OPTION);
				switch (opt) {
				case JOptionPane.OK_OPTION:
					if (dir!="") {
						try {
							save(true);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						try {
							save(false);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					JFileChooser filechoose = new JFileChooser();
					FileNameExtensionFilter filter2 = new FileNameExtensionFilter(".java files", "java", "java");
					FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt", "text");
					filechoose.setFileFilter(filter);
					filechoose.setFileFilter(filter2);
					int value = filechoose.showOpenDialog(null);
					if (value == JFileChooser.APPROVE_OPTION) {
						File file = filechoose.getSelectedFile();
						dir = filechoose.getSelectedFile().getAbsolutePath();
						try {
							fileRead(file);
							frame.setTitle(filechoose.getSelectedFile().getName() + " - Notepad");
							modified = false;
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					break;
				case JOptionPane.CANCEL_OPTION:
					break;
				case JOptionPane.NO_OPTION:
					JFileChooser filechoose1 = new JFileChooser();
					FileNameExtensionFilter filter3 = new FileNameExtensionFilter(".java files", "java", "java");
					FileNameExtensionFilter filter4 = new FileNameExtensionFilter(".txt files", "txt", "text");
					filechoose1.setFileFilter(filter3);
					filechoose1.setFileFilter(filter4);
					int value1 = filechoose1.showOpenDialog(null);
					if (value1 == JFileChooser.APPROVE_OPTION) {
						File file = filechoose1.getSelectedFile();
						dir = filechoose1.getSelectedFile().getAbsolutePath();
						try {
							fileRead(file);
							frame.setTitle(filechoose1.getSelectedFile().getName() + " - Notepad");
							modified = false;
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					
				}
			} else {
				JFileChooser filechoose = new JFileChooser();
				FileNameExtensionFilter filter2 = new FileNameExtensionFilter(".java files", "java", "java");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt", "text");
				filechoose.setFileFilter(filter);
				filechoose.setFileFilter(filter2);
				int value = filechoose.showOpenDialog(null);
				if (value == JFileChooser.APPROVE_OPTION) {
					File file = filechoose.getSelectedFile();
					dir = filechoose.getSelectedFile().getAbsolutePath();
					try {
						fileRead(file);
						frame.setTitle(filechoose.getSelectedFile().getName() + " - Notepad");
						modified = false;
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		} else if(ae.getSource()==saveas) {
			try {
				save(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (ae.getSource()==save){
			if (dir=="") {
				try {
					save(false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					save(true);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		else if (ae.getSource()==Cut) {
			text.cut();
			text.updateUI();
		}else if (ae.getSource()==Copy) {
			text.copy();
			text.updateUI();
		}else if (ae.getSource()==Paste) {
			text.paste();
			text.updateUI();
		}

	}
	
	public void findNext(String find, boolean upordown, boolean match) {
		
		String holder = text.getText();
		curstring = find;
		if (!match) {
			find = find.toLowerCase();
			holder = holder.toLowerCase();
		}
		int index;
		int last = find.length();
		if (upordown) {
			if (text.getSelectedText()!=null && text.getSelectedText().equalsIgnoreCase(find)) {
			index = holder.substring(0, text.getCaretPosition()-find.length()).lastIndexOf(find);}
			else {
				index = holder.substring(0, text.getCaretPosition()).lastIndexOf(find);
			}
			if (index!=-1) {
			int indexhold = index;
			int lasthold = index+last;
			text.select(indexhold, lasthold);
			}else {
				JOptionPane.showMessageDialog(frame, "Can't find any more");
			}
				
		} else {
			index = holder.substring(text.getCaretPosition(),holder.length()).indexOf(find);
			if (index!=-1) {
			text.select(text.getCaretPosition()+index, text.getCaretPosition()+index+last);
			} else {
				JOptionPane.showMessageDialog(frame, "Can't find any more");
			}
		}
		
//		while ( index >= 0 ) {
//			int len = find.length();
//		    if (upordown) {
//		    	int index
//		    		
//		    			text.select(index, index+len);
//
//		    } else if(!upordown ) {
//		    	cur++;
//		    		text.select(index, index+len);
//		    	
//		    }
//		    
//		    index = holder.indexOf(find, index+len);
//		}
	}
	    
	public void fileRead(File x) throws IOException {
		text.setText("");
		BufferedReader read= new BufferedReader(new FileReader(x));
		String line = read.readLine();
		while (line!= null) {
			text.append(line);
			line = read.readLine();
			text.append("\n");
		}
		read.close();

	}

	public void save(boolean alreadyexists) throws IOException {
		Boolean exists = true;
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/home/me/Documents"));
		while (exists && !alreadyexists) {
			int option = chooser.showSaveDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {
				String hold;
				File file = chooser.getSelectedFile();
				if (file.exists()) {
					int results = JOptionPane.showConfirmDialog(chooser, "Do you want to overwrite existing file?", "File Exists!",JOptionPane.YES_NO_CANCEL_OPTION);
					switch(results){
	                case JOptionPane.YES_OPTION:
	                    exists = false;
	                    break;
	                case JOptionPane.NO_OPTION:
	                    break;
	                case JOptionPane.CLOSED_OPTION:
	                    break;
	                case JOptionPane.CANCEL_OPTION:
	                    break;

	            }
				}
				if (!exists) {
					String newname;
					if (filetypecheck(chooser.getSelectedFile().getAbsolutePath())){
						hold = chooser.getSelectedFile().getAbsolutePath();
						newname = chooser.getSelectedFile().getName();
						
					} else {
						hold = chooser.getSelectedFile().getAbsolutePath() + ".txt";
						newname = chooser.getSelectedFile().getName()+".txt";
					}

					FileWriter writer = new FileWriter(hold);
					dir = hold;
					writer.write(text.getText());
					writer.close();
					modified = false;
					frame.setTitle(newname + " - Notepad");
				} else {
					String newname;
					if (filetypecheck(chooser.getSelectedFile().getAbsolutePath())){
						hold = chooser.getSelectedFile().getAbsolutePath();
						newname = chooser.getSelectedFile().getName();
						
					} else {
						hold = chooser.getSelectedFile().getAbsolutePath() + ".txt";
						newname = chooser.getSelectedFile().getName()+".txt";
					}
					
					FileWriter writer = new FileWriter(hold);
					writer.write(text.getText());
					writer.close();
					exists =false;
					modified = false;
					frame.setTitle(newname + " - Notepad");
				}
				} else {
					exists = false;
				}
		}
		if (alreadyexists) {
			FileWriter writer = new FileWriter(dir);
			writer.write(text.getText());
			writer.close();
			modified = false;
		}
	}

	public boolean filetypecheck(String x) {
		if (x.substring(x.length()-4, x.length()).equals(".txt")) {
			return true;
		} else {
			return false;
		}
	}
}
