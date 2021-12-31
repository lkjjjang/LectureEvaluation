<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>한양사이버대 강의평가</title>
	<!-- 부트스트랩 CSS 추가하기 -->
	<link rel="stylesheet" 
			href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" 
			integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" 
			crossorigin="anonymous">
	<!-- 커스텀 CSS 추가하기 -->
	<link rel="stylesheet" href="./css/custom.css">
	
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="index.jsp">한양사이버대 강의평가</a>	
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<a class="nav-link" href="lectureBoardController?pageNumber=1">강의평가</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="freeBoradController?pageNumber=1">자유게시판</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown" href="index.jsp">
						회원관리
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdown">						
						<a class="dropdown-item" href="index.jsp">로그인</a>
						<a class="dropdown-item" href="userJoin.jsp">회원가입</a>
					</div>
				</li>
			</ul>
		</div>	
	</nav>
	
	<img src="images/hycu1.jpg" style="display: block; margin: 0 auto;">
	<section class="container mt-3" style="max-width: 560px;">
		<form method="post" action="userRegisterController">
			<div class="form-group">
				<label>아이디</label>
				<input type="text" name="userID" class="form-control" placeholder="아이디를 입력하세요 (모든 게시글은 익명 입니다.)">
			</div>
			<div class="form-group">
				<label>비밀번호</label>
				<input type="password" id="pass_1" name="userPassword" class="form-control" onchange="isSame()" placeholder="비밀번호를 입력하세요">
			</div>
			<div class="form-group">
				<label>비밀번호 확인</label>
				<input type="password" id="pass_2" name="ConfirmPassword" class="form-control" onchange="isSame()" placeholder="비밀번호를 입력하세요">
				<label id="same"></label>
			</div>
			<div class="form-group">
				<label>이메일</label>
				<input type="email" name="userEmail" class="form-control" placeholder="한사대 이메일만 사용 가능합니다. (학번@hycu.ac.kr)">
			</div>
			<div style="text-align: center;">
				<button type="submit" class="btn btn-primary">회원가입</button>
			</div>
		</form>
	</section>
	
	<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
		Copyright &copy; 2021이기주All Rights Reserved.
	</footer>
	<script src="https://code.jquery.com/jquery-3.6.0.js"
  			integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
  			crossorigin="anonymous">
	</script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" 
			integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" 
			crossorigin="anonymous">
	</script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" 
			integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" 
			crossorigin="anonymous">
	</script>
	<script type="text/javascript">
		function isSame() {
			var pass1 = document.getElementById('pass_1').value;
		    if (pass1.length < 6 || pass1.length > 16) {
		        window.alert('비밀번호는 6글자 이상, 16글자 이하만 이용 가능합니다.');
		        document.getElementById('pass_1').value=document.getElementById('pass_2').value='';
		        document.getElementById('same').innerHTML='';
		    }
		    if(document.getElementById('pass_1').value!='' && document.getElementById('pass_2').value!='') {
		        if(document.getElementById('pass_1').value==document.getElementById('pass_2').value) {
		            document.getElementById('same').innerHTML='비밀번호가 일치합니다.';
		            document.getElementById('same').style.color='blue';
		        }
		        else {
		            document.getElementById('same').innerHTML='비밀번호가 일치하지 않습니다.';
		            document.getElementById('same').style.color='red';
		        }
		    }
		}
	</script>
</body>
</html>















