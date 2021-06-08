package com.github.junbaor.jike.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AddCommentsRep {

    private boolean success;
    private DataBean data;
    private String toast;

    @NoArgsConstructor
    @Data
    public static class DataBean {
        private String type;
        private String id;
        private String targetType;
        private String targetId;
        private Object threadId;
        private String createdAt;
        private int level;
        private String content;
        private List<?> urlsInText;
        private int likeCount;
        private int replyCount;
        private String status;
        private UserBean user;
        private List<?> pictures;
        private boolean liked;
        private ReadTrackInfoBean readTrackInfo;
        private boolean collapsed;
        private boolean collapsible;
        private boolean hideable;
        private boolean deletable;

        @NoArgsConstructor
        @Data
        public static class UserBean {
            @NoArgsConstructor
            @Data
            public static class AvatarImageBean {
                private String thumbnailUrl;
                private String smallPicUrl;
                private String middlePicUrl;
                private String picUrl;
                private String format;
                private String badgeUrl;
            }

            @NoArgsConstructor
            @Data
            public static class StatsCountBean {
            }
        }

        @NoArgsConstructor
        @Data
        public static class ReadTrackInfoBean {
        }
    }
}
