<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>댓글삭제</title>
</head>
<body>
	비밀번호
	<input type="password" id="delPassword">
	<input type="button" value="삭제" onclick="commentRemove()">

	<script src="https://code.jquery.com/jquery-3.6.0.js"
  			integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
  			crossorigin="anonymous">
	</script>
	<script type="text/javascript">
		function commentRemove(){
			// 전달되는 파라미터값 형태
			// delID_ comment or reply or _ bbsContent _ id
			var param = getParam('id').split('_');
			var divide = param[1];
			var id = param[2];
			var password = $('#delPassword').val();
			console.log('divide : ' + divide);	
			console.log('id : ' + id);	
			console.log('password : ' + $('#delPassword').val());	
			removeAction(divide, id, password);
	    }
		
		function removeAction(divide, id, password) {
			var data = {
					divide: divide,
					id: id, 
					password: password						
			}	
			
			var url = getUrl(divide);
			
			$.ajax({
				type: "post",
				url: url,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",				
				success: function(json) {
					var resultCode = json[0].resultCode;
										
					if (divide == 'bbsContent') {
						if (resultCode == 'OK') {
							opener.bbsDelMsg("삭제 되었습니다.", "OK");
						} else if (resultCode == 'WRONG_PASS') {
							opener.bbsDelMsg("잘못된 비밀번호 입니다.");
						} else if (resultCode == 'ERROR') {
							opener.bbsDelMsg("데이터베이스 오류 입니다.", "OK");					
						}
						window.close();
					}
					
					var commentCount = json[1].commentCount;
					if (resultCode == 'OK') {
						opener.delMsg("삭제 되었습니다.", resultCode, commentCount);
					} else if (resultCode == 'WRONG_PASS') {
						opener.delMsg("잘못된 비밀번호 입니다.", resultCode, commentCount);
					} else if (resultCode == 'ERROR') {
						opener.delMsg("데이터베이스 오류 입니다.", resultCode, commentCount);					
					}
					window.close();
				},
				error: function(json) {
					console.log(json[0]);
					opener.delMsg("시스템 오류 입니다.");
				}
			});
		}
		
		function getUrl(divide) {
			let url;
			if (divide == 'bbsContent') {
				url = 'freeBoardDeleteController';
			} else if (divide == 'comment') {
				url = 'commentDeleteController';
			} else {
				url = 'replyDeleteController';
			}
			return url;
		}
		
		// 파라미터 id값으로 분류 사용처에서 getParam(원하는 id);
		function getParam(sname) {
		    var params = location.search.substr(location.search.indexOf("?") + 1);
		    var sval = "";
		    params = params.split("&");
		    for (var i = 0; i < params.length; i++) {
		        temp = params[i].split("=");
		        if ([temp[0]] == sname) { sval = temp[1]; }
		    }
		    return sval;
		}
	</script>
</body>
</html>