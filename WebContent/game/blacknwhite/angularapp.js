/**
 * 
 */
var socket = io('http://54.65.20.191:8001');

socket.emit('init', {
	name : $('#userName').val(),
	id : $('#userId').val()
});

socket.on('chat', function(data) {
	chat(data);
});

function chat(data) {
	var scop = angular.element($('#chatword')).scope();
	scop.chats.push(data);
	scop.$apply();
	$('#chat-panel').scrollTop(
			document.getElementById('chat-panel').scrollHeight);
}

socket.on('roomupdate', function(data) {
	var scop = angular.element($('#chatrooms')).scope();
	scop.rooms = data;
	scop.$apply();
});

// 방 조인 처리
socket.on('join', function(success, roomdata) {
	if (!success)
		return;
	if (roomdata == undefined)
		return;
	var scop = angular.element($('#roomdata')).scope();
	if (roomdata.roomid == 'square') {
		bnw.show = false;
	} else {
		bnw.show = true;
	}
	bnw.scope().$apply();

	scop.room = roomdata;
	scop.$apply();
});

// 방만들기 처리
var makeroombtn = $('#makeroombtn');
var roomname = $('#roomname');
makeroombtn.click(function() {
	if (roomname.val() == "")
		return;
	var roomdata = {
		name : roomname.val(),
		maker : $('#userName').val(),
		time : getNow()
	}
	socket.emit('makeroom', roomdata);
	roomname.val('');
	$('#makeroom').modal('toggle');
});

// 채팅처리
var chatbtn = $('#btn-chat');
var input = $('#btn-input');
chatbtn.click(function() {
	if (input.val() == "")
		return;
	var chatdata = {
		chat : input.val(),
		time : getNow()
	}
	socket.emit('sendchat', chatdata);
	input.focus();
	input.val('');
});

input.keypress(function(e) {
	if (e.which == 13)
		chatbtn.click();
});

function getNow() {
	var date = new Date(Date.now());
	return date.getHours() + "시 " + date.getMinutes() + "분";
}

// 게임 시작 처리
var startbtn = $('#startbtn');
startbtn.click(function() {
	if (bnw.ready) {
		socket.emit('game', {
			type : 'start'
		});
		startbtn.hide();
		bnw.ongame = true;
		alertToScreen('레디!');
		return;
	}
	alertToScreen('레디!');
	socket.emit('game', {
		type : 'ready'
	});
});

var app = angular.module('blacknwhite', []);
var joined = false;

app.controller('chatrooms', [ '$scope', function($scope) {
	$scope.rooms = [];
	$scope.status = function(room) {
		if (room.users < room.maxusers)
			return "waiting";
		return "full"
	}
	$scope.joinRoom = function(roomId) {
		console.log(roomId);
		if (joined)
			return;
		socket.emit('joinRoom', roomId);
	}

} ]);

app.controller('chatword', [ '$scope', function($scope) {
	$scope.chats = [];
} ]);

app.controller('roomdata', [ '$scope', function($scope) {
	$scope.room = {
		name : "광장",
		roomid : "",
		maxusers : "",
		users : ""
	}
} ]);

app.controller('blacknwhite', [ '$scope', function($scope) {
	$scope.show = function() {
		return bnw.show;
	}
	$scope.reset = function() {
		$scope.game = {
			round : 0,
			point : 99
		}
		$scope.enemy = {
			round : 0,
			point : 99
		}
		bnw.ongame = false;
		bnw.ready = false;
		bnw.submittedPoint = undefined;
		bnw.myPoints = [];
		$('#alertTo').unbind('click');
		$('#alertTo').show();
		startbtn.removeClass('btn-success');
		startbtn.text('Ready');
		startbtn.show();
	}
	$scope.reset();
	$scope.submitPoint = "";
	$scope.submit = function() {
		if (!bnw.myTurn) {
			alertToScreen("내 차례가 아닙니다.");
			return;
		}
		if (isNaN(parseInt($scope.submitPoint))) {
			alertToScreen("숫자를 입력해주세요");
			return;
		}

		$scope.game.point -= $scope.submitPoint;
		bnw.submitPoint($scope.submitPoint);
		$scope.submitPoint = "";
	}
} ]);

function alertToScreen(message, type) {
	var alertTo = $('#alertTo');
	chat({
		chat : message.replace(/<br>/g, " "),
		time : getNow()
	});
	if (type == 'small') {
		message = "<p class='smallalert'>" + message + "</p>";
	}
	if (alertTo.css('display') == 'block') {
		alertTo.html(alertTo.html() + "<br>" + message);
	} else {
		alertTo.html(message);
	}
	alertTo.show('drop', 500);
	setTimeout(function() {
		alertTo.hide('drop', 500);
	}, 2000);
	alertTo.click(function() {
		alertTo.hide();
	});
}

function removeArray(arr, index) {
	var result = [];
	for (var i = 0; i < arr.length; i++)
		if (i != index)
			result.push(arr[i]);
	return result;
}
