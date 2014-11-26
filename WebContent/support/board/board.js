/**
 * 
 */

var app = angular.module('boardapp', []);
var contentview = $('#contentview');

app.controller('board', [
		'$scope',
		'$http',
		function($scope, $http) {
			
			$scope.getContents = function() {
				$.ajax({
					type : 'POST',
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					},
					url : '/contents/getcontents/',
					dataType : "json",
					data : {
						type : type
					}
				}).done(function(result) {
					console.log(result);
					$scope.contents = result;
					$scope.$apply();
				});
			}
			
			$scope.getContents();
			$scope.content = {};
			$scope.addcontent = {};

			$scope.link = function() {
				return 'http://youngsterblues.com/board/' + type + '/'
						+ $scope.content.id;
			}

			$scope.modContentSubmit = function() {
				$.ajax({
					type : 'POST',
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					},
					url : '/contents/modcontent/',
					dataType : "json",
					data : {
						id : $scope.content.id,
						head : $scope.content.head,
						content : $scope.content.content.replace(/\r\n|\r|\n/g,"$#@!")
					}
				}).done(function(result) {
					if (result.state) {
						$('#modarticle').modal('hide');
						contentview.modal('show');
						$scope.getContents();
					}
				});

			}

			$scope.modContent = function() {
				$('#modarticle').modal('show');
				contentview.modal('hide');
			}

			$scope.getContent = function(id) {
				$.ajax({
					type : 'POST',
					url : '/contents/getcontent/',
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					},
					dataType : "json",
					data : {
						id : id
					}
				}).done(function(data) {
					if (data == 'null') {
						alert('글이 없네요!?');
						return;
					}
					$scope.content = data;
					$scope.$apply();
					console.log(data);
					contentview.modal('show');
				});
			}

			$scope.addContent = function() {
				$.ajax({
					type : 'POST',
					url : '/contents/addcontent/',
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					},
					dataType : "json",
					data : {
						id : $('#userId').val(),
						content : $scope.addcontent.content.replace(/\r\n|\r|\n/g,"$#@!"),
						head : $scope.addcontent.head,
						type : type
					}
				}).done(function(data) {
					if (data.state)
						location.reload();
				});
			}

			$scope.isMine = function(id) {
				return id == $('#userId').val();
			}

			$scope.deleteContent = function() {
				if (!confirm('삭제하시겠습니까?'))
					return;
				$.ajax({
					type : 'POST',
					url : '/contents/deletecontent/',
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					},
					dataType : "json",
					data : {
						id : $('#userId').val(),
						content : $scope.content.id
					}
				}).done(function(data) {
					if (data.state) {
						contentview.modal('hide');
						$scope.getContents();
					}
				});
			}

		} ]);