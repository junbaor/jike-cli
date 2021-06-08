package com.github.junbaor.jike.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FollowerRep {

    private boolean success;
    private List<DataBean> data;
    private LoadMoreKeyBean loadMoreKey;

    @NoArgsConstructor
    @Data
    public static class LoadMoreKeyBean {
        private String createdAt;
    }

    @NoArgsConstructor
    @Data
    public static class DataBean {
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
        private BackgroundImageBean backgroundImage;
        private String bio;
        private String gender;
        private String city;
        private String country;
        private String province;
        private String storyStatus;
        private boolean following;
        private String ref;
        private List<TrailingIconsBean> trailingIcons;

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
        public static class BackgroundImageBean {
            private String picUrl;
        }

        @NoArgsConstructor
        @Data
        public static class TrailingIconsBean {
            private PictureBean picture;
            private String url;
            private String picUrl;

            @NoArgsConstructor
            @Data
            public static class PictureBean {
                private String picUrl;
                private String format;
            }
        }
    }
}
