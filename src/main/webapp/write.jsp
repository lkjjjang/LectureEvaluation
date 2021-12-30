<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>한양사이버대 강의평가</title>
	<!-- 부트스트랩 CSS 추가하기 -->
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet"> 
	<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet"> 
	
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> 
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
	<script src=" https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
	
</head>
<body>
	<br>
	<br>
	<br>
	<br>
	<br>
	
	<div class="container">
		글쓰기
		<div>
			<form method="post" action="freeBoardRegisterController">
				<table class="table">
					<thead>
						<tr>
							<th colspan="2" style="background-color: #eeeeee; text-align: center;">게시글은 익명으로 처리 됩니다!</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class="form-control" placeholder="닉네임" name="nickName" maxlength="10"></td>
							<td><input type="password" class="form-control" placeholder="비밀번호" name="password" maxlength="10"></td>
						</tr>
						<tr>
							<td colspan="2"><input type="text" class="form-control" placeholder="글 제목" name="title" maxlength="50"></td>
						</tr>
						<tr>
							<td colspan="2"><textarea id="summernote" name="content" class="summernote" maxlength="2048"></textarea></td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="hidden" id="userID" name="userID" value="${userID}">
								<input type="submit" value="등록">
							</td>
						</tr>						
					</tbody>					
				</table>
			</form>
		</div>
	</div>
	<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
		Copyright &copy; 2021이기주All Rights Reserved.
	</footer>
   	<script>
	    $(document).ready(function() {
	    	//여기 아래 부분
	    	$('#summernote').summernote({
	    		  height: 300,                 // 에디터 높이 
	    		  minHeight: null,             // 최소 높이
	    		  maxHeight: null,             // 최대 높이
	    		  focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
	    		  lang: "ko-KR",					// 한글 설정
	    		  placeholder: '최대 2048자까지 쓸 수 있습니다',	//placeholder 설정
	    		  toolbar: [
	  			    // [groupName, [list of button]]
	  			    ['fontname', ['fontname']],
	  			    ['fontsize', ['fontsize']],
	  			    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
	  			    ['color', ['forecolor','color']],
	  			    ['table', ['table']],
	  			    ['para', ['ul', 'ol', 'paragraph']],
	  			    ['height', ['height']],
	  			    ['insert',['picture','link','video']],
	  			    ['view', ['fullscreen', 'help']]
	  			  ],
	  			 fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
	  			 fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],
	  			 callbacks: {	//여기 부분이 이미지를 첨부하는 부분
					onImageUpload : function(files) {
						for (var i = 0; i < files.length; i++) {
							uploadSummernoteImageFile(files[i]);
						}
					},
					onPaste: function (e) {
						var clipboardData = e.originalEvent.clipboardData;
						if (clipboardData && clipboardData.items && clipboardData.items.length) {
							var item = clipboardData.items[0];
							if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
								e.preventDefault();
							}
						}
					}
				}
	    	});
	    });
	    
		function uploadSummernoteImageFile(file) {
			var userID = document.getElementById("userID").value;	
			console.log(userID);
			data = new FormData();
			data.append("file", file);
			data.append("userID", userID);
			$.ajax({
				data : data,
				type : "post",
				url : "uploadSummernoteImageFile",
				cache: false,
		        contentType: false, // contentType 보내는 데이터의 유형 (false 설정시 기본유형으로됨)
		        processData: false,
		        //dataType: "json", // 서버에서 보내주는 데이터를 받는 유형 (서버에서 설정한 유형과 같아야한다)
		        //enctype: "multipart/form-data",
		        // success함수는 기본적으로 3개의 인자를 받지만 응답코드 하나만 인자로 사용해도 무방
				success : function(response) {
					if (response[0].resultCode == 'capacityFull') {
						console.log(response[1].msg);
						alert(response[1].msg)
					} else {
						$('#summernote').summernote('insertImage', response);
					}
					
				},
				error : function(error) {
			        alert('시스템 오류');
			    }
			});
		}
	</script>
</body>
</html>
