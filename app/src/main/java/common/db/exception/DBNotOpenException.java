package common.db.exception;

/**
 * @description 数据库没有打开
 * @author 许友爻
 * @date 2014年7月14日下午2:35:01
 * @version 1.0
 */
public class DBNotOpenException extends Exception
{
	private static final long serialVersionUID = 1L;

	public DBNotOpenException()
	{
		super();
	}

	public DBNotOpenException(String detailMessage)
	{
		super(detailMessage);
	}

}
