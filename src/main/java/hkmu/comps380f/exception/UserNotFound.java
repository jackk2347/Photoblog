package hkmu.comps380f.exception;

import java.util.UUID;

public class UserNotFound extends Exception{
    public UserNotFound(String username) {
        super("Username" + username + " does not exist.");
    }
}
