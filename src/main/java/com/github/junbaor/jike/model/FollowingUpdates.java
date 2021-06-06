package com.github.junbaor.jike.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class FollowingUpdates {

    private boolean success;
    private List<DataBean> data;
    private LoadMoreKeyBean loadMoreKey;

    @NoArgsConstructor
    @Data
    public static class LoadMoreKeyBean {
        private String session;
        private long lastPageEarliestTime;
        private long lastReadTime;
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
        private int likeCount;
        private int commentCount;
        private int repostCount;
        private int shareCount;
        private TopicBean topic;
        private List<PicturesBean> pictures;
        private boolean collected;
        private Object collectTime;
        private UserBean user;
        private Date createdAt;
        private boolean isFeatured;
        private boolean enablePictureComments;
        private RolloutsBean rollouts;
        private List<ScrollingSubtitlesBean> scrollingSubtitles;
        private ReadTrackInfoBean readTrackInfo;
        private List<String> updateIds;
        private String action;
        private List<String> usernames;
        private List<String> targetUsernames;
        private List<UsersBean> users;
        private List<TargetUsersBean> targetUsers;
        private PoiBean poi;
        private String likeIcon;
        private VideoBean video;
        private LiveBean live;
        private String verb;

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
            private String storyStatus;

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
        public static class ReadTrackInfoBean {
            private String storyStatus;
            private long loadedAt;
            private String feedType;
        }

        @NoArgsConstructor
        @Data
        public static class PoiBean {
            private List<Double> location;
            private String name;
            private String pname;
            private String cityname;
            private String formattedAddress;
            private String poiId;
            private String countryname;
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
        public static class LiveBean {
            private String id;
            private UserBean user;
            private String title;
            private String status;
            private boolean isFinished;
            private String createdAt;
            private TopicBean topic;
            private PictureBean picture;

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
                private String ref;
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
            }

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
            public static class PictureBean {
                private String picUrl;
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

        @NoArgsConstructor
        @Data
        public static class UsersBean {
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
            private String ref;
            private String storyStatus;

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
        }

        @NoArgsConstructor
        @Data
        public static class TargetUsersBean {
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
            private String ref;

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
        }
    }
}
