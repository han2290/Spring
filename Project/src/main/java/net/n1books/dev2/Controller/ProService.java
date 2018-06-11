package net.n1books.dev2.Controller;

import java.io.IOException;
import java.util.HashMap;

public interface ProService {
	public String getAppList(HashMap<String, String> option) throws IOException;
	public String getAppInfo(String Name) throws Exception;
	
}
