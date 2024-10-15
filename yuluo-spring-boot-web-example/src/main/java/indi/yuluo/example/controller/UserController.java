package indi.yuluo.example.controller;

import java.util.Map;

import indi.yuluo.core.web.annotation.request.RequestClassApi;
import indi.yuluo.core.web.annotation.request.RequestGetApi;
import indi.yuluo.core.web.domain.result.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 */

@RequestClassApi(
		classApi = "/api/v1/user",
		tagName = "用户管理",
		tagDesc = "用户管理"
)
public class UserController {

	@Value("${user.id}")
	private Long id;

	@Value("${user.name}")
	private String name;

	@RequestGetApi(
			methodApi = "/{id}/{name}",
			summary = "用户登录接口"
	)
	public Result<Map<String, String>> login(
			@PathVariable("id") String id,
			@PathVariable("name") String name
	) {

		Map<String, String> user = Map.of("id", id, "name", name);

		return Result.success(user);
	}

}

