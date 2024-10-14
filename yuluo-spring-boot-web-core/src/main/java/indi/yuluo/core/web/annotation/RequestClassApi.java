package indi.yuluo.core.web.annotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * 控制器注解：
 * 1. 集成 @RequestMapping
 * 2. 集成 @RestController
 * 3.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Controller
@ResponseBody
public @interface RequestClassApi {
}
