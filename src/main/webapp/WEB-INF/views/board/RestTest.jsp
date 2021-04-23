<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

<script type="text/javascript">
	$(document).ready(function(){
		
		$("#getListBtn").on("click",function(){
			console.log("test");
			getAjaxList();
		});
	});
	
	function getAjaxList(){
		$.ajax({
			url : '/reply/list/300',
			method : 'get',
			dataType : 'json',
			success : function(data, textStatus, jqXHR){
				console.log("data", data);
				
				console.log($("#repleTbl").html());
				
				$("#repleTbl").html("");
				var tblContent = "";
				
				$.each(data, function(index, item){
					tblContent += "<tr><td>" + item.reply + "</td><td>" + item.reply + "</td></tr>";
				});
				
				tblContent += "<tr><td>" + item.reply + "</td><td>" + item.reply + "</td></tr>";
				
				$("#repleTbl").html(tblContent);
			},
			error : function(jqHR, textStatus, errorThrown){
				console.log("textStatus", textStatus);
			}
		})
	}
</script>
</head>
<body>
<button id="getListBtn">리스트</button>
<br>
<table border="1" id="repleTbl">

<tr><td>리플</td><td>작성자</td></tr>
</table>
</body>
</html>