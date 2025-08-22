package dev.shann.mcuserservice.exceptions;

public class ExistingUserException extends RuntimeException {

  public ExistingUserException() {
    super("User already exists");
  }

  public ExistingUserException(String email) {
    super("User with email " + email + " already exists");
  }
}
