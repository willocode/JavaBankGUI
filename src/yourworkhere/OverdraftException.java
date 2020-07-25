package yourworkhere;

public class OverdraftException extends RuntimeException  {
	/**
	 * our overdraft exception/warning thrown when Checking account falls below 0. 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public String getMessage() {
        return "You cannot put your account in the negative.  A fee will be added for this transaction";
    }
}
