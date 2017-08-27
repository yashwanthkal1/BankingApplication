/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package myatm;

import java.io.Serializable;

public class LoginBean implements Serializable{
  private String name;
  private String p;
  public void setName(String n){
  name=n;
  }
  public String getName(){
  return name;
  }
  public void setP(String pinPas){
   p=pinPas;
  }
  public String getP(){
  return p;
  }
}
