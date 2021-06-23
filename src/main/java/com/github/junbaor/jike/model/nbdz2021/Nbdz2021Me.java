package com.github.junbaor.jike.model.nbdz2021;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Nbdz2021Me {

    private String id;
    private String username;
    private String screenName;
    private String avatar;
    private String briefIntro;
    private String camp;
    private ContributionBean contribution;
    private List<TeammatesBean> teammates;

    @NoArgsConstructor
    @Data
    public static class ContributionBean {
        private int todayScore;
        private int careerScore;
        private double percentileRank;
        private String title;
        private String titlePic;
    }

    @NoArgsConstructor
    @Data
    public static class TeammatesBean {
        private String id;
        private String username;
        private String screenName;
        private String avatar;
        private String briefIntro;
        private String camp;
    }
}
