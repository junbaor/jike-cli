package com.github.junbaor.jike.data;

import com.github.junbaor.jike.common.Config;
import com.github.junbaor.jike.exceptions.NoLoginException;
import com.github.junbaor.jike.exceptions.SmsCodeErrorException;
import com.github.junbaor.jike.exceptions.UnauthorizedException;
import com.github.junbaor.jike.model.*;
import com.github.junbaor.jike.util.JsonUtils;
import com.github.junbaor.jike.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class JikeClientImpl {

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

    public PersonalUpdate single(String userName, PersonalUpdate.LoadMoreKeyBean loadMoreKeyBean) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("limit", 10);
        map.put("username", StringUtils.defaultIfBlank(userName, Config.getUserName()));
        if (loadMoreKeyBean != null) {
            map.put("loadMoreKey", loadMoreKeyBean);
        }
        String json = JsonUtils.toJson(map);
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
            Profile profile = JsonUtils.asObject(body.string(), Profile.class);
            Config.setUserName(profile.getUser().getUsername());
            return profile;
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

                profile(); // 获取个人信息, 为了补充配置文件中的 username
            }
        } else {
            throw new SmsCodeErrorException();
        }
    }

    public CreatePostsRep createPosts(String content) throws IOException {
        Map<String, Object> map = new HashMap<>();
        // https://cdn.jellow.site/FqavXkNAuPb-IVP5Nw6RdtPe2ZAH.jpg
        map.put("pictureKeys", Collections.emptyList()); // "FqavXkNAuPb-IVP5Nw6RdtPe2ZAH.jpg"
//        "coord": {
//            "lng": "116.30330012748468",
//                    "lat": "39.962664618300245",
//                    "coordType": "wgs84"
//        },
        map.put("syncToPersonalUpdate", true);
        map.put("content", content);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, JsonUtils.toJson(map));
        Request request = new Request.Builder()
                .url("https://api.ruguoapp.com/1.0/originalPosts/create")
                .method("POST", body)
                .headers(getDefaultHeader())
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 401) {
            throw new UnauthorizedException();
        }
        boolean success = response.code() == 200;
        if (success) {
            ResponseBody responseBody = response.body();
            assert responseBody != null;
            return JsonUtils.asObject(responseBody.string(), CreatePostsRep.class);
        }
        return null;
    }

    public String uploadToken(File file) throws IOException {
        String md5 = DigestUtils.md5Hex(new FileInputStream(file));
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .get()
                .url("https://api.ruguoapp.com/1.0/upload/token?md5=" + md5)
                .headers(getDefaultHeader())
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 401) {
            throw new UnauthorizedException();
        }
        boolean success = response.code() == 200;
        if (success) {
            ResponseBody responseBody = response.body();
            assert responseBody != null;
            Map map = JsonUtils.asObject(responseBody.string(), Map.class);
            return MapUtils.getString(map, "uptoken");
        }
        return null;
    }

    public boolean like(String id) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"id\":\"" + id + "\"}");
        Request request = new Request.Builder()
                .url("https://api.ruguoapp.com/1.0/originalPosts/like")
                .method("POST", body)
                .headers(getDefaultHeader())
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 401) {
            throw new UnauthorizedException();
        }
        return response.code() == 200;
    }

    public boolean unLike(String id) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"id\":\"" + id + "\"}");
        Request request = new Request.Builder()
                .url("https://api.ruguoapp.com/1.0/originalPosts/unlike")
                .method("POST", body)
                .headers(getDefaultHeader())
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 401) {
            throw new UnauthorizedException();
        }
        return response.code() == 200;
    }

    public AddCommentsRep addComments(String id, String content) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        HashMap<String, Object> map = RequestUtils.map(
                "targetId", id,
                "content", content,
                "pictureKeys", Collections.emptyList(),
                "targetType", "ORIGINAL_POST",
                "currentPageName", 14,
                "sourcePageName", 50,
                "force", false,
                "syncToPersonalUpdates", false
        );
        String json = JsonUtils.toJson(map);
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://api.ruguoapp.com/1.0/comments/add")
                .method("POST", body)
                .headers(getDefaultHeader())
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 401) {
            throw new UnauthorizedException();
        }
        boolean success = response.code() == 200;
        if (success) {
            ResponseBody responseBody = response.body();
            assert responseBody != null;
            return JsonUtils.asObject(responseBody.string(), AddCommentsRep.class);
        }
        return null;
    }

    public FollowingRep getFollowingList(FollowingRep.LoadMoreKeyBean loadMoreKey) throws IOException {
        String json = JsonUtils.toJson(RequestUtils.map(
                "username", Config.getUserName(),
                "limit", 10,
                "loadMoreKey", loadMoreKey
        ));
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://api.ruguoapp.com/1.0/userRelation/getFollowingList")
                .method("POST", body)
                .headers(getDefaultHeader())
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 401) {
            throw new UnauthorizedException();
        }
        boolean success = response.code() == 200;
        if (success) {
            ResponseBody responseBody = response.body();
            assert responseBody != null;
            String string = responseBody.string();
            return JsonUtils.asObject(string, FollowingRep.class);
        }
        return null;
    }

    public FollowerRep getFollowerList(FollowerRep.LoadMoreKeyBean loadMoreKey) throws IOException {
        String json = JsonUtils.toJson(RequestUtils.map(
                "username", Config.getUserName(),
                "limit", 20,
                "loadMoreKey", loadMoreKey
        ));
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://api.ruguoapp.com/1.0/userRelation/getFollowerList")
                .method("POST", body)
                .headers(getDefaultHeader())
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 401) {
            throw new UnauthorizedException();
        }
        boolean success = response.code() == 200;
        if (success) {
            ResponseBody responseBody = response.body();
            assert responseBody != null;
            return JsonUtils.asObject(responseBody.string(), FollowerRep.class);
        }
        return null;
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
