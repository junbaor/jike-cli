package com.github.junbaor.jike.cmd;

import com.github.junbaor.jike.common.AppConstant;
import com.github.junbaor.jike.common.Config;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "logout", description = "登出")
public class LogoutCmd implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Config.reset();
        System.out.println("已登出 ヾ(￣▽￣)Bye~Bye~");
        return AppConstant.CODE_SUCCESS;
    }

}
