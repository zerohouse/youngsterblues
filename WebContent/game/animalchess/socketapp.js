/**
 * 
 */
var socket = io('http://54.65.20.191:8000');

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
		chess.show = false;
	} else {
		chess.show = true;
	}
	chess.scope().$apply();

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

startbtn = $('#startbtn');
startbtn.click(function() {
	if (chess.ready) {
		socket.emit('game', {
			type : 'start'
		});
		startbtn.hide();
		chess.ongame = true;
		alertToScreen('레디!');
		return;
	}
	alertToScreen('레디!');
	socket.emit('game', {
		type : 'ready'
	});
});

socket.on('game', function(data) {
	switch (data.type) {
	case 'start':
		chess.ongame = true;
		startbtn.hide();
		if (Math.random() > 0.5) {
			myTurn();
		} else {
			turnOver();
		}
		break;
	case 'ready':
		startbtn.toggleClass('btn-success');
		startbtn.text('Game Start');
		chess.ready = true;
		break;
	case 'turnOver':
		console.log(data);
		chess.scope().mydie = transferDie(data.yourdie);
		chess.scope().yourdie = transferDie(data.mydie);
		chess.scope().ongame = transfer(data.ongame);
		chess.scope().$apply();
		myTurn();
		if (chess.tigerInWin)
			gameEnd(true);
		break;
	case 'myturn':
		alertToScreen('상대방의 턴입니다.');
		break;
	case 'youlose':
		gameEnd(false);
		break;
	}

});

function transferDie(data) {
	var result = [];
	for (var i = data.length - 1; i > -1; i--) {
		if (data[i].animal == 'empty') {
		} else {
			result.push(animal(data[i].animal, !data[i].my));
		}
	}
	return result;
}

function transfer(data) {
	var result = [];
	for (var i = data.length - 1; i > -1; i--) {
		if (data[i].animal == 'empty') {
			result.push(empty());
		} else {
			result.push(animal(data[i].animal, !data[i].my));
		}
	}
	return result;
}

function myTurn() {
	alertToScreen('내 차례!');
	chess.myturn = true;
	socket.emit('game', {
		type : 'myturn'
	});
}

function turnOver() {
	socket.emit('game', {
		type : 'turnOver',
		mydie : chess.scope().mydie,
		yourdie : chess.scope().yourdie,
		ongame : chess.scope().ongame
	});
	chess.myturn = false;
}
