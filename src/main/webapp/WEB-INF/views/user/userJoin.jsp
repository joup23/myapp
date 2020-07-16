<p><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<head>
<title>회원가입</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
    <div class="user_view wdp_30" >
        <div class="con_center under_line"><h5>회원 가입</h5></div>
        <form id="frm" class="padding_5" onSubmit="JavaScript:fn_joinUser()">
            <fieldset>
                <input type="text" class="input_text" name="UID" maxlength="15" placeholder="아이디" value="" id="uid" required>
 
                <input type="password" class="input_text" name="UPW" maxlength="15" placeholder="비밀번호" value="" id="upw" required>
 
                <input type="text" class="input_text" name="EMAIL" maxlength="50" placeholder="이메일" value="" id="email" required>
 
                <input type="text" class="input_text" name="UNAME" maxlength="15" placeholder="이름" value="" id="uname" required>
 
                <input type="text" class="input_text" name="UNICK" maxlength="15" placeholder="닉네임" value="" id="unick" required>
 
            </fieldset>
             
 
            <c:if test="${! empty error}">
                ${error} 값이 중복된 값입니다.           
            </c:if>
            <button type="submit" class="btn con_center">회원 가입</button>
        </form>
    </div>
</body>
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
         
        function fn_joinUser(){
            var comSubmit = new ComSubmit("frm");
            comSubmit.setUrl("<c:url value='/sample/joinUser.do'/>");
            comSubmit.submit();
        }
         
    </script>
</html></p>