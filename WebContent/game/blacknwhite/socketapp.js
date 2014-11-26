socket.on('game', function(data) {
	switch (data.type) {

	case 'start':
		if (Math.random() > 0.5) {
			bnw.gameStart();
		} else {
			socket.emit('game', {
				type : 'yourStart'
			})
		}
		break;

	case 'yourStart':
		bnw.gameStart();
		break;

	case 'ready':
		bnw.ready = true;
		startbtn.toggleClass('btn-success');
		startbtn.text('Game Start');
		break;

	case 'gameStart':
		bnw.ongame = true;
		bnw.myTurn = false;
		startbtn.hide();
		break;

	case 'roundEnd':
		alertToScreen(data.result);
		bnw.scope().game = data.enemy;
		bnw.scope().enemy = data.game;
		break;

	case 'blacknwhite':
		alertToScreen(data.bnw);
		break;

	case 'yourturn':
		console.log(data.point);
		bnw.scope().game = data.enemy;
		bnw.scope().enemy = data.game;
		if (bnw.submittedPoint == undefined) {
			if (data.point != 'start')
				bnw.submittedPoint = data.point;
			bnw.myTurnStart();
			return;
		}
		bnw.winCheck(data.point);
		bnw.submittedPoint = undefined;
		break;

	case 'myTurn':
		alertToScreen('상대방의 턴입니다.');
		break;
	case 'youlose':
		bnw.gameEnd(false);
		break;
	case 'youwin':
		bnw.gameEnd(true);
		break;

	}

});

var bnw = {

	gameStart : function() {
		bnw.ongame = true;
		bnw.myTurn = true;
		startbtn.hide();
		socket.emit('game', {
			type : 'gameStart'
		});
		bnw.myTurnStart();
	},

	myTurnStart : function() {
		if (!bnw.ongame)
			return;
		alertToScreen('내 차례!');
		bnw.myTurn = true;
		socket.emit('game', {
			type : 'myTurn'
		});
	},

	show : false,

	scope : function() {
		return angular.element($('#blacknwhitearea')).scope()
	},
	ready : false,
	submittedPoint : undefined,
	submitPoint : function(point) {
		if (bnw.submittedPoint == undefined) {
			bnw.blacknwhite(point);
			socket.emit('game', {
				type : 'yourturn',
				point : point,
				game : bnw.scope().game,
				enemy : bnw.scope().enemy
			});
			console.log(point);
			bnw.myTurn = false;
			return;
		}
		bnw.blacknwhite(point);
		bnw.winCheck(point);

	},
	winCheck : function(point) {
		if (bnw.submittedPoint < point) {
			bnw.roundEnd(true);
			return;
		}
		bnw.roundEnd(false);
	},
	blacknwhite : function(point) {
		if (parseInt(point) > 9) {
			socket.emit('game', {
				type : 'blacknwhite',
				bnw : '백'
			});
			return;
		}
		socket.emit('game', {
			type : 'blacknwhite',
			bnw : '흑'
		});
	},
	roundEnd : function(isWin) {
		if (isWin) {
			alertToScreen("라운드 승리!");
			alertToScreen("다음 라운드 시작!");
			bnw.scope().game.round++;
			bnw.submittedPoint = undefined;
			socket.emit('game', {
				type : 'roundEnd',
				result : '라운드 패배!',
				game : bnw.scope().game,
				enemy : bnw.scope().enemy
			});
			if (bnw.scope().game.round > 4) {
				bnw.gameEnd(true);
			}
			return;
		}
		alertToScreen("라운드 패배!");
		alertToScreen("다음 라운드 시작!");
		bnw.scope().enemy.round++;
		bnw.submittedPoint = undefined;
		socket.emit('game', {
			type : 'roundEnd',
			result : '라운드 승리!',
			game : bnw.scope().game,
			enemy : bnw.scope().enemy,
		});
		if (bnw.scope().enemy.round > 4) {
			bnw.gameEnd(false);
		}
	},

	gameEnd : function(isWin) {
		if (!bnw.ongame)
			return;
		bnw.ongame = false;
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
			bnw.scope().reset();
		}, 5000);
	}

};
