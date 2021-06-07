package com.github.junbaor.jike.cmd;

import com.github.junbaor.jike.App;
import com.github.junbaor.jike.common.AppConstant;
import com.github.junbaor.jike.exceptions.NoLoginException;
import com.github.junbaor.jike.model.Profile;
import com.github.junbaor.jike.util.StringUtils;
import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

import static com.diogonunes.jcolor.Attribute.YELLOW_TEXT;

@CommandLine.Command(name = "me", description = "显示个人信息")
public class MeCmd implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        meHandler();
        return AppConstant.CODE_SUCCESS;
    }

    private static void meHandler() throws IOException, NoLoginException {
        Profile profile = App.jikeClient.profile();
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
