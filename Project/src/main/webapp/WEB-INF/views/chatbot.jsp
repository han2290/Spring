<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- bootstrap start -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title> page title </title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-3.3.1.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<!-- bootstrap end -->
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet"> 
<script type="text/javascript">
$(document).ready(function(){
	$('#btn_restart').click(function(){
		let re = confirm('모든 내용이 초기화됩니다. \n 정말 재시작 할까요?');
		if(re){			
			$('#said').html('');
			ajax_process('');			
		}
		
	});
	
	
	$('#txt_isay').keypress(function(e){
		if(e.which==13){
			conversation();
		}
	});
	
	$('#btn_isay').click(function(e){
		conversation();
	});
	
	ajax_process('');
});

function conversation(){
	let _isay = $.trim($('#txt_isay').val());
	if(_isay==''){
		alert('내용을 입력하세요');
		return;
	}
	let isay ='<div class="right">';
		isay += '<div ><span class="boxR">'+
		
		'<font size="2">'+_isay+'</font>'
				
				+'</span>';		
				
				
		isay +='</div>';			
			
	$('#said').append(isay);
	$('#txt_isay').val('');
	$('html, body').stop().animate( { scrollTop :$(document).height()});
	ajax_process(_isay);
	
}

function ajax_process(_isay){
	$.ajax({//값 받아오기, 여기서 json 매핑을 실행해서 리턴받아야 한다.
		type:'POST',
		url:'watsonsay',
		data:{'isay':_isay},
		async:true,//비동기: 거짓, 즉 저거는 동기다.
		success:function(data){
			let watsonsay='<div class="left">';	
				//watsonsay += '<p class="col-lg-1"/>';
				watsonsay += '<img class="icon"'+
				' src="<c:url value="/resources/img/co2.png" />" width="50"/>';
				watsonsay +=  
					//'<div class="col-lg-6">'+
					/* +'<div class="icon">ee</div>'+ */
					/* style="display: inline; background-color: blue; color:white;" */
					'<div><span class="box">'+
					'<font size="2">'+data.output.text+'</font>'					
					+'</span></div>'
					//+'</div>';
				watsonsay += '</div>';	
			$('#said').append(watsonsay);
			$('html, body').stop().animate( { scrollTop :$(document).height()});
			//alert(data.intents.val(0));
			if(data.context.req=='list'){
				ajax_process_getList(data);
			}else if(data.context.req=='info'){
				ajax_process_getInfo(data.context.Name);
			}
			
			
				//html body에서 현재 문서의 스크롤의 top는 문서의 높이만큼이다
				//내려가는 시간은 0.5초이다.
		}
	});//ajax 괄호 끝	
}

function ajax_process_getInfo(Name){
	//alert('함수: '+Name);
	/* alert('함수 실행: '+option.context.tag+option.context.nop+option.context.disc); */
	$.ajax({
		type:'POST',
		url:'getInfo',
		data:{'Name':Name},
		async:true,
		success:function(data){
			let watsonsay='<div class="left row">';	
				watsonsay +=  
					'<span class="addbox">'+
					'<font size="2">'+data.Result+'</font>'
					+'</span>';
				watsonsay += '</div>';	
			$('#said').append(watsonsay);
			$('html, body').stop().animate( { scrollTop :$(document).height()});
			$(document).scrollTop($(document).height());
				//html body에서 현재 문서의 스크롤의 top는 문서의 높이만큼이다
				//내려가는 시간은 0.5초이다.
		}
	});//ajax 괄호 끝	
}

function ajax_process_getList(option){
	/* alert('함수 실행: '+option.context.tag+option.context.nop+option.context.disc); */
	$.ajax({
		type:'POST',
		url:'getList',
		data:{'tag':option.context.tag,
			  'nop':option.context.nop,
			  'disc':option.context.disc},
		async:true,
		success:function(data){
			let watsonsay='<div class="left row">';	
				watsonsay +=  
					'<span class="addbox">'+
					'<font size="2">'+data.Result+'</font>'+
					'</span>'
				watsonsay += '</div>';	
				
				
			$('#said').append(watsonsay);
			$('html, body').stop().animate( { scrollTop :$(document).height()});
			$(document).scrollTop($(document).height());
				//html body에서 현재 문서의 스크롤의 top는 문서의 높이만큼이다
				//내려가는 시간은 0.5초이다.
		}
	});//ajax 괄호 끝	
}

</script>




</head>
<body>
	<div id="wrapper">

		<div id="header">
		<h1>게임 추천</h1>
		</div>

		<div id="cont">
			 <div id="said" class="say"></div>
		</div>


		<div id="footer">
			<input type="text" id="txt_isay"/>
			<button type="button" id="btn_isay" >전송</button>
<!-- 			<button type="button" id="btn_restart" class="btn btn-default">대화
				재시작</button>  -->
		</div>

	</div>

</body>
</html>