package com.mrs.sysmgr.common;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SendSms {

	private static String Url = "http://106.ihuyi.com/webservice/sms.php?method=Submit";

	public static void main(String[] args) {

		System.out.println(createAuthCode());
	}

	public static String createAuthCode() {

		String authCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
		return authCode;
	}

	public static boolean sendSms(String content, String tel) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);

		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

		NameValuePair[] data = {// 提交短信
		new NameValuePair("account", "cf_ltys"),
				new NameValuePair("password", "87d1620aaaeedb9fa2e30862b42fbd5a"), // 密码可以使用明文密码或使用32位MD5加密
				new NameValuePair("mobile", tel),
				new NameValuePair("content", content),
		};
		method.setRequestBody(data);
		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");

			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
			if (code.equals("2")) {
				System.out.println(tel + "用戶短信提交成功");
				return true;
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return false;
	}

}
