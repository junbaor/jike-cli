package com.github.junbaor.jike.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Profile {

    private UserBean user;
    private List<String> enabledFeatures;
    private AbtestFeaturesBean abtestFeatures;
    private List<?> agreedProtocol;

    @NoArgsConstructor
    @Data
    public static class UserBean {
        private String id;
        private String username;
        private String screenName;
        private String createdAt;
        private String updatedAt;
        private Boolean isVerified;
        private String verifyMessage;
        private String briefIntro;
        private AvatarImageBean avatarImage;
        private String profileImageUrl;
        private StatsCountBean statsCount;
        private Boolean isBannedForever;
        private BackgroundImageBean backgroundImage;
        private String bio;
        private String gender;
        private String city;
        private String country;
        private String province;
        private PreferencesBean preferences;
        private Boolean isBetaUser;
        private Boolean usernameSet;
        private String weiboUid;
        private String wechatOpenId;
        private String wechatUnionId;
        private String qqOpenId;
        private String mobilePhoneNumber;
        private String areaCode;
        private String birthday;
        private WechatUserInfoBean wechatUserInfo;
        private QqUserInfoBean qqUserInfo;
        private List<?> topicRoles;
        private String zodiac;
        private String industry;
        private Boolean showRespect;
        private List<MedalsBean> medals;
        private List<ProfileTagsBean> profileTags;
        private String backgroundMaskColor;
        private Boolean isLoginUser;
        private Boolean isBanned;
        private Boolean userHasPosted;
        private String registerAppVersion;
        private Boolean hasStories;
        private Boolean isDefaultScreenName;
        private ProfileVisitInfoBean profileVisitInfo;

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
        public static class PreferencesBean {
            private Boolean repostWithComment;
            private Boolean syncCommentToPersonalActivity;
            private String env;
            private Boolean debugLogOn;
            private String topicPushSettings;
            private Boolean saveDataUsageMode;
            private Boolean privateTopicSubscribe;
            private Boolean subscribeWeatherForecast;
            private Boolean dailyNotificationOn;
            private Boolean topicTagGuideOn;
            private Boolean autoPlayVideo;
            private Boolean undiscoverableByWeiboUser;
            private Boolean undiscoverableByPhoneNumber;
            private Boolean blockStrangerPoke;
            private Boolean followedNotificationOn;
            private Boolean enableOperationPush;
            private Boolean enablePrivateChat;
            private String tabOnLaunch;
            private Boolean hideSubscribeTab;
            private Boolean openMessageTabOnLaunch;
            private Boolean enablePictureWatermark;
            private Boolean enableChatSound;
        }

        @NoArgsConstructor
        @Data
        public static class WechatUserInfoBean {
            private String id;
            private String externalName;
        }

        @NoArgsConstructor
        @Data
        public static class QqUserInfoBean {
            private String id;
            private String externalName;
        }

        @NoArgsConstructor
        @Data
        public static class ProfileVisitInfoBean {
            private int todayCount;
            private LatestVisitorBean latestVisitor;

            @NoArgsConstructor
            @Data
            public static class LatestVisitorBean {
                private String id;
                private String username;
                private String screenName;
                private String createdAt;
                private String updatedAt;
                private Boolean isVerified;
                private String verifyMessage;
                private String briefIntro;
                private AvatarImageBean avatarImage;
                private String profileImageUrl;
                private StatsCountBean statsCount;
                private Boolean isBannedForever;
                private String gender;

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
            }
        }

        @NoArgsConstructor
        @Data
        public static class MedalsBean {
            private String picUrl;
            private String name;
            private String gotMedalAt;
        }

        @NoArgsConstructor
        @Data
        public static class ProfileTagsBean {
            private String picUrl;
            private String type;
            private String text;
        }
    }

    @NoArgsConstructor
    @Data
    public static class AbtestFeaturesBean {
        private String nEW_USER_GUIDE;
    }
}
