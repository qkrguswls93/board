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
		
		//아이디와 비번찾기 필드셋을 숨김처리
		$("#searchId").hide();
		$("#searchpwd").hide();
		
		//아이디찾기 버튼 눌렀을 때
		$("#btnSearchId").on("click",function(){
			console.log("btnSearchId","click");
			
			var idVo = {
				name : $("#name").val(),
				email : $("#idemail").val()
			};
			
			console.log("idVo", idVo);
			console.log("json형식", JSON.stringify(idVo));
		
			$.ajax({
				url : '/searchId',
				method : 'post',
				dataType : 'json',
				
				data : JSON.stringify(idVo),
				contentType : 'application/json; charset=UTF-8',
				
				success : function(data){ //data는 서버로부터 리턴받은 데이터의 이름
					console.log("결과",data)
					$("#errorMsgArea").html(data.msg);
				},
				
				error : function(){
					console.log("btnSearchId","ajax error")
				},
			});
		});
		//비번찾기 버튼 눌렀을 때
		$("#btnSearchpwd").on("click",function(){
			console.log("btnSearchpwd","click");
			
			var pwdVo = {
					id : $("#id").val(),
					email : $("#pwdemail").val()
			};
			
			console.log("pwdVo", pwdVo);
			console.log("json형식", JSON.stringify(pwdVo));
			
			$.ajax({
				url : '/searchpwd',
				method : 'post',
				dataType : 'json',
				
				data : JSON.stringify(pwdVo),
				contentType : 'application/json; charset=UTF-8',
				
				success : function(res){
					console.log("결과",res)
				},
				
				error : function(){
					console.log("error")
				},
			});
		});
		
		//$("#errorMsgArea").text('${msg}');
		
	});
	
	//아이디 찾기를 클릭했을때 아이디 찾기 화면을 영역에 보여준다.
	function viewsearchId(){
		
		//해당 이름과 메일을 가진 사용자의 아이디 메시지로 출력
		console.log("viewsearchId","실행");
		$("#login").hide();
		$("#searchpwd").hide();
		$("#searchId").show();
		
		
	}
	function viewsearchpwd(){
		
		console.log("viewsearchpwd","실행");
		$("#login").hide();
		$("#searchId").hide();
		$("#searchpwd").show();
		
	}
	function viewlogin(){
		
		console.log("viewlogin","실행");
		$("#login").show();
		$("#searchId").hide();
		$("#searchpwd").hide();
		
	}
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
                        <form role="form" action="/loginAction" method="post">
                       
                            <fieldset id="login">
                                <div class="form-group">
                                <p id=errorMsgArea></p>
                                    <input class="form-control" placeholder="id" name="id" type="id" autofocus value="user01">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="pwd" type="password" value="1234">
                                </div>
                              <div class="checkbox">
                                    <label>
                                        <input name="useCookie" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <button type="submit" class="btn btn-lg btn-success btn-block">Login</button>             
                            </fieldset>
                            
                            <!-- 아이디 찾기 -->
                            <fieldset id="searchId">
                                <div class="form-group">
                                <p id=errorMsgArea></p>
                                    <input class="form-control" placeholder="name" id="name" type="name" autofocus value="name">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="idemail" id="idemail" value="a@naver.com">
                                </div>
                             
                                <!-- Change this to a button or input when using this as a form -->
                                <button type="button" id="btnSearchId" class="btn btn-lg btn-success btn-block">아이디찾기</button>
                            </fieldset>
                            
                            <!-- 비밀번호 찾기 -->
                            <fieldset id="searchpwd">
                                <div class="form-group">
                                <p id=errorMsgArea></p>
                                    <input class="form-control" placeholder="id" id="id" type="id" autofocus value="user01">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="pwdemail" id="pwdemail" value="a@naver.com">
                                </div>
                             
                                <!-- Change this to a button or input when using this as a form -->
                                <button type="button"id="btnSearchpwd" class="btn btn-lg btn-success btn-block">비밀번호 찾기</button>
                            </fieldset>
                            <p>
                                <a href="#" onclick="viewlogin()">로그인</a>&nbsp;&nbsp;
                                <a href="#" onclick="viewsearchId()">아이디찾기</a>&nbsp;&nbsp;
                                <a href="#" onclick="viewsearchpwd()">비밀번호찾기</a>
                            </p>
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
