<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Youngster Blues - 자유게시판</title>
<%@ include file="/components/_css.jspf"%>
<link rel='stylesheet' href='/support/board/board.css'>
</head>
<body ng-app='boardapp'>
	<%@ include file="/components/_header.jspf"%>

	<div class='jumbotron'>
		<div class="container">

			<h2>
				<c:choose>
					<c:when test="${type=='free'}">자유게시판</c:when>
					<c:when test="${type=='bugreport'}">버그제보!!</c:when>
					<c:otherwise>
						<c:redirect url="/" />
					</c:otherwise>
				</c:choose>


			</h2>
		</div>
	</div>

	<div class='container' id='board' ng-controller="board">

		<div class='row'>

			<div class='col-md-12'>
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th class='col-md-3'>작성자</th>
							<th class='col-md-6'>제목</th>
							<th class='col-md-3'>작성일</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="content in contents"
							ng-click="getContent(content.id)">
							<td>{{content.id}}</td>
							<td class='col-md-3'>{{content.userId}}</td>
							<td class='col-md-6'>{{content.head}}</td>
							<td class='col-md-3'>{{content.datetime}}</td>
						</tr>
					</tbody>
				</table>


			</div>

		</div>

		<div class='row'>
			<div class="align-center">
				<ul class="pagination center">
					<li ng-repeat="pagination in paginations"
						ng-class="{active:pagination.getClass, disabled:pagination.disabled}"><a
						href="{{pagination.link}}">{{pagination.page}}</a></li>
				</ul>
			</div>
		</div>
		<div class='row'>
			<div class='col-md-12'>
				<button class='btn btn-primary btn-lg' data-toggle="modal"
					data-target="#writearticle">글쓰기</button>
			</div>

		</div>


		<!-- Modal 글본문 -->
		<div class="modal fade" id="contentview" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">{{content.head}}</h4>
						<h5>
							<span class='glyphicon glyphicon-user'></span> {{content.userId}}
							<span class='glyphicon glyphicon-time'></span>
							{{content.datetime}}
						</h5>
						<h6>
							<span class='glyphicon glyphicon-share'></span> <a
								href="{{link()}}">{{link()}}</a>
						</h6>
					</div>
					<div class="modal-body">

						<p ng-bind-html="content.content"></p>
					</div>
					<div class="modal-footer">
						<div ng-show="!reply.show" ng-click="getReplies()">댓글보기</div>
						<ul class="reply" ng-show="reply.show">
							<li ng-repeat="reply in reply.replies"><span>{{reply.userId}}</span><span>{{reply.reply}}</span><small>{{reply.timestamp}}</small></li>
							<li ng-show="reply.isWrited()"><span>{{reply.new.author}}</span><span>{{reply.new.reply}}</span></li>

							<div>
								<span>{{reply.new.author}}</span>
								<input type='text' ng-model="reply.new.reply" />
								<span class='btn btn-success' ng-click="writeReply()">댓글쓰기</span>
							</div>

						</ul>



						<div class="btn btn-primary" ng-show="isMine(content.userId)"
							ng-click="deleteContent()">삭제</div>
						<div class="btn btn-success" ng-show="isMine(content.userId)"
							ng-click='modContent()'>수정</div>

						<div class="btn btn-default" data-dismiss="modal">닫기</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Modal 글쓰기  -->
		<div class="modal fade" id="writearticle" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h2 class="modal-title">
							<input type='text' class="form-control" placeholder='제목'
								ng-model="addcontent.head" />
						</h2>
						<h5>
							<span class='glyphicon glyphicon-user'></span>
							<c:out value="${sessionScope.user.id}" />
						</h5>
					</div>
					<div class="modal-body">
						<textarea class="form-control" placeholder='내용'
							ng-model="addcontent.content"></textarea>
					</div>
					<div class="modal-footer">
						<div class="btn btn-primary" ng-click='addContent()'>글쓰기</div>
						<div class="btn btn-default" data-dismiss="modal">닫기</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Modal 수정  -->
		<div class="modal fade" id="modarticle" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							<input type='text' class="form-control" placeholder='제목'
								ng-model="content.head" value="{{content.head}}" />
						</h4>
						<h5>
							<span class='glyphicon glyphicon-user'></span>
							<c:out value="${sessionScope.user.id}" />
						</h5>
					</div>
					<div class="modal-body">
						<textarea class="form-control" placeholder='내용' id='modContent'></textarea>
					</div>
					<div class="modal-footer">
						<div class="btn btn-primary" ng-click='modContentSubmit()'>수정하기</div>
						<div class="btn btn-default" data-dismiss="modal">닫기</div>
					</div>
				</div>
			</div>
		</div>



		<%@ include file="/components/_footer.jspf"%>
	</div>
	<%@ include file="/components/_imports.jspf"%>
	<script src="/plugin/angular/angular-sanitize.min.js"></script>
	<script src='/plugin/jquery/jquery.autosize.min.js'></script>
	<script>
		var type = '${type}';
		var content = '${content}';
		var page = '${page}';
		var url = '${url}'
		if (page == '')
			page = 1;
		$(document).ready(function() {
			$('textarea').autosize();
			if (content == '')
				return;
			if (content == 'page') {
				return;
			}
			angular.element($('#board')).scope().getContent(content);

		});
	</script>
	<script src='/support/board/board.js'></script>
</body>

</html>