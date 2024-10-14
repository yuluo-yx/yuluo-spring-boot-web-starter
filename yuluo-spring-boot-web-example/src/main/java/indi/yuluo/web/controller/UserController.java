package indi.yuluo.web.controller;

import indi.yuluo.core.web.annotation.Loggable;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

import indi.yuluo.core.web.domain.Result;

@Loggable
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
