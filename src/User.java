import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.session.IoSession;
import org.json.JSONArray;


/**
 * @author pierre
 *
 */
public class User {
	
	private String account;
	private String name;
	private  JSONArray msgQueue = new JSONArray();
	
	private IoSession  session ;
	// session <=> account
	private  HashMap<Long, String>  sessinMapping = new HashMap<Long, String>();
	
	
	public IoSession getSession() {
		return session;
	}
	public void setSession(IoSession session) {
		this.session = session;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		System.out.println("---------");
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		System.out.println("---55------");
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (this.account.equals(other.account)) {
		
				return false;
		} 
		return true;
	}
	


	@Override
	public String toString() {
		return "{\"account\" : \"" + account + "\",\"name\" :\"" + name + "\", \"msgQueue\":\""
				+  msgQueue + "\"]";
	}
	public JSONArray getMsgQueue() {
		return msgQueue;
	}
	public void setMsgQueue(JSONArray msgQueue) {
		this.msgQueue = msgQueue;
	}
	public HashMap<Long, String> getSessinMapping() {
		return sessinMapping;
	}
	

	
}
