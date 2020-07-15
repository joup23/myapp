<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>글 수정하기</title>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
</head>
<body>
	<form id="frm" enctype="multipart/form-data">
		<table class="board_view">
			<caption>글 수정하기</caption>
			<colgroup>
				<col width="15%">
				<col width="*">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">글 번호</th>
					<td>${map.idx }</td>
					<th scope="row">조회수</th>
					<td>${map.hit_cnt }</td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td>${map.crea_id }</td>
					<th scope="row">작성시간</th>
					<td>${map.crea_dtm }</td>
				</tr>
				<tr>
					<th scope="row">제목</th>
					<td colspan="3"><input type="text" id="TITLE" name="TITLE"
						class="wdp_90" value="${map.title }" /></td>
				</tr>
				<tr>
					<th scope="row">내용</th>
					<td colspan="3" class="view_text"><textarea rows="20"
							cols="100" title="내용" id="CONTENTS" name="CONTENTS">${map.contents }</textarea>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
						<div id="fileDiv">
							<c:forEach items="${list }" varStatus="row" var="var">
								<p>
                                    <input name="IDX_${row.index }" id="IDX" type="hidden" value="${var.idx }"/>
                                    <%-- <input name="ORIGINAL_FILE_NAME_${row.index }" id="name_${row.index }" type="hidden" value="${var.original_file_name}"/> --%>
                                    <a href="#this" name="name_${row.index }" id="name_${row.index }" >${var.original_file_name }</a>
                                    <input type="file" name="file_${row.index }">
                                    <a href="#this" id="delete_${row.index }" name="delete_${row.index }" class="btn">삭제하기</a>
                                </p>
							</c:forEach>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<a href="#this" id="list" class="btn">목록으로</a>
	<a href="#this" id="add" class="btn">파일 추가하기</a>
	<a href="#this" id="modify" class="btn">수정하기</a>
	<a href="#this" id="delete" class="btn">삭제하기</a>

	<%@ include file="/WEB-INF/include/include-body.jspf"%>
	<script type="text/javascript">
	var gfv_count = '${fn:length(list)+1}';
		$(document).ready(function() {
			$("#modify").on("click", function(e) {
				e.preventDefault();
				fn_modifyBoard();
			})
			$("#list").on("click", function(e) {
				e.preventDefault();
				fn_openBoardList();
			})
			$("#delete").on("click", function(e) {
				e.preventDefault();
				fn_deleteBoard();
			})
			$("#add").on("click",function(e){
                e.preventDefault();
                fn_fileAdd();   
            })
            $("a[name^='delete']").on("click",function(e){
                e.preventDefault();
                fn_fileDelete($(this));
            })
		})

		function fn_modifyBoard() {
			var comSubmit = new ComSubmit("frm");
			var idx = "${map.idx}";
			comSubmit.setUrl("<c:url value='/sample/modifyBoard.do'/>");
			comSubmit.addParam("IDX", idx);
			comSubmit.submit();
		}
		function fn_openBoardList() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/openBoardList.do'/>");
			comSubmit.submit();
		}
		function fn_deleteBoard() {
			var comSubmit = new ComSubmit("frm");
			var idx = "${map.idx}";
			comSubmit.setUrl("<c:url value='/sample/deleteBoard.do'/>");
			comSubmit.addParam("IDX", idx);
			comSubmit.submit();
		}
		function fn_fileAdd(){
            var str = "<p>" +
                    "<input type='file' id='file_"+(gfv_count)+"' name='file_"+(gfv_count)+"'>"+
                    "<a href='#this' class='btn' id='delete_"+(gfv_count)+"' name='delete_"+(gfv_count)+"'>삭제</a>" +
                "</p>";
            $("#fileDiv").append(str);
            $("#delete_"+(gfv_count++)).on("click", function(e){ //삭제 버튼
                e.preventDefault();
                fn_fileDelete($(this));
            });
        }
		function fn_fileDelete(obj){
            obj.parent().remove();
        }
	</script>
</body>
</html>