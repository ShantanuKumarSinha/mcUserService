package dev.shann.mcuserservice;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(){
         throw new RuntimeException("Email Id Not Found");
     }

}
