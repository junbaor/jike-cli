package com.github.junbaor.jike.cmd;

import com.github.junbaor.jike.common.AppConstant;
import com.github.junbaor.jike.handler.LoginHandler;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "login", description = "登录")
public class LoginCmd implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        new LoginHandler().handler();
        System.out.println("⭐️ 已登录 ⭐");
        return AppConstant.CODE_SUCCESS;
    }

}
