<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>상세보기</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
    <table class="board_view">
        <caption>상세보기</caption>
        <colgroup>
            <col width="15%">
            <col width="35%">
            <col width="15%">
            <col width="*">
        </colgroup>
        
        <tbody>
            <tr>
                <th>제목</th>
                <td>${map.title}</td>
                <th>조회수</th>
                <td>${map.hit_cnt }</td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>${map.crea_id }</td>
                <th>작성시간</th>
                <td>${map.crea_dtm }</td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    ${map.contents }
                </td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td colspan="3">
                    <c:forEach items="${list}" var="row" >
                        <p>
                            <input type="hidden" value="${row.idx }" id="IDX">
                            <a href="#this" name="file">${row.original_file_name }</a>
                            (${row.file_size }Byte)
                        </p>
                    </c:forEach>                          
            </tr>
        </tbody>
    </table>
    <c:choose>
    <c:when test="${map.del_gb =='Y' }">
    	<a href="#this" id="deletelist" class="btn">목록으로</a>
    </c:when>
    <c:otherwise>
    <a href="#this" id="list" class="btn">목록으로</a>
    </c:otherwise>
    </c:choose>
    <a href="#this" id="modify" class="btn">수정하기</a>
    <c:if test="${map.del_gb =='Y' }">
    	<a href="#this" id="restore" class="btn">복원하기</a>
    </c:if>
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    
    <script type="text/javascript">
        $(document).ready(function(){
            $("#list").on("click",function(e){
                e.preventDefault();
                fn_openBoardList();
            })
            $("#deletelist").on("click",function(e){
                e.preventDefault();
                fn_openDeleteBoardList();
            })
            $("#modify").on("click",function(e){
                e.preventDefault();
                fn_openBoardModify();
            })
            $("#restore").on("click",function(e){
                e.preventDefault();
                fn_openBoardRestore();
            })
            $("a[name='file']").on("click",function(e){
                e.preventDefault();
                fn_fileDownload($(this));
            })
        })
        
        function fn_openBoardList(){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/openBoardList.do'/>");
            comSubmit.submit();
        }
        function fn_openDeleteBoardList(){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/deleteBoardList.do'/>");
            comSubmit.submit();
        }
        function fn_openBoardModify(){
            var idx = "${map.idx}";
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/openBoardModify.do'/>");
            comSubmit.addParam("IDX",idx);
            comSubmit.submit();
        }
        function fn_openBoardRestore(){
            var idx = "${map.idx}";
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/restoreBoard.do'/>");
            comSubmit.addParam("IDX",idx);
            comSubmit.submit();
        }
        function fn_fileDownload(obj){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/common/downloadFile.do'/>");
            comSubmit.addParam("IDX",obj.parent().find("#IDX").val());
            comSubmit.submit();
            $("#commonForm").children().remove();
        } 
    </script> 
</body>
</html>