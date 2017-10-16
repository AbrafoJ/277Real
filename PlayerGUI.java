
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import static com.oracle.nio.BufferSecrets.instance;
import static com.sun.javafx.image.impl.IntArgb.ToIntArgbPreConv.instance;
import static com.sun.xml.internal.fastinfoset.stax.events.EmptyIterator.instance;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.*;
import javax.swing.JTextField;
import javazoom.jlgui.basicplayer.BasicPlayerException;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;



import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.sql.SQLException;
import java.util.Map;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import static jdk.nashorn.internal.objects.Global.instance;
import static sun.font.SunLayoutEngine.instance;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Otter
 */
public class PlayerGUI extends JFrame implements ActionListener  {
    
    private JPanel contentPane;
	private JTable table = new JTable();
	private JTextField textField;
	private JTextField textField_1;
        private JMenuBar menuBar;
        JProgressBar progressBar;
        private JPopupMenu pop;
	
	private ArrayList<String> songPaths;
	private DataBase db;
	private JMenuItem mntmShuffle_1 ;
	private JMenuItem mntmRepeat_1;

	private MusicPlayerController mcp;

	//table values
	private String[] columnNames= {"Title", "Artist","Genre","Ablum", "Year","Comments"};
	private String[][] data;
        private int index = 0;

	//booleans
	private boolean shuffle;
	private boolean repeat;
	private boolean playing;
        private boolean needtoStop;
        private boolean songSelected = true;
        
        
        private long duration;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public PlayerGUI() throws ClassNotFoundException, SQLException, Exception {
		playing = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 645, 395);
		
             menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//first menu inside menubar
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);

		//open menuItem inside menu
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(this);
		mntmOpen.setActionCommand("Open");
		mnMenu.add(mntmOpen);
		
		//close menutItem inside menu
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(this);
		mntmClose.setActionCommand("Close");
		mnMenu.add(mntmClose);
		
		//add inside menu
		JMenuItem mntmAdd = new JMenuItem("Add");
		mntmAdd.addActionListener(this);
		mntmAdd.setActionCommand("Add");
		mnMenu.add(mntmAdd);
		
		//delete menuItem inside menu
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.addActionListener(this);
		mntmDelete.setActionCommand("Delete");
		mnMenu.add(mntmDelete);
		
		//second menu insdie menuBar
		JMenu mnControls = new JMenu("Controls");
		menuBar.add(mnControls);
		
		//play menuItem inside Controls
		JMenuItem mntmPlay = new JMenuItem("Play");
		mntmPlay.addActionListener(this);
		mntmPlay.setActionCommand("Play");
		mntmPlay.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_SPACE));
		mnControls.add(mntmPlay);
		
		//next menuitem inside Controls
		JMenuItem mntmNext = new JMenuItem("Next");
		mntmNext.addActionListener(this);
		mntmNext.setActionCommand("Next");
		mntmNext.setAccelerator(KeyStroke.getKeyStroke((char)KeyEvent.VK_RIGHT,ActionEvent.CTRL_MASK));
		mnControls.add(mntmNext);
		
		JMenuItem mntmPrevious = new JMenuItem("Previous");
		mntmPrevious.addActionListener(this);
		mntmPrevious.setActionCommand("Previous");
		mntmPrevious.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,ActionEvent.CTRL_MASK));
		mnControls.add(mntmPrevious);
		
		//go to current song menuItem inside Controls
		JMenuItem mntmGoToCurrent = new JMenuItem("Go to Current Song");
		mntmGoToCurrent.addActionListener(this);
		mntmGoToCurrent.setActionCommand("GoToCurrent");
		mntmGoToCurrent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK));
		mnControls.add(mntmGoToCurrent);
		
		//Increase Volume inside Controls
		JMenuItem mntmIncreaseVolume = new JMenuItem("Increase Volume");
		mntmIncreaseVolume.addActionListener(this);
		mntmIncreaseVolume.setActionCommand("IncrVolume");
		mntmIncreaseVolume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
		mnControls.add(mntmIncreaseVolume);
		
		//Decrease Volume inside Controls
		JMenuItem mntmDecreaseVolume = new JMenuItem("Decrease Volume");
		mntmDecreaseVolume.addActionListener(this);
		mntmDecreaseVolume.setActionCommand("DecrVolume");
		mntmDecreaseVolume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.CTRL_MASK));
		mnControls.add(mntmDecreaseVolume);
		
		//Shuffle menuItem inside Controls
		JMenuItem mntmShuffle = new JMenuItem("Shuffle");
		mntmShuffle.addActionListener(this);
		mntmShuffle.setActionCommand("Shuffle");
		mnControls.add(mntmShuffle);
		
		//repeat menuItem inside Controls
		JMenuItem mntmRepeat = new JMenuItem("Repeat");
		mntmRepeat.addActionListener(this);
		mntmRepeat.setActionCommand("Repeat");
		mnControls.add(mntmRepeat);
		
		//third menu in MenuBar
		JMenu mnPlayRecent = new JMenu("Play Recent");
		menuBar.add(mnPlayRecent);
		
		//textfield to show time of song
		textField = new JTextField("00:00:00");
		menuBar.add(textField);
		textField.setColumns(5);
                boolean falsevalue = false;
		textField.setEditable(falsevalue);
       
		
		//Progressbar for the song
		progressBar = new JProgressBar(0,100);
		menuBar.add(progressBar);
		
		//textfield to show time of song left
		textField_1 = new JTextField("00:00:00");
		menuBar.add(textField_1);
		textField_1.setColumns(5);
		textField_1.setEditable(false);
		
		//shuffle item in MenuBar
		mntmShuffle_1 = new JMenuItem("Shuffle");
		mntmShuffle_1.addActionListener(this);
		mntmShuffle_1.setActionCommand("Shuffle");
		menuBar.add(mntmShuffle_1);
		
		//repeat inside menuBar
		mntmRepeat_1 = new JMenuItem("Repeat");
		mntmRepeat_1.addActionListener(this);
		mntmRepeat_1.setActionCommand("Repeat");
		menuBar.add(mntmRepeat_1);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//Table for contents
		table = new JTable();
                
		//songPaths = new ArrayList<String>();
		db = new DataBase();
		createTable();//should initialize table
                JScrollPane tableCont = new JScrollPane(table);
		contentPane.add(tableCont, BorderLayout.CENTER);
                
                JPopupMenu popupMenu = new JPopupMenu();
                addPopup(table, popupMenu);

                JMenuItem mntmAdd_1 = new JMenuItem("Add");
                mntmAdd_1.addActionListener(this);
                mntmAdd_1.setActionCommand("Add");
                popupMenu.add(mntmAdd_1);

                JMenuItem mntmDelete_1 = new JMenuItem("Delete");
                mntmDelete_1.addActionListener(this);
                mntmDelete_1.setActionCommand("Delete");
                popupMenu.add(mntmDelete_1);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		//previous button at bottom
		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(this);
		btnPrevious.setActionCommand("Previous");
		panel.add(btnPrevious);
		
		//play button at bottom
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(this);
		btnPlay.setActionCommand("Play");
		panel.add(btnPlay);

		//pause button at bottom
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(this);
		btnPause.setActionCommand("Pause");
		panel.add(btnPause);

		//stop bottom at bottom
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(this);
		btnStop.setActionCommand("Stop");
		panel.add(btnStop);
		
		//next button at bottom
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(this);
		btnNext.setActionCommand("Next");
		panel.add(btnNext);
		
		//slider 
		JSlider slider = new JSlider();
		panel.add(slider);
                /*
                double i = slider.getValue()/100;
                mcp.adjustVolume(i);
		*/
		

	}

        private void updatePogressBar(){
            
        }

	public void createTable() throws SQLException, IOException, UnsupportedTagException, InvalidDataException, PrinterException, BasicPlayerException{
            
		songPaths = db.selectSongLocations();
		//final String [] columnNames = {"Title", "Artist","Genre"};
                data = new String[songPaths.size()][6];
               // data[1][]='';
		for(int i = 0; i<songPaths.size();i++){//create table right herrrrrr
                    
                        mcp = new MusicPlayerController(songPaths.get(i));
                        String title = mcp.getTitle();
                        String artist = mcp.getArtist();
                        String genre = mcp.getGenre();
                        String album = mcp.getAlbum();
                        String year = mcp.getYear();
                        String comm = mcp.getComments();
                        //System.out.println(" Entering new row");
                    data[i][0] = title;
                    data[i][1] = artist;
                    data[i][2] = genre;
                    data[i][3] = album;
                    data[i][4] = year;
                    data[i][5] = comm;
                }
                
                
                
                    for (String[] a : data) {
                        for (String y : a) {
                            System.out.print(y + "\t");
                        }
                        System.out.println("\n");
                    }
                    

		
                DefaultTableModel tm;
        tm = new DefaultTableModel(data,columnNames){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
                //table.setModel(tm);
                //table = new JTable(tm);
                 table.setModel(tm);
        
                table.getTableHeader().setReorderingAllowed(false);
               // table = new JTable();
               // table.print();
		table.setDragEnabled(true);
               // table.setDropMode(DropMode.INSERT_ROWS);
                table.setDropTarget(new AddToTableDropTarget());
                
                //table.setTransferHandler(new TableRowTransferHandler(table));
                
		table.setRowSelectionAllowed(true);
                
                //table.setDropTarget();
                
                table.addMouseListener(new MouseAdapter() {
                     public void mousePressed(MouseEvent me) {
                        JTable table =(JTable) me.getSource();
                            Point p = me.getPoint();
                            int row = table.rowAtPoint(p);
                            if (me.getClickCount() == 2) {
                                for(int i = 0; i<songPaths.size();i++){
                                    if(row == i){
                                        try {
                                            index = row;
                                            System.out.println("New song");
                                            try {
                                                mcp.changeSong(songPaths.get(i));
                                                //mcp = new MusicPlayerController(songPaths.get(i));
                                            } catch (BasicPlayerException ex) {
                                                Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        } catch (IOException ex) {
                                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (UnsupportedTagException ex) {
                                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (InvalidDataException ex) {
                                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                                   
                             }
                        }
                    });
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()){
		case "Open":
		JFileChooser open = new JFileChooser();
                open.showOpenDialog(null);
            File file = open.getSelectedFile();
                {
                    try {
                        try {
                            mcp = new MusicPlayerController(file.getPath());
                        } catch (BasicPlayerException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedTagException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidDataException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                {
                    try {
                        mcp.playSong();
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
			break;
		case "Close":
			System.exit(0);
			break;
		case "Add":
                    
			JFileChooser open1 = new JFileChooser();
                         open1.showOpenDialog(null);
                          File file1 = open1.getSelectedFile();
            songPaths.add(file1.getPath());
                {
                    try {
                        db.insertSpecificsong(file1.getPath());
                    } catch (SQLException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                {
                    try {
                        try {
                            try {
                                
                                createTable();
                                
                                
                            } catch (BasicPlayerException ex) {
                                Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (PrinterException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedTagException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidDataException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
			break;
		case "Delete":
                    if(!table.getSelectionModel().isSelectionEmpty()){
                        int i = table.getSelectedRow();
                        String del = songPaths.get(i);
                    try {
                        db.deleteOneSong(del);
                    } catch (SQLException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        createTable();
                    } catch (SQLException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedTagException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidDataException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (PrinterException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        mcp.stopSong();
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
			break;
		case "Play":
                 
        	if(!playing){
            	try {
                	mcp.playSong();
            	} catch (BasicPlayerException ex) {
                	Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
            	} catch (InterruptedException ex) {
                	Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
            	}
            	playing = true;
        	}else{
        		try {
                       mcp.continueSong();
                   } catch (BasicPlayerException ex) {
                       Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                   }
        	}
        	break;
		case "Pause":
				try {
                    mcp.pauseSong();
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
			break;
		case "Stop":
			try {
                mcp.stopSong();
             } catch (BasicPlayerException ex) {
              	    Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
             }
             playing = false;
			break;
		case "Next":
                {
                    if(repeat){
                        try {
                            mcp.playSong();
                        } catch (BasicPlayerException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else if(shuffle){
                        index = (int) (Math.random() * songPaths.size() - 1);
                        try {
                            mcp.changeSong(songPaths.get(index));
                        } catch (IOException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnsupportedTagException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidDataException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BasicPlayerException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else if(index == songPaths.size() -1){
                        index = 0;
                    try {
                        //index = songPaths.indexOf(mcp.getFileName());//getIndex(mcp.getFileName());
                        mcp.changeSong(songPaths.get(index));
                    } catch (IOException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedTagException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidDataException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    else{
                        index += 1;
                        try {
                            mcp.changeSong(songPaths.get(index));
                        } catch (IOException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnsupportedTagException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidDataException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BasicPlayerException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
			
			}
			break;
                case "Previous":
                     if(index == 0){
                        index = songPaths.size()-1;
                    try {
                        //index = songPaths.indexOf(mcp.getFileName());//getIndex(mcp.getFileName());
                        mcp.changeSong(songPaths.get(index));
                    } catch (IOException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedTagException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidDataException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    else{
                        index -= 1;
                        try {
                            mcp.changeSong(songPaths.get(index));
                        } catch (IOException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnsupportedTagException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidDataException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BasicPlayerException ex) {
                            Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
		case "GoToCurrent":
                   scrollToVisible(table, table.getSelectedRow(), table.getSelectedColumn());
			break;
		case "IncrVolume":
                {
                    try {
                        mcp.increaseVolume();
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
			break;
		case "DecrVolume":
                    try {
                        mcp.decreaseVolume();
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(PlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
			break;
		case "Shuffle":
			shuffle = !shuffle;
			if(shuffle){
				mntmShuffle_1.setBackground(Color.CYAN);
			}else{
				mntmShuffle_1.setBackground(new JMenuItem().getBackground());
			}
			break;
		case "Repeat":
			repeat = !repeat;
			if(repeat){
				mntmRepeat_1.setBackground(Color.CYAN);
			}else{
				mntmRepeat_1.setBackground(new JMenuItem().getBackground());
			}
			break;
			
		}
	}


     private class AddToTableDropTarget extends DropTarget {
        @Override
        public synchronized void drop(DropTargetDropEvent dtde) {
            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            Transferable t = dtde.getTransferable();
            java.util.List fileList;
            try {
                fileList = (java.util.List) t.getTransferData(DataFlavor.javaFileListFlavor);
                for(Object file : fileList) {
                    MusicPlayerController temp = new MusicPlayerController(file.toString());
                    db.insertSpecificsong(file.toString());
                    //songPaths = db.selectSongLocations();
                    createTable();
                    
                        // If this is the main application window & the music table == library
                        // Only add song to library table if it is not already present in db
                    /*
                        int id = ShiTunes.db.insertSong(song);
                        if (id != -1) {
                            // if song successfully added to database
                            // add song to music library table
                            table.addSongToTable(id, temp);
                        }
                    */
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     
        private static void addPopup(Component component, final JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
        showMenu(e);
        }
        }
        public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
        showMenu(e);
        }
        }
        private void showMenu(MouseEvent e) {
        popup.show(e.getComponent(), e.getX(), e.getY());
        }
        });
    }
        public static void scrollToVisible(JTable table, int rowIndex, int vColIndex)
            {
    if (!(table.getParent() instanceof JViewport)) return;
    JViewport viewport = (JViewport)table.getParent();
    Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);
    Point pt = viewport.getViewPosition();
    rect.setLocation(rect.x-pt.x, rect.y-pt.y);
    viewport.scrollRectToVisible(rect);
            }
        
}



    
    
    

