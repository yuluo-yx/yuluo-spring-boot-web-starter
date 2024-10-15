package indi.yuluo.core.web.domain.result;

import indi.yuluo.core.web.enums.IEnums;
import indi.yuluo.core.web.enums.ResultEnums;

/**
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 */

public class Result<T> {

	/**
	 * 业务异常码
	 */
	private Integer code;

	/**
	 * 返回信息
	 */
	private String info;

	/**
	 * 数据信息
	 */
	private T data;

	/**
	 * 错误信息
	 */
	private String message;

	public Result() {
	}

	public Result(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 自定义 Result 实例
	 * @param code 业务状态码
	 * @param message 消息
	 * @param data 数据
	 * @return Result 实例
	 * @param <T> 实例类型
	 */
	public static <T> Result<T> instance(Integer code, String message, T data) {

		Result<T> result = new Result<>();
		result.setCode(code);
		result.setMessage(message);
		result.setData(data);
		return result;
	}

	/**
	 * 成功返回封装
	 * @param data 数据
	 * @return Result实例
	 * @param <T> 返回数据类型
	 */
	public static <T> Result<T> success(T data) {

		return new Result<>(
				ResultEnums.SUCCESS.getCode(),
				ResultEnums.SUCCESS.getMessage(),
				data
		);
	}

	/**
	 * 错误返回
	 * @return Result 实例
	 */
	public static Result<?> failed() {

		return new Result<>(
				ResultEnums.COMMON_FAILED.getCode(),
				ResultEnums.COMMON_FAILED.getMessage(),
				null
		);
	}

	public static Result<?> failed(String message) {

		return new Result<>(
				ResultEnums.COMMON_FAILED.getCode(),
				message,
				null
		);
	}

	/**
	 * 自定义错误枚举地失败返回
	 * @param errorResult 错误枚举 implements IEnums
	 * @return Result 实例
	 */
	public static Result<?> failed(IEnums errorResult) {
		return new Result<>(errorResult.getCode(), errorResult.getMessage(), null);
	}

}
