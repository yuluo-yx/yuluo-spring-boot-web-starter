package indi.yuluo.core.web.annotation.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
		method = "Delete"
)
@RequestMapping(
		method = {RequestMethod.DELETE}
)
@Parameters
public @interface RequestDeleteApi {

	@AliasFor(
			annotation = RequestMapping.class,
			attribute = "value"
	)
	String[] methodApi() default {};

	@AliasFor(
			annotation = Operation.class,
			attribute = "summary"
	)
	String summary() default "";

}
