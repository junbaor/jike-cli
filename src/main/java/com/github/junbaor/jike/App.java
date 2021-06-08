package com.github.junbaor.jike;

import com.github.junbaor.jike.cmd.*;
import com.github.junbaor.jike.common.AppConstant;
import com.github.junbaor.jike.data.JikeClient;
import com.github.junbaor.jike.exceptions.NoLoginException;
import com.github.junbaor.jike.handler.TimeLineHandler;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.util.Scanner;
import java.util.concurrent.Callable;

@Slf4j
@CommandLine.Command(
        name = "jike-cli",
        subcommands = {
                LoginCmd.class,
                LogoutCmd.class,
                MeCmd.class,
                HomeCmd.class,
                MessageCmd.class,
                FollowingCmd.class,
                FollowerCmd.class,
                NewCmd.class
        },
        description = "即刻命令行客户端",
        versionProvider = VersionProvider.class
)
public class App implements Callable<Integer> {

    public static final JikeClient jikeClient = new JikeClient();
    public static final Scanner scanner = new Scanner(System.in);

    @CommandLine.Option(names = {"-v", "-version"}, versionHelp = true, description = "打印版本号")
    boolean version;

    @CommandLine.Option(names = {"-h", "-help"}, usageHelp = true, description = "打印帮助信息")
    boolean help;

    @Override
    public Integer call() {
        try {
            new TimeLineHandler().handler();
        } catch (NoLoginException e) {
            System.out.println("请先执行 jike-cli login 登录");
            return AppConstant.CODE_NO_LOGIN;
        } catch (Exception e) {
            return AppConstant.CODE_EXCEPTION;
        }
        return AppConstant.CODE_SUCCESS;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App())
//                .setColorScheme(CommandLine.Help.defaultColorScheme(CommandLine.Help.Ansi.ON))
                .execute(args);
        System.exit(exitCode);
    }

}
