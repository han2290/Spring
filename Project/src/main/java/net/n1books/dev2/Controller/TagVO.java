package net.n1books.dev2.Controller;

import java.util.HashMap;

public class TagVO {
	private HashMap<String,String> Tag = new HashMap<>();
	private HashMap<String,String> NoP = new HashMap<>();
	//선언하지 않았지만 Disc도 사용할 것임(할인)
	private HashMap<String,String> User = new HashMap<>();
	//값을 줄 때 애초에 맵으로 줘야 편하다
	
	public TagVO() {
		setTag();
		setNoP();
	}
	
	

	
	public HashMap<String, String> getUser() {
		return User;
	}




	public void setUser(HashMap<String, String> user) {
		User = user;
	}




	public HashMap<String, String> getTag() {
		return Tag;
	}
	public void setTag() {
		Tag.put("indie", "492");
		Tag.put("action", "19");
		Tag.put("adventure", "21");
		Tag.put("casual", "592");
		Tag.put("strategy", "9");
		Tag.put("rpg", "122");
		Tag.put("fps", "1663");
		Tag.put("openworld", "1695");
		//System.out.println(Tag.toString());
	}
	public HashMap<String, String> getNoP() {
		return NoP;
	}
	public void setNoP() {
		NoP.put("single", "2");
		NoP.put("multi", "1");
		NoP.put("online", "25");
		System.out.println(NoP.toString());
	}
	
	public String AddOption(HashMap<String,String> option) {
		String OptionUrl="";
		System.out.println("option: "+option.toString());
		for(String str:option.keySet()) {
			System.out.println("str:" + str);
			
			if(str.equals("tag")&&isKey(getTag(),option.get(str))) {
				//System.out.println("tag check");
				//option에 tag가 있고 그 tag의 값이 Map tag의 값 중에 있는 값일 경우
				OptionUrl+="&tags=" + getTag().get(option.get(str).toString());
			}else if(str.equals("nop")&&isKey(getNoP(),option.get(str))){
			
				OptionUrl+="&category3="+getNoP().get(option.get(str).toString());
				
			}else if(str.equals("disc")){
				//System.out.println("disc check");
				OptionUrl+="&specials=1";
			}
		}
		//System.out.println("!!"+OptionUrl);
		return OptionUrl;
		
	}
	
	public boolean isKey(HashMap<String, String> map, String key) {
		for(String str:map.keySet()) {
			//System.out.println(str+":"+key);
			if(str.equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
}
