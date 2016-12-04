import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaTimeClient {
    
    public static void main(String[] args){
    	
    	User user = new User();
    	user.setAccount("12");
    	user.setName("55");
    	User user2 = new User();
    	user.setAccount("12");
    	user.setName("55");
    	HashMap<User, String>  table = new HashMap<User, String>();
    	table.put(user, "33");
    	table.put(user2, "33");
    	System.out.println(table);
        // 创建客户端连接器.
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", 
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        
        // 设置连接超时检查时间
        connector.setConnectTimeoutCheckInterval(30);
        connector.setHandler(new TimeClientHandler());
        
        // 建立连接
        ConnectFuture cf = connector.connect(new InetSocketAddress("192.168.11.124", 6482));
        // 等待连接创建完成
        cf.awaitUninterruptibly();
        
        cf.getSession().write("我叫王铁涛");
        cf.getSession().write("吃饭");
        
        // 等待连接断开
        cf.getSession().getCloseFuture().awaitUninterruptibly();
        // 释放连接
        connector.dispose();
    }
}