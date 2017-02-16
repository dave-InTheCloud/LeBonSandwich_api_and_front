package exception;

import java.io.Serializable;

public class SandwichBadRequest extends Exception {

    public SandwichBadRequest(String msg) {
        super(msg);
    }
}
