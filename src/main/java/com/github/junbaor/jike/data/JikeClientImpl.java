package com.github.junbaor.jike.data;

import com.github.junbaor.jike.common.Config;
import com.github.junbaor.jike.exceptions.NoLoginException;
import com.github.junbaor.jike.exceptions.SmsCodeErrorException;
import com.github.junbaor.jike.exceptions.UnauthorizedException;
import com.github.junbaor.jike.model.*;
import com.github.junbaor.jike.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class JikeClientImpl {

    public static final JikeClientImpl INS = new JikeClientImpl();

    public static final String DEVICE_ID = "4653BFCE-9D54-471C-809C-422AC240DA7B";
    public static final String IDFV = "5C5FC6BB-F6E6-4689-BB5A-E88763186C55";

    private final OkHttpClient client = new OkHttpClient().newBuilder()
            .addNetworkInterceptor(new HttpLoggingInterceptor(log::debug).setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    public FollowingUpdates followingUpdates(Integer limit, FollowingUpdates.LoadMoreKeyBean moreKeyBean)
            throws UnauthorizedException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("limit", limit);
        if (moreKeyBean != null) {
            map.put("loadMoreKey", moreKeyBean);
        }
        String json = JsonUtils.toJson(map);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Headers headers = getDefaultHeader();
        Request request = new Request.Builder()
                .url("https://api.ruguoapp.com/1.0/personalUpdate/followingUpdates")
                .method("POST", body)
                .headers(headers)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 401) {
            throw new UnauthorizedException();
        }
        boolean success = response.code() == 200;
        if (success) {
            ResponseBody responseBody = response.body();
            assert responseBody != null;
            return JsonUtils.asObject(responseBody.string(), FollowingUpdates.class);
        }
        return null;
    }

    public PersonalUpdate personalUpdate() {
        try {
            String json = "{\"limit\":10,\"username\":\"" + Config.getUserName() + "\"}";
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, json);
            Headers headers = getDefaultHeader();
            Request request = new Request.Builder()
                    .url("https://api.ruguoapp.com/1.0/personalUpdate/single")
                    .method("POST", body)
                    .headers(headers)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() == 401) {
                throw new UnauthorizedException();
            }
            boolean success = response.code() == 200;
            if (success) {
                ResponseBody responseBody = response.body();
                assert responseBody != null;
                return JsonUtils.asObject(responseBody.string(), PersonalUpdate.class);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Profile profile() throws UnauthorizedException, IOException {
        Headers headers = getDefaultHeader();
        Request request = new Request.Builder()
                .url("https://api.ruguoapp.com/1.0/users/profile")
                .method("GET", null)
                .headers(headers)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 401) {
            throw new UnauthorizedException();
        }
        boolean success = response.code() == 200;
        if (success) {
            ResponseBody body = response.body();
            assert body != null;
            return JsonUtils.asObject(body.string(), Profile.class);
        }
        return null;
    }

    public void refreshToken() throws NoLoginException {
        if (StringUtils.isBlank(Config.getRefreshToken())) {
            throw new NoLoginException();
        }

        log.info("触发 refreshToken 更新");

        try {
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
            RequestBody body = RequestBody.create(mediaType, "https://api.ruguoapp.com/app_auth_tokens.refresh");
            String refreshToken = Config.getRefreshToken();
            Request request = new Request.Builder()
                    .url("https://api.ruguoapp.com/app_auth_tokens.refresh")
                    .method("POST", body)
                    .headers(getDefaultHeader())
                    .addHeader("x-jike-refresh-token", refreshToken == null ? "" : refreshToken)
                    .build();
            Response response = client.newCall(request).execute();
            boolean success = response.code() == 200;
            if (success) {
                ResponseBody responseBody = response.body();
                assert responseBody != null;
                RefreshToken token = JsonUtils.asObject(responseBody.string(), RefreshToken.class);
                Config.setAccessToken(token.getAccessToken());
                Config.setRefreshToken(token.getRefreshToken());
                return;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw new NoLoginException();
    }

    public SendSms sendSmsCode(String areaCode, String mobile) {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            Map<String, Object> map = new HashMap<>();
            map.put("action", "PHONE_MIX_LOGIN");
            map.put("mobilePhoneNumber", mobile);
            map.put("areaCode", areaCode);
            RequestBody body = RequestBody.create(mediaType, JsonUtils.toJson(map));
            Request request = new Request.Builder()
                    .url("https://api.ruguoapp.com/1.0/users/getSmsCode")
                    .method("POST", body)
                    .headers(getDefaultHeader())
                    .build();
            Response response = client.newCall(request).execute();

            boolean success = response.code() == 200;
            if (success) {
                ResponseBody responseBody = response.body();
                assert responseBody != null;
                return JsonUtils.asObject(responseBody.string(), SendSms.class);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void smsCodeLogin(String areaCode, String mobile, String smsCode) throws SmsCodeErrorException, IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Map<String, Object> map = new HashMap<>();
        map.put("smsCode", smsCode);
        map.put("mobilePhoneNumber", mobile);
        map.put("areaCode", areaCode);
        RequestBody body = RequestBody.create(mediaType, JsonUtils.toJson(map));
        Request request = new Request.Builder()
                .url("https://api.ruguoapp.com/1.0/users/mixLoginWithPhone")
                .method("POST", body)
                .headers(getDefaultHeader())
                .build();
        Response response = client.newCall(request).execute();

        boolean success = response.code() == 200;
        if (success) {
            String accessToken = response.header("x-jike-access-token");
            String refreshToken = response.header("x-jike-refresh-token");
            Config.setAccessToken(accessToken);
            Config.setRefreshToken(refreshToken);
            Config.setAreaCode(areaCode);
            Config.setMobilePhoneNumber(mobile);
            if (StringUtils.isNotBlank(accessToken) && StringUtils.isNotBlank(refreshToken)) {
                log.info("登录成功");
                log.info("accessToken:" + accessToken);
                log.info("refreshToken:" + refreshToken);
            }
        } else {
            throw new SmsCodeErrorException();
        }
    }

    @NotNull
    private Headers getDefaultHeader() {
        Headers.Builder builder = new Headers.Builder();
        builder.add("Host", "api.ruguoapp.com");
        builder.add("Cookie", "abtest_info={}; abtest_info.sig=TpMSLxutJSIb6SX-RcpsEJ9rvBM");
        builder.add("king-card-status", "unavailable");
        builder.add("client-request-id", UUID.randomUUID().toString().toUpperCase());
        builder.add("user-agent", "jike/7.17.0 (com.ruguoapp.jike; build:1901; iOS 14.7.0) Alamofire/5.4.3");
        builder.add("x-jike-device-properties", "{\"idfv\":\"" + IDFV + "\"}");
        builder.add("app-buildno", "1901");
        builder.add("x-jike-device-id", DEVICE_ID);
        builder.add("os", "ios");
        if (StringUtils.isNotBlank(Config.getAccessToken())) {
            builder.add("x-jike-access-token", Config.getAccessToken());
        }
        builder.add("manufacturer", "Apple");
        builder.add("bundleid", "com.ruguoapp.jike");
        builder.add("accept-language", "zh-Hans-CN;q=1.0");
        builder.add("support-h265", "true");
        builder.add("model", "iPhone10,1");
        builder.add("app-permissions", "14");
        builder.add("accept", "*/*");
        builder.add("accept", "*/*");
        builder.add("app-version", "7.17.0");
        builder.add("wificonnected", "true");
        builder.add("os-version", "Version 14.7 (Build 18G5033e)");
        return builder.build();
    }

}
