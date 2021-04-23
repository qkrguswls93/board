/**
 * 리플 Ajax
 */
function getAjaxList(){
	
	$.ajax({
		url : '/reply/list/' + $("#bno").val(),
		method : 'get',
		dataType : 'json',
		
		success : function(data, status, xhr){
			console.log("data", data);		
		
		},
		error : function(xhr, Status, error){
			console.log("error", error);
		}
});

}

/*
 *replyinsert 
 */
/*function getAjaxInsert(){
	
	//javascript object
	var replyDate = {
			bno : $("#bno").val(),
			reply : $("#reply").val(),
			replyer : $("#replyer").val()
			
	};
	
	console.log("obj", replyData);
	console.log("json", JSON.stringify(replyData));
	
	$.ajax({
		url : '/reply/insert',
		method : 'post',
		dataType : 'json',

		//JSON 형식으로 변환
		data : JSON.stringify(replyData),
		contentType : 'application/json; charset=UTF-8',
		
		success : function(data, status){
			
			console.log(data);
			
			//alert(data.result);
		
			if(data.result == "success"){
				
				//모달창을 닫기
				$("#myModal").modal("hide");
				
				//리스트 조회하기
				getAjaxList();				
			} else {
				alert("입력중 오류가 발생했습니다.")
			}		
		},
		error : function(xhr, status, error){
			console.log(error);
		}
		
	});
	
}

*//**
 *1건의 리플을 조회
 *@returns
 *
 *//*
function getAjax(){
	
	$.ajax({
		url : '/reply/get/' +$("#rno").val(),
		method : 'get',
		dataType : 'json',
		
		success : function(data, status){
			console.log("data", data);		
			
			$("#reply").val(data.reply);
			$("#replyer").val(data.replyer);
				
			},
			
			error : function(nhr, status, error){
				console.log(data);
			}
			
			
		});
	
	}




function commAjax(url, method, data, callback, error){
	$.ajax({
		url : '/reply/insert',
		method : 'post',
		dataType : 'json',

		//JSON 형식으로 변환
		data : JSON.stringify(replyData),
		contentType : 'application/json; charset=UTF-8',
		
		success : function(data, status){
			
			console.log(data);
			
			//alert(data.result);
	
			if(callback){
				callback(data);
			}
		},
		error : function(xhr, status, error){
			console.log(error);
			if(error){
				error(errorThrown);
			}
		}
		
	});
	
}

*/













