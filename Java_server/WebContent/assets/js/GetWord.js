$(document).ready(function() {

	$("#start-recite").click(function(){
        name = "maxinyin";
        wordnumber = 10;
        wordset = 1;
        $.get("http://localhost:8080/Java_server/GetTodayWord", 
            {
                name: name,
                wordnumber: 10,
                wordset: 1
            },
            function(data,status,request){
                console.log(data);
                wordlist = data.word;
                console.log(wordlist.length)
                for (var i=1; i<=wordlist.length; i++) {
                    $("#task > div.word-container").append("\
                    <div class=\"thumb\">\
                        <div class=\"cube\"></div>\
                        <div class=\"cube alt\"></div>\
                        <div class=\"cube text\">\
                            <strong class=\"uncite\">" + i + "</strong>\
                        </div>\
                    </div>")
                }
                $('.thumb').on('mouseenter', function (event) {
                    $(this).find('.alt').css(_getDir($(this), event)).animate({
                        top: 0,
                        left: 0
                    }, 300)
                    var color = $(this).find('strong').css("color")
                    if (color == "rgb(136, 136, 136)")
                        $(this).find('strong').css("color", "#666666")
                    else
                        $(this).find('strong').css("color", "#344444")
                });

                $('.thumb').on('mouseleave', function (event) {
                    $(this).find('.alt').animate(_getDir($(this), event), 100);
                    var color = $(this).find('strong').css("color")
                    if (color == "rgb(52, 68, 68)")
                        $(this).find('strong').css("color", "#fff")
                    else
                        $(this).find('strong').css("color", "#888")
                });
            })
    });

    function _getDir($el, event) {
        var w = $el.width(),
            h = $el.height(),
            cx = $el.offset().left + w / 2,
            cy = $el.offset().top + h / 2,
            x = event.pageX - cx * (w > h ? (h / w) : 1),
            y = -(event.pageY - cy) * (h > w ? (w / h) : 1),
            direction = Math.round(((Math.atan2(x, y) + Math.PI) / (Math.PI / 2)) + 2) % 4;

        var directions = {
            0: {
                left: 0,
                top: -h
            },
            1: {
                left: w,
                top: 0
            },
            2: {
                left: 0,
                top: h
            },
            3: {
                left: -w,
                top: 0
            }
        }
        return directions[direction];
    }

})