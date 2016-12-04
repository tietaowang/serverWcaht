

import java.util.ArrayList;
import java.util.List;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
/**
 * push服务器 推送信息
 * @author hanfeili
 * 相应的代码可以去
 * http://code.google.com/p/javapns获取
 */
public class PushsServer {
	
	public static void send(String apsjson) {
		/**
		 向执行设备发送Push通知的device token。
		 不同设备的device token应该由iOS应用通过网络发送给服务端程序，
		 服务端程序应该将这些device token保存在服务器中，
		 然后通过循环向每个device token发送Push通知即可。
		 */
		String deviceToken = "0bae0d481294b9491585061b94c5447de80e7f6c9bb9a4c7a00700d871a718a5";
		try {
			// 创建PushNotificationPayload
			//第一种方式 直接 使用字符串 可以传入自定义内容
			//PushNotificationPayload payload = new PushNotificationPayload("{\"aps\":{\"alert\":\"Yogot mail.\",\"badge\":1,\"sound\":\"default\",\"expiry\":1,\"expiration_interval\":1,\"pn_ttl\":1}}");
			PushNotificationPayload payload = new PushNotificationPayload(apsjson);
			// 设置推送消息体
			 //payload.addAlert("");
			
			//设置 通知过期时间
			//payload.setExpiry(2);
			// 设置应用程序图标的小红圈中的数值
			// payload.addBadge(1);
			// 设置推送通知的提示声音
			// payload.addSound("default");
			// 发送推送通知    // p12证书直接导出即可使用不需要再进行其它的   一定要使用push证书
			Push.payload(payload, "/Users/pierre/Desktop/sandao/java/workspace/wchat 2/pushzhengshu.p12", // 指定包含证书和私钥的文件
					"123456", // 设置aps_developer_identity.p12文件的导出密码
					false, // 是否为产品化阶段
					deviceToken);
			
			//PushedNotification.findFailedNotifications(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		/**
		 向执行设备发送Push通知的device token。
		 不同设备的device token应该由iOS应用通过网络发送给服务端程序，
		 服务端程序应该将这些device token保存在服务器中，
		 然后通过循环向每个device token发送Push通知即可。
		 */
		String deviceToken = "eda249689d2d1ba3d912fb8dc3fc8ce9a4d9fa1b3427c3a04af10beda2fba24c";
		try {
			// 创建PushNotificationPayload
			//第一种方式 直接 使用字符串 可以传入自定义内容
			//PushNotificationPayload payload = new PushNotificationPayload("{\"aps\":{\"alert\":\"Yogot mail.\",\"badge\":5,\"sound\":\"default\",\"expiry\":1,\"expiration_interval\":1,\"pn_ttl\":1}}");
			PushNotificationPayload payload = new PushNotificationPayload("{\"aps\":{\"content\":\"可以输入很多内容不显示出来\"}}");
			// 设置推送消息体
			 payload.addAlert("符合0.5");
			
			//设置 通知过期时间
			//payload.setExpiry(2);
			// 设置应用程序图标的小红圈中的数值
			 payload.addBadge(1);
			// 设置推送通知的提示声音
			 payload.addSound("default");
			// 发送推送通知    // p12证书直接导出即可使用不需要再进行其它的   一定要使用push证书
			Push.payload(payload, "/Users/pierre/Desktop/sandao/java/workspace/pushzhengshu.p12", // 指定包含证书和私钥的文件
					"123456", // 设置aps_developer_identity.p12文件的导出密码
					false, // 是否为产品化阶段
					deviceToken);
			
			//PushedNotification.findFailedNotifications(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
