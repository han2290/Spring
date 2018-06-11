package net.n1books.dev2.Controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Repository
public class WebScraping {// 왓슨에서 값을 리턴받아서 그것을 인자로 사용 할 것임.

	TagVO tagVO = new TagVO();


	public void Test() throws IOException {
		
		String url = "http://news.naver.com/";
		Document doc = Jsoup.connect(url).get();
		
		
		Elements href = doc.select("div.newsnow");
		
		for(String str:href.select("li").eachText()) {
			System.out.println(str);
		}
		
		
	}

	public String GameRec(HashMap<String, String> option) throws IOException {// String tag
		Document doc;
		String Result = "";
		String url = "http://store.steampowered.com/search/?filter=globaltopsellers&os=win";

		doc = Jsoup.connect(url + tagVO.AddOption(option) + "&l=korean").get();
		int i = 0;
		for (String str : doc.select("span.title").eachText()) {
			Result += (i + 1) + ". " + str + "<br/>";
/*			if (i > 8)
				break;*/
			i++;

		}
		if (Result.length() < 10)
			return "";

		System.out.println("결과\n" + Result);

		return Result;
	}
	

	public String GameInfo(String Name) throws Exception {
		String idUrl = "http://store.steampowered.com/search/?l=korean&term=" + Name;
		Document doc = Jsoup.connect(idUrl).get();
		Elements href = doc.select("div#search_result_container");

		String AppID = href.get(0).getElementsByAttribute("data-ds-appid").attr("data-ds-appid");

		URL url = new URL("http://store.steampowered.com/api/appdetails/?l=korean&appids=" + AppID);
		System.out.println(url);
		InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
		System.out.println(isr.toString());
		JSONObject object = (JSONObject) JSONValue.parse(isr);
		System.out.println("json: "+object);
		JSONObject data1 = (JSONObject) object.get(AppID);
		JSONObject data2 = (JSONObject) data1.get("data");

		//날짜
		JSONObject date = (JSONObject) data2.get("release_date");
		System.out.println(data2);
		//pc 사양
		JSONObject requirement = (JSONObject)data2.get("pc_requirements");
		//pc 사양-최소
		String minimum	="<br/><br/><strong>게임 사양</strong><br/>" + requirement.get("minimum").toString()
				.replaceAll("<ul class=\"bb_ul\">", "")
				.replaceAll("</li>", "")
				.replaceAll("</ul>", "")
				.replaceAll("<br>", "")
				.replaceAll("<li>", "<br/>");
		//사진
		JSONArray screenshots = (JSONArray)data2.get("screenshots");
		
		String about = data2.get("about_the_game").toString();
		if(about.indexOf("<br")<0||about.indexOf("img")>0) {
			about = data2.get("short_description").toString();
		}else {
			about = about.substring(0,about.indexOf("<br"));
			
		}
		System.out.println("about: "+about);
		//\"col-lg-7 col-sm-12\
		String Result = "";
		Result += "<strong>출시날짜:</strong> " + date.get("date");
		Result += "<br/><img class=\"size\"  src=\"" 
		+((JSONObject)screenshots.get(0)).get("path_full")+"\"/>";
		Result += "<br/><strong>게임에 대하여</strong><br/>" + about;
		
		
		System.out.println("minimum시작");
		if(minimum.indexOf("추가")>0) {
			Result += minimum.substring(0, minimum.indexOf("추가"));
		}else {
			Result += minimum;
		}
		

		System.out.println("결과: "+Result);
		return Result;
	}

	public String GameInfo22(String Name) throws Exception {
		Document doc;
		Elements sy;
		String Result = "";
		String GameIntro = "div#game_area_description.game_area_description";
		String GameLow = "div.game_area_sys_req_leftCol";
		String GameHigh = "div.game_area_sys_req_rightCol";
		String GameDate = "div.date";
		String GameDev = "div.summary.column";
		Name = "gta5";
		String url = "http://store.steampowered.com/search/?category1=998&term=" + Name;
		System.out.println("데이터 가져오기~~~ ");
		doc = Jsoup.connect(url + "&l=korean").get();// data-ds-appid
		
		Elements ahref = doc.select("div#search_result_container a");
		//System.out.println(ahref);
		String GameUrl = ahref.get(0).getElementsByAttribute("href").attr("href");
		System.out.println(GameUrl);
		doc = Jsoup.connect(GameUrl + "&l=korean").get();

		Result += "이름: " + Name + "<br/>";

		sy = doc.select(GameIntro); // replace("게임에 대해", "")
		Result += "<br/>게임에 대하여<br/>" + sy.eachText().get(0).replace("게임에 대해", "").trim();

		sy = doc.select(GameDate);
		Result += "<br/>출시 날짜: " + sy.eachText().get(0);

		sy = doc.select(GameDev);
		Result += "<br/>개발사: " + sy.eachText().get(3);

		sy = doc.select(GameLow);// 사양
		Result += "<br/><br/>최소 사양";
		for (int i = 0; i < sy.select("li").eachText().size() - 1; i++) {
			Result += "<br/>" + sy.select("li").eachText().get(i);
		}

		sy = doc.select(GameHigh);// 사양
		Result += "<br/><br/>권장 사양";
		for (int i = 0; i < sy.select("li").eachText().size() - 1; i++) {
			Result += "<br/>" + sy.select("li").eachText().get(i);
		}
		System.out.println("결과: "+Result);
		if (Result.length() < 50)
			return "";
		
		return Result;
	}

	public String getAPPID(String appName) throws JsonParseException, JsonMappingException, IOException {
		// http://store.steampowered.com/api/appdetails/?appids=271590&filter=basic&l=korean
		// https://api.steampowered.com/ISteamApps/GetAppList/v2/
		URL url = new URL("https://api.steampowered.com/ISteamApps/GetAppList/v2/");
		InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
		JSONObject object = (JSONObject) JSONValue.parse(isr);
		JSONObject applist = (JSONObject) object.get("applist");
		JSONArray apps = (JSONArray) applist.get("apps");
		JSONObject app = null;

		appName = appName.toLowerCase().trim().replaceAll(" ", "");
		System.out.println(appName);
		for (int i = 0; i < apps.size(); i++) {
			app = (JSONObject) apps.get(i);

			if (app.get("name").toString().toLowerCase().trim().replaceAll(" ", "").equals(appName)) {
				System.out.println(appName + ":" + app.get("name").toString().toLowerCase().replaceAll(" ", "").trim());
				break;
			}
		}
		// System.out.println(appName+":"+app.get("name").toString().trim().toLowerCase().replaceAll("
		// ", ""));

		// System.out.println(app.get("appid").toString());

		return app.get("appid").toString();

	}


	@org.junit.Test//String Name
	public void GameInfoT() throws Exception {
		String Name = "gta5";
		String idUrl = "http://store.steampowered.com/search/?l=korean&term=" + Name;
		Document doc = Jsoup.connect(idUrl).get();
		Elements href = doc.select("div#search_result_container");

		String AppID = href.get(0).getElementsByAttribute("data-ds-appid").attr("data-ds-appid");

		URL url = new URL("http://store.steampowered.com/api/appdetails/?l=korean&appids=" + AppID);
		System.out.println(url);
		InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
		//***************
		Document doc2 = Jsoup.connect("http://store.steampowered.com/search/?l=korean&term=gta5").get();
		System.out.println(doc2);
		System.out.println(url.toString());

		//***************
		System.out.println(isr.toString());
 
		JSONObject object = (JSONObject) JSONValue.parse(isr);
		System.out.println("json: "+object);
		JSONObject data1 = (JSONObject) object.get(AppID);
		JSONObject data2 = (JSONObject) data1.get("data");

		//날짜
		JSONObject date = (JSONObject) data2.get("release_date");
		System.out.println(data2);
		//pc 사양
		JSONObject requirement = (JSONObject)data2.get("pc_requirements");
		//pc 사양-최소
		String minimum	="<br/><br/><strong>게임 사양</strong><br/>" + requirement.get("minimum").toString()
				.replaceAll("<ul class=\"bb_ul\">", "")
				.replaceAll("</li>", "")
				.replaceAll("</ul>", "")
				.replaceAll("<br>", "")
				.replaceAll("<li>", "<br/>");
		//사진
		JSONArray screenshots = (JSONArray)data2.get("screenshots");
		
		String about = data2.get("about_the_game").toString();
		if(about.indexOf("<br")<0||about.indexOf("img")>0) {
			about = data2.get("short_description").toString();
		}else {
			about = about.substring(0,about.indexOf("<br"));
			
		}
		//System.out.println("about: "+about);
		//\"col-lg-7 col-sm-12\
		String Result = "";
		Result += "<strong>출시날짜:</strong> " + date.get("date");
		Result += "<br/><img class=\"size\"  src=\"" 
		+((JSONObject)screenshots.get(0)).get("path_full")+"\"/>";
		Result += "<br/><strong>게임에 대하여</strong><br/>" + about;
		
		
		System.out.println("minimum시작");
		if(minimum.indexOf("추가")>0) {
			Result += minimum.substring(0, minimum.indexOf("추가"));
		}else {
			Result += minimum;
		}
		

		System.out.println("결과: "+Result);
		//return Result;
	}
	
	  
}
