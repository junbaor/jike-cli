package com.github.junbaor.jike.handler;

import com.github.junbaor.jike.App;
import com.github.junbaor.jike.exceptions.SmsCodeErrorException;
import com.github.junbaor.jike.model.SendSms;
import com.github.junbaor.jike.util.StringUtils;

import java.io.IOException;

public class LoginHandler {

    public void handler() throws SmsCodeErrorException, IOException {
        System.out.println("✨ 需要登录即刻账号");
        System.out.print("请输入手机国家区号,如+86:");
        String areaCode = App.scanner.nextLine();
        if (StringUtils.isBlank(areaCode)) {
            areaCode = "+86";
        }
        if (!areaCode.startsWith("+")) {
            areaCode = "+" + areaCode;
        }
        System.out.print("请输入手机号:");
        String mobile = App.scanner.nextLine();
        SendSms sendSms = App.jikeClient.sendSmsCode(areaCode, mobile);
        if (sendSms == null) {
            System.out.println("短信验证码发送失败,请重试");
            return;
        }
        System.out.print("验证码已发送,");
        do {
            System.out.print("请输入手机短信验证码:");
            String smsCode = App.scanner.nextLine();
            try {
                App.jikeClient.smsCodeLogin(areaCode, mobile, smsCode);
                return;
            } catch (SmsCodeErrorException e) {
                System.out.println("验证码错误");
            }
        } while (true);
    }

}
