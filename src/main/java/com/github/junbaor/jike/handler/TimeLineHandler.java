package com.github.junbaor.jike.handler;

import com.github.junbaor.jike.App;
import com.github.junbaor.jike.exceptions.NoLoginException;
import com.github.junbaor.jike.model.FollowingUpdates;
import com.github.junbaor.jike.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.diogonunes.jcolor.Attribute.*;

public class TimeLineHandler {

    private FollowingUpdates.LoadMoreKeyBean moreKeyBean = null;

    public void handler() throws NoLoginException, IOException {
        String next = "";
        while (true) {
            if (StringUtils.isBlank(next) || Objects.equals(next, "j")) {
                FollowingUpdates followingUpdates = App.jikeClient.followingUpdates(10, moreKeyBean);
                printFollowingUpdates(followingUpdates);
                moreKeyBean = followingUpdates.getLoadMoreKey();
            } else if (Objects.equals(next, "r")) {
                moreKeyBean = null;
                next = "j";
                continue;
            } else if (Objects.equals(next, "q")
                    || Objects.equals(next, "quit")
                    || Objects.equals(next, "exit")) {
                System.out.println("ヾ(￣▽￣)Bye~Bye~");
                return;
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
            System.out.print("[-] 输入信息流命令 (h 显示帮助) > ");
            next = App.scanner.next();
        }
    }

    private void printFollowingUpdates(FollowingUpdates followingUpdates) {
        int i = 1;
        for (FollowingUpdates.DataBean item : followingUpdates.getData()) {
            StringBuilder sb = new StringBuilder();
            String index = i + "";
            sb.append("[").append(index).append("] ");

            if (Objects.equals(item.getType(), "ORIGINAL_POST")) {
                String screenName = item.getUser().getScreenName();
                String topicName = "";
                if (item.getTopic() != null && Objects.equals(item.getTopic().getType(), "TOPIC")) {
                    topicName = item.getTopic().getContent();
                }
//                String content = StringUtils.abbreviate(item.getContent(), "...", 100);
                String content = item.getContent();
                sb.append("[").append(StringUtils.pad(screenName)).append("] ");
                if (StringUtils.isNotBlank(topicName)) {
                    sb.append("〖").append(StringUtils.pad(topicName, BLUE_TEXT())).append("〗");
                }
                sb.append(content.replace("\n", "↴"));
                List<FollowingUpdates.DataBean.PicturesBean> pictures = item.getPictures();
                if (CollectionUtils.isNotEmpty(pictures)) {
                    sb.append(" [").append(StringUtils.pad(pictures.size() + "张图")).append("]");
                }
                if (item.getPoi() != null) {
                    sb.append(" [").append(StringUtils.pad(item.getPoi().getName())).append("]");
                }
            } else if (Objects.equals(item.getType(), "PERSONAL_UPDATE")) {
                // LIVE_SHARE USER_FOLLOW
                if (Objects.equals(item.getAction(), "LIVE_SHARE")) {
                    String title = item.getLive().getTitle(); // 直播间名
                    String verb = item.getVerb(); // 直播已结束
//                    String screenName = item.getUser().getScreenName();
//                    sb.append("")
                } else if (Objects.equals(item.getAction(), "USER_FOLLOW")) {
                    String collect = item.getUsers().stream().map(FollowingUpdates.DataBean.UsersBean::getScreenName).collect(Collectors.joining("、"));
                    sb.append("[").append(StringUtils.pad(collect)).append("]");
                    sb.append("关注了");
                    for (FollowingUpdates.DataBean.TargetUsersBean targetUser : item.getTargetUsers()) {
                        String screenName1 = targetUser.getScreenName();
                        String briefIntro = targetUser.getBriefIntro().replace("\n", "↴");
                        sb.append("[").append(StringUtils.pad(screenName1, YELLOW_TEXT()))
                                .append(StringUtils.pad("(" + briefIntro + ")", CYAN_TEXT())).append("]");
                    }
                } else {
                    sb.append(item.getAction()).append("不受支持");
                }
            } else {
                sb.append(item.getType()).append(" 类型的消息暂时不支持显示");
            }

            Date createdAt = item.getCreatedAt();
            String format = DateFormatUtils.format(createdAt, "MM-dd HH:mm");
            sb.append(StringUtils.pad(" (" + format + ")", CYAN_TEXT()));
            System.out.println(sb);
            i++;
        }
    }

}
