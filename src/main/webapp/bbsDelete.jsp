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
	<input type="button" value="확인" onclick="contentDelete()">

	<script src="https://code.jquery.com/jquery-3.6.0.js"
  			integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
  			crossorigin="anonymous">
	</script>
	<script type="text/javascript">
		function contentDelete(){
			console.log(111111111111);
			var param = getParam('id').split('_');
			var bbsID = param[1];
			var password = $('#delPassword').val();
			deleteAction(bbsID, password);
	    }
		
		function deleteAction(bbsID, password) {
			console.log('remove inside');	
			var data = {bbsID: bbsID, password: password}				
			$.ajax({
				type: "post",
				url: "freeBoardDeleteController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",				
				success: function(json) {
					var resultCode = json[0].resultCode;
					if (resultCode == 'ok') {
						opener.passwordError("삭제되었습니다.");		
					} else if (resultCode == 'wrongPass') {
						opener.passwordError("잘못된 비밀번호 입니다.");
					} else if (resultCode == 'error') {
						opener.passwordError("데이터 베이스 오류 입니다.");
					}
					window.close();
				},
				error: function(json) {
					opener.passwordError("데이터베이스 오류 입니다.");
				}
			});
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