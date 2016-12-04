import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.mina.core.buffer.AbstractIoBuffer;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.json.JSONObject;

/**
 * 服务器端业务逻辑
 */
public class TimeServerHandler extends IoHandlerAdapter {

	public static Hashtable<String, User> sessionStack = new Hashtable<String, User>();

	/**
	 * 连接创建事件
	 */
	@Override
	public void sessionCreated(IoSession session) {
		// 显示客户端的ip和端口
		System.out.println(session.getRemoteAddress().toString());
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	/**
	 * 消息接收事件
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String strMsg = message.toString();
		if (strMsg.trim().equalsIgnoreCase("quit")) {
			session.close(true);
			return;
		}

		IoBuffer ib = (IoBuffer) message;
		// FileInputStream fileInputStream = new
		// FileInputStream(ib.asInputStream());
		String ms = new String(ib.array(), "UTF-8");
		// String imageName = UUID.randomUUID()+".png";
		// FileOutputStream out = new
		// FileOutputStream("/Users/pierre/Desktop/"+imageName);
		// BufferedImage image = ImageIO.read(ib.asInputStream());
		// if(image != null ){//是图片
		// ImageIO.write(image, "png",out);
		// User u = getUserBySeesion(session.getId());
		// JSONObject jo = new JSONObject();
		// jo.put("guest", "true");
		// jo.put("guest", "true");
		// jo.put("date", new Date().getTime());
		// u.getMsgQueue().put(jo);
		//
		// //new Base64Encoder().encode(, arg1, arg2, arg3)
		// return ;
		// }
		// ib.asInputStream().close();
		IoBuffer b = IoBuffer.allocate(100);
		b.putShort((short) 18);// len
		// String str ="Hello";
		ByteArrayOutputStream outputPacket = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(outputPacket);
		// dos.writeUTF( new String(str.getBytes(), "utf-8") );
		dos.flush();
		b.put(outputPacket.toByteArray());
		b.flip();
		// 打印客户端传来的消息内容
		System.out.println("Message written : " + strMsg);
		String[] arrays = ms.split("\\|");
		User user = sessionStack.get(arrays[1]);
		if (user == null ) {
			user = new User();
			user.setAccount(arrays[1]);
			user.setName(arrays[2]);
			user.setSession(session);
			sessionStack.put(arrays[1], user);
			user.getSessinMapping().put(session.getId(), user.getAccount());
		} else {
			if (!user.getSession().isConnected()) {// 更新
				user.setSession(session);
				user.getSessinMapping().put(session.getId(), user.getAccount());

			}
			JSONObject jo = new JSONObject();
			jo.put("guest", arrays[3].trim());
			jo.put("date", new Date().getTime());
			user.getMsgQueue().put(jo);
		}

		System.out.println(ms + "===" + session.getId() + "===="
				+ user.getSession().getId());

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("IDLE" + session.getIdleCount(status));

	}

	public void sendMsg(String account, String msg) throws Exception {

		User user = sessionStack.get(account);
		if (user != null) {
			IoBuffer b = IoBuffer.allocate(1024);
			// b.putShort();// len
			ByteArrayOutputStream outputPacket = new ByteArrayOutputStream();

			DataOutputStream dos = new DataOutputStream(outputPacket);

			dos.write(msg.getBytes());
			dos.flush();
			b.put(outputPacket.toByteArray());
			b.flip();
			System.out.println(user.getSession().isConnected());
			System.out.println(user.getSession().isWriterIdle());
			user.getSession().write(b);

			JSONObject jo = new JSONObject();
			jo.put("me", msg);
			jo.put("date", new Date().getTime());

			user.getMsgQueue().put(jo);
		}
		System.out.println(user.getMsgQueue());

	}

	public User getUserBySeesion(Long sessionid) {
		for (Map.Entry<String, User> map : sessionStack.entrySet()) {
			if (map.getValue().getSession().getId() == sessionid) {
				return map.getValue();

			}
		}

		return null;
	}
}