package indi.yuluo.core.web.enums;

/**
 * 内置错误结果状态码
 *
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 */
public enum ResultEnums implements IEnums {

	/**
	 * return success result.
	 */
	SUCCESS(1000, "接口调用成功"),

	/**
	 * return business common failed.
	 */
	COMMON_FAILED(1001, "接口调用失败");

	private Integer code;

	private String message;

	ResultEnums() {
	}

	ResultEnums(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
