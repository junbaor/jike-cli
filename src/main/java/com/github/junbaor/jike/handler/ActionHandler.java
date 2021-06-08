package com.github.junbaor.jike.handler;

import com.github.junbaor.jike.App;
import com.github.junbaor.jike.cmd.HomeCmd;
import com.github.junbaor.jike.exceptions.NoLoginException;
import com.github.junbaor.jike.model.AddCommentsRep;
import com.github.junbaor.jike.model.FollowingUpdates;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class ActionHandler {

    public static void goAuthorHome(String next, List<FollowingUpdates.DataBean> dataList) throws Exception {
        String[] params = next.trim().split("g ");
        if (params.length != 2) {
            System.out.println("输入错误, 请重新输入");
        } else {
            int index = Integer.parseInt(params[1]); // 序号
            if (CollectionUtils.isEmpty(dataList) || dataList.size() < index) {
                System.out.println("根据序号未匹配到动态");
            } else {
                FollowingUpdates.DataBean dataBean = dataList.get(index - 1);
                new HomeCmd(dataBean.getUser().getUsername()).call();
            }
        }
    }

    public static void like(String next, List<FollowingUpdates.DataBean> dataList)
            throws NoLoginException, IOException {
        String[] params = next.trim().split("nb ");
        if (params.length != 2) {
            System.out.println("输入错误, 请重新输入");
        } else {
            int index = Integer.parseInt(params[1]); // 序号
            if (CollectionUtils.isEmpty(dataList) || dataList.size() < index) {
                System.out.println("根据序号未匹配到动态");
            } else {
                FollowingUpdates.DataBean dataBean = dataList.get(index - 1);
                String id = dataBean.getId();
                boolean success = App.jikeClient.like(id);
                System.out.println("点赞" + (success ? "成功" : "失败"));
            }
        }
    }

    public static void unLike(String next, List<FollowingUpdates.DataBean> dataList)
            throws NoLoginException, IOException {
        String[] params = next.trim().split("lj ");
        if (params.length != 2) {
            System.out.println("输入错误, 请重新输入");
        } else {
            int index = Integer.parseInt(params[1]); // 序号
            if (CollectionUtils.isEmpty(dataList) || dataList.size() < index) {
                System.out.println("根据序号未匹配到动态");
            } else {
                FollowingUpdates.DataBean dataBean = dataList.get(index - 1);
                String id = dataBean.getId();
                boolean success = App.jikeClient.unLike(id);
                System.out.println("取消点赞" + (success ? "成功" : "失败"));
            }
        }
    }

    public static void comment(String next, List<FollowingUpdates.DataBean> dataList)
            throws NoLoginException, IOException {
        String[] params = next.trim().split(" ");
        if (params.length != 3) {
            System.out.println("输入错误, 请重新输入, 评论内容不允许有空格");
        } else {
            int index = Integer.parseInt(params[1]); // 序号
            String comment = params[2];
            if (CollectionUtils.isEmpty(dataList) || dataList.size() < index) {
                System.out.println("根据序号未匹配到动态");
            } else {
                FollowingUpdates.DataBean dataBean = dataList.get(index - 1);
                String id = dataBean.getId();
                AddCommentsRep addCommentsRep = App.jikeClient.addComments(id, comment);
                System.out.println("发表评论" + (addCommentsRep != null ? "成功" : "失败"));
            }
        }
    }
}
