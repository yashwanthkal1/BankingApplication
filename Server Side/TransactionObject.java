/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package myatm;
import java.io.*;
import java.util.*;

public class TransactionObject implements Serializable{
String type;
String id;
String num;
String name;
String message;
Date date;
float amount;

public TransactionObject(){}

public void setType(String type){
this.type = type;
}
public String getType(){
return type;
} 
public void setId(String type){
this.id = id;
}
public String getId(){
return id;
}
public void setNum(String num){
this.num = num;
}
public String getNum(){
return num;
} 
public void setName(String name){
this.name = name;
}
public String getName(){
return name;
}
public void setMessage(String message){
this.message = message;
}
public String getMessage(){
return message;
}
public void setDate(Date date){
this.date = date;
}
public Date getDate(){
return date;
}
public void setAmount(float amount){
this.amount = amount;
}
public float getAmount(){
return amount;
}
}