package com.github.junbaor.jike.handler;

import com.github.junbaor.jike.App;
import com.github.junbaor.jike.cmd.HomeCmd;
import com.github.junbaor.jike.exceptions.NoLoginException;
import com.github.junbaor.jike.model.AddCommentsRep;
import com.github.junbaor.jike.model.FollowingUpdates;
import com.github.junbaor.jike.util.StringUtils;
import com.github.junbaor.jike.util.SystemUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;

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

    public static void showImages(String next, List<FollowingUpdates.DataBean> dataList) {
        String[] params = next.trim().split(" ");
        if (params.length != 2) {
            System.out.println("输入错误, 请重新输入");
        } else {
            int index = Integer.parseInt(params[1]); // 序号
            if (CollectionUtils.isEmpty(dataList) || dataList.size() < index) {
                System.out.println("根据序号未匹配到动态");
            } else {
                FollowingUpdates.DataBean dataBean = dataList.get(index - 1);
                if (CollectionUtils.isNotEmpty(dataBean.getPictures())) {
                    for (FollowingUpdates.DataBean.PicturesBean picture : dataBean.getPictures()) {
                        printPicture(picture);
                    }
                }
            }
        }
    }

    private static void printPicture(FollowingUpdates.DataBean.PicturesBean picture) {
        String picUrl = picture.getPicUrl();
        String extension = FilenameUtils.getExtension(picUrl);

        /**
         * cdn 路径不带后缀的图片终端无法显示, Content-Type 都是 image/heif, 需要利用七牛云转码
         * 每月 0-20TB：免费, 20TB 以上：0.025 元/GB
         * 瓦恁不要打我 ...
         */
        if (StringUtils.isBlank(extension) && !picUrl.contains("imageMogr2")) {
            picUrl += "?imageMogr2/format/jpg";
        }

        byte[] urlBytes = App.jikeClient.getUrlBytes(picUrl);
        if (urlBytes != null) {
            SystemUtils.printImage(urlBytes);
        }
    }

}
