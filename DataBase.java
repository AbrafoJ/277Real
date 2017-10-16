/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Otter
 */
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class DataBase {
    MusicPlayerController mpc;
   // MyDialog dialog;
    private int songCount = 0;
    //PlayerGUI pg; 
    Connection conn; //once a connection is established it stays
                     //as long as the code that created this
			     //instance does not exit

    Statement stat;  //stat can be reused in every operation
    //public static final String SQL_STATEMENT = "select * from channels";

    public DataBase()throws IOException, ClassNotFoundException, 
                            SQLException, Exception
    {
        //pg = new PlayerGUI();
      //  dialog = new MyDialog();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Derby driver not found.");
        }
  //In the string to getConnection you may replace "MP3Player"      
        try {
            
            conn = DriverManager.getConnection("jdbc:derby://localhost/MP3Player;create=true;user=APP;pass=APP");
            stat = conn.createStatement();
           // ResultSet resultSet = stat.executeQuery(SQL_STATEMENT);
            //ResultSetMetaData rsmd = resultSet.getMetaData();
            
            } catch (SQLException ex) {
                ex.printStackTrace();
        }      
    }
    
    public void createSongsTable() throws SQLException{
        String sqlCreate = "CREATE TABLE" + " Songs "
           // + "  (id           INTEGER,"
            + "   (location     VARCHAR(500),"
            + "  title           VARCHAR(20),"
            + " artist       VARCHAR(20),"
            + "genre        VARCHAR(20) )";
        
        stat = conn.createStatement();
        stat.execute(sqlCreate);
        
        /*
        try{
                PreparedStatement create = conn.prepareStatement(" CREATE TABLE Songs (id INT , Location VARCHAR(100) , Title VARCHAR(32), Artist VARCHAR(32) , Genre VARCHAR(32) ) ");
		//stat.execute("CREATE TABLE Songs(id INT ,Location VARCHAR(100), Title VARCHAR(32) , Artist VARCHAR(32), Genre VARCHAR(32) )");//SQL uses ALL CAPS,
                create.executeUpdate();
               // System.out.print("Created table");
                
        }catch(Exception e){
            
            System.out.println(e);
        }
        finally{
            System.out.println("Function complete!");
                    }
        */
        
    }
    
    public void insertSpecificsong(String local) throws SQLException, BasicPlayerException{
        try {
            mpc = new MusicPlayerController(local);
        } catch (IOException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedTagException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }//create an mpc to be able to get the title and artist and genre
       
        //keep track of id and update it so the next song has it's own number
       
        //songCount += 1;
        
        
        //get title
        String location = local;
        
        String title = mpc.getTitle();
        
        String artist = mpc.getArtist();
        
        String genre = mpc.getGenre();
        
        //check if it's there by calling the song array with all the inserted locations
        boolean isItIn = false;
        ArrayList array = this.selectSongLocations();
        for(int i = 0; i< array.size();i++){
            if(array.get(i).equals(local)){
                System.out.println("ERROR !: Cannot insert, already there.");
                return;
            }
        }
        
        try{
        
        PreparedStatement insert = conn.prepareStatement("INSERT INTO Songs VALUES("
                //+ id+ "," 
                + "'"+location +"',"
                +"'"+ title + "',"
                +"'"+ artist +"',"
                +"'"+ genre + "')"
        );
        insert.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
        finally{
            System.out.println("Inserted " + title + ".");
            
        }
        
        
        
        }
        
    
	
	public void deleteOneSong(String location) throws SQLException{
            ArrayList<String> s = selectSongLocations();
            
            boolean there = false;
            for(int i = 0;i<s.size();i++){
                if(s.get(i).equals(location)){
                    there = true;
                }
            }
            
            if(there){
                 System.out.println("Deleting song");
                 PreparedStatement delete = conn.prepareStatement("DELETE FROM Songs WHERE Location = "
                    + "'" + location +"'"
                );
                delete.executeUpdate();
            }
            
	}
        
        public void dropTable()throws SQLException{
            String query = "DROP TABLE Songs";
            PreparedStatement clear = conn.prepareStatement(query);
            
            clear.executeUpdate();
            
            //songCount = 0;
            
            
        }
        
        public ArrayList<String> selectSongLocations() throws SQLException{
            
            try{
            PreparedStatement select = conn.prepareStatement("SELECT location from Songs");
            ResultSet rs = select.executeQuery();
            
            ArrayList<String> array = new ArrayList<String>();
            
            while(rs.next()){
                //System.out.println(rs.getString("location"));
                
                array.add(rs.getString("location"));
            }
            //System.out.println("Checking songs in array");
            return array;
            }catch(Exception e){System.out.println(e);}
            /*
            return array;
            String query = "SELECT location FROM Songs";
            ResultSet m_ResultSet = stat.executeQuery(query);
            //String query = "INSERT INTO Songs = ";
                    */
            System.out.println("Returning null");
            return null;
        }
        
        public void PrintSongs() throws IOException, UnsupportedTagException, InvalidDataException, SQLException, BasicPlayerException{
            ArrayList<String> s = selectSongLocations();
            for(int i = 0;i<s.size();i++){
                mpc = new MusicPlayerController(s.get(i));
                System.out.println(mpc.getTitle()+" by " + mpc.getArtist()+ " in " + mpc.getGenre()+".");
            }
            
        }
        
        public int getSongCount() throws SQLException{
            ArrayList<String> s = selectSongLocations();
            int count = 0;
            for(int i = 0;i<s.size();i++){
                count++;
            }
            songCount = count;
            return count;
        }
        
        
}
