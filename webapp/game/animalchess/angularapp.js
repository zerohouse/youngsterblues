/**
 * 
 */

var app = angular.module('animalchess', []);
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

var move = function(die, stuff, mydie, yourdie) {
	if (!chess.ongame) {
		alertToScreen('게임을 시작되지 않았어요.');
		return;
	}
	if (!chess.myturn) {
		alertToScreen('상대방의 턴입니다.');
		return;
	}
	var died = stuff.indexOf(die);
	if (stuff[chess.moving].my == stuff[died].my) {
		alertToScreen('내 말 위로는 못가요');
		return;
	}
	if (stuff[chess.moving].animal == 'tiger' && died < 4) {
		chess.tigerInWin = true;
	}
	// 이동
	if (stuff[died].animal == 'tiger')
		gameEnd(true);
	if (stuff[chess.moving].animal == 'snake' && died <= 2)
		stuff[chess.moving] = animal('dragon', stuff[chess.moving].my);
	if (stuff[died].animal != 'empty') {
		stuff[died].guide = false;
		stuff[died].my = true;
		mydie.push(stuff[died]);
	}
	stuff[died] = stuff[chess.moving];
	stuff[chess.moving] = empty();
	hideGuideReborn(stuff);
	turnOver();
}

function gameEnd(isWin) {
	if (!chess.ongame)
		return;
	chess.ongame = false;
	var alertTo = $('#alertTo');
	if (isWin) {
		alertTo.text('승리');
		socket.emit('game', {
			type : 'youlose'
		});
	} else {
		alertTo.text('패배');
	}
	alertTo.unbind('click');
	alertTo.show('pulsate', 500);
	setTimeout(function() {
		chess.scope().reset();
	}, 5000);
}

function alertToScreen(message) {
	var alertTo = $('#alertTo');
	if (alertTo.css('display') == 'block') {
		alertTo.html(alertTo.text() + "<br>" + message);
	} else {
		alertTo.text(message);
	}
	alertTo.show('drop', 500);
	setTimeout(function() {
		alertTo.hide('drop', 500);
	}, 2000);
	chat({
		chat : message,
		time : getNow()
	});
	alertTo.click(function() {
		alertTo.hide();
	});
}

function hideGuideReborn(stuff) {
	for (var i = 0; i < stuff.length; i++) {
		stuff[i].guide = false;
		stuff[i].reborn = false;
	}
}

var rebornAnimal;
var empty = function() {
	var result = {
		moveAble : function(data) {
		},
		move : function(stuff, mydie, yourdie) {
			move(result, stuff, mydie, yourdie);
		},
		alive : true,
		my : false,
		animal : 'empty',
		name : '',
		direction : '',
		rebornTo : function(stuff, mydie) {
			if (!chess.ongame) {
				alertToScreen('게임이 시작되지 않았어요.');
				return;
			}
			if (!chess.myturn) {
				alertToScreen('상대방의 턴입니다.');
				return;
			}
			var index = stuff.indexOf(result);
			var myindex = mydie.indexOf(rebornAnimal);
			chess.scope().mydie = removeArray(mydie, myindex);
			stuff[index] = rebornAnimal;
			stuff[index].my = true;
			hideGuideReborn(stuff);
			turnOver();
		}
	}
	return result;
}

function removeArray(arr, index) {
	var result = [];
	for (var i = 0; i < arr.length; i++)
		if (i != index)
			result.push(arr[i]);
	return result;
}

var chess = {
	show : false,
	scope : function() {
		return angular.element($('#chesspan')).scope()
	}
};

var animal = function(animal, my) {
	var name;
	var direction = [ true, true, true, true, false, true, true, true, true ];
	switch (animal) {
	case 'pig':
		name = '돼지';
		direction[0] = false;
		direction[2] = false;
		direction[6] = false;
		direction[8] = false;
		break;
	case 'tiger':
		name = '호랑이';
		break;
	case 'snake':
		name = '뱀';
		direction[0] = false;
		direction[2] = false;
		direction[3] = false;
		direction[5] = false;
		direction[6] = false;
		direction[7] = false;
		direction[8] = false;
		break;
	case 'dog':
		name = '개';
		direction[1] = false;
		direction[3] = false;
		direction[5] = false;
		direction[7] = false;
		break;
	case 'dragon':
		name = '용';
		direction[6] = false;
		direction[8] = false;
		break;
	case 'null':
		direction = [ false, false, false, false, false, false, false, false,
				false ];
		break;
	}
	var result = {
		getClass : function() {
			if (!result.my)
				return result.animal + '-enemy';
			return result.animal;
		},
		moveAble : function(data) {
			if (!chess.ongame) {
				alertToScreen('게임을 시작되지 않았어요.');
				return;
			}
			if (!chess.myturn) {
				alertToScreen('상대방의 턴입니다.');
				return;
			}
			hideGuideReborn(data);
			if (chess.showGuides) {
				chess.showGuides = false;
				return;
			}
			chess.showGuides = true;
			var pos = data.indexOf(result);
			for (var i = 0; i < 9; i++) {
				var dx = i - 4;
				var firstColumn = pos % 3 == 0 && (pos + dx) % 3 == 2;
				var thirdColumn = pos % 3 == 2 && (pos + dx) % 3 == 0;
				var range = pos + dx >= 0 && pos + dx <= 11;
				var isMine = function() {
					return result.my;
				}
				if (!firstColumn && !thirdColumn && range && direction[i]
						&& isMine()) {
					data[pos + dx].guide = true;
					chess.moving = pos;
				}
			}
		},
		move : function(stuff, mydie, yourdie) {
			move(result, stuff, mydie, yourdie);
		},
		alive : true,
		my : my,
		animal : animal,
		name : name,
		direction : direction
	}
	return result;
};

app.controller('chess', [
		'$scope',
		function($scope) {
			$scope.show = function() {
				return chess.show;
			}
			
			$scope.reset = function() {
				$scope.yourdie = [];
				$scope.mydie = [];
				$scope.stuff = [ animal('pig', false),
						animal('tiger', false), animal('dog', false), empty(),
						animal('snake', false), empty(), empty(),
						animal('snake', true), empty(), animal('dog', true),
						animal('tiger', true), animal('pig', true) ];
				chess.ongame = false;
				chess.ready = false;
				$('#alertTo').unbind('click');
				$('#alertTo').show();
				startbtn.removeClass('btn-success');
				startbtn.text('Ready');
				startbtn.show();
			}
			$scope.reset();

			$scope.reborn = function(data, animal) {
				if (!chess.ongame) {
					alertToScreen('게임을 시작되지 않았어요.');
					return;
				}
				if (!chess.myturn) {
					alertToScreen('상대방의 턴입니다.');
					return;
				}
				hideGuideReborn(data);
				rebornAnimal = animal;
				for (var i = 0; i < data.length; i++)
					if (data[i].animal == 'empty')
						data[i].reborn = true;
			}
		} ]);