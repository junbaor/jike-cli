package com.github.junbaor.jike.cmd;

import com.github.junbaor.jike.common.AppConstant;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "follower", description = "被关注")
public class FollowerCmd implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(AppConstant.MSG_TODO_FEATURE);
        return AppConstant.CODE_SUCCESS;
    }

}
