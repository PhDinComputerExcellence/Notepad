import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
	Find, FindNext, Replace, Goto, SelectAll, TimeDate, WordWrap, Font, StatusBar, ViewHelp, About;
	JTextArea text;
	PrinterJob pj;
	UndoManager un;
	boolean modified = false;
	String dir;
	public Notepad() {
		dir = "";
		
		frame= new JFrame();
		frame.setTitle("Untitled - Notepad");
		text = new JTextArea();
		text.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	modified = true;
	        }
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	modified = true;
	        }
	        @Override
	        public void changedUpdate(DocumentEvent arg0) {
	        	modified = true;
	        }
		});
		JScrollPane textscroll = new JScrollPane(text);
		PrinterJob pj = PrinterJob.getPrinterJob();
		un = new UndoManager();
		text.getDocument().addUndoableEditListener(un);

		frame.add(textscroll);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

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
		Find.addActionListener(this);
		FindNext = new JMenuItem("Find Next");
		FindNext.addActionListener(this);
		Replace = new JMenuItem("Replace");
		Replace.addActionListener(this);
		Goto = new JMenuItem("Go To...");
		Goto.addActionListener(this);
		SelectAll = new JMenuItem("Select All");
		SelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				text.selectAll();
			}
		});
		TimeDate = new JMenuItem("Time and Date");
		TimeDate.addActionListener(this);
		WordWrap = new JMenuItem("Word Wrap");
		WordWrap.addActionListener(this);
		Font = new JMenuItem("Font...");
		Font.addActionListener(this);
		StatusBar = new JMenuItem("Status Bar");
		StatusBar.addActionListener(this);
		ViewHelp = new JMenuItem("View Help");
		ViewHelp.addActionListener(this);
		About = new JMenuItem("About JNotepad");
		About.addActionListener(this);




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
		format.add(Font);
		Font.setMnemonic('F');

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


		frame.setJMenuBar(menubar);






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
                    break;
                case JOptionPane.NO_OPTION:
                	frame.setTitle("Untitled - Notepad");
    				text.setText("");
    				modified = false;
                    break;
                case JOptionPane.CLOSED_OPTION:
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;

            }
			} else {
				frame.setTitle("Untitled - Notepad");
				text.setText("");
			}
		} else if (ae.getSource()==exit) {
			frame.dispose();
		} else if(ae.getSource()==open) {
			if (modified) {
				
			} else {
				JFileChooser filechoose = new JFileChooser();
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
