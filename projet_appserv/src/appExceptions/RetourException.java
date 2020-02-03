package appExceptions;

public class RetourException extends Exception {
	private static final long serialVersionUID = 1L;	
	private String message;
	public RetourException() {}
	public RetourException(String msg) {
		this.message = msg;
	}
	public String toString() {
		return this.message;
	}
}
