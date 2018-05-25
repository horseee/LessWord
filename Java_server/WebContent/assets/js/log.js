$(document).ready(function(){
    $("p[type='alert']").hide();

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
                    password: password
                },
                function(data,status){  
                    location.hash = '';
                    $("#SignName").val('');
                    $("#SignEmail").val('');
                    $("#SignPassword").val('');
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

