import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.mina.*;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaTimeServer {
	// 定义监听端口
	private static final int PORT = 8995;

	public static TimeServerHandler serverHandler = null;

	public static void main(String[] args) throws IOException {

		// 创建服务端监控线程
		IoAcceptor acceptor = new NioSocketAcceptor();
		// acceptor.getSessionConfig().set;
		// acceptor.getSessionConfig().setReadBufferSize(2048*10000);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		// 设置日志记录器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		// acceptor.setFilterChainBuilder(new )
		// 设置编码过滤器
		// acceptor.getFilterChain().addLast(
		// "codec",
		// new ProtocolCodecFilter(new TextLineCodecFactory()));
		// 指定业务逻辑处理器
		serverHandler = new TimeServerHandler();
		acceptor.setHandler(serverHandler);
		// 设置端口号
		try {
			acceptor.bind(new InetSocketAddress(PORT));
			// 启动监听线程
			acceptor.bind();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}