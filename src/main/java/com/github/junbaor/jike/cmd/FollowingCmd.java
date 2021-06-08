package com.github.junbaor.jike.cmd;

import com.github.junbaor.jike.App;
import com.github.junbaor.jike.common.AppConstant;
import com.github.junbaor.jike.model.FollowingRep;
import com.github.junbaor.jike.util.StringUtils;
import picocli.CommandLine;

import java.util.Objects;
import java.util.concurrent.Callable;

import static com.diogonunes.jcolor.Attribute.BLUE_TEXT;

@CommandLine.Command(name = "following", description = "关注")
public class FollowingCmd implements Callable<Integer> {

    private FollowingRep.LoadMoreKeyBean loadMoreKey;

    @Override
    public Integer call() throws Exception {
        String next = "";
        while (true) {
            if (StringUtils.isBlank(next) || Objects.equals(next, "j")) {
                FollowingRep followingList = App.jikeClient.getFollowingList(loadMoreKey);
                printFollowing(followingList);
                loadMoreKey = followingList.getLoadMoreKey();
            } else if (Objects.equals(next, "r")) {
                loadMoreKey = null;
                next = "j";
                continue;
            } else if (Objects.equals(next, "q")
                    || Objects.equals(next, "quit")
                    || Objects.equals(next, "exit")) {
                System.out.println("ヾ(￣▽￣)Bye~Bye~");
                return AppConstant.CODE_SUCCESS;
            } else if (Objects.equals(next, "h")) {
                int padSize = 1;
                String padStr = " ";
                System.out.println("<" + StringUtils.pad("j", padSize, padStr) + "> 下一页");
                System.out.println("<" + StringUtils.pad("r", padSize, padStr) + "> 刷新");
                System.out.println("<" + StringUtils.pad("h", padSize, padStr) + "> 帮助");
                System.out.println("<" + StringUtils.pad("q", padSize, padStr) + "> 退出");
            } else {
                System.out.println("无效命令");
            }
            System.out.print(AppConstant.MSG_PLEASE_INPUT);
            next = App.scanner.nextLine();
        }
    }

    private void printFollowing(FollowingRep followingList) {
        int i = 1;
        for (FollowingRep.DataBean item : followingList.getData()) {
            String gender = Objects.equals(item.getGender(), "MALE") ?
                    "👨" : "\uD83D\uDC69";
            String screenName = item.getScreenName();
            String verifyMessage = item.getVerifyMessage();
            String briefIntro = item.getBriefIntro();
            int followingCount = item.getStatsCount().getFollowingCount();
            int followedCount = item.getStatsCount().getFollowedCount();
            int highlightedPersonalUpdates = item.getStatsCount().getHighlightedPersonalUpdates();
            int respectedCount = item.getStatsCount().getRespectedCount();
            int liked = item.getStatsCount().getLiked();

            StringBuilder sb = new StringBuilder();
            sb.append("[").append(i).append("]").append(" ").append(gender).append(" ");
            sb.append("[").append(StringUtils.pad(screenName)).append("]").append(" ");//.append(gender).append(" ");
            if (StringUtils.isNotBlank(verifyMessage)) {
                sb.append("(").append(StringUtils.pad(verifyMessage)).append(")").append(" ");
            }
            if (StringUtils.isNotBlank(briefIntro)) {
                String replace = briefIntro.replace("\n", "↴");
                sb.append("(").append(StringUtils.pad(replace)).append(")").append(" ");
            }
            sb.append("[").append(StringUtils.pad("关注: ", BLUE_TEXT())).append(followingCount).append(" ")
                    .append(StringUtils.pad("被关注: ", BLUE_TEXT())).append(followedCount).append(" ")
                    .append(StringUtils.pad("赞: ", BLUE_TEXT())).append(liked).append(" ")
                    .append(StringUtils.pad("精选: ", BLUE_TEXT())).append(highlightedPersonalUpdates).append(" ")
                    .append(StringUtils.pad("夸夸: ", BLUE_TEXT())).append(respectedCount).append("]");
            System.out.println(sb);
            i++;
        }
    }

}
