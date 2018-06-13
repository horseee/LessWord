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
});
