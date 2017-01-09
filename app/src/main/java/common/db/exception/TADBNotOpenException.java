package common.db.exception;

/**
 * 描述:
 * Created by mjd on 2017/1/9.
 */
public class TADBNotOpenException extends Exception {


    public TADBNotOpenException() {
        super();
    }

    public TADBNotOpenException(String detailMessage) {
        super(detailMessage);
    }

}
