$(document).ready(function () {

    $('.back').hide();
    $('.front').hide();
    $('.word-tooltip').hide();
    $('.refresh').hide();
    $('.arrow').hide();
    $('.word-container').hide();

    var word = new Array();
    $('.start-recite').click(function(){
        $('.front').show();
        $('.refresh').show();
        $('.arrow').show();
        $('.word-container').show();    
        $('.recite-goal-today').hide();
        word.push("familiar");

        $.getJSON("http://api.douban.com/book/subject/1220562?alt=xd&callback=?", function(movie){  
            var title = movie.title['$t'];  
            var author = movie.author;  
            var summary = movie.summary['$t'];  
            alert(title+'   '+author[0].name['$t']+'     '+author[1].name['$t']+'     '+summary);  
       }); 

    })

    $('.fa-play-circle').click(function(){
        document.getElementById("audio").play();
        console.log("111")
    })

    
    

    //翻转单词卡
    var isFront = true;
    $('.refresh').click(function(){
        $('.card').toggleClass('flipped');
        if (isFront) {
            $('.front').hide();
            $('.back').show();
            isFront = !isFront;
        } else {
            $('.front').show();
            $('.back').hide();
            isFront = !isFront;
        }
    });

    $('#show-translate').click(function(){
        if ($('.word-tooltip').is(':hidden'))
            $('.word-tooltip').show();
        else 
            $('.word-tooltip').hide();
    })


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
});

