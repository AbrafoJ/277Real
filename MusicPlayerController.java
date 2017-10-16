
import com.mpatric.mp3agic.*;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javazoom.jlgui.basicplayer.*;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import static sun.audio.AudioPlayer.player;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Otter
 */
public class MusicPlayerController implements BasicPlayerListener {
    
    boolean playing = false;
    static Mp3File mp3;
    String mp3location;
    BasicController control;
    BasicPlayer player;
    static double volume = 0.5;
    private long duration;
    
    
    
    
   
    
    
    public MusicPlayerController (String s) throws IOException, UnsupportedTagException, InvalidDataException, BasicPlayerException{
       
        mp3location = s;
       
        this.mp3 = new Mp3File(s);
        
       player=new BasicPlayer();
         control =(BasicController)player;
         control.open(new File(mp3location));
        //player = new BasicPlayer();
        //control = (BasicController) player;
        //player.setGain(1);
        
        
        //this.mp3 = new Mp3File("//Users//Otter//Desktop//Mp3s//Issues.mp3");
        
    }
    
    public void playSong() throws BasicPlayerException, InterruptedException{
         playing = true;
         
        //player.addBasicPlayerListener(null);
       
            control.open(new File(mp3location));
            control.play();
            
    }
    
    public void changeSong(String s) throws IOException, UnsupportedTagException, InvalidDataException, BasicPlayerException{
        mp3location = s;
        control.stop();
        player = new BasicPlayer();
        
        control = (BasicController) player;
        
        
        //this.mp3 = new Mp3File(s);
        control.open(new File(s));
        
        control.play();
        control.setGain(volume);
        
        
    }
    public void stopSong() throws BasicPlayerException{
       // player.stop();
        control.pause();
    }
    
    public void pauseSong() throws BasicPlayerException{
        control.pause();
    }
    
    public void continueSong() throws BasicPlayerException{
        control.resume();
    }
    //returns the length in seconds of the mp3 file as a long type
    
    public long getLength(){
        return mp3.getLengthInMilliseconds();
    }
    
    //checks for tag
    public boolean iD3v1Check(){
        return mp3.hasId3v1Tag();
    }
    //checks for tag
    public boolean iD3v2Check(){
        return mp3.hasId3v2Tag();
    }
    //checks for tag
    public boolean customTagCheck(){
        return mp3.hasCustomTag();
    }
    
    public String getTitle(){
        
        if(mp3.hasId3v1Tag()){
            ID3v1 id3v1Tag = mp3.getId3v1Tag();
            return  id3v1Tag.getTitle();
        }
        else if(mp3.hasId3v2Tag()){
            ID3v2 id3v2Tag = mp3.getId3v2Tag();
                    if(id3v2Tag ==null){
                        System.out.println("LOL");
                    }
            return id3v2Tag.getTitle() + id3v2Tag.getComment();
        }
        else {
            return mp3.getFilename();
            
        }
           
        
    }
    
    public String getArtist(){
        if(mp3.hasId3v1Tag()){
            ID3v1 id3v1Tag = mp3.getId3v1Tag();
            return  id3v1Tag.getArtist();
            
        }
        else if(mp3.hasId3v2Tag()){
            ID3v2 id3v2Tag = mp3.getId3v2Tag();
                    if(id3v2Tag ==null){
                        System.out.println("LOL");
                    }
            return id3v2Tag.getArtist() + id3v2Tag.getComment();
            
        }
        else {
            return mp3.getFilename();
        }
    }
    
    public String getGenre(){
         if(mp3.hasId3v1Tag()){
            ID3v1 id3v1Tag = mp3.getId3v1Tag();
            return  id3v1Tag.getGenreDescription();
            
        }
        else if(mp3.hasId3v2Tag()){
            ID3v2 id3v2Tag = mp3.getId3v2Tag();
                    if(id3v2Tag ==null){
                        System.out.println("LOL");
                    }
            return id3v2Tag.getGenreDescription() + id3v2Tag.getComment();
            
        }
        else {
            return "Custom Genre";
        }
    }
    
    public String getComments(){
         if(mp3.hasId3v1Tag()){
            ID3v1 id3v1Tag = mp3.getId3v1Tag();
            return  id3v1Tag.getComment();
            
        }
        else if(mp3.hasId3v2Tag()){
            ID3v2 id3v2Tag = mp3.getId3v2Tag();
                    if(id3v2Tag ==null){
                        System.out.println("LOL");
                    }
            return id3v2Tag.getComment() + id3v2Tag.getComment();
            
        }
        else {
            return "Custom Genre";
        }
    }
    
    public String getYear(){
         if(mp3.hasId3v1Tag()){
            ID3v1 id3v1Tag = mp3.getId3v1Tag();
            return  id3v1Tag.getYear();
            
        }
        else if(mp3.hasId3v2Tag()){
            ID3v2 id3v2Tag = mp3.getId3v2Tag();
                    if(id3v2Tag ==null){
                        System.out.println("LOL");
                    }
            return id3v2Tag.getYear() + id3v2Tag.getComment();
            
        }
        else {
            return "Custom Genre";
        }
    }
    
    public String getAlbum(){
         if(mp3.hasId3v1Tag()){
            ID3v1 id3v1Tag = mp3.getId3v1Tag();
            return  id3v1Tag.getAlbum();
            
        }
        else if(mp3.hasId3v2Tag()){
            ID3v2 id3v2Tag = mp3.getId3v2Tag();
                    if(id3v2Tag ==null){
                        System.out.println("LOL");
                    }
            return id3v2Tag.getAlbum() + id3v2Tag.getComment();
            
        }
        else {
            return "Custom Genre";
        }
    }
    
    
    public String getFileName(){
        return mp3.getFilename();
    }
    
    public void increaseVolume() throws BasicPlayerException{
        if(volume < 1){
            volume += 0.05;
        control.setGain(volume);
        }
    }
    
    public void decreaseVolume() throws BasicPlayerException{
        if(volume > 0.0){
            volume -= 0.05;
          control.setGain(volume);
        }
        
        
        
    }
    
    public void adjustVolume(double i) throws BasicPlayerException{
        volume = i;
        control.setGain(volume);
    }
    
    public double getVolume() throws BasicPlayerException{
        return volume;
    }

    
    public void opened(Object stream, Map properties) {
       
    }

    @Override
    public void progress(int i, long l, byte[] bytes, Map map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stateUpdated(BasicPlayerEvent bpe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setController(BasicController bc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
