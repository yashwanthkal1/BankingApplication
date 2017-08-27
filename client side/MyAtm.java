/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//import myatm.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yash
 */
public class MyAtm {

 
    public static void main(String[] args) {
      
        UserLogin userLogin=new UserLogin();
        userLogin.setVisible(true);
    }
    public static void createFiles() throws IOException{
        File userFile = new File("users.txt");
        File userData=new File("userData.txt");
        boolean creatUserFile = userFile.createNewFile();
        boolean createDataFile = userData.createNewFile();
       
    }
}
