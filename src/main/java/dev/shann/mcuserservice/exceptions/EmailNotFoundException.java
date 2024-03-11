package dev.shann.mcuserservice.exceptions;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(){
         super("Email Id Not Found");
     }

}
