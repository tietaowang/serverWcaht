

import java.io.IOException;
import java.net.URLDecoder;

import javapns.Push;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Servlet implementation class DragonAction
 */
@WebServlet("/DragonAction")
public class DragonAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DragonAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//发送通知
		String  timer = new String( request.getParameter("timer").getBytes("iso8859-1"),"utf-8");
		String  team = new String( request.getParameter("team").getBytes("iso8859-1"),"utf-8");
		String  score = new String( request.getParameter("score").getBytes("iso8859-1"),"utf-8");
		String  id = new String( request.getParameter("id").getBytes("iso8859-1"),"utf-8");
	    
		System.out.println(new String( request.getParameter("timer").getBytes("iso8859-1"),"utf-8"));
		System.out.println(new String( request.getParameter("team").getBytes("iso8859-1"),"utf-8"));
		System.out.println(new String( request.getParameter("score").getBytes("iso8859-1"),"utf-8"));
		System.out.println(new String( request.getParameter("id").getBytes("iso8859-1"),"utf-8"));
		
		String aps = "{\"aps\":{\"alert\":\"时间:"+timer+"\",\"badge\":1,\"sound\":\"default\",\"timer\":\""+timer+"\",\"team\":\""+team+"\",\"score\":\""+score+"\",\"id\":\""+id+"\"}}";
		
		PushsServer.send(aps);
	}
	
	public static void main(String[] args) {
     String aps = "{\"aps\":{\"alert\":\"时间:"+123+"\",\"badge\":1,\"sound\":\"default\",\"timer\":\""+123+"\",\"team\":\""+3+"\",\"id\":\""+123+"\"}}";
		
		PushsServer.send(aps);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
