/**
 * 
 */
$(document).ready(function() {
	$('textarea').autosize();
});
var app = angular.module('board', []);
var contentview = $('#contentview');
function dataTransfer(obj) {
	var str = [];
	for ( var p in obj)
		str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
	return str.join("&");
}

app.controller('free', [ '$scope', '$http', function($scope, $http) {

	$http({
		method : 'POST',
		url : '/contents/getcontents/',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		transformRequest : dataTransfer,
		data : {
			type : 'free'
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
				type : 'free'
			}
		}).success(function(data) {
			if (data.state)
				location.reload();
		});
	}

} ]);