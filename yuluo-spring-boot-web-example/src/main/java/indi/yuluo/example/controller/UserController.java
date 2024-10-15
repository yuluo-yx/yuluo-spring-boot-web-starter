package indi.yuluo.example.controller;

import java.util.Map;

import indi.yuluo.core.web.domain.result.Result;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 */

public class UserController {

	@Value("${user.id")
	private Long id;

	@Value("${user.name}")
	private String name;

	@Value("${user.password}")
	private Integer password;

	public Result<Map<String, String>> login() {

		return null;
	}

}

