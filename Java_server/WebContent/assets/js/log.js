$(document).ready(function(){
    $("#LogSubmit").click(function(){
        username = $("#LogName").val();
        password = $("#LogPassword").val();
        $.post("http://localhost:8080/Java_server/log", 
        	{
        		name: username,
        		password: password
        	},
        	function(data,status){
        		alert("success");
    		})
    });

    $("#Register").click(function(){
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
                alert("success");
            })
    });

    var NameFocus = false;
    var EmailFocus = false;
    $("#SignName").focus(function(){
        NameFocus = true;
    });

    $("#SignName").blur(function(){
        if (NameFocus == true) {
            NameFocus = false;
            username = $("#SignName").val();
            $.get("http://localhost:8080/Java_server/sign", {
                value: username,
                nametype: "name"
            }, 
            function(data, status) {
                console.log(data);
            })
        }
    });

    $("#SignEmail").focus(function(){
        EmailFocus = true;
    });

    $("#SignEmail").blur(function(){
        if (EmailFocus == true) {
            EmailFocus = false;
            email = $("#SignEmail").val();
            $.get("http://localhost:8080/Java_server/sign", {
                value: email,
                nametype: "email"
            }, 
            function(data, status) {
                console.log(data);
            })
        }
    });
});

