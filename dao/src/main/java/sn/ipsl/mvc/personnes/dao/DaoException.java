package sn.ipsl.mvc.personnes.dao;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// code erreur
	private int code;

	public int getCode() {
		return code;
	}

	public DaoException(String message,int code) {
		super(message);
		this.code=code;
	}
}
