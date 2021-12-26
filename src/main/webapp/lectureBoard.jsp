<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<li class="nav-item active">
					<a class="nav-link" href="lectureBoardController?pageNumber=1">강의평가</a>
				</li>
				<li class="nav-item">
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
	
	<section class="container">
		<form method="get" action="lectureBoardController" class="form-inline mt-3">
			<select name="lectureDivide" class="form-control mx-1 mt-2">
				<option value="전체">전체</option>
				<option value="전공" ${(param.lectureDivide == "전공")? "selected" : ""}>전공</option>
				<option value="교양" ${(param.lectureDivide == "교양")? "selected" : ""}>교양</option>
				<option value="기타" ${(param.lectureDivide == "기타")? "selected" : ""}>기타</option>
			</select>
			<select name="searchType" class="form-control mx-1 mt-2">
				<option value="최신순">최신순</option>
				<option value="추천순" ${(param.searchType == "추천순")? "selected" : ""}>추천순</option>
			</select>
			<input type="text" name="search" value="${param.search}" class="form-control mx-1 mt-2" placeholder="내용을 입력하세요">
			<button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
			<a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal">등록하기</a>
			<a class="btn btn-danger mx-1 mt-2" data-toggle="modal" href="#reportModal">신고</a>
		</form> 
		
		<!-- 게시글 출력 -->
		<c:forEach var="evaluationList" varStatus="status" items="${evaluationList}" begin="0" end="${lectureListPrintCount}">		
			<div class="card bg-light mt-3">
				<div class="card-header bg-light">
					<div class="row">
						<div class="col-8 text-left">${evaluationList.lectureName}&nbsp;<small>${evaluationList.professorName}</small></div>
						<div class="col-4 text-right">
							종합<span style="color: red;">${evaluationList.totalScore}</span>
						</div>
					</div>
				</div>
				<div class="card-body">
					<h5 class="card-title">
						${evaluationList.evaluationTitle}&nbsp;<small>(${evaluationList.lectureYear}년 ${evaluationList.semesterDivide})</small>
					</h5>
					<p class="card-text">${evaluationList.evaluationContent}</p>
					<div class="row">
						<div class="col-9 text-left">
							성적<span style="color: red;">${evaluationList.creditScore}</span>
							널널<span style="color: red;">${evaluationList.comfortableScore}</span>
							강의<span style="color: red;">${evaluationList.lectureScore}</span>
							<span style="color: green;">(추천: ${evaluationList.likeCount})</span>
						</div>
						<div class="col-3 text-right">
							<form method="post" action="updateModal">
								<a onclick="return confirm('추천하시겠습니까?')" 
									href="likecontroller?evaluationID=${evaluationList.evaluationID}&lectureDivide=${param.lectureDivide}&searchType=${param.searchType}&search=${param.search}&pageNumber=${param.pageNumber}">추천</a>
								
								<c:set var="sessionID" value="${userID}"></c:set>
								<c:set var="writerID" value="${evaluationList.userID}"></c:set>
								<c:if test="${sessionID == writerID}">
									<a onclick="return confirm('삭제하시겠습니까?')" 
										href="evaluationDeleteController?evaluationID=${evaluationList.evaluationID}&lectureDivide=${param.lectureDivide}&searchType=${param.searchType}&search=${param.search}&pageNumber=${param.pageNumber}">삭제</a>
										<input type="hidden" name="lectureName" value="${evaluationList.lectureName}">
										<input type="hidden" name="professorName" value="${evaluationList.professorName}">
										<input type="hidden" name="totalScore" value="${evaluationList.totalScore}">
										<input type="hidden" name="evaluationTitle" value="${evaluationList.evaluationTitle}">
										<input type="hidden" name="lectureYear" value="${evaluationList.lectureYear}">
										<input type="hidden" name="semesterDivide" value="${evaluationList.semesterDivide}">
										<input type="hidden" name="evaluationContent" value="${evaluationList.evaluationContent}">
										<input type="hidden" name="evaluationID" value="${evaluationList.evaluationID}">
										<input type="hidden" name="creditScore" value="${evaluationList.creditScore}">
										<input type="hidden" name="comfortableScore" value="${evaluationList.comfortableScore}">
										<input type="hidden" name="lectureScore" value="${evaluationList.lectureScore}">
										<input type="hidden" name="lectureDivide" value="${evaluationList.lectureDivide}">
									<button type="submit" class="btn btn-primary">수정</button>
								</c:if>
							</form>
						</div>
					</div>
				</div>
			</div>	
		</c:forEach>
		
		
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
					<li class="page-item"><a class="page-link" href="lectureBoardController?lectureDivide=${param.lectureDivide}&searchType=${param.searchType}&search=${param.search}&pageNumber=${param.pageNumber - 1}">이전</a></li>
				</c:if>
				
				<c:forEach var="i" begin="0" end="4">
					<c:if test="${(startNum + i) <= lastNum}">
						<c:if test="${param.pageNumber == (startNum + i)}">
							<li class="page-item active"><a class="page-link" href="lectureBoardController?lectureDivide=${param.lectureDivide}&searchType=${param.searchType}&search=${param.search}&pageNumber=${startNum + i}">${startNum + i}</a></li>
						</c:if>
						<c:if test="${param.pageNumber != (startNum + i)}">
							<li class="page-item"><a class="page-link" href="lectureBoardController?lectureDivide=${param.lectureDivide}&searchType=${param.searchType}&search=${param.search}&pageNumber=${startNum + i}">${startNum + i}</a></li>
						</c:if>
					</c:if>
				</c:forEach>
				
				<c:if test="${param.pageNumber == lastNum}">
					<li class="page-item"><a class="page-link" onclick="alert('다음 페이지가 없습니다.');">다음</a></li>
				</c:if>
				<c:if test="${param.pageNumber != lastNum}">
					<li class="page-item"><a class="page-link" href="lectureBoardController?lectureDivide=${param.lectureDivide}&searchType=${param.searchType}&search=${param.search}&pageNumber=${param.pageNumber + 1}">다음</a></li>
				</c:if>
			</ul>
		</div>		
	</section>	
	
	<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modal">평가 등록</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="evaluationRegisterController" method="post">
						<div class="form-row">
							<div class="form-group col-sm-6">
								<label>강의명</label>
								<input type="text" name="lectureName" class="form-control" maxlength="20">
							</div>
							<div class="form-group col-sm-6">
								<label>교수명</label>
								<input type="text" name="professorName" class="form-control" maxlength="20">
							</div>
						</div>
						<div class="form-row">
							<div class="form-group col-sm-4">
								<label>수강 연도</label>
								<select name="lectureYear" class="form-control">
									<option value="2017">2017</option>
									<option value="2018">2018</option>
									<option value="2019">2019</option>
									<option value="2020">2020</option>
									<option value="2021" selected>2021</option>
									<option value="2022">2022</option>
									<option value="2023">2023</option>
									<option value="2023">2024</option>
									<option value="2023">2025</option>
									<option value="2023">2026</option>
								</select>
							</div>
							<div class="form-group col-sm-4">
								<label>수강 학기</label>
								<select name="semesterDivide" class="form-control">
									<option value="1학기" selected>1학기</option>
									<option value="여름학기">여름학기</option>
									<option value="2학기">2학기</option>
									<option value="겨울학기">겨울학기</option>
								</select>
							</div>
							<div class="form-group col-sm-4">
								<label>강의 구분</label>
								<select name="lectureDivide" class="form-control">
									<option value="전공" selected>전공</option>
									<option value="교양">교양</option>
									<option value="기타">기타</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label>제목</label>
							<input type="text" name="evaluationTitle" class="form-control" maxlength="30">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea name="evaluationContent" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
						</div>
						<div class="form-row">
							<div class="form-group col-sm-3">
								<label>종합</label>
								<select name="totalScore" class="form-control">
									<option value="A" selected>A</option>
									<option value="B">B</option>
									<option value="C">C</option>
									<option value="D">D</option>
									<option value="F">F</option>
								</select>
							</div>
							<div class="form-group col-sm-3">
								<label>성적</label>
								<select name="creditScore" class="form-control">
									<option value="A" selected>A</option>
									<option value="B">B</option>
									<option value="C">C</option>
									<option value="D">D</option>
									<option value="F">F</option>
								</select>
							</div>
							<div class="form-group col-sm-3">
								<label>널널</label>
								<select name="comfortableScore" class="form-control">
									<option value="A" selected>A</option>
									<option value="B">B</option>
									<option value="C">C</option>
									<option value="D">D</option>
									<option value="F">F</option>
								</select>
							</div>
							<div class="form-group col-sm-3">
								<label>강의</label>
								<select name="lectureScore" class="form-control">
									<option value="A" selected>A</option>
									<option value="B">B</option>
									<option value="C">C</option>
									<option value="D">D</option>
									<option value="F">F</option>
								</select>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
							<button type="submit" class="btn btn-primary">등록하기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modal">신고하기</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="reportController" method="post"><div class="form-group">
							<label>신고 제목</label>
							<input type="text" name="reportTitle" class="form-control" maxlength="30">
						</div>
						<div class="form-group">
							<label>신고 내용</label>
							<textarea name="reportContent" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
							<button type="submit" class="btn btn-danger">신고하기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
		Copyright &copy; 2021이기주All Rights Reserved.
	</footer>
		
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" 
			integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" 
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
</body>
</html>















