/**
 * 
 */
(function() {

	var id = $('#signid');
	var password = $('#signpassword');
	var passwordconfirm = $('#signpasswordconfirm');
	var name = $('#signname');

	id.keypress(onlyEng);
	password.keypress(onlyEng);

	function onlyEng(event) {
		var ew = event.which;
		if (ew == 32)
			return true;
		if (48 <= ew && ew <= 57)
			return true;
		if (65 <= ew && ew <= 90)
			return true;
		if (97 <= ew && ew <= 122)
			return true;
		return false;
	}

	function isEng(text) {
		var reg = /^([a-zA-Z0-9]{1,20})$/
		return reg.test(text);
	}

	id.focusout(function() {
		if (id.val() == "")
			return;
		if (id.val().length < 4) {
			error("아이디는 4~12자여야 합니다.", id);
			return;
		}
		if (!isEng(id.val())) {
			error("ID는 영문과 숫자만 사용해주세요.", id);
			return;
		}
		$.ajax({
			url : "/users/checkid",
			type : "POST",
			data : {
				id : id.val()
			}
		}).done(function(data) {
			if (JSON.parse(data)) {
				error("이미 존재하는 아이디입니다.", id);
				return;
			}
			ok(id);
		});
	});

	password.focusout(function() {
		if (password.val() == "")
			return;
		if (password.val().length < 4) {
			error("패스워드는 4자이상이어야 합니다.", password);
			return;
		}
		ok(password);

	});

	passwordconfirm.focusout(function() {
		if (passwordconfirm.val() == "")
			return;
		if (password.val() != passwordconfirm.val()) {
			error("비밀번호가 일치하지 않습니다.");
			return;
		}
		ok(passwordconfirm);
	});

	name.focusout(function() {
		if (name.val() == "")
			return;
		if (name.val() == "") {
			error("이름을 입력해주세요.", name);
			return;
		}
		ok(name);
	});

	function ok(obj) {
		obj.tooltip('destroy');
		alertText.hide(500);
	}

	function error(message, obj) {
		obj.tooltip({
			title : message
		});
		obj.tooltip('show');
		obj.focus();
	}

	$('#signupbtn').click(function() {
		if (id.val().length < 4) {
			error("아이디는 4~12자여야 합니다.", id);
			return;
		}
		if (password.val().length < 4) {
			error("패스워드는 4자이상이어야 합니다.", password);
			return;
		}
		if (password.val() != passwordconfirm.val()) {
			error("비밀번호가 일치하지 않습니다.", passwordconfirm);
			return;
		}
		if (name.val() == "") {
			error("이름을 입력해주세요.", name);
			return;
		}

		var user = {
			id : id.val(),
			password : password.val(),
			name : name.val()
		};

		$.ajax({
			url : "/users/signup",
			type : "POST",
			data : {
				user : JSON.stringify(user)
			}

		}).done(function(data) {
			if (!data.state) {
				alertMessage(data.errorMessage);
				return;
			}
			window.alert("가입되었습니다. 감사합니다.");
			location.href = "/";
		});

	});

	var alertText = $('#alertMessage');
	alertText.click(function() {
		$(this).hide(500);
	});

	function alertMessage(text) {
		alertText.text(text);
		alertText.show(500);
	}

})();