package net.n1books.dev2.Controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

@RestController
@RequestMapping("chatbot")
public class WatsonSay {
	private static final Logger logger = LoggerFactory.getLogger(WatsonSay.class);
	
	@RequestMapping("watsonsay")		
	public MessageResponse watsonsay(String isay, HttpSession session) {//session을 브라우저로부터 받아옴
		// MessageResponse: 왓슨에서 전달해주는 값.
		// 답변이라던지 여러 상태라든디 MessageResponse 안에 들어감
		logger.info("user input: " + isay);

		Conversation service = new Conversation(Conversation.VERSION_DATE_2017_05_26);
		service.setUsernameAndPassword("id", "pw");

		InputData input = new InputData.Builder(isay).build();
		MessageOptions options=null;
		
		if(!isay.equals("")) {
			 options = 
					new MessageOptions.Builder("workspace id")
					.input(input)
		            .context((Context)session.getAttribute("context"))
		            .build();
			//메세지를 던지기 위해...
		}else {
			 options = 
					new MessageOptions.Builder("workspace id")
					.input(input)
		            .build();
			//메세지를 던지기 위해...
		}
		
		
		MessageResponse response = service.message(options).execute();
		//옵션을 실행해서 응답을 얻음
		
		session.setAttribute("context",response.getContext());
		//context값을 받아와서 넣어주면 흐름을 유지시킬 수 있다.
		
		String watsonsay = response.getOutput().getText().get(0);

		for(String str : response.getContext().keySet()) {
			//System.out.println(str+":"+response.getContext().get(str));
			//+":"+response.getContext().get(str)
		}
		//System.out.println(response.getContext().keySet());
		//logger.info(response.toString());
		//System.out.println(response);

		return response;
	}
}
