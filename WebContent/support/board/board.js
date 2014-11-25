/**
 * 
 */

var app = angular.module('boardapp', []);
var contentview = $('#contentview');

function dataTransfer(obj) {
	var str = [];
	for ( var p in obj)
		str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
	return str.join("&");
}

app.controller('board', [ '$scope', '$http', function($scope, $http) {

	$http({
		method : 'POST',
		url : '/contents/getcontents/',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		transformRequest : dataTransfer,
		data : {
			type : type
		}
	}).success(function(result) {
		$scope.contents = result;
	});

	$scope.contents = [];
	$scope.content = {};
	$scope.addcontent = {};

	$scope.getContent = function(id) {
		$http({
			method : 'POST',
			url : '/contents/getcontent/',
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			transformRequest : dataTransfer,
			data : {
				id : id
			}
		}).success(function(data) {
			if (data == 'null') {
				alert('글이 없네요!?');
				return;
			}
			$scope.content = data;
			console.log(data);
			contentview.modal('show');
		});
	}

	$scope.addContent = function() {
		$http({
			method : 'POST',
			url : '/contents/addcontent/',
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			transformRequest : dataTransfer,
			data : {
				id : $('#userId').val(),
				content : $scope.addcontent.content,
				head : $scope.addcontent.head,
				type : type
			}
		}).success(function(data) {
			if (data.state)
				location.reload();
		});
	}

	$scope.isMine = function(id) {
		return id == $('#userId').val();
	}

	$scope.deleteContent = function(contentid) {
		if (!confirm('삭제하시겠습니까?'))
			return;
		$http({
			method : 'POST',
			url : '/contents/deletecontent/',
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			transformRequest : dataTransfer,
			data : {
				id : $('#userId').val(),
				content : contentid
			}
		}).success(function(data) {
			if (data.state) {
				location.reload();
			}
		});
	}

} ]);