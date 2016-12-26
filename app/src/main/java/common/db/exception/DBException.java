package common.db.exception;

/**
 * @description 数据库操作错误
 * @author 许友爻
 * @date 2014年7月14日下午2:33:01
 * @version 1.0
 */
public class DBException extends Exception
{
	private static final long serialVersionUID = 1L;

	public DBException()
	{
		super();
	}

	public DBException(String detailMessage)
	{
		super(detailMessage);
	}

}
