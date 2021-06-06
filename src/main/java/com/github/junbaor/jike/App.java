package com.github.junbaor.jike;

import com.github.junbaor.jike.data.JikeClient;
import com.github.junbaor.jike.exceptions.NoLoginException;
import com.github.junbaor.jike.handler.LoginHandler;
import com.github.junbaor.jike.handler.TimeLineHandler;
import com.github.junbaor.jike.model.Profile;
import com.github.junbaor.jike.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static com.diogonunes.jcolor.Attribute.YELLOW_TEXT;

@Slf4j
public class App {

    public static final JikeClient jikeClient = new JikeClient();
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        log.debug("程序启动");
        String next = "m";

        while (true) {
            try {
                if (next == null || Objects.equals(next, "m")) {
                    meHandler();
                } else if (Objects.equals(next, "t")) {
                    new TimeLineHandler().handler();
                } else if (Objects.equals(next, "qq")) {
                    Config.reset();
                    System.out.println("已退出登录");
                    next = null;
                    continue;
                } else if (Objects.equals(next, "login")) {
                    new LoginHandler().handler();
                    next = "m"; // 登录后显示一下个人信息
                    continue;
                } else if (Objects.equals(next, "h")) {
                    helpHandler();
                } else if (Objects.equals(next, "q")
                        || Objects.equals(next, "quit")
                        || Objects.equals(next, "exit")) {
                    System.out.println("ヾ(￣▽￣)Bye~Bye~");
                    System.exit(0);
                } else {
                    System.out.println("无效命令");
                }
            } catch (NoLoginException e) {
                next = "login";
                continue;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                System.out.println("网络异常,请重试");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                System.out.println("噢? 好像哪里不对劲~");
            }

            System.out.print("[-] 输入主菜单命令(h显示帮助)> ");
            next = scanner.next();
        }
    }

    private static void helpHandler() {
        int padSize = 2;
        String padStr = " ";
        System.out.println("<" + StringUtils.pad("m", padSize, padStr) + "> 显示个人信息");
        System.out.println("<" + StringUtils.pad("t", padSize, padStr) + "> 显示关注信息流");
//        System.out.println("<" + StringUtils.pad("login", padSize, padStr) + "> 登录");
        System.out.println("<" + StringUtils.pad("h", padSize, padStr) + "> 帮助信息");
        System.out.println("<" + StringUtils.pad("qq", padSize, padStr) + "> 退出登录");
        System.out.println("<" + StringUtils.pad("q", padSize, padStr) + "> 退出");
    }

    private static void meHandler() throws IOException, NoLoginException {
        Profile profile = jikeClient.profile();
        printUser(profile.getUser());
    }

    private static void printUser(Profile.UserBean user) {
        int size = 3;
        System.out.println("[" + StringUtils.pad("昵称", size, YELLOW_TEXT()) + "] " + user.getScreenName());
        System.out.println("[" + StringUtils.pad("签名", size, YELLOW_TEXT()) + "] " + user.getBriefIntro());
        System.out.println("[" + StringUtils.pad("关注", size, YELLOW_TEXT()) + "] " + user.getStatsCount().getFollowingCount());
        System.out.println("[" + StringUtils.pad("被关注", size, YELLOW_TEXT()) + "] " + user.getStatsCount().getFollowedCount());
        System.out.println("[" + StringUtils.pad("赞", size, YELLOW_TEXT()) + "] " + user.getStatsCount().getLiked());
        System.out.println("[" + StringUtils.pad("精选", size, YELLOW_TEXT()) + "] " + user.getStatsCount().getHighlightedPersonalUpdates());
    }

}
