package com.github.junbaor.jike.cmd;

import com.github.junbaor.jike.App;
import com.github.junbaor.jike.common.AppConstant;
import com.github.junbaor.jike.exceptions.NoLoginException;
import com.github.junbaor.jike.handler.ActionHandler;
import com.github.junbaor.jike.model.FollowingUpdates;
import com.github.junbaor.jike.model.PersonalUpdate;
import com.github.junbaor.jike.util.StringUtils;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import static com.diogonunes.jcolor.Attribute.BLUE_TEXT;
import static com.diogonunes.jcolor.Attribute.CYAN_TEXT;

@NoArgsConstructor
@CommandLine.Command(name = "home", description = "显示个人动态")
public class HomeCmd implements Callable<Integer> {

    private String userName;

    public HomeCmd(String userName) {
        this.userName = userName;
    }

    @Override
    public Integer call() throws Exception {
        homeHandler();
        return AppConstant.CODE_SUCCESS;
    }

    private void homeHandler() throws IOException, NoLoginException {
        PersonalUpdate.LoadMoreKeyBean loadMoreKey = null;
        List<FollowingUpdates.DataBean> dataList = Collections.emptyList();
        String next = "";
        while (true) {
            if (StringUtils.isBlank(next) || Objects.equals(next, "j")) {
                PersonalUpdate single = App.jikeClient.single(userName, loadMoreKey);
                printPersonalUpdate(single);
                dataList = single.getData();
                loadMoreKey = single.getLoadMoreKey();
            } else if (Objects.equals(next, "r")) {
                loadMoreKey = null;
                next = "j";
                continue;
            } else if (Objects.equals(next, "q")
                    || Objects.equals(next, "quit")
                    || Objects.equals(next, "exit")) {
                System.out.println("ヾ(￣▽￣)Bye~Bye~");
                return;
            } else if (next.startsWith("nb ")) { // 点赞
                ActionHandler.like(next, dataList);
            } else if (next.startsWith("lj ")) { // 点赞
                ActionHandler.unLike(next, dataList);
            } else if (Objects.equals(next, "h")) {
                int padSize = 1;
                String padStr = " ";
                System.out.println("<" + StringUtils.pad("j", padSize, padStr) + "> 下一页");
                System.out.println("<" + StringUtils.pad("r", padSize, padStr) + "> 刷新");
                System.out.println("<" + StringUtils.pad("h", padSize, padStr) + "> 帮助");
                System.out.println("<" + StringUtils.pad("q", padSize, padStr) + "> 退出");
                System.out.println("<" + StringUtils.pad("i 序号", padSize, padStr) + "> 查看图片");
                System.out.println("<" + StringUtils.pad("c 序号 评论内容", padSize, padStr) + "> 评论");
                System.out.println("<" + StringUtils.pad("nb 序号", padSize, padStr) + "> 点赞");
                System.out.println("<" + StringUtils.pad("lj 序号", padSize, padStr) + "> 取消点赞");
            } else if (next.startsWith("i ")) {
                ActionHandler.showImages(next, dataList);
            } else if (next.startsWith("c ")) {
                ActionHandler.comment(next, dataList);
            } else {
                System.out.println("无效命令");
            }
            System.out.print(AppConstant.MSG_PLEASE_INPUT);
            next = App.scanner.nextLine();
        }
    }

    private void printPersonalUpdate(PersonalUpdate single) {
        int i = 1;
        for (FollowingUpdates.DataBean item : single.getData()) {
            StringBuilder sb = new StringBuilder();
            String index = i + "";
            sb.append("[").append(index).append("] ");

            if (Objects.equals(item.getType(), "ORIGINAL_POST")) {
                if (Boolean.TRUE.equals(item.getLiked())) {
                    sb.append(" ").append("👍🏻").append(" ");
                }
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
