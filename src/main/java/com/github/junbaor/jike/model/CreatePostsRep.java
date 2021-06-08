package com.github.junbaor.jike.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CreatePostsRep {

    private boolean success;
    private String toast;
    private DataBean data;

    @NoArgsConstructor
    @Data
    public static class DataBean {
        private String id;
        private String type;
        private String content;
        private List<?> urlsInText;
        private String status;
        private boolean isCommentForbidden;
        private int likeCount;
        private int commentCount;
        private int repostCount;
        private int shareCount;
        private List<?> pictures;
        private boolean collected;
        private Object collectTime;
        private UserBean user;
        private String createdAt;
        private boolean isFeatured;
        private boolean enablePictureComments;
        private RolloutsBean rollouts;
        private List<ScrollingSubtitlesBean> scrollingSubtitles;

        @NoArgsConstructor
        @Data
        public static class UserBean {
            private String id;
            private String username;
            private String screenName;
            private String createdAt;
            private String updatedAt;
            private boolean isVerified;
            private String verifyMessage;
            private String briefIntro;
            private AvatarImageBean avatarImage;
            private String profileImageUrl;
            private StatsCountBean statsCount;
            private boolean isBannedForever;
            private String gender;
            private RefRemarkBean refRemark;

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
                private int topicSubscribed;
                private int topicCreated;
                private int followedCount;
                private int followingCount;
                private int highlightedPersonalUpdates;
                private int liked;
                private int respectedCount;
            }

            @NoArgsConstructor
            @Data
            public static class RefRemarkBean {
                private String type;
                private String refId;
            }
        }

        @NoArgsConstructor
        @Data
        public static class RolloutsBean {
            private WmpShareBean wmpShare;

            @NoArgsConstructor
            @Data
            public static class WmpShareBean {
                private String id;
                private String path;
            }
        }

        @NoArgsConstructor
        @Data
        public static class ScrollingSubtitlesBean {
            private String text;
            private String type;
        }
    }
}
