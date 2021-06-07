package com.github.junbaor.jike.cmd;

import com.github.junbaor.jike.common.AppConstant;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "following", description = "关注")
public class FollowingCmd implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(AppConstant.MSG_TODO_FEATURE);
        return AppConstant.CODE_SUCCESS;
    }

}
