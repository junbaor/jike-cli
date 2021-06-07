package com.github.junbaor.jike.cmd;

import com.github.junbaor.jike.common.AppConstant;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "new",
        aliases = "-n",
        description = "发新动态",
        mixinStandardHelpOptions = true
)
public class NewCmd implements Callable<Integer> {

    @CommandLine.Option(names = "-p", description = "图片文件路径, 最多9张")
    public File[] image;

    @CommandLine.Parameters(index = "0", prompt = "动态内容", description = "动态内容说明")
    public String content;

    @Override
    public Integer call() throws Exception {
        System.out.println(AppConstant.MSG_TODO_FEATURE);
        return AppConstant.CODE_SUCCESS;
    }

}
