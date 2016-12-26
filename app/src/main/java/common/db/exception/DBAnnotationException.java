package common.db.exception;
/**数据库注解错误*/
public class DBAnnotationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DBAnnotationException() {
		super();
	}

	public DBAnnotationException(String detailMessage) {
		super(detailMessage);
	}
}
