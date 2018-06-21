$(document).ready(function () {
    $('.back').hide();
    $('.front').hide();
    $('.word-tooltip').hide();
    $('.refresh').hide();
    $('.arrow').hide();
    $('.word-container').hide();

    var wordlist;
    var wordptr = 1;
    var originPtrColor = 'rgba(255, 255, 255, 0.075)';

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
                wordlist = data.wordlist;
                console.log(wordlist.length)
                for (var i=1; i<=wordlist.length; i++) {
                    $("#task > div.word-container").append("\
                    <div class=\"thumb\" id = \"thumb-" + i + "\" >\
                        <div class=\"cube back\"></div>\
                        <div class=\"cube alt\"></div>\
                        <div class=\"cube text\">\
                            <strong class=\"uncite\" id=\"uncite-" + (i) + "\">" + i + "</strong>\
                        </div>\
                    </div>")
                }
                
                $('.thumb').click(function(){
                		
                		$('#thumb-' + wordptr).find('.back').css('background-color', originPtrColor);
                		wordptr = parseInt($(this).find('strong').text());
                		setWordContent();
                		if (isFront == false) {
                			$('.card').toggleClass('flipped');
                    		$('.front').show();
                    		$('.back').hide();
                    		$('.thumb').find('.back').css('display', '');
                    		isFront = true;
                		}
                		originPtrColor = $(this).find('.back').css('background-color');
                		$(this).find('.back').css('background-color', 'rgb(220, 205, 203)');
                		
                		//$(this).find('.back').css('background-color', 'rgba(0, 0, 0, 0.2)');
                		
                })
                
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
                        $(this).find('strong').css("color", "#ffffff")
                    else
                        $(this).find('strong').css("color", "#888888")
                });

                $('.front').show();
                $('.refresh').show();
                $('.arrow').show();
                $('.word-container').show();    
                $('.recite-goal-today').hide();
                setWordContent()
                $('.back').hide();
                $('.thumb').find('.back').css('display', '');
                $('#thumb-1').find('.back').css('background-color', 'rgb(220, 205, 203)');
            })
    });

    function setWordContent() {
        word_detail_info = wordlist[wordptr-1]
        $('.word-literal').text(word_detail_info.word)
        $('.word-pron').text('[' + word_detail_info.pron + ']')
        $('.word-example-eng').text(word_detail_info.sample_english)
        $('#task > div.slide-container > div.card > div.front > div.word-detail > div.word-tooltip').text(word_detail_info.sample_chinese)
        wordmeaning = word_detail_info.definition
        $('.word-trans').remove();
        for (var i=0; i<wordmeaning.length; i++){
            if (wordmeaning[i].meaning == "")
                continue
            $('div.back > div.word-detail').append("<div class=\"word-trans\">" +
                    wordmeaning[i].form + " "+ wordmeaning[i].meaning +
                                  "</div>")
        }
        $('.word-input').remove();
        if (word_detail_info.word.length <= 6) {
            for (var i=0; i<word_detail_info.word.length; i++)
                $('#input_box_default').append("<input class=\"word-input\" id = \"word-input-" + i + "\" maxlength=\"1\"> </input>")
        } else {
            for (var i=0; i<6; i++)
                $('#input_box_default').append("<input class=\"word-input\" id = \"word-input-" + i + "\" maxlength=\"1\"> </input>")
            for (var i=6; i<word_detail_info.word.length; i++){
                $('#input_box_spare').append("<input class=\"word-input\" id = \"word-input-" + i + "\" maxlength=\"1\"> </input>") 
                $('#word-input-'+i).css('width', '14%');
                $('#input_box_spare').css('justify-content', 'center');
                $('#input_box_default').css('padding-bottom', '0.5rem');
                $('#input_box_spare').css('padding-bottom', '1.5rem');
            }
        }
        $('#audio').attr("src", word_detail_info.pronlink)
        
        $(".word-input").keydown(function(event){
        		var input_box_id = parseInt($(this).attr("id").split('-')[2])
        		event.preventDefault();
        		var origin_letter = $('#word-input-' + (input_box_id+1)).val();
        		if (event.which >= 65 &&  event.which <= 90) {
        			$('#word-input-' +input_box_id).val(event.key);
            		$('#word-input-' + (input_box_id)).blur();
            		$('#word-input-' + (input_box_id+1)).focus();
            		$('#word-input-' + (input_box_id+1)).val(origin_letter);
        		} else if (event.which == 8) {
        			$('#word-input-' + (input_box_id-1)).focus();
        		} else {
        			$('#word-input-' +input_box_id).val(origin_letter);
        		}
        		
        		wordlength = wordlist[wordptr-1].word.length
        		word_user_input = ""
        		for (var i=0; i<wordlength; i++) {
        			word_user_input = word_user_input + $('#word-input-' +i).val()
        		}
        		if (word_user_input == wordlist[wordptr-1].word) {
        			$('#thumb-' + wordptr).find('.back').css('background-color', 'rgba(0, 0, 0, 0.2)');
        			originPtrColor = 'rgba(0, 0, 0, 0.2)';
            		$('#thumb-' + wordptr).find('.back').css('display', '');
        		} else {
        			$('#thumb-' + wordptr).find('.back').css('background-color', 'rgb()');
        			originPtrColor = 'rgba(255, 255, 255, 0.075)';
            		$('#thumb-' + wordptr).find('.back').css('display', 'rgb(220, 205, 203)');
        		}
        	});
        
    }


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
    

    $('.fa-play-circle').click(function(){
        document.getElementById("audio").play();
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
            $('.word-input').val('');
            $('.thumb').find('.back').css('display', '');
            isFront = !isFront;
        }
    });
    
    $('.fa-arrow-right').click(function(){
    		$('#thumb-' + wordptr).find('.back').css('background-color', originPtrColor);
    		wordptr = wordptr + 1;
		setWordContent();
		originPtrColor = $('#thumb-' + wordptr).find('.back').css('background-color');
		$('#thumb-' + wordptr).find('.back').css('background-color', 'rgb(220, 205, 203)');
		
		if (isFront == false) {
			$('.card').toggleClass('flipped');
	    		$('.front').show();
	    		$('.back').hide();
	    		$('.thumb').find('.back').css('display', '');
	    		isFront = true;
		}
    })

    $('#show-translate').click(function(){
        if ($('.word-tooltip').is(':hidden'))
            $('.word-tooltip').show();
        else 
            $('.word-tooltip').hide();
    })
});
