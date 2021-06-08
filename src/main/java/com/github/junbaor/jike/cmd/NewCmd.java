package com.github.junbaor.jike.cmd;

import com.github.junbaor.jike.App;
import com.github.junbaor.jike.common.AppConstant;
import com.github.junbaor.jike.model.CreatePostsRep;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "new",
        aliases = "-n",
        description = "发新动态"
)
public class NewCmd implements Callable<Integer> {

    @CommandLine.Option(names = "-p", description = "图片文件路径, 最多9张")
    public File[] image;

    @CommandLine.Parameters(index = "0", prompt = "动态内容", description = "动态内容")
    public String content;

    @CommandLine.Option(names = {"-h", "-help"}, usageHelp = true, description = "打印帮助信息")
    boolean help;

    @Override
    public Integer call() throws Exception {
        CreatePostsRep posts = App.jikeClient.createPosts(content + "\n\n来自 jike-cli");
        if (posts != null) {
            System.out.println("✌️ 已发表");
        }
        return AppConstant.CODE_SUCCESS;
    }

}
