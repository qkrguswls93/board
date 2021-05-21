<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Bootstrap Admin Theme</title>

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<script type="text/javascript">
	$(document).ready(function(){
		
		$("input[name=id]").on(change,function(){
			//중복체크 다시 진행하게 처리
			$("input[name=id]").prop("dataValue",false);
		});
			
		
		
		$("#registerBtn").on("click", function(){	
			let id = $("input[name=id]").val(); //input태그 중에서 속성값이 id인것 찾는거(name이 id인것 찾는거) ->id체크를 위해 가져온다 
			if($.isEmptyObject(id)){ //object비어있는지 체크 
				alert("id를 입력해주세요");
				return;
			}
			//중복체크가 제대로 안된 경우 중복체크 메시지출력
			//중복아이디면 중복검사 진행
			//중복체크를 한 경우 가능한 아이디는 dataValue=true값을 입력
			if(!$("input[name=id]").prop("dataValue")){
				alert("id 중복검사를 해주세요");
				return false;
			}
			
		/* 	if(checkPassword()){
				return false;
			}; */
			$("#registerForm").submit();
		
	});
		
		$("#idCheck").on("click",function(){
			let id = $("input[name=id]").val();
			if($.isEmptyObject(id)){
			alert("id를 입력해주세요");
			return;
		}
			//아이디값 변경 했을때
			//초기화
			$("input[name=id]").prop("dataValue", false);

			//id가 등록이 되었는지 확인
			
		 	$.ajax({
				url : '/checkId/'+id,
				method : 'get',
				dataType : 'json',
				
				success : function(data){
					//등록 가능한 아이디
					if(data){
						alert("등록 가능한 아이디 입니다.");
						
						//등록 가능한 아이디인 경우 회원가입버튼 클릭시 중복체크 했는지 
						//속성값을 추가
						$("input[name=id]").prop("dataValue", true);
						
					//이미 등록된 아이디일때
					}else {
						alert("이미 등록된 아이디 입니다.");
					}
				},
				error : function(data){
						console.log('error');
					
				}
			}); 
			
		
		});
		});
	
</script>
</head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" id="registerForm" action="/registerMember" method="post">
                            <fieldset>
                                <div class="form-group">
                                <p id=errorMsgArea></p>
                                <label>ID</label>
                                    <input class="form-control" placeholder="id" name="id" id="id"                                                                                      " autofocus required
                                    pattern = "[0-9A-Za-z]{6,20}" autofocus required>
                                    
                                    <button type="button" class="form-control" id="idCheck">중복확인</button>
                                </div>
                                <div class="form-group">
                                <label>PASSWORD</label>
                                    <input class="form-control" placeholder="Password" name="pwd" type="password" value="1234"
                                    pattern = "[0-9A-Za-z]{5,12}"
                                    maxlength="12">
                                
                                </div>
                                <div class="form-group">
                                <label>NAME</label>
                                    <input class="form-control" placeholder="name" name="name">
                                </div>
                                <div class="form-group">
                                <label>EMAIL</label>
                                    <input class="form-control" placeholder="email" name="email" type="email">
                                </div>
                                
                                <!-- Change this to a button or input when using this as a form -->
                                <button type="submit" class="btn btn-lg btn-success btn-block">회원가입</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- jQuery -->
    <script src="/resources/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/resources/dist/js/sb-admin-2.js"></script>

</body>

</html>
