package com.github.junbaor.jike.data;

import com.github.junbaor.jike.enums.ActionEnum;
import com.github.junbaor.jike.exceptions.NoLoginException;
import com.github.junbaor.jike.exceptions.SmsCodeErrorException;
import com.github.junbaor.jike.exceptions.UnauthorizedException;
import com.github.junbaor.jike.model.*;
import com.github.junbaor.jike.model.nbdz2021.Nbdz2021Me;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class JikeClient {

    private final JikeClientImpl proxy = new JikeClientImpl();

    public FollowingUpdates followingUpdates(Integer limit, FollowingUpdates.LoadMoreKeyBean moreKeyBean)
            throws NoLoginException, IOException {
        try {
            return proxy.followingUpdates(limit, moreKeyBean);
        } catch (UnauthorizedException e) {
            proxy.refreshToken();
            return proxy.followingUpdates(limit, moreKeyBean);
        }
    }

    public Profile profile() throws NoLoginException, IOException {
        try {
            return proxy.profile();
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.profile();
        }
    }

    public PersonalUpdate single(String userName, PersonalUpdate.LoadMoreKeyBean loadMoreKeyBean)
            throws NoLoginException, IOException {
        try {
            return proxy.single(userName, loadMoreKeyBean);
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.single(userName, loadMoreKeyBean);
        }
    }

    public SendSms sendSmsCode(String areaCode, String mobile) {
        return proxy.sendSmsCode(areaCode, mobile);
    }

    public void smsCodeLogin(String areaCode, String mobile, String smsCode) throws SmsCodeErrorException, IOException {
        proxy.smsCodeLogin(areaCode, mobile, smsCode);
    }

    public CreatePostsRep createPosts(String content) throws NoLoginException, IOException {
        try {
            return proxy.createPosts(content);
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.createPosts(content);
        }
    }

    public String uploadToken(File file) throws NoLoginException, IOException {
        try {
            return proxy.uploadToken(file);
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.uploadToken(file);
        }
    }

    public boolean like(String id) throws NoLoginException, IOException {
        try {
            return proxy.like(id);
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.like(id);
        }
    }

    public boolean unLike(String id) throws NoLoginException, IOException {
        try {
            return proxy.unLike(id);
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.unLike(id);
        }
    }

    public AddCommentsRep addComments(String id, String content) throws IOException, NoLoginException {
        try {
            return proxy.addComments(id, content);
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.addComments(id, content);
        }
    }

    public FollowingRep getFollowingList(FollowingRep.LoadMoreKeyBean loadMoreKey) throws IOException, NoLoginException {
        try {
            return proxy.getFollowingList(loadMoreKey);
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.getFollowingList(loadMoreKey);
        }
    }

    public FollowerRep getFollowerList(FollowerRep.LoadMoreKeyBean loadMoreKey) throws IOException, NoLoginException {
        try {
            return proxy.getFollowerList(loadMoreKey);
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.getFollowerList(loadMoreKey);
        }
    }

    public Nbdz2021Me getNbdz2021Me() throws IOException, NoLoginException {
        try {
            return proxy.getNbdz2021Me();
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.getNbdz2021Me();
        }
    }

    public String getNbdz2021Act(ActionEnum actionEnums) throws IOException, NoLoginException {
        try {
            return proxy.getNbdz2021Act(actionEnums);
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.getNbdz2021Act(actionEnums);
        }
    }

}
