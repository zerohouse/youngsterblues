<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>젊은 날의 부르스 - YoungsterBlues</title>
<%@ include file="/components/_css.jspf"%>
</head>
<body>

	<%@ include file="/components/_header.jspf"%>
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<div class="container">
			<h1>안녕하세요!</h1>
			<p>
				지니어스게임 보다가 캐답답해서 내가 해보려고 만든 사이트입니다만..? <br>로그인해야 다른데 가볼 수 있다.
				정보는 최~소만 받고 있으니 가입해도 별 문제 없을 듯?!
			</p>
			<p>
				<a class="btn btn-primary btn-lg">더보기 버튼이지만 눌러지지 않는다. &raquo;</a>
			</p>
		</div>
	</div>

	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<div class="col-lg-4">
				<h2>동물장기</h2>
				<p>동물장기는 일본의 12장기를 변형한 보드게임으로, 지니어스게임 시즌3 7화에서 십이장기라는 명칭으로 데스매치에
					사용되었다. 경기결과 이종범이 종범되었다.</p>
				<p>
					<a class="btn btn-default" href="/game/animalchess/">게임하기
						&raquo;</a>
				</p>
			</div>
			<div class="col-lg-4">
				<h2>흑과 백2</h2>
				<p>100포인트를 나누어 쓰는 게임, 9라운드에 걸쳐 진행되며 5승을 먼저 챙긴 플레이어가 이긴다. 선공과 후공이
					동점이면 선공이 이긴다.</p>
				<p>
					<a class="btn btn-default" href="#">게임하기 &raquo;</a>
				</p>
			</div>
			<div class="col-lg-4">
				<h2>자유게시판</h2>
				<p>게시판 정도는 있어야 되지 않을까 싶어서 만든, 게시판. 글을 쓸수는 있지만 지울수도 없고 수정할 수도 없다.
					현재 개발중.</p>
				<p>
					<a class="btn btn-default" href="/board/free/">가보기 &raquo;</a>
				</p>
			</div>
		</div>

		<hr>

		<footer>
			<p>&copy; 박成호 @NHNnext 2014</p>
		</footer>
	</div>
	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<%@ include file="/components/_imports.jspf"%>
</body>
</html>