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
		$("#attachNo").on("change", function(){
			getFileList($("#attachNo").val());
		});
		
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
					console.log("uploadAjax result", result);
					$("#attachNo").val(result.attachNo);
					$("#uploadFile").val("");
					//document/uploadForm/uploadFile.value=""; -> jquery 안쓸때
					getFileList(result.attachNo);				
				},
					
				error : function(){
					console.log("error");
				} 
			});					
		});
	});

/*

*/
function getFileList(attachNo){
	
	$.ajax({
		url : '/getFileList/' + attachNo,
		method : 'get',
		dataType : 'json',
	
		
		success : function(result){
			//result : List<attachFileVo>
			var htmlContent = "";
			$.each(result, function(index, vo){ //vo그냥 변수임 item으로 해도 됨
				//만약 이미지면 이미지를 보여주고  
				var s_savePath = encodeURIComponent(vo.s_savePath);
				var savePath = encodeURIComponent(vo.savePath);
				console.log("인코딩전=======",vo.s_savePath);
				console.log("인코딩후=======",savePath);
				if(vo.fileType == "Y"){
					//이미지 썸네일의 경로를 인코딩해서 서버에 보내기
					//uploadpath경로의 일부를 전송위해 인코딩한다
					//문자나 특수문자는 허용되는 방식으로 변환
				htmlContent += "<li><a href=/download?fileName="+savePath+">"
								+"<img src=/display?fileName="+s_savePath+"></a>"
								+"<span onclick=attachFileDelete('"+vo.uuid+"','"+vo.attachNo+"') data-type='image'>X</span>"
								+vo.fileName+"</li>";
				}else{ //그 외의 파일은 파일리음을 출력
					htmlContent += "<li><a href=/download?fileName="+savePath+">"+vo.fileName+"</a></li>";
				}
			});
			$("#fileListView").html(htmlContent);
			console.log(attachNo);
			console.log("getFileList", result);			
		}				
	});
}

 function attachFileDelte(uuid, attachNo){
	 
	 $.ajax({
			url : '/attachFileDelete/'+uuid+'/'+attachNo,
			method : 'get',

			success : function(res){
				console.log("delete", res);
			},
			error : function(){
				console.log("error");
			}
		});
	 console.log("func end");
 }
 
</script>
</head>
<body>

 <form action="/uploadFormAction" method="post" enctype="multipart/form-data" name="uploadForm">
 	
 attachNo :	<input type="text" name="attachNo" id="attachNo" value="0"><br>
 	<input type='file' name='uploadFile' multiple>
 	<button type="button" id="uploadBtn">Ajax 업로드</button>
 <!-- 서버로부터 받아온 파일리스트를 출력 -->
 <div>
 	<ul id = "fileListView">
 		<li>fileName</li>
 		<li>fileName</li>
 		<li>fileName</li>
 	</ul>
 
 
 
 </div>
 
 
 </form>

</body>
</html>