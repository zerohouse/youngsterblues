<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/components/_css.jspf"%>

    <meta charset="utf-8"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="css/main.css"/>
    <script src="/socket.io/socket.io.js"></script>
</head>
<body>

	<%@ include file="/components/_header.jspf"%>
<div id="fb-root"></div>
    <ul id='menu'>
        <li id="conlogin">로그인</li>
        <li id="conusercon">현재 접속자</li>
        <li id="conchat">채팅</li>
        <li id="constatuscon">상태메시지</li>
        <li id="congameContainer">흑과백2</li>
        <li id="congameinfo">게임방법</li>
    </ul>

    <div class='foot' id="login">
        <div class="fb-login-button" data-max-rows="1" data-size="xlarge" data-show-faces="true" data-auto-logout-link="false"></div>
        <br>
        <div class="foottitle"><span id="logintitle">로그인</span><span class="close"></span></div>
        <div id="logincontents">
        <br>
        패이스북으로 로그인해주세요!!<br>로그인해야 사용가능합니다.<br><br>페이스북 아이디는 식별자로 사용됩니다.<br>정보는 저장하지 않습니다.
        </div>
        <div id="gamewin">0</div>
    </div>



    <div class='foot' id="usercon">
            <div class="foottitle">현재 접속자<span class="close"></span></div>
            <ul class="list" id="users" oncontextmenu="return false;"></ul>
    </div>



    <div class='foot' id="chat">
            <div class="foottitle"><span id="chattitle">채팅</span><span class="close"></span></div>
            <ul class="list" id="conversation"></ul>
            <div id="inputarea">
                <input id="data"/>
                <input type="button" id="datasend" value="말하기" class="btn" />
            </div>
    </div>

    <div class='foot' id="statuscon">
            <div class="foottitle">상태메시지<span class="close"></span></div>
            <ul class="list" id="status">
            </ul>
    </div>


<div id="gameContainer"   class="foot">
    <div id="warring"><font size="6"><br></font>흑과백2<br><font size="4">게임을 시작하려면 접속자 목록에서<br> 게임하실분의 이름을 눌러주세요.</font></div>
    <div>
        <span class="close closeicon"></span>
        <ul id="dashboard">
            <li id="playerid">player</li>
            <li id="youWin">0</li>
            <li>Me</li>
            <li id="myWin">0</li>
        </ul>

    </div>
    <ul id="blackwhite">
        <li>흑(0~9)</li>
        <li>백(10~)</li>
    </ul>

    <div id="gameBody">
        <div id="char"></div>
        <ul id="rivalblock" class="block">
            <li>80~99</li>
            <li>60~79</li>
            <li>40~59</li>
            <li>20~39</li>
            <li>0~19</li>
        </ul>
        <ul id="myblock" class="block">
            <li>80~99</li>
            <li>60~79</li>
            <li>40~59</li>
            <li>20~39</li>
            <li>0~19</li>
        </ul>

        <div id="pointWrap">
            <input id="point" placeholder="0" maxlength="2"/>
            <input id="submitpoint" type="button" value="포인트내기" class="btn" disabled="disabled"/>
        </div>
    </div>
</div>

<div class="foot" id='gameinfo'>
    <div class="foottitle">게임방법<span class="close"></span></div>
    <div style="text-align: center;margin-top:40px;">99포인트를 9라운드에 분배하여 대결<br><strong>5라운드를 먼저 가져가는 플레이어가 승리.</strong></div>
    <iframe class="embed-responsive-item" scrolling="no" src="//www.youtube.com/embed/eb5cGFRrmsg" frameborder="0" allowfullscreen="" style="
    width: 100%;
    padding-top:30px;
    height: 420px;
    bottom:0;
    position:absolute;
"></iframe><br>
</div>
	<%@ include file="/components/_imports.jspf"%>
<script src="js/jquery-ui.min.js"></script>
<script src="js/main.js"></script>

</body>
</html>
