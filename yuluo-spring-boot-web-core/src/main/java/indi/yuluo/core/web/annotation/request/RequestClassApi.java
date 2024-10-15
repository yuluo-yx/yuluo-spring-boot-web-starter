package indi.yuluo.core.web.annotation.request;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 控制器注解：
 * 1. 集成 @RequestMapping
 * 2. 集成 @RestController
 * 3. 集成 @Tag
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Tag(name = "")
@Controller
@ResponseBody
@RequestMapping
public @interface RequestClassApi {

	/**
	 * 控制器类的接口
	 * e.g. @RequestMapping(value = "/api/v1")
	 */
	@AliasFor(
			annotation = RequestMapping.class,
			attribute = "value"
	)
	String[] classApi() default {};

	/**
	 * e.g. @Tag(name = "用户管理")
	 */
	@AliasFor(
			annotation = Tag.class,
			attribute = "name"
	)
	String tagName() default "";

	/**
	 * e.g. @Tag(description = "用户管理")
	 */
	@AliasFor(
			annotation = Tag.class,
			attribute = "description"
	)
	String tagDesc() default "";

}
