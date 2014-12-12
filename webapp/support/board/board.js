/**
 * 
 */

var app = angular.module('boardapp', [ 'ngSanitize' ]);
var contentview = $('#contentview');
var loading = $('.loading');
app
		.controller(
				'board',
				[
						'$scope',
						'$http',
						'$sce',
						function($scope, $http, $sce) {
							$scope.getContents = function() {
								loading.fadeIn(500);
								$
										.ajax(
												{
													type : 'POST',
													headers : {
														'Content-Type' : 'application/x-www-form-urlencoded'
													},
													url : '/contents/getcontents/',
													dataType : "json",
													data : {
														type : type,
														page : page
													}
												}).done(function(result) {

											$scope.setPage(result[0]);
											$scope.contents = result[1];
											$scope.$apply();
											loading.fadeOut(500);
										});
							}
							$scope.setPage = function(count) {
								$scope.paginations = [];

								$scope.paginations
										.push({
											page : "|<",
											link : page == 1 ? "#"
													: "http://youngsterblues.com/board/"
															+ type
															+ "/page/"
															+ 1,
											disabled : page == 1
										});
								$scope.paginations
										.push({
											page : "<",
											link : page == 1 ? "#"
													: "http://youngsterblues.com/board/"
															+ type
															+ "/page/"
															+ (page - 1),
											disabled : page == 1
										});
								var contentsinonepage = 10;
								var max = parseInt(count / contentsinonepage) + 1;
								var startpage = parseInt((page - 1) / 5) * 5 + 1
								var j = startpage;

								for (var i = 0; i < 5; i++) {
									$scope.paginations
											.push({
												page : j,
												link : "http://youngsterblues.com/board/"
														+ type + "/page/" + j,
												getClass : page == j
											});
									if (j >= max)
										break;
									j++;
								}

								$scope.paginations
										.push({
											page : ">",
											link : page == j ? "#"
													: "http://youngsterblues.com/board/"
															+ type
															+ "/page/"
															+ (parseInt(page) + 1),
											disabled : page == (parseInt(count
													/ contentsinonepage) + 1)
										});

								$scope.paginations
										.push({
											page : ">|",
											link : page == j ? "#"
													: "http://youngsterblues.com/board/"
															+ type
															+ "/page/"
															+ (parseInt(count
																	/ contentsinonepage) + 1),
											disabled : page == (parseInt(count
													/ contentsinonepage) + 1)
										});

							};
							$scope.getContents();
							$scope.content = {};
							$scope.addcontent = {};

							$scope.link = function() {
								return 'http://youngsterblues.com/board/'
										+ type + '/' + $scope.content.id;
							}

							$scope.modContentSubmit = function() {
								$
										.ajax(
												{
													type : 'POST',
													headers : {
														'Content-Type' : 'application/x-www-form-urlencoded'
													},
													url : '/contents/modcontent/',
													dataType : "json",
													data : {
														id : $scope.content.id,
														head : $scope.content.head,
														content : $(
																'#modContent')
																.val()
																.replace(
																		/\r\n|\r|\n/g,
																		"BrAkELInE")
													}
												}).done(function(result) {
											if (result.success) {
												location.href = $scope.link();
											}
										});

							}

							$scope.modContent = function() {
								$('#modarticle').modal('show');
								contentview.modal('hide');
								$('#modContent').val(
										$scope.content.content.replace(/<br>/g,
												"\n"));
							}

							$scope.getContent = function(id) {
								loading.fadeIn(500);
								$
										.ajax(
												{
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
											loading.fadeOut(500);
										});
							}

							$scope.addContent = function() {
								$
										.ajax(
												{
													type : 'POST',
													url : '/contents/addcontent/',
													headers : {
														'Content-Type' : 'application/x-www-form-urlencoded'
													},
													dataType : "json",
													data : {
														id : $('#userId').val(),
														content : $scope.addcontent.content
																.replace(
																		/\r\n|\r|\n/g,
																		"BrAkELInE"),
														head : $scope.addcontent.head,
														type : type
													}
												}).done(function(data) {
											if (data.success)
												location.reload();
										});
							}

							$scope.isMine = function(id) {
								return id == $('#userId').val();
							}

							$scope.deleteContent = function() {
								if (!confirm('삭제하시겠습니까?'))
									return;
								$
										.ajax(
												{
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
											if (data.success) {
												contentview.modal('hide');
												$scope.getContents();
											}
										});
							}

						} ]);