package net.n1books.dev2.Controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProServiceImpl implements ProService{
	
	@Autowired
	WebScraping ws;

	@Override
	public String getAppList(HashMap<String, String> option) throws IOException {
		return ws.GameRec(option);
	}

	@Override
	public String getAppInfo(String Name) throws Exception {
		return ws.GameInfo(Name);
	}

}
