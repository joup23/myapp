<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<head>
<title>로그인</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
    <div class="user_view wdp_30" >
        <div class="con_center under_line"><h5>로그인</h5></div>
        <form id="frm" class="padding_5" onsubmit="JavaScript:fn_loginUser();"  >
            <fieldset>
                <input type="text" class="input_text" name="UID" maxlength="15" placeholder="아이디" value="" id="uid">
 
                <input type="password" class="input_text" name="UPW" maxlength="15" placeholder="비밀번호" value="" id="upw">
 
            </fieldset>
             
 
            <c:if test="${error}">
                아이디나 비밀번호가 일치하지 않습니다.           
            </c:if>
            <button type="submit" class="btn con_center">로그인</button>
 
        </form>
    </div>
</body>
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
         
        function fn_loginUser(){
            var comSubmit = new ComSubmit("frm");
            comSubmit.setUrl("<c:url value='/sample/loginUser.do'/>");
            comSubmit.submit();
        }
         
    </script>
</html>