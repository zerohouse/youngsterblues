<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/components/_css.jspf"%>
<link rel='stylesheet' href='chat.css'>
<link rel='stylesheet' href='animal.css'>
</head>
<body ng-app="animalchess">
	<%@ include file="/components/_header.jspf"%>
	<div class='jumbotron'>
		<div class='container'>
			<h1>동물 장기!</h1>
		</div>
	</div>

	<div class="container">
		<div id='chesspan' class="row" ng-controller='chess' ng-show='show()'>
			<div class="col-md-12">
				<div class='panel panel-default'>
					<div id='alertTo'></div>

					<div class='panel-heading'>동물장기</div>

					<div id='chess' class='panel-body chess'>
						<ul>
							<li ng-repeat="animal in ongame"
								ng-click="animal.moveAble(ongame)" ng-class='animal.getClass()'>
								<div class='guide' ng-show="animal.guide"
									ng-click="animal.move(ongame, mydie, yourdie); $event.stopPropagation();"></div>
								<div class='reborn' ng-show="animal.reborn"
									ng-click="animal.rebornTo(ongame, mydie); $event.stopPropagation();"></div>

							</li>
						</ul>
						<ul>
							<div>
								<ul class='panel panel-default dies' id='yourdie'>
									<div class='panel-heading'>상대방의 동물</div>
									<div class='panel-body'>
										<li ng-repeat="animal in yourdie" ng-class='animal.getClass()'></li>
									</div>
								</ul>
							</div>
							<div>
								<ul class='panel panel-default dies' id='mydie'>
									<div class='panel-heading'>나의 동물</div>
									<div class='panel-body'>
										<li ng-repeat="animal in mydie"
											ng-click="reborn(ongame, animal)"
											ng-class='animal.getClass()'></li>
									</div>
								</ul>
							</div>
						</ul>
						<div id='startbtn' class='btn btn-primary'>Ready</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-7">
				<div class='panel panel-default'>
					<div class='panel-heading'>방 목록</div>
					<div class='panel-body'>
						<ul id='chatrooms' class="list-group" ng-controller="chatrooms">
							<li ng-repeat="(key, room) in rooms" ng-click="joinRoom(key)"
								class="list-group-item">{{room.name}} <span
								class='pull-right'><span class="glyphicon glyphicon-user"></span>
									{{room.maker}} <span class="glyphicon glyphicon-time"></span>
									{{room.time}} ({{room.users}}/{{room.maxusers}}
									{{status(room)}})</span>
							</li>
						</ul>
					</div>
					<div class='panel-footer'>
						<!-- Button trigger modal -->
						<div type="button" class="btn btn-primary"
							aria-labelledby="makeroomlabel" data-toggle="modal"
							data-target="#makeroom">방 만들기</div>

						<!-- Modal -->
						<div class="modal fade" id="makeroom" tabindex="-1" role="dialog"
							aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="makeroomlabel">방 만들기</h4>
									</div>
									<div class="modal-body">

										<div class="input-group">
											<span class="input-group-addon">방 제목</span> <input
												type="text" class="form-control" id='roomname'
												placeholder="Room Name">
										</div>

									</div>
									<div class="modal-footer">
										<div class="btn btn-primary" id='makeroombtn'>방 만들기</div>
										<div class="btn btn-default" data-dismiss="modal">닫기</div>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>

			<div class="col-md-5">
				<div class="panel panel-primary">
					<div class="panel-heading" id='roomdata' ng-controller='roomdata'>
						<span class="glyphicon glyphicon-comment"></span> {{room.name}}
					</div>
					<div class="panel-body" id='chat-panel'>
						<ul class="chat" ng-controller='chatword' id='chatword'>
							<li ng-repeat='chat in chats' class="left clearfix">
								<div class="chat-body clearfix">
									<div class="header">
										<strong class="primary-font">{{chat.name}}</strong> <small
											class="pull-right text-muted"> <span
											class="glyphicon glyphicon-time"></span>{{chat.time}}
										</small>
									</div>
									<p>{{chat.chat}}</p>
								</div>
							</li>
						</ul>
					</div>
					<div class="panel-footer">
						<div class="input-group">
							<input id="btn-input" type="text" class="form-control input-sm"
								placeholder="Type your message here..."> <span
								class="input-group-btn">
								<button class="btn btn-warning btn-sm" id="btn-chat">
									Send</button>
							</span>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>


	<%@ include file="/components/_imports.jspf"%>
	<script src="/socket.io/socket.io.js"></script>
	<script src="angularapp.js"></script>
	<script src="socketapp.js"></script>
</body>

</html>