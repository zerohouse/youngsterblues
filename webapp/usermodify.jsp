<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>젊음 - Blues</title>
<c:if test="${empty sessionScope.user}">
	<c:redirect url="signup.jsp" />
</c:if>
<%@ include file="/components/_css.jspf"%>
</head>
<body>

	<%@ include file="/components/_header.jspf"%>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="text-center">
					<h1>회원정보변경</h1>
					<form>
						<div class="col-md-6 col-md-offset-3">
							<div id='alertMessage' style="display: none"
								class="alert alert-danger" role="alert"></div>
							<div class="input-group input-group-lg">
								<span class="input-group-addon">UserID</span><input id='signid'
									type="text" class="form-control" placeholder="UserID"
									maxlength="12" data-placement="bottom"
									value="${sessionScope.user.id}" disabled>
							</div>

							<div class="input-group input-group-lg">
								<span class="input-group-addon">Password</span> <input
									type="password" id='signorigin' class="form-control"
									placeholder="Password" maxlength="20" data-placement="bottom">
							</div>

							<div class="input-group input-group-lg">
								<span class="input-group-addon">new Password</span> <input
									type="password" id='signpassword' class="form-control"
									placeholder="Password" maxlength="20" data-placement="bottom">
								<input id='signpasswordconfirm' type="password"
									class="form-control" placeholder="Password Confirm"
									maxlength="20" data-placement="bottom">
							</div>
							<div class="input-group input-group-lg">
								<span class="input-group-addon">Name</span><input id='signname'
									type="text" class="form-control" placeholder="UserName"
									maxlength="30" data-placement="bottom">
							</div>
							<div id='signupbtn' class="btn btn-info btn-lg">정보변경</div>

						</div>
					</form>
				</div>
			</div>
		</div>
		<%@ include file="/components/_footer.jspf"%>
	</div>

	<%@ include file="/components/_imports.jspf"%>
	<script src="/support/update/update.js"></script>
</body>
</html>