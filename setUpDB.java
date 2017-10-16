
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Otter
 */
public class setUpDB {
    static DataBase db;
    /*
    static String filename1 = "//Users//Otter//Desktop//Mp3s//Issues.mp3";
    static String filename2 = "//Users//Otter//Desktop//Mp3s//ABC.mp3";
    static String filename3 = "//Users//Otter//Desktop//Mp3s//Friction.mp3";
    static String filename4 = "//Users//Otter//Desktop//Mp3s//humble.mp3";
    static String filename5 = "//Users//Otter//Desktop//Mp3s//ImTheOne.mp3";
    static String filename6 = "//Users//Otter//Desktop//Mp3s//FreeRoom.mp3";
    static String filename7 = "//Users//Otter//Desktop//Mp3s//Thunder.mp3";
    static String filename8 = "//Users//Otter//Desktop//Mp3s//LoveSick.mp3";
    static String filename9 = "//Users//Otter//Desktop//Mp3s//Sexual.mp3";
    static String filename10 = "//Users//Otter//Desktop//Mp3s//Stay.mp3";
    */
    static MusicPlayerController mpc;
    static PlayerGUI gui;
    
    //JFileChooser chooser = new JFileChooser();
    
    public static void main(String[] args) throws IOException, UnsupportedTagException, InvalidDataException, BasicPlayerException, InterruptedException, SQLException, Exception{
                
         
        /*
        String filename = "//Users//Otter//Desktop//Mp3s//Issues.mp3";
          
          System.out.println(mpc.getLength());
          
          mpc.playSong();
          Thread.sleep(mpc.getLength() / 2);
            mpc.stopSong();
                  */
       // mpc = new MusicPlayerController(filename);
        //String s = mpc.getTitle();
        
        
            
            
         
               
                db = new DataBase();
                
                
                db.dropTable();
                db.createSongsTable();
                //db.deleteOneSong("//Users//Otter//Desktop//Mp3s//Heya.mp3");
                /*
                db.insertSpecificsong(filename1);//Issues
                
                db.insertSpecificsong(filename2);//Issues
                db.insertSpecificsong(filename3);//Issues
                db.insertSpecificsong(filename4);//Issues
                db.insertSpecificsong(filename5);//Issues
                db.insertSpecificsong(filename6);//Issu                    db.insertSpecificsong(filename1);//Issues
                db.insertSpecificsong(filename7);//Issues
                db.insertSpecificsong(filename9);//Issues
                db.insertSpecificsong(filename10);//Issues
                */
                
              //  mpc = new MusicPlayerController(filename4);
                //mpc.playSong();
                System.out.println("kill me");
               db.PrintSongs();
               
               EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerGUI frame = new PlayerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
            }
               /*
                //System.out.println(db.pg.getSongTitle());
              //  db.dropTable();
              //  db.createSongsTable();
                //ArrayList<String> i = db.selectSongLocations();
                //db.PrintSongs();
                //inserting songs
                db.insertSpecificsong(filename1);//Issues
                db.insertSpecificsong(filename2);//Issues
                db.insertSpecificsong(filename3);//Issues
                db.insertSpecificsong(filename4);//Issues
                db.insertSpecificsong(filename5);//Issues
                db.insertSpecificsong(filename6);//Issu                    db.insertSpecificsong(filename1);//Issues
                db.insertSpecificsong(filename7);//Issues
                db.insertSpecificsong(filename9);//Issues
                db.insertSpecificsong(filename10);//Issues
                db.PrintSongs();
                
                
                /*
                for(int y = 0; y < i.size(); y++){
                    System.out.println(i.get(y));
                }
                        
            } catch (SQLException ex) {
                Logger.getLogger(setUpDB.class.getName()).log(Level.SEVERE, null, ex);
                 ex.printStackTrace();
            } catch (Exception ex) {
                Logger.getLogger(setUpDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerGUI frame = new PlayerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

           
        
    }
                       */
    
}
