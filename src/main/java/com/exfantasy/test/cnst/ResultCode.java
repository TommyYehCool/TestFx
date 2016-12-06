package com.exfantasy.test.cnst;

/**
 * <pre>
 * 定義操作結果代碼
 * </pre>
 * 
 * @author tommy.feng
 *
 */
public enum ResultCode {

	/** Code: 0, Message: 成功 */
	SUCCESS(0, "Succeed"),

	/** Code: 1001, Message: 格式錯誤 */
	INVALID_FORMAT(1001, "Invalid format"),
	
	/** Code: 2001, Message: Email 已被使用 */
	EMAIL_ALREADY_IN_USED(2001, "Email already in used"),
	
	/** Code: 3001, Message: 資料重複 */
	DUPLICATE_KEY(3001, "Data already existed"),

	/** Code: 4444, Message: 無權限操作 */
	ACCESS_DENIED(4444, ""),

	/** Code: 9999, Message: 系統發生錯誤 */
	SYSTEM_EXCEPTION(9999, "");
	
	private final int code;
	private final String message;
	
	private ResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public static ResultCode convert(int code) {
		for (ResultCode e : ResultCode.values()) {
			if (e.getCode() == code) {
				return e;
			}
		}
		return null;
	}
}
