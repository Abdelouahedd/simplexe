package exceptions;

public class PivotException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2477754224790691614L;

	public PivotException() {
		super();
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "No Pivot found";
	}
	
}
