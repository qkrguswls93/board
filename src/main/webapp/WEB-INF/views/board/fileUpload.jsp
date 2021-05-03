<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
	
	//화면이 준비가 되면 실행
	$(document).ready(function(){
		//업로드버튼누르면 이벤트 실행
		$("#uploadBtn").on("click",function(){
			//formData 생성
			var formData = new FormData(document.uploadForm);
			console.log("attachNo" + formData.get("attachNo"));
			
			//파일업로드 컨트롤러 호출,파일을 서버에 저장
			$.ajax({
				url : '/fileUploadAjax',
				method : 'post',
				dataType : 'json',
			
				processData : false,
				contentType : false,
				data : formData,
					
				success : function(result){
					console.log("callBack result", result);
				},
					
				error : function(){
					console.log("error");
				} 
			});
		});
	});
</script>
</head>
<body>

 <form action="/uploadFormAction" method="post" enctype="multipart/form-data" name="uploadForm">
 	
 attachNo :	<input type="text" name="attachNo" value="0"><br>
 	<input type='file' name='uploadFile' multiple>
 	<button>전송</button>
 	<button type="button" id="uploadBtn">Ajax 버튼</button>
 
 </form>

</body>
</html>