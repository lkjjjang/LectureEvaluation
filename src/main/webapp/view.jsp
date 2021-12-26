<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


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
						<a class="dropdown-item" href="userLogout.jsp">로그아웃</a>
					</div>
				</li>
			</ul>
		</div>	
	</nav>
	
	<div class="container">
		<div>
			<c:set var="detail" value="${freeBoardDetail}"></c:set>
			<table class="table" style="text-align: left;">
				<thead>
					<tr>
						<th colspan="3"><h1>${detail.bbsTitle}</h1></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="text-align: left;">
							<span style="font-weight: bold;">작성자  </span>${detail.nickName}
						</td>
						<td colspan="2" style="text-align: right;">
							<span style="font-weight: bold;">작성일자  </span>${detail.bbsDate}
						</td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: left; height: 300px;">${detail.bbsContente}</td>
					</tr>
					<tr id="upvote_bbsID">
						<td colspan="3" style="text-align: center;">
							<!-- ${userID} 로그인시 세션에 저장해둠 -->
							<button onclick="upvoteUpdate(${detail.bbsID}, ${userID})" class="btn btn-primary">${detail.bbsUpvote} 추천</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div style="text-align: right;">
			<button class="btn btn-outline-secondary" id="bbsContentUpdate_${detail.bbsID}">수정</button>
			<button class="btn btn-outline-secondary" id="delID_bbsContent_${detail.bbsID}">삭제</button>
		</div>
	</div>
	
	<br>
	<br>
	<br>
	
	<div class="container">
		<c:set var="commentCount" value="${commentCount}"></c:set>
		<input type="hidden" id="commentCount" value="${commentCount}">
		<table class="table">
			<colgroup>
				<col width="15%"/>
				<col width="70%"/>
				<col width="15%"/>
			</colgroup>
			<thead>
				<tr id="commentTest">
					<th colspan="3"><h5>전체 댓글 ${commentCount}개</h5></th>
				</tr>
			</thead>
			<tbody id="commentTable">
				<c:forEach var="comments" varStatus="status" items="${comments}">
					<tr>
						<td style="text-align: left;">
							${comments.writeID}
						</td>
						<td style="text-align: left;">
							<input type="hidden" id="dispID_${comments.commentID}" value="${comments.commentID}">
							<input type="hidden" id="commentWriter_${comments.commentID}" value="${comments.writeID}">
							<a href="javascript:doDisplay(dispID_${comments.commentID});">${comments.comment}</a>
						</td>
						<td style="text-align: right; font-size: 12px">
							${comments.regDate} 
							<button class="btn btn-light" id="delID_comment_${comments.commentID}" style="font-size: 12px">[삭제]</button>
						</td>
					</tr>
					<!-- 대댓글 등록 창-->
					<tr id="riply_${comments.commentID}" style="display:none;">
						<td>
							<div>
								<input id="riply_nickName_${comments.commentID}" class="form-control" type="text" style="font-size: 12px" placeholder="아이디">
							</div>
							<div>
								<input id="riply_password_${comments.commentID}" class="form-control" type="password" style="font-size: 12px" placeholder="비밀번호">
							</div>
						</td>	
						<td>
							<div>
								<textarea id="riply_replyComment_${comments.commentID}" class="form-control" id="comment" name="comment" placeholder="댓글을 입력하세요" maxlength="100" style="font-size: 12px;"></textarea>
							</div>
						</td>	
						<td>
							<div style="text-align: right;">
								<button class="btn btn-light" onclick="riplyRegister(${comments.commentID}, dispID_${comments.commentID})">등록</button>
							</div>
						</td>
					</tr>
					<!-- 대댓글 리스트 -->
					<c:forEach var="reply" varStatus="status" items="${comments.replyList}">
						<tr id="reply_table_${comments.commentID}" bgcolor="#F5F5F5">
							<td style="text-align: left; font-size: 12px;">
								${reply.nickName}
							</td>
							<td style="text-align: left; font-size: 12px;">
								ㄴ <span style="background-color: lavender;">@ ${comments.writeID}</span> ${reply.replyComment}
							</td>
							<td style="text-align: right; font-size: 12px">
								${reply.regDate}
								<button class="btn btn-light" id="delID_reply_${reply.replyID}" style="font-size: 12px">[삭제]</button>
							</td>
						</tr>
					</c:forEach>							
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	

	<br>
	<br>
	<br>
	
	
	<div class="container">
		<table class="table">
				<thead>
					<tr>
						<th colspan="2"><h5>댓글 작성 하기</h5></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<input class="form-control" type="text" id="writeID" name="writeID" placeholder="아이디" maxlength="20">
						</td>
						<td>
							<input class="form-control" type="password" id="password" name="password" placeholder="비밀번호" maxlength="20">
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<textarea class="form-control" id="comment" name="comment" placeholder="댓글을 입력하세요" maxlength="100"></textarea>
							<div id="text_count">(0 / 100)</div>
							<br>
							<div style="text-align: center;">
								<input type="hidden" id="bbsID" value="${detail.bbsID}">
								<button class="btn btn-primary" onclick="commentRegister()">댓글등록</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
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
		$("button[id^='bbsContentUpdate_']").click(function(envet){   
			var x = event.clientX - 250;    
			var	y = event.clientY;		
				openUpdateForm(x, y, this);	  			
			});	
	</script>
	<script type="text/javascript">
	// 게시글 추천
		function upvoteUpdate(bbsID, userID) {
			var data = {bbsID: bbsID, userID: userID}
			$.ajax({
				type: "post",
				url: "voteController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					if (json[0].resultCode == 'ok') {
						upvotePrint(json, bbsID);
					} else if (json[0].resultCode == 'no') {
						alert('추천은 한번만 가능 합니다.')
						upvotePrint(json, bbsID);
					} else {
						alert('데이터베이스 오류 입니다.')
						upvotePrint(json, bbsID);
					}
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
				}
			});
		}
		
		function upvotePrint(json, bbsID) {
			var upvote = document.getElementById("upvote_bbsID");	
			upvote.innerHTML = '<td colspan="3" style="text-align: center;">' +
								   	'<button onclick="upvoteUpdate('+ bbsID +', '+ ${userID} +')" class="btn btn-primary">' +
								   	json[0].upvoteCount + ' 추천</button>' +
							   '</td>'
		}
	</script>
	<script type="text/javascript"> 
		// 페이지 진입시 가장 첫음에 하는 세팅
		// "문서내 버튼태그의 id값이 'delID_' 로 시작하는 것들
		$("button[id^='delID_']").click(function(envet){   
			var x = event.clientX - 250;    
			var	y = event.clientY;		
  			openForm(x, y, this);	  			
  		});
		
		// 댓글 삭제 버튼 클릭시 자식창에서 보내주는 메시지를 띄움
		function delMsg(msg, resultCode, commentCount){
			alert(msg);
			if (resultCode == 'OK') {
				var bbsID = $('#bbsID').val();				
				pageReload(bbsID);
			}
		}
		
		function bbsDelMsg(msg, code){
			alert(msg);	
			if (code == "OK") {
				location.href = 'freeBoardController?pageNumber=1';
			}
		}
		
		function pageReload(bbsID) {
			var data = {bbsID: bbsID}
			$.ajax({
				type: "post",
				url: "commentReloadController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					commentPrint(json)
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
				}
			});
		}	
	</script>
	<script type="text/javascript">
	// 대댓글
		function riplyRegister(id, tagHideID){
			var data = {
					commentID: id,
					bbsID: $('#bbsID').val(),
					nickName: $('#riply_nickName_' + id + '').val(),
					password: $('#riply_password_' + id + '').val(),
					replyComment: $('#riply_replyComment_' + id + '').val()
				}
			
				$.ajax({
					type: "post",
					url: "replyRegisterController",
					data: JSON.stringify(data),
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					
					success: function(json) {
						// 작성완료시 대댓글창 없앰
						doDisplay(tagHideID);
						print(json, id);
					},
					error: function(json) {
						alert('빈칸없이 작성해야 합니다.')
					}
				});
		}
		
		function print(id) {
			commentPrint(json);	
			// 사용된 태그들 공백처리
			$('#riply_nickName_' + id + '').val("");
			$('#riply_password_' + id + '').val("");
			$('#riply_replyComment_' + id + '').val("");	
		}
	</script>
	<script type="text/javascript">
	// 댓글
		function commentRegister() {
			var data = {
				writeID: $("#writeID").val(),
				password: $("#password").val(),
				bbsID: $("#bbsID").val(),
				comment: $("#comment").val()
			}
	
			$.ajax({
				type: "post",
				url: "commentRegisterController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				
				success: function(json) {
					print(json);
				},
				error: function(json) {
					alert('시스템 오류 입니다.')
				}
			});
		}
		
		function print(json) {			
			commentPrint(json);
			// 사용된 태그들 공백처리
			$('#text_count').html("(0 / 100)");
			$('#writeID').val("");
			$('#password').val("");
			$('#comment').val("");
		}
	</script>
	<script type="text/javascript">
		function commentPrint(json) {
			var table = document.getElementById("commentTable");	
			var commentCount = json.length;
			table.innerHTML = " ";			
			
			for (var i = 0; i < commentCount; i++) {
				var commentID = json[i].commentID;
				var writeID = json[i].writeID;
				row =	'<tr>' +
							'<td style="text-align: left;">' +
								writeID +
							'</td>' +
							'<td style="text-align: left;">' +
								'<input type="hidden" id="dispID_' + commentID + '" value="' + commentID + '">' +
								'<input type="hidden" id="commentWriter_' + commentID + '" value="' + writeID + '">' +
								'<a href="javascript:doDisplay(dispID_' + commentID + ');">'+ json[i].comment +'</a>' +
							'</td>' +
							'<td style="text-align: right; font-size: 12px">' +
								json[i].regDate +
								'<button class="btn btn-light" id="delID_comment_'+ commentID +'" style="font-size: 12px">[삭제]</button>' +
							'</td>' +
						'</tr>' +
						'<!-- 대댓글 등록 -->' +
						'<tr id="riply_'+ commentID +'" style="display:none;">' +
							'<td>' +
								'<div>' +
									'<input id="riply_nickName_'+ commentID +'" class="form-control" type="text" style="font-size: 12px" placeholder="아이디">' +
								'</div>' +
								'<div>' +
									'<input id="riply_password_'+ commentID +'" class="form-control" type="password" style="font-size: 12px" placeholder="비밀번호">' +
								'</div>' +
							'</td>' +
							'<td>' +
								'<div>' +
									'<textarea id="riply_replyComment_'+ commentID +'" class="form-control" id="comment" name="comment" placeholder="댓글을 입력하세요" maxlength="100" style="font-size: 12px;"></textarea>' +
								'</div>' +
							'</td>' +
							'<td>' +
								'<div>' +
									'<button class="btn btn-light" onclick="riplyRegister('+ commentID +', dispID_'+ commentID +')">등록</button>' +
								'</div>' +
							'</td>' +
						'</tr>';
				if (json[i].replyList != null) {
					var reply = json[i].replyList;
					for (var j = 0; j < reply.length; j++) {
						
						row +=  '<!-- 대댓글 리스트 -->' +
								'<tr id="reply_table_'+ commentID +'" bgcolor="#F5F5F5">' +
									'<td style="text-align: left; font-size: 12px;">' + 
										reply[j].nickName +
									'</td>' +
									'<td style="text-align: left; font-size: 12px;">' +
										'ㄴ <span style="background-color: lavender;">@'+ writeID +'</span>' + 
										'&nbsp;&nbsp;&nbsp;' + reply[j].replyComment +
									'</td>' +
									'<td style="text-align: right; font-size: 12px">' +
										reply[j].regDate + '<button class="btn btn-light" id="delID_reply_' + reply[j].replyID + '"' + commentID + '" style="font-size: 12px">[삭제]</button>' +
									'</td>' +
								'</tr>';
					}	
					table.innerHTML += row;
				}	
			}
			
			var totalCommentCount = 0;
			var jsonLength = json.length;
			for (var i = 0; i < jsonLength; i++) {
				totalCommentCount += json[0].replyList.length;
			}
			
			totalCommentCount += jsonLength;
			var commentTest = document.getElementById('commentTest');
			commentTest.innerHTML = '<th colspan="3"><h5>전체 댓글 ' + totalCommentCount + '개</h5></th>';
			
			// 삭제비번창 작업		
			// "문서내 버튼태그의 id값이 'delID_' 로 시작하는 것들
			$("button[id^='delID_']").click(function(envet){   
				var x = event.clientX - 250;    
				var	y = event.clientY;		
	  			openForm(x, y, this)	
	  		});
		}
	</script>
	<script type="text/javascript">
		function doDisplay(id){
			var id = id.value;
			var con = document.getElementById('riply_' + id + '');
			if (con.style.display=='none'){
				con.style.display = '';
			} else {
				con.style.display = 'none';
			}
		}
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#comment').on('keyup', function() {
				$('#text_count').html("("+$(this).val().length+" / 100)");
				
				if($(this).val().length > 100) {
					$(this).val($(this).val().substring(0, 100));
					$('#text_count').html("(100 / 100)");
				}
			});
		});
	</script>
	<script type="text/javascript">
		function openForm(x, y, tagID) {
			var options = 'width=200, height=100, top='+ y +', left='+ x +', resizable=no, scrollbars=no, location=no'; 
			window.open('./delForm.jsp?id=' + (tagID.id) + '', 'delForm', options);
		}
	</script>
</body>
</html>















