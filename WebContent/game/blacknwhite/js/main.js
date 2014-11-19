



    $('.close').each(function(){
        $(this).click(function(){
            var parent = $(this).parent().parent();
            parent.hide(500);
            $('#con'+parent.attr('id')).show(500);
        });
    });

    $('#menu li').each(function(){
        $(this).click(function(){
            $(this).hide(500);
            $('#'+$(this).attr('id').substring(3)).show(500);
        });
    });

    var usedpoints = [];




    var socket;
    var game;
    function challenge(name, key){
        if(confirm(name+"님께 게임 요청을 보낼까요?"))
            socket.emit('challenge', key);
    }
    

function login(id, name){

    status('로그인 되었습니다.');
    status('현재 접속자 창에서 다른 사용자의 이름을 눌러 대결을 신청해 보세요.');

    socket = io.connect('http://54.65.20.191:8000');
    game = {usablePoint : 99, round:1, rival: "", myid: id, ing:false};

    $('#logintitle').text(name);
    $('#logincontents').empty();
    $('#gamewin').show();

    var warringhtml = "<font size='6'><br></font>흑과백2<br><font size='4'>게임을 시작하려면 접속자 목록에서<br> 게임하실분의 이름을 눌러주세요.</font>";

    socket.on('gamewinupdate', function(val){
        $('#gamewin').text(val);
    });

    socket.on('connect', function(){
        socket.emit('adduser', game.myid, name);
    });

    socket.on('updatechat', function (username, data, whisper) {
        if(game.ing && !whisper)
            return;
        var con = $('#conversation');
        con.append('<li>'+username + ': ' + data + '</li>');
        con.scrollTop(1000000);
    });

    socket.on('out', function () {
            setTimeout(function(){
                    $('#warring').html(warringhtml).show();}, 4000
            );
            game.ing = false;
            status('상대가 나갔습니다.');
            $('#usercon').show(500);
            $('#chattitle').text("채팅");
            $('#conversation').empty();
            $('#conversation').append("<li>붕대맨 : 게임이 끝났습니다. 새로운 상대를 선택해 보세요.</li>");
    });

    socket.on('updateusers', function(data) {
        $('#users').empty();
        $.each(data, function(key, value) {
            if(key == game.myid) {
                $('#users').append("<li style='cursor:auto;color:darkred'>" + value.name + ' ( 나 )</li>');
                return;
            }
            if(value.ing) {
                $('#users').append("<li style='cursor:auto;color:gray' data-id="+ key + ">" + value.name + ' ( 게임중 )</li>');
                return;
            }

            $('#users').prepend("<li onclick=\"challenge('"+value.name+"','"+ key +"')\" data-id="+ key + ">" + value.name + '</li>');

        });
    });

    socket.on('challenge', function (username, Id) {
        if(confirm(username + "님이 도전하셨습니다. 게임을 시작할까요?")){
            socket.emit('gameStart', Id);
        }
        else{
            socket.emit('decline', Id);
        }
    });

    socket.on('yourusedpoints', function(othersusedpoints){

        status(serialize(usedpoints, othersusedpoints));

        function serialize(me, you){
            var result = "이번 게임에서 사용한 포인트<br>";
            for(var i=0; i<me.length;i++){
                if(parseInt(me[i])<10)
                    me[i] = "&nbsp;" + me[i];
                if(parseInt(you[i])<10)
                    you[i] = "&nbsp;" + you[i];
                result += (i+1) + "라운드 [" + " 나 : " + me[i] +", 상대 : " + you[i] + " ]<br>";
            }
            return result;
        }

    });

    socket.on('winner', function(){
        socket.emit('usedpoints', usedpoints);
        warring('게임 승리!!');
        winplus();
        status('게임에서 승리하였습니다.<br>게임이 종료되었습니다.');
        game.ing = false;
        setTimeout(function(){$('#warring').html(warringhtml).show();}, 4000
        );
        $('#usercon').show(500);
        $('#chattitle').text("채팅");
        $('#conversation').empty();
        $('#conversation').append("<li>붕대맨 : 게임이 끝났습니다. 새로운 상대를 선택해 보세요.</li>");

        function winplus(){
            var gamewin = $('#gamewin');
            var win = parseInt(gamewin.text());
            win++;
            gamewin.text(win);
        }
    });

    socket.on('loser', function(){
        socket.emit('usedpoints', usedpoints);
        warring('게임 패배ㅠㅠ');
        status('게임에서 패배하였습니다.<br>게임이 종료되었습니다.');
        game.ing = false;
        setTimeout(function(){$('#warring').html(warringhtml).show();}, 4000
        );
        $('#usercon').show(500);
        $('#chattitle').text("채팅");
        $('#conversation').empty();
        $('#conversation').append("<li>붕대맨 : 게임이 끝났습니다. 새로운 상대를 선택해 보세요.</li>");

    });


    socket.on('start', function (type, name, id) {
                usedpoints = [];
                $('#usercon').hide(500);
                $('#chattitle').text(name+"님과의 채팅");
                $('#conversation').empty();

                phaseAdopt(5,5);
                game.usablePoint = 99;
                game.round =1;
                game.rival ={name:name,id:id};
                game.ing = true;
                $('#point').attr('placeholder', game.usablePoint);

                status(name+"님 과의 게임이 시작되었습니다.<br>");
                $('#playerid').text(name);

                roundUpdate();

                if(type){
                    status("당신은 선 플레이어 입니다.<br> 사용할 포인트를 입력해주세요.");
                    $('#submitpoint').removeAttr('disabled');
                    $('#point').css('border','3px solid red');
                   return;
                }
        status("상대가 포인트를 입력하고 있습니다.");
    });

    socket.on('updateGame', function (mydata, rivaldata) {

        game.usablePoint = mydata.point;
        $('#myWin').text(mydata.win);
        $('#youWin').text(rivaldata.win);
        phaseAdopt(mydata.phase, rivaldata.phase);

    });


    socket.on('turnOver', function (isWin) {
        if(isWin){
            status((game.round-1)+'라운드에서 승리하여 승점을 획득하였습니다.');
            $('#myWin').text(parseInt($('#myWin').text())+1);
            warring((game.round-1)+'라운드 승리!');
            roundUpdate();
            return;
        }
        $('#youWin').text(parseInt($('#youWin').text())+1);
        status('이번 라운드에서 승점을 얻지 못했습니다.');
        warring((game.round-1)+'라운드 패배!');


        roundUpdate();
    });

    socket.on('rivaldoing', function () {
        status('상대가 포인트를 입력하고 있습니다.');
    });

    socket.on('doTurn', function (isFirst) {
        $('#submitpoint').removeAttr('disabled');
        $('#point').css('border','3px solid red');
        if(isFirst){
            status("당신은 선 플레이어 입니다.<br>사용할 포인트를 입력해주세요.");
            return;
        }
        status("사용할 포인트를 입력해주세요.");
    });

    socket.on('blackwhite', function (type) {
        if(type=='white'){
            warring('백');
            status('상대방이 백을 제시했습니다.');
            $('#blackwhite li:eq(0)').css('opacity', 0.1);
            $('#blackwhite li:eq(1)').css('opacity', 1);
            return;
        }
        $('#blackwhite li:eq(0)').css('opacity', 1);
        $('#blackwhite li:eq(1)').css('opacity', 0.1);
        warring('흑');
        status('상대방이 흑을 제시했습니다.');
    });

    socket.on('decline', function (username) {
        status(username + "님이 도전을 거절하셨습니다.")
    });

    $(function(){

        $('#datasend').click( function() {
            var message = $('#data').val();
            if(message=="")
                return;
            $('#data').val('');
            $('#data').focus();
            socket.emit('sendchat', message);
        });

        $('#data').keypress(function(e) {
            if(e.which == 13) {
                $(this).blur();
                $('#datasend').focus().click();
            }
        });

        $('#point').keypress(function(e) {
            if(e.which == 13) {
                $('#submitpoint').focus().click();
            }
        });

        $('#submitpoint').click(function (){
            var point = $('#point').val();

            if (point =="")
                return;

            if( 0 <= point  && point <= game.usablePoint ){
                socket.emit('submitpoint', point);
                usedpoints.push(point);
                $('#submitpoint').attr('disabled', '');
                $('#point').css('border','none');
                $('#point').val('');
                game.usablePoint -= point;
                $('#point').attr('placeholder', game.usablePoint);
                status(point+' 포인트를 냈습니다.');
                warring(point + '포인트 제출');

               return;
            }
            status("Err! : 0~"+game.usablePoint+" 사이의 포인트를 입력해주세요.")
            $('#point').val('');
            $('#point').focus();

        });

    });

    function status(message){
        var status =  $('#status');
        $('#status li').css('opacity', 0.3);
        $('#status li:last-child').css('opacity', 0.7);
        status.append('<li>'+message+'</li>');
        status.scrollTop(1000000);
    }

    function phaseAdopt(myphase, rivalphase){
        phase(rivalphase,'#rivalblock');
        phase(myphase, '#myblock');

        function phase(phase, domId){
            $(domId+' li').css('opacity',1);
            for (var i=0;i<5-phase;i++){
                $(domId+' li:eq('+i+')').css('opacity',0.2);
            }
        }

    }

    function warring(message){
        var warring = $('#warring');
        if(warring.css('display')!='none'){
            warring.html(warring.html()+"<br>"+message);
            }else{
             warring.html(message);
            }
        warring.show();
        setTimeout(function(){
            warring.hide(500);
        }, 2000);

    }

    function roundUpdate(){
        warring("라운드"+ game.round + "시작");
        status("라운드"+ game.round +" 이 시작되었습니다.");
        game.round++;
    }

}


