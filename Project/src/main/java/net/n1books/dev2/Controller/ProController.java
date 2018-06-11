package net.n1books.dev2.Controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.watson.developer_cloud.conversation.v1.model.Context;

@RestController
@RequestMapping("chatbot")
public class ProController {
	
	@Autowired
	ProService ps;
	
	@RequestMapping("Test")
	public ModelAndView TestVIew() {
		return new ModelAndView("Test");
	}
	@RequestMapping("getInfo")
	public Context getInfo(@RequestParam(defaultValue="") String Name) {
		Context context = new Context();
		String Result = null;
		try {
			Result = ps.getAppInfo(Name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			Result = "해당 정보를 찾을 수 없습니다";
		}
		System.out.println("name: "+Name);
		
		System.out.println(Result);
		context.put("Result", Result);

		return context;
	}
	
	@RequestMapping("getList")
	public Context getList(
			@RequestParam(defaultValue = "") String tag,
			@RequestParam(defaultValue = "") String nop,
			@RequestParam(defaultValue = "") String disc) {
		Context context = new Context();
		HashMap<String,String> option = new HashMap<>();
		System.out.println(tag+":"+nop+":"+disc);
		if(!tag.equals(""))		option.put("tag", tag);
		if(!nop.equals(""))		option.put("nop", nop);
		if(!disc.equals(""))	option.put("disc", disc);
		String Result=null;
		try {
			Result = ps.getAppList(option);
		} catch (IOException e) {
			Result = "해당 정보를 찾지 못했습니다.";
			e.printStackTrace();
		}		
		context.put("Result", Result);
		
		return context;
	}
	
	
	
}
