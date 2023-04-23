package hkmu.comps380f.exception;

import java.util.UUID;

public class PhotoNotFound extends Exception {

    public PhotoNotFound(UUID id) {
        super("Photo " + id + " does not exist.");
    }

}
