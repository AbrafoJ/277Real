
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Otter
 */
public class MyDialog extends JDialog {
    String filename;
    boolean OK = false;
    JFileChooser chooser;
    
    public MyDialog(){
        //run();
    }
    
    public String getFilename(){
        run();
        return filename;
    }
    public void run(){
        setSize(300,300);
        chooser = new JFileChooser();
        chooser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                filename =  getFile(e);
            }
            
        });
        add(chooser);
        setModal(true);
        
        
        
    }
    
    public String getFile(ActionEvent e){
        if(e.getActionCommand().equalsIgnoreCase("ApproveSelection")){
            filename = chooser.getSelectedFile().getAbsolutePath();
            OK = true;
        }
        else
            filename = "You wish";
            OK = false;
        setVisible(false);
        return filename;
        
        }
    
    public void setFile(){
        filename = getFilename();
    }
    
    }
    
    


    

