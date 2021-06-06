package com.github.junbaor.jike.data;

import com.github.junbaor.jike.exceptions.NoLoginException;
import com.github.junbaor.jike.exceptions.SmsCodeErrorException;
import com.github.junbaor.jike.exceptions.UnauthorizedException;
import com.github.junbaor.jike.model.FollowingUpdates;
import com.github.junbaor.jike.model.PersonalUpdate;
import com.github.junbaor.jike.model.Profile;
import com.github.junbaor.jike.model.SendSms;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.function.Supplier;

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

    public PersonalUpdate personalUpdate() throws NoLoginException {
        try {
            return proxy.personalUpdate();
        } catch (UnauthorizedException e1) {
            proxy.refreshToken();
            return proxy.personalUpdate();
        }
    }

    public SendSms sendSmsCode(String areaCode, String mobile) {
        return proxy.sendSmsCode(areaCode, mobile);
    }

    public void smsCodeLogin(String areaCode, String mobile, String smsCode) throws SmsCodeErrorException, IOException {
        proxy.smsCodeLogin(areaCode, mobile, smsCode);
    }

    public <T> T exec(Supplier<T> supplier) throws NoLoginException {
        try {
            return supplier.get();
        } catch (UnauthorizedException e) {
            proxy.refreshToken();
            return supplier.get();
        }
    }

}
