

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageInputStreamImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class SendMsgAction
 */
//@WebServlet("/SendMsgAction")
public class SendMsgAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
 @Override
public void init() throws ServletException {
	// TODO Auto-generated method stub
	super.init();
	System.out.println("======SendMsgAction======");
	try {
		MinaTimeServer.main(null);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
 

    /**
     * Default constructor. 
     */
    public SendMsgAction() {
    	
    	
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("=========="+MinaTimeServer.serverHandler.sessionStack);
		String json = "'[ ";
		
		  for(Map.Entry<String, User>  obj : MinaTimeServer.serverHandler.sessionStack.entrySet()){
			  System.out.println(obj);
			  json += "{\"account\":\""+obj.getKey().trim() +"\",\"name\":\"" + obj.getValue().getName().trim()+"\"},";
		  }
		  
		  response.setHeader("Content-type", "text/html;charset=UTF-8");
		  response.setCharacterEncoding("UTF-8");
		 json =   json.substring(0, json.length()-1)+"]'" ;
		 System.out.println("===="+json.trim());
		response.getWriter().write(json.trim());
		
		//request.getRequestDispatcher("/index.jsp").forward(request, response);
			
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("=========="+request.getParameterMap());
			try {
				if(request.getParameter("msg") != null){
					System.out.println(new String(request.getParameter("msg").getBytes(),"ISO8859-1"));
					MinaTimeServer.serverHandler.sendMsg(request.getParameter("account"), new String( request.getParameter("msg").getBytes("iso8859-1"),"utf8"));
				}
			      User  u = 	TimeServerHandler.sessionStack.get(request.getParameter("account"));
			      if(u != null ){
			    	  response.setHeader("Content-type", "text/html;charset=UTF-8");
					  response.setCharacterEncoding("UTF-8"); 
					 System.out.println("=聊天历史====="+u.getMsgQueue() );
					response.getWriter().write(u.getMsgQueue().toString());
			      }
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		
		
		
	}

}
