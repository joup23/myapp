<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<head>
<title>first</title>
</head>
<body>
	<h2>삭제된 게시판 목록</h2>
	<table style="border: 1px solid #ccc">
		<colgroup>
			<col width="10%" />
			<col width="*" />
			<col width="15%" />
			<col width="20%" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col">글번호</th>
				<th scope="col">제목</th>
				<th scope="col">조회수</th>
				<th scope="col">작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${fn:length(list) > 0}">
					<c:forEach items="${list }" var="row">
						<tr>
							<td>${row.IDX }</td>
							<td><input type="hidden" id="IDX" value="${row.IDX }" /><a
								class="title" href="#this" name="title">${row.TITLE }</a></td>
							<td>${row.HIT_CNT }</td>
							<td>${row.CREA_DTM }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="4">조회된 결과가 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>

		</tbody>
	</table>

	<a href="#this" id="list" class="btn">게시글 목록으로</a>
	<%@ include file="/WEB-INF/include/include-body.jspf"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#list").on("click", function(e) {
				e.preventDefault();
				fn_openBoardList();
			})
			$("a[name='title']").on("click", function(e) {
				e.preventDefault();
				fn_openBoardDetail($(this));
			})
		})

		function fn_openBoardList() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/openBoardList.do'/>");
			comSubmit.submit();
		}
		function fn_openBoardDetail(val) {
			var comSubmit = new ComSubmit();
			comSubmit.addParam("IDX", val.parent().find("#IDX").val());
			comSubmit.setUrl("<c:url value='/sample/openBoardDetail.do'/>");
			comSubmit.submit();
		}
	</script>

</body>
</html>