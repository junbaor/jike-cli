package com.github.junbaor.jike.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class PersonalUpdate {

    private boolean success;
    //    private List<DataBean> data;
    private List<FollowingUpdates.DataBean> data;
    private LoadMoreKeyBean loadMoreKey;

    @NoArgsConstructor
    @Data
    public static class LoadMoreKeyBean {
        private String lastId;
    }

    @NoArgsConstructor
    @Data
    public static class DataBean {
        private String actionTime;
        private String id;
        private String type;
        private String content;
        private List<?> urlsInText;
        private String status;
        private boolean isCommentForbidden;
        private Boolean liked;
        private int likeCount;
        private int commentCount;
        private int repostCount;
        private int shareCount;
        private TopicBean topic;
        private PoiBean poi;
        private List<PicturesBean> pictures;
        private boolean collected;
        private Object collectTime;
        private UserBean user;
        private Date createdAt;
        private boolean isFeatured;
        private boolean enablePictureComments;
        private RolloutsBean rollouts;
        private List<ScrollingSubtitlesBean> scrollingSubtitles;
        private PinnedBean pinned;
        private LinkInfoBean linkInfo;
        private VideoBean video;
        private String likeIcon;

        @NoArgsConstructor
        @Data
        public static class TopicBean {
            private String id;
            private String type;
            private String content;
            private int subscribersCount;
            private SquarePictureBean squarePicture;
            private String briefIntro;
            private String topicType;
            private String operateStatus;
            private boolean isValid;
            private boolean enablePictureWatermark;
            private String lastMessagePostTime;
            private String subscribersTextSuffix;
            private String subscribersName;
            private TipsBean tips;
            private String intro;
            private String squarePostUpdateTime;
            private String subscribersAction;
            private String approximateSubscribersCount;
            private String subscribersDescription;
            private String ref;

            @NoArgsConstructor
            @Data
            public static class SquarePictureBean {
            }

            @NoArgsConstructor
            @Data
            public static class TipsBean {
            }
        }

        @NoArgsConstructor
        @Data
        public static class PoiBean {
            private List<Double> location;
            private String countryname;
            private String formattedAddress;
            private String pname;
            private String name;
            private String cityname;
            private String poiId;
        }

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
            private BackgroundImageBean backgroundImage;
            private String bio;
            private String gender;
            private String city;
            private String country;
            private String province;
            private RefRemarkBean refRemark;
            private boolean following;

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
        public static class PinnedBean {
            private boolean personalUpdate;
        }

        @NoArgsConstructor
        @Data
        public static class LinkInfoBean {
            private String title;
            private String pictureUrl;
            private String linkUrl;
            private String source;
        }

        @NoArgsConstructor
        @Data
        public static class VideoBean {
            private String type;
            private ImageBean image;
            private int duration;
            private int width;
            private int height;

            @NoArgsConstructor
            @Data
            public static class ImageBean {
                private String picUrl;
                private String thumbnailUrl;
                private String format;
            }
        }

        @NoArgsConstructor
        @Data
        public static class PicturesBean {
            private String key;
            private String thumbnailUrl;
            private String smallPicUrl;
            private String middlePicUrl;
            private String picUrl;
            private String format;
            private double cropperPosX;
            private double cropperPosY;
            private int width;
            private int height;
            private String watermarkPicUrl;
        }

        @NoArgsConstructor
        @Data
        public static class ScrollingSubtitlesBean {
            private String text;
            private String type;
        }
    }
}
