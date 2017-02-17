package exception;

/**
 * Created by David on 17/02/2017.
 */
public class OrderBadRequest  extends  Exception{

    public OrderBadRequest(String msg) {
        super(msg);
    }
}
