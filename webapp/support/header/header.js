/**
 * 헤더자바스크립트
 */

$().showUp('.navbar', {
	upClass : 'navbar-show',
	downClass : 'navbar-hide'
});

$(window).load(function() { $('.loading').fadeOut(500); });

(function() {

	var errorMessage = $('#errorMessage');
	errorMessage.click(function() {
		errorMessage.hide(500);
	});

	$('#signup').click(function() {
		location.href = "/signup.jsp";
	});

	$('#password').keypress(function(e) {
		if (e.which == 13)
			loginbtn.click();
	});

	var loginbtn = $('#loginsubmit');
	loginbtn.click(function() {
		var id = $('#id').val();
		var password = $('#password').val();
		if (id == "" || password == "") {
			errorMessage.show(500);
			errorMessage.text("아이디와 비밀번호를 입력해주세요.");
			return;
		}
		$.ajax({
			url : "/users/login",
			type : "POST",
			data : {
				id : id,
				password : password
			}

		}).done(function(data) {
			if (!data.state) {
				errorMessage.show(500);
				errorMessage.text(data.errorMessage);
				return;
			}
			location.reload();
		});
	});

})();
