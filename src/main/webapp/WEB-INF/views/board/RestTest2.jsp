<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
$.ajax({
	// 서버 접속 URL
	url : '/reply/list/300',
	method  : 'get',
	// 반환 데이터 타입
	dataType : 'json',
	
	// 처리 성공
	success : function(data, textStatus, jqXHR){
		console.log("success!");
		//console.log("data", data);
		//console.log("textStatus", textStatus);
		//console.log("jqXHR", jqXHR);
		
		// 콜백함수가 있으면 콜백함수 실행
		if(callback){
			callback(data);
		}
		
	},
	
	// 처리 실패
	error : function(jqXHR, textStatus, errorThrown){
		console.log("error!");
		console.log("errorThrown", errorThrown);
		console.log("textStatus", textStatus);
		console.log("jqXHR", jqXHR);
		
		// 콜백함수가 있으면 콜백함수 실행
		if(error){
			error(errorThrown);
		}
		
	}
	
});





</script>
<body>

</body>
</html>