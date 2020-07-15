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
                 </td>                        
            </tr>
        </tbody>
    </table>
    
    <form id="frm">
        <table class="board_view">
            <colgroup>
                <col width="15%">
                <col width="85%">
            </colgroup>
            <tbody>
                <tr>
                    <th>댓글</th>
                    <td>
                        ${fn:length(comment) }
                    </td>
                </tr>
                <c:if test="${fn:length(comment)>0 }">
                    <c:forEach items="${comment }" var="com">
                        <tr>
                            <td style="background:#f7f7f7;color:#3b3a3a;" >
                                ${com.CREA_ID }
                                <p style="font-size: 8px;" >${com.CREA_DTM }</p>
                            </td>
                            <td>
                                <input type="hidden" value="${com.IDX }" id="com_IDX">
                                <div id="com_Div"><input type="hidden" value ="${com.CONTENTS}" id="com_CON">${com.CONTENTS }</div>
                                <div align="right">
                                    <a href="#this" name="com_Del" class="btn">삭제</a>
                                    <a href="#this" name="com_Mod" class="btn">수정</a>
                                </div>                            
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <tr>
                    <td colspan="2">
                        <div>
                            Admin
 
 
                            <textarea  rows="5" cols="130" name="COM_CONTENTS" ></textarea>
                            <p align="right" ><a href="#this" id="com_write" class="btn">등록</a></p>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
    
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
            $("#com_write").on("click",function(e){
                e.preventDefault();
                fn_writeComment();
            })
            $("a[name=com_Del]").on("click",function(e){
                e.preventDefault();
                fn_deleteComment($(this));
            })
            $("a[name=com_Mod]").on("click",function(e){
                e.preventDefault();
                fn_commentModify($(this));
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
        function fn_writeComment(){
            var comSubmit = new ComSubmit("frm");
            comSubmit.addParam("IDX",${map.idx})
            comSubmit.setUrl("<c:url value='/sample/writeComment.do'/>");
            comSubmit.submit();
        }
 
        function fn_deleteComment(obj){
            var comSubmit = new ComSubmit();
            comSubmit.addParam("IDX",${map.idx})
            comSubmit.addParam("COM_IDX",obj.parent().parent().find("#com_IDX").val());
            comSubmit.setUrl("<c:url value='/sample/deleteComment.do'/>");
            comSubmit.submit();
        }
 
        function fn_commentModify(obj){
            var con = obj.parent().parent().find("#com_Div").find("#com_CON").val();
            var str = "<textarea  rows='5' cols='100' name='COM_CONTENTS_UPD'>"+con+"</textarea><p align='right' ><a href=''#this' name='com_Upd' class='btn'>등록</a></p><hr/>";
            var div = obj.parent().parent().find("#com_Div");
 
            div.empty();
            div.append(str)
             
            $("a[name=com_Upd]").on("click",function(e){
                e.preventDefault();
                fn_updateComment($(this));
            })
        }
 
        function fn_updateComment(obj){
            var comSubmit = new ComSubmit("frm");
            comSubmit.addParam("IDX",${map.idx})
            comSubmit.addParam("COM_IDX",obj.parent().parent().parent().find("#com_IDX").val());
            comSubmit.setUrl("<c:url value='/sample/updateComment.do'/>");
            comSubmit.submit();
        }
    </script> 
</body>
</html>