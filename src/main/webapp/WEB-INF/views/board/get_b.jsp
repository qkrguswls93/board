<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

<jsp:include page="/resources/header/header.jsp"/>
<script type="text/javascript">
if('${resMsg}'!=''){
	alert('${resMsg}');
}
//상세보기 이동
function detailBtn(url){
	document.detailForm.action=url;
	document.detailForm.submit();
} 

$(document).ready(function(){
		if('${vo.attachNo}' != ''){
		getFileList('${vo.attachNo}');
		$("input[name=attachNo]").val('${atttachNo}');
	}
	$("#fileInputArea").remove();
});


</script>

  <div id="page-wrapper">
      <div class="row">
         <div class="col-lg-12">
             <h1 class="page-header">Tables</h1>
         </div>
         <!-- /.col-lg-12 -->
     </div>
     <!-- /.row -->
     <div class="row">
         <div class="col-lg-12">
             <div class="panel panel-default">
                 <div class="panel-heading">
                    [NO ${vo.bno }]
                 </div>
                 <!-- /.panel-heading -->
                 <div class="panel-body">
                    <div class="form-group">
                        <label>제목</label>
                        <input readonly class="form-control" value="${vo.title }">
                    </div>
                     <div class="form-group">
                         <label>내용</label>
                         <textarea readonly class="form-control" rows="3">${vo.content }</textarea>
                     </div> 
                     <div class="form-group">
                        <label>작성자</label>
                        <input readonly class="form-control" value="${vo.writer }">
                    </div>
                    <div class="form-group">
                        <label>등록시간</label>
                        <input readonly class="form-control" value="${vo.regdate }">
                    </div>
                    
					<button type="button" onclick="detailBtn('/board/edit')" class="btn btn-default" >수정</button>
					<button type="button" class="btn btn-default" onclick="detailBtn('/board/delete')">삭제</button>
					<button type="button" class="btn btn-default" onclick="detailBtn('/board/list')">목록</button>
				
				<form method="get" name="detailForm">
					<input type=hidden name=bno value=${vo.bno }>
					<input type=hidden name=pageNo value=${criteria.pageNo }>
					<input type=hidden name=type value=${criteria.type }>
					<input type=hidden name=keyword value=${criteria.keyword }>
				</form>
					<!-- 첨부파일 -->
					<jsp:include page="fileUpload.jsp"/>
					
					<!-- 댓글 -->
					<br>
					<jsp:include page="reply.jsp"/>
					
					
					
                 </div>
                 <!-- /.panel-body -->
             </div>
             <!-- /.panel -->
         </div>
         <!-- /.col-lg-12 -->
     </div>
     
 </div>
 <!-- /#page-wrapper -->
<jsp:include page="/resources/header/bottom.jsp"/>
