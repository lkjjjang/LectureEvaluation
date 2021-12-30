<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
				<li class="nav-item active">
					<a class="nav-link" href="freeBoardController?pageNumber=1">자유게시판</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown" href="index.jsp">
						회원관리
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdown">			
						<a class="dropdown-item" href="logout.jsp">로그아웃</a>
					</div>
				</li>
			</ul>
		</div>	
	</nav>
	
	<div class="container">
		<h1>
    		<a class="ed link-primary text-bold title-underline" href="freeBoardController?pageNumber=1">자유게시판</a>
  		</h1>	
			<table class="table table-hover">
				<colgroup>
					<col width="10%"/>
					<col width="50%"/>
					<col width="15%"/>
					<col width="10%"/>
					<col width="15%"/>
				</colgroup>
				<thead style="text-align: center;">			
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>조회수</th>
						<th>작성일</th>					
					</tr>				
				</thead>
				<tbody>
					<c:forEach var="freeBBS" items="${freeBBS}" begin="0" end="${freeBBSListPrintCount}">
						<tr>
							<td style="text-align: center;">
								<c:if test="${userID == 'admin'}">
									<input type="checkbox" name="delCheck_id" id="delCheck_id" value="${freeBBS.bbsID}">
								</c:if>
								${freeBBS.bbsID} 
							</td>
							<td>
								<a href="freeBoardDetailController?id=${freeBBS.bbsID}">${freeBBS.bbsTitle}</a>
								<c:if test="${freeBBS.commentCount != 0}">[${freeBBS.commentCount}]</c:if>	
								<c:if test="${freeBBS.useImage == 1}"><img src="images/imageIcon.png"></c:if>						
							</td>
							<td style="text-align: center;">${freeBBS.nickName}</td>
							<td style="text-align: center;">${freeBBS.bbsHit}</td>
							<td style="text-align: center;">${freeBBS.bbsDate}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>	
			<c:if test="${userID == 'admin'}">
				<div style="text-align: right;">
					<a href="write.jsp" class="btn btn-primary">글쓰기</a>
				</div>	
			</c:if>
		<div id="checkList">
			<button class="btn btn-primary" id="selectAll" onclick="select(this)">전체선택</button>
			<button class="btn btn-danger" id="deleteAll" onclick="deleteAll()">선택삭제</button>
		</div>
		<c:if test="${userID != 'admin'}">
			<div style="text-align: right;">
				<a href="write.jsp" class="btn btn-primary">글쓰기</a>
			</div>	
		</c:if>
		
		
		
		<!-- 페이지번호 출력 -->
		<!-- 파라미터로 들어오는 페이지 번호를 이용해 startNum값을 구함 -->
		<div>
			<c:set var="page" value="${(param.pageNumber == null) ? 1 : param.pageNumber}"></c:set>
			<c:set var="startNum" value="${page - (page - 1) % 5}"></c:set>
			<c:set var="lastNum" value="${listCount}"></c:set>
			
			<ul class="pagination justify-content-center mt-3">
				<c:if test="${param.pageNumber == 1}">
					<li class="page-item"><a class="page-link" onclick="alert('이전 페이지가 없습니다.');">이전</a></li>
				</c:if>
				<c:if test="${param.pageNumber != 1}">
					<li class="page-item"><a class="page-link" href="freeBoardController?pageNumber=${param.pageNumber - 1}">이전</a></li>
				</c:if>
				
				<c:forEach var="i" begin="0" end="4">
					<c:if test="${(startNum + i) <= lastNum}">
						<c:if test="${param.pageNumber == (startNum + i)}">
							<li class="page-item active"><a class="page-link" href="freeBoardController?pageNumber=${startNum + i}">${startNum + i}</a></li>
						</c:if>
						<c:if test="${param.pageNumber != (startNum + i)}">
							<li class="page-item"><a class="page-link" href="freeBoardController?pageNumber=${startNum + i}">${startNum + i}</a></li>
						</c:if>
					</c:if>
				</c:forEach>
				
				<c:if test="${param.pageNumber == lastNum}">
					<li class="page-item"><a class="page-link" onclick="alert('다음 페이지가 없습니다.');">다음</a></li>
				</c:if>
				<c:if test="${param.pageNumber != lastNum}">
					<li class="page-item"><a class="page-link" href="freeBoardController?pageNumber=${param.pageNumber + 1}">다음</a></li>
				</c:if>
			</ul>
		</div>		
	</div>
	
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
		function deleteAll() {
			var checkList = document.getElementsByName('delCheck_id');
			var checkedList = new Array();
			var count = 0;
			for (var i = 0; i < checkList.length; i++) {
				if (checkList[i].checked == true) {
					count++;
					checkedList[i] = checkList[i].value;
				}
			}
			
			if (count == 0) {
				alert('선택사항이 없습니다.');
				return;
			}
			
			var data = new Array();
			for (var i = 0; i < checkedList.length; i++) {
				if (checkedList[i] != null) {
					var dataTemp = new Object();
					dataTemp.id = checkedList[i];
					data.push(dataTemp);
				}
			}
			
			$.ajax({
				type: "post",
				url: "freeBoardDeleteController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					if (json[0].resultCode == 'ok') {
						alert('삭제 되었습니다.');
					} else {
						alert('데이터베이스 오류 입니다.');
					}
					location.reload();
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
				}
			});
		}
	</script>
	<script type="text/javascript">
		function select(tagID) {
			var id = tagID.id;
			var checkList = document.getElementsByName('delCheck_id');
			for (var i = 0; i < checkList.length; i++) {
				if (id == 'selectAll') {
					checkList[i].checked = true;
				} else {
					checkList[i].checked = false;
				}
			}
			
			if (id == 'selectAll') {
				changeId('cancel', '선택해제');
			} else {
				changeId('selectAll', '전체선택');
			}
		}
		
		function changeId(id, text)  {
			  const btnElement = document.getElementById('checkList');
			  btnElement.innerHTML = '<button class="btn btn-primary" id="' + id + '" onclick="select(this)">'+ text +'</button>' +
			  							'<button class="btn btn-danger" id="deleteAll" onclick="deleteAll()">선택삭제</button>';
		}
	</script>
</body>
</html>















