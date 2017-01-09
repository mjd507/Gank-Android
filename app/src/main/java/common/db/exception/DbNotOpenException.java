package common.db.exception;

/**
 * 描述:
 * Created by mjd on 2017/1/9.
 */
public class DbNotOpenException extends Exception {


    public DbNotOpenException() {
        super();
    }

    public DbNotOpenException(String detailMessage) {
        super(detailMessage);
    }

}
