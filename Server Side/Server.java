/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package myatm;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Server {
    public int port = 9274;
    public static ServerSocket ss=null;
    public static Socket s=null;
    public static String current_name;
    public static String current_id;
    public static Set<String> accounts=new HashSet<String>();
    public Server() throws IOException{
  }
   
public static boolean adminAuthenticate(){
    while(true){
String name="";
String pass="";   
try {
ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
LoginBean loginBean = (LoginBean)ois.readObject();
if (loginBean!=null)
    name=loginBean.getName();
    pass=loginBean.getP();
//is.close();
}catch(Exception e){System.out.println(e);}
if(name=="admin"&&pass=="admin"){
return true;
}else{
return false;
}}
}
 public static void createFiles() throws IOException{
        File userFile = new File("users.txt");
        File userData=new File("userData.txt");
        boolean creatUserFile = userFile.createNewFile();
        boolean createDataFile = userData.createNewFile();
       
    }
public static void main(String[] args) throws IOException, ClassNotFoundException{
   ServerSocket serverSocket=new ServerSocket(9274);
   String name="";
   String p="";
   createFiles();
   while(true){ /// while loop handle every request.
   try{
        Socket socket=serverSocket.accept();// port is open untill to you close this file
        ObjectInputStream get = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream send=new ObjectOutputStream(socket.getOutputStream());
        String choise=(String)get.readObject();
        setAccounts();
        if(choise.equals("adminLogin")){
        LoginBean loginBean = (LoginBean)get.readObject();
        if (loginBean.getName()!=null&&loginBean.getP()!=null){
           name=loginBean.getName();
           p=loginBean.getP();
           System.out.println(name.equals("admin")&&p.equals("admin"));
         if(name.equals("admin")&&p.equals("admin")){
          send.writeObject("true");
         }else{
          send.writeObject("false");
         }
        }
        }else if(choise.equals("createUser")){
        String uname=(String) get.readObject();
        String upin=(String) get.readObject();
       boolean exist= isUserExist(upin);
        if(exist){
        send.writeObject("false");
        }else{
        createUser(uname,upin);
        send.writeObject("true");
        }
        }else if(choise.equals("userLogin")){
        String userName=(String)get.readObject();
        String userPin=(String)get.readObject();
        boolean check= authenticateUser(userName,userPin);
        if(check){
        send.writeObject("true");
        send.writeObject(accounts);
        }else{
        send.writeObject("false");
        }
        }else if(choise.equals("createAccount")){
         TransactionObject to=(TransactionObject)get.readObject();
         File userData = new File("userData.txt");
	 FileOutputStream fosdata = new FileOutputStream(userData,true);
        BufferedWriter bwdata = new BufferedWriter(new OutputStreamWriter(fosdata));
        int size=accounts.size()+1;
        bwdata.write(current_name+","+size+","+to.getMessage()+","+to.getType()+","+to.getAmount()+","+to.getNum()+","+current_id+","+to.getDate().getTime()+"\n");
        bwdata.newLine();
 	bwdata.close();
        }else if(choise.equals("deposit")){
            Date date=new Date();
        TransactionObject to=new TransactionObject();
        to.setId(current_id);
        to.setAmount(Float.parseFloat((String) get.readObject()));
        to.setDate(date);
        to.setMessage("Money deposit");
        to.setName(current_name);
        to.setNum((String) get.readObject());
        to.setType("Deposit");
         deposite(to);
        }else if(choise.equals("withDraw")){
            Date date=new Date();
        TransactionObject to=new TransactionObject();
        to.setId(current_id);
        to.setAmount(Float.parseFloat((String) get.readObject()));
        to.setDate(date);
        to.setMessage("Money withDraw");
        to.setName(current_name);
        to.setNum((String) get.readObject());
        to.setType("WithDraw");
        withDraw(to);
        }else if(choise.equals("transfer")){
        Date date=new Date();
        TransactionObject to=new TransactionObject();
        to.setId(current_id);
        to.setAmount(Float.parseFloat((String) get.readObject()));
        to.setDate(date);
        to.setMessage("Money Transfer");
        to.setName(current_name);
        to.setNum((String) get.readObject());
        to.setType("Transfer");
        transfer(to,(String)get.readObject());
        }else if(choise.equals("info")){
         FileInputStream fstream = new FileInputStream("userData.txt");
   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
   String strLine;
    ArrayList<String> all=new ArrayList<String>();
      while ((strLine = br.readLine()) != null)   {
        String[] splits=strLine.split(",");
         if(splits.length>1){
          if(splits[6].equals(current_id)){
              System.out.println(strLine);
           all.add(strLine);
          }
         }
    }
         br.close();
         send.writeObject(all);
        }
        
    send.close();
    get.close();
   }
         catch(Exception e){System.out.println(e);}
       }//while
      }
public static void deposite(TransactionObject t) throws FileNotFoundException, IOException{
   FileInputStream fstream = new FileInputStream("userData.txt");
   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
   String strLine;
   float amount = 0;
ArrayList<String> all=new ArrayList<String>();
   while ((strLine = br.readLine()) != null)   {
       String[] splits=strLine.split(",");
         if(splits.length>1){
          if(splits[1].equals(t.getNum())){
              amount=t.getAmount()+Float.parseFloat(splits[4]);
             String oneLine=t.getName()+","+splits[1]+","+t.getMessage()+","+t.getType()+","+amount+","+t.getAmount()+","+splits[6]+","+t.getDate().getTime()+"\n";
    
           System.out.println(oneLine);   
         all.add(oneLine);
         
          }else{
           String oneLine=splits[0]+","+splits[1]+","+splits[2]+","+splits[3]+","+splits[4]+","+splits[5]+","+splits[6]+","+splits[7]+"\n";
          all.add(oneLine);
          }
         }
    }
br.close();
reWrite(all);
}
public static void reWrite(ArrayList<String> s) throws FileNotFoundException, IOException{
 File userData = new File("userData.txt");
	 FileOutputStream fosdata = new FileOutputStream(userData);
        BufferedWriter bwdata = new BufferedWriter(new OutputStreamWriter(fosdata));
        for(String a:s){
        bwdata.write(a);
        bwdata.newLine();
 	}
        bwdata.close();
} 
public static void withDraw(TransactionObject t) throws IOException{
   FileInputStream fstream = new FileInputStream("userData.txt");
   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
   String strLine;
   float amount = 0;
ArrayList<String> all=new ArrayList<String>();
   while ((strLine = br.readLine()) != null)   {
       String[] splits=strLine.split(",");
         if(splits.length>1){
          if(splits[1].equals(t.getNum())){
              amount=Float.parseFloat(splits[4])-t.getAmount();
              
             String oneLine=t.getName()+","+splits[1]+","+t.getMessage()+","+t.getType()+","+amount+","+t.getAmount()+","+splits[6]+","+t.getDate().getTime()+"\n";
    
           System.out.println(oneLine);   
         all.add(oneLine);
         
          }else{
           String oneLine=splits[0]+","+splits[1]+","+splits[2]+","+splits[3]+","+splits[4]+","+splits[5]+","+splits[6]+","+splits[7]+"\n";
          all.add(oneLine);
          }
         }
    }
br.close();
reWrite(all);    
}
public static void transfer(TransactionObject t,String to) throws FileNotFoundException, IOException{
   FileInputStream fstream = new FileInputStream("userData.txt");
   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
   String strLine;
   float amount = 0;
  System.out.println("TO "+to);
ArrayList<String> all=new ArrayList<String>();
   while ((strLine = br.readLine()) != null)   {
       String[] splits=strLine.split(",");
         if(splits.length>1){
 System.out.println(strLine);   
            
             if(splits[1].equals(t.getNum())){
              amount=Float.parseFloat(splits[4])-t.getAmount();
             String oneLine=t.getName()+","+splits[1]+","+t.getMessage()+","+t.getType()+","+amount+","+t.getAmount()+","+splits[6]+","+t.getDate().getTime()+"\n";
            System.out.println(oneLine);   
            all.add(oneLine);            
          }if(to.equals(splits[1])){
          System.out.println("TO split  "+to.equals(splits[1]));
          System.out.println("TO amount  "+t.getAmount());
          float am=Float.parseFloat(splits[4])+t.getAmount();
          String oneLine=splits[0]+","+splits[1]+","+splits[2]+","+splits[3]+","+am+","+splits[5]+","+splits[6]+","+splits[7]+"\n";
          all.add(oneLine);
          }if((!to.equals(splits[1])&&!splits[1].equals(t.getNum()))){
           String oneLine=splits[0]+","+splits[1]+","+splits[2]+","+splits[3]+","+splits[4]+","+splits[5]+","+splits[6]+","+splits[7]+"\n";
          all.add(oneLine);
          }
         
         }
    }
br.close();
reWrite(all);   
}


public static void setAccounts() throws FileNotFoundException, IOException{
   FileInputStream fstream = new FileInputStream("userData.txt");
   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
   String strLine;

   while ((strLine = br.readLine()) != null)   {
       String[] splits=strLine.split(",");
         if(splits.length>1){
   accounts.add(splits[1]);
         }
   
    }
   System.out.println(accounts.size());
//Close the input stream
br.close();
}
 public static boolean isUserExist(String pin) throws FileNotFoundException, IOException{
   FileInputStream fstream = new FileInputStream("users.txt");
   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
   String strLine;

//Read File Line By Line
   while ((strLine = br.readLine()) != null)   {
  // Print the content on the console
  String[] splits=strLine.split(",");
  if(splits[1].equals(pin)){
      setAccounts();
  return true;
  }
    }
//Close the input stream
br.close();
   return false;
   }
 
 public static boolean authenticateUser(String name,String pin) throws FileNotFoundException, IOException{
   FileInputStream fstream = new FileInputStream("users.txt");
   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
   String strLine;

//Read File Line By Line
   while ((strLine = br.readLine()) != null)   {
  // Print the content on the console
  String[] splits=strLine.split(",");
  
  if(splits[0].equals(name)&&splits[1].equals(pin)){
      current_id=splits[1];
      current_name=splits[0];
     setAccounts();
      System.out.println(current_id+current_name);
  return true;
  }
    }

//Close the input stream
br.close();
   return false;
   }
 public static void createUser(String name,String pin) throws IOException{
        File fout = new File("users.txt");
	 File userData = new File("userData.txt");
	
        FileOutputStream fos = new FileOutputStream(fout,true);
        FileOutputStream fosdata = new FileOutputStream(userData,true);
    
        Date date=new Date();
        
	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
   	BufferedWriter bwdata = new BufferedWriter(new OutputStreamWriter(fosdata));
         int size=accounts.size()+1;
            bwdata.write(name+","+size+","+"nothing"+","+"saving"+","+"0.0,"+"0,"+pin+","+date.getTime()+"\n");
        bwdata.newLine();
        bw.write(name+","+pin);
        bw.newLine();
	bw.close();
        bwdata.close();
   
 }
 public static void createAccount(){
 
 }
 }



