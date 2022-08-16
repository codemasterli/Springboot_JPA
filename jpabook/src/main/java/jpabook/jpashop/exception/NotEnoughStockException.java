package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException{
         
	public NotEnoughStockException() {
		super();
	}
	//메세지 넘기고
	public NotEnoughStockException(String message) {
		super(message);
	}
	// cause는 오류 경과
	public NotEnoughStockException(String message, Throwable cause) {
		super(message,cause);
	}
	public NotEnoughStockException( Throwable cause) {
		super(cause);
	}
//	public NotEnoughStockException(String message, Throwable cause,boolean enableSuppression,boolean writableStackTrace) {
//		super(message,cause,enableSuppression,writableStackTrace);
//	}
}
