package dev.shann.mcuserservice.exceptions;

public class UserAlreadyExistException extends RuntimeException {

  public UserAlreadyExistException() {
    super("User already exists");
  }

  public UserAlreadyExistException(String email) {
    super("User with email " + email + " already exists");
  }
}
