$(document).ready(function(){
	var word_total = 0;
	var day_choose = 30;
	var select_portrait="";
	var user_portrait="fa-user";
	var recite_cet4 = -1;
	var recite_cet6 = -1;
	var recite_toefl = -1;
	
    $("p[type='alert']").hide();
    $("p[name='DayAlert']").show();
    
    $(".portrait-choose").mouseenter(function(){
    		var portrait_id = $(this).attr('id')
    		if (portrait_id != select_portrait) {
    			$(this).css('border','solid 1px #fff')
    			$(this).append('<style>div #' + portrait_id + ' .icon:before{color: #fff;}</style>')
    		}
    })
    
    $(".portrait-choose").mouseleave(function(){
    		var portrait_id = $(this).attr('id')
    		if (portrait_id != select_portrait) {
    			$(this).append('<style>div #' + portrait_id + ' .icon:before{color: #888888;}</style>')
    			$(this).css('border','solid 1px #888888')
    		}
    })
    $(".portrait-choose").click(function(){
    		if (select_portrait != "") {
    			$("#" + select_portrait).css('border','solid 1px #888888');
    			$("#" + select_portrait).append('<style>div #' + select_portrait + ' .icon:before{color: #888888;}</style>')
    		}
    		
    		$(this).css('border','solid 2px #fff')
    		var portrait_id = $(this).attr('id')
    		select_portrait = portrait_id
    		$(this).append('<style>div #' + portrait_id + ' .icon:before{color: #fff;}</style>')
    		$('#log > form:nth-child(5) > div > div:nth-child(2) > div.flex.center > div > span').removeClass(user_portrait)
    		user_portrait = $(this).find('.icon').prop("className").split(' ')[1];
    		$('#log > form:nth-child(5) > div > div:nth-child(2) > div.flex.center > div > span').addClass(user_portrait)
    })
    
    $("input[type='checkbox']").change(function(){
    		dic_name = $(this).attr('id');
    		check_value =  $(this).is(':checked');
    		if (check_value == true) check_value = 1
    		else check_value = -1
    		if (dic_name == 'dic-cet4') {
    			word_total = 3596 * check_value + word_total;
    			recite_cet4 = check_value;
    		} else if (dic_name == 'dic-cet6') {
    			word_total = 1000 * check_value + word_total;
    			recite_cet6 = check_value;
    		} else if (dic_name == 'dic-toefl') {
    			word_total = 4680 * check_value + word_total;
    			recite_toefl = check_value;
    		}
    		$("p[name='DayAlert']").text(day_choose + " days. About " + parseInt(word_total * 1.0/day_choose) + " words per day.");
    });
    
    $( "#day-slider" ).slider({
    		orientation: "horizontal",
	    range: "min",
	    max: 200,
	    value: 30,
	    min: 10,
	    slide: refreshSwatch,
	    change: refreshSwatch
	  });
    
    function refreshSwatch() {
    		day_choose = parseInt($( "#day-slider" ).slider( "value" ));
    		$("p[name='DayAlert']").text(day_choose + " days. About " + parseInt(word_total * 1.0/day_choose) + " words per day.");
    		$("p[name='DayAlert']").show();
    }

    var NameFocus = false;
    var EmailFocus = false;
    var PasswordFocus = false;
    var NameIsOk = false;
    var PasswordIsOk = false;
    var EmailIsOk = false;

    $("#LogSubmit").click(function(){
        username = $("#LogName").val();
        password = $("#LogPassword").val();
        $.post("http://localhost:8080/Java_server/log", 
            {
                name: username,
                password: password
            },
            function(data,status,request){
                console.log(data);
                if (data.success == true) {
                    location.hash = '';
                    $("#LogName").val('');
                    $("#LogPassword").val('');
                }
                else {
                    console.log(data.info);
                }
            })
    });

    $("#Register").click(function(){
        if (NameIsOk && PasswordIsOk && EmailIsOk) {
            username = $("#SignName").val();
            email = $("#SignEmail").val();
            password = $("#SignPassword").val();
            $.post("http://localhost:8080/Java_server/sign", 
                {
                    name: username,
                    email: email,
                    password: password,
                    portrait: user_portrait,
                    cet4: recite_cet4,
                    cet6: recite_cet6,
                    toefl: recite_toefl,
                    day: day_choose
                },
                function(data,status){  
                    location.hash = '';
                    word_total = 0;
                    day_choose = 30;
                    recite_toefl = -1;
                    recite_cet6 = -1;
                    recite_cet4 = -1;
                    $("#SignName").val('');
                    $("#SignEmail").val('');
                    $("#SignPassword").val('');
                    $('input[type="checkbox"]').prop('checked',false);
                    var origin_class = $("#header > div.logo > span").prop("className").split(' ')[1];
                    $("#header > div.logo > span").removeClass(origin_class);
                    $("#header > div.logo > span").addClass(user_portrait);
                    $("#header > div.logo").css('width', '5.5rem');
                    $("#header").append("<style>#header .logo .icon:before{font-size: 2.5rem}</style>");
                    
            })
        }
    });


    $("#SignName").focus(function(){
        NameFocus = true;
    });

    $("#SignName").blur(function(){

        if (NameFocus == true) {
            NameFocus = false;
            var username = $("#SignName").val();

            if (username.length < 6) {
                $("p[name='NameAlert']").text("At least 6 characters");
                $("p[name='NameAlert']").show();
                $("#SignName").css("background-color","#c45a54");
                $("#DivName").css("margin-bottom", "1.0rem");
                NameIsOk = true;
            } else {
                $.get("http://localhost:8080/Java_server/sign", {
                    value: username,
                    nametype: "name"
                }, 
                function(data, status) {
                    console.log(data);
                    if (data.Duplicate == 1) {
                        $("p[name='NameAlert']").text("This username has been registered");
                        $("p[name='NameAlert']").show();
                        $("#SignName").css("background-color","#c45a54");
                        $("#DivName").css("margin-bottom", "1.0rem");
                        NameIsOk = true;
                    } else {
                        $("p[name='NameAlert']").hide();
                        $("#SignName").css("background","transparent");
                        $("#DivName").css("margin-bottom", "1.8rem");
                        NameIsOk = true;
                    }
                })
            }
        }
    });

    $("#SignEmail").focus(function(){
        EmailFocus = true;
    });

    $("#SignEmail").blur(function(){
        if (EmailFocus == true) {
            EmailFocus = false;
            var email = $("#SignEmail").val();
            var n = email.search(/@[a-z0-9]*\.[a-z0-9]*/i);
            if (n < 0) {
                $("p[name='EmailAlert']").text("Incorrect email address");
                $("p[name='EmailAlert']").show();
                $("#SignEmail").css("background-color","#c45a54");
                $("#DivEmail").css("margin-bottom", "1.0rem");
                EmailIsOk = false;
            } else {
                $.get("http://localhost:8080/Java_server/sign", {
                    value: email,
                    nametype: "email"
                }, 
                function(data, status) {
                    console.log(data);
                    if (data.Duplicate == 1) {
                        $("p[name='EmailAlert']").text("This email has been registered");
                        $("p[name='EmailAlert']").show();
                        $("#SignEmail").css("background-color","#c45a54");
                        $("#DivEmail").css("margin-bottom", "1.0rem");
                        EmailIsOk = false;
                    } else {
                        $("p[name='EmailAlert']").hide();
                        $("#SignEmail").css("background","transparent");
                        $("#DivEmail").css("margin-bottom", "1.8rem");
                        EmailIsOk = true;
                    }
                })
            }
            
        }
    });

    $("#SignPassword").focus(function(){
        PasswordFocus = true;
    });

    $("#SignPassword").blur(function(){
        if (PasswordFocus == true) {
            PasswordFocus = false;
            var password = $("#SignPassword").val();

            if (password.length < 6 || password.length > 16) {
                $("p[name='PasswordAlert']").show();
                $("#SignPassword").css("background-color","#c45a54")
                $("#DivPassword").css("margin-bottom", "1.0rem")
                PasswordIsOk = false;
            } else {
                $("p[name='PasswordAlert']").hide();
                $("#SignPassword").css("background","transparent")
                $("#DivPassword").css("margin-bottom", "1.8rem")
                PasswordIsOk = true;
            }
        }
    });



});

