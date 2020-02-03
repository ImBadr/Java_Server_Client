package appExceptions;

public class EmpruntException extends Exception {
	private String message;
	private static final long serialVersionUID = 1L;
	public EmpruntException() {}
	public EmpruntException(String msg) {
		this.message = msg;
	}
	public String toString() {
		return this.message;
	}
}
