$(document).ready(function () {
	$('.recite-goal-today').hide();
    $('.review-goal-today').hide();
    $('.cut-line').hide();
	
	$('.back').hide();
    $('.front').hide();
    $('.word-tooltip').hide();
    $('.refresh').hide();
    $('.arrow').hide();
    $('.word-container').hide();
    
    $('.to-log').click(function(){
    		$('#task').removeClass("active");
    		$('#task').hide();
        $('#log').addClass("active");
        $('#log').show();
    })
    
    $('.fa-heart').click(function() {
    		if (myWordLike[wordptr] != 1) {
    			$.get("http://localhost:8080/Java_server/ChangeWord", 
    		            {
    		                name: username,
    		                wordset: wordlist[wordptr-1].wordset,
    		                wordid: wordlist[wordptr-1].wordid,
    		                status: 3
    		            },
    		            function(data,status,request){
    		            		console.log(data)
    		            }
    		    )
    		    myWordLike[wordptr] = 1
    		    $(this).append('<style>.fa-heart:before{color:rgba(255,50,0,0.4)}</style>')
    		} else {
    			$.get("http://localhost:8080/Java_server/ChangeWord", 
    		            {
    		                name: username,
    		                wordset: wordlist[wordptr-1].wordset,
    		                wordid: wordlist[wordptr-1].wordid,
    		                status: 2
    		            },
    		            function(data,status,request){
    		            		console.log(data)
    		            }
    		    )
    		    myWordLike[wordptr] = 0
    		    $(this).append('<style>.fa-heart:before{color:rgba(255,255,255,0.7)}</style>')
    		}
    		
	    
    })

    var wordlist;
    var wordptr = 1;
    var originPtrColor = 'rgba(255, 255, 255, 0.075)';
    var myWordLike = new Array()
    var myWordFinish = new Array()
    var task_now_dst = 1;
    var today_task = 0;

    $(".start-task").click(function(){
    		if ($(this).attr('id') == "start-recite") {
    			for (var i=0; i<today_recite; i++) {
        			myWordLike[i] = 0
        			myWordFinish[i] = 0
        		}
    			today_task = today_recite;
    			task_now_dst = 1;
    		} else {
    			for (var i=0; i<today_review; i++) {
        			myWordLike[i] = 0
        			myWordFinish[i] = 0
        		}
    			today_task = today_review;
    			task_now_dst = 0;
    		}
    		
        $.get("http://localhost:8080/Java_server/GetTodayWord", 
            {
                name: username,
                wordnumber: today_task,
                type: $(this).attr('id')
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
                		if (isFront == false && task_now_dst == 1) {
                			$('.card').toggleClass('flipped');
                	        	$('.front').show();
                	        	$('.back').hide();
                	    		$('.thumb').find('.back').css('display', '');
                	    		isFront = true;
                	    		$('.thumb').find('.back').css('display', '');
                		}
                		
                		if (isFront == true && task_now_dst == 0) {
                			$('.card').toggleClass('flipped');
                	        	$('.front').hide();
                	        	$('.back').show();
                	    		$('.thumb').find('.back').css('display', '');
                	    		isFront = false;
                	    		$('.thumb').find('.back').css('display', '');
                		}
                    	
                		originPtrColor = $(this).find('.back').css('background-color');
                		$(this).find('.back').css('background-color', 'rgb(255, 255, 255, 0.2)');
                		$('.word-tooltip').hide();
                		
                		//$(this).find('.back').css('background-color', 'rgba(0, 0, 0, 0.2)');
                		if (myWordLike[wordptr] == 1)
                			$('.fa-heart').append('<style>.fa-heart:before{color:rgba(255,50,0,0.4)}</style>')
                		else 
                			$('.fa-heart').append('<style>.fa-heart:before{color:rgba(255,255,255,0.7)}</style>')
                		
                })
                
                $('.thumb').on('mouseenter', function (event) {
                    $(this).find('.alt').css(_getDir($(this), event)).animate({
                        top: 0,
                        left: 0
                    }, 300)
                    var color = $(this).find('strong').css("color")
                    
                });

                $('.thumb').on('mouseleave', function (event) {
                    $(this).find('.alt').animate(_getDir($(this), event), 100);
                    var color = $(this).find('strong').css("color")
                    
                });
                
                $('.cut-line').hide();
                if (task_now_dst == 1) {
                		$('.front').show();
                		$('.back').hide();
                } else {
                		$('.card').toggleClass('flipped');
                		$('.front').hide();
                		$('.back').show();
                		isFront = false;
                }
                
                $('.refresh').show();
                $('.arrow').show();
                $('.word-container').show();    
                $('.recite-goal-today').hide();
                $('.review-goal-today').hide();
                setWordContent()
                
                $('.thumb').find('.back').css('display', '');
                $('#thumb-1').find('.back').css('background-color', 'rgba(255, 255, 255, 0.2)');
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
        			//correct
        			$('#thumb-' + wordptr).find('.back').css('background-color', 'rgba(0, 0, 0, 0.2)');
        			originPtrColor = 'rgba(0, 0, 0, 0.2)';
            		$('#thumb-' + wordptr).find('.back').css('display', '');
            		$.get("http://localhost:8080/Java_server/ChangeWord", 
            	            {
            	                name: username,
            	                wordset: wordlist[wordptr-1].wordset,
            	                wordid: wordlist[wordptr-1].wordid,
            	                status: task_now_dst
            	            },
            	            function(data,status,request){
            	            		console.log(data)
            	            }
            	    )
            	    myWordFinish[wordptr-1] = 1
            	    var i=0;
            	    for (i=0; i<today_task; i++) {
            	    		if (myWordFinish[i] == 0) break;
            	    }
            	    if (i == today_task) {
            	    		if (task_now_dst == 1) {
            	    			alert("Finish Recite Task Today!");
            	    			$('.recite-goal-today').show();
            	    		    $('.review-goal-today').show();
            	    		    $('.cut-line').show();
            	    			
            	    			$('.back').hide();
            	    		    $('.front').hide();
            	    		    $('.word-tooltip').hide();
            	    		    $('.refresh').hide();
            	    		    $('.arrow').hide();
            	    		    $('.word-container').hide();
            	    		    
            	    		    $('.recite-goal-today > h3').text("YOU NEED TO RECITE 0 WORDS TODAY");
            	    		    $('#start-recite').addClass('disabled');
            	    		}
            	    		else {
            	    			alert("Finish Review Task Today!");
            	    			$('.recite-goal-today').show();
            	    		    $('.review-goal-today').show();
            	    		    $('.cut-line').show();
            	    			
            	    			$('.back').hide();
            	    		    $('.front').hide();
            	    		    $('.word-tooltip').hide();
            	    		    $('.refresh').hide();
            	    		    $('.arrow').hide();
            	    		    $('.word-container').hide();
            	    		    
            	    		    $('.review-goal-today > h3').text("You need to review 0 words today");
            	    		    $('#start-review').addClass('disabled');
            	    		}
            	    		if (isFront == false) {
            	    			$('.card').toggleClass('flipped');
            	    			isFront = true;
            	    		}
            	    		
            	    }
        		} else {
        			$('#thumb-' + wordptr).find('.back').css('background-color', 'rgb()');
        			originPtrColor = 'rgba(255, 255, 255, 0.075)';
            		$('#thumb-' + wordptr).find('.back').css('display', 'rgb(220, 205, 203)');
            		$.get("http://localhost:8080/Java_server/ChangeWord", 
            	            {
            	                name: username,
            	                wordset: wordlist[wordptr-1].wordset,
            	                wordid: wordlist[wordptr-1].wordid,
            	                status: task_now_dst + 1
            	            },
            	            function(data,status,request){
            	            		console.log(data)
            	            }
            	    )
            	    myWordFinish[wordptr] = 0
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
    $('.fa-refresh').click(function(){
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
		$('.word-tooltip').hide();
		$('#thumb-' + wordptr).find('.back').css('background-color', 'rgba(255, 255, 255, 0.2)');
		
		
		if (isFront == false && task_now_dst == 1) {
			$('.card').toggleClass('flipped');
	        	$('.front').show();
	        	$('.back').hide();
	    		$('.thumb').find('.back').css('display', '');
	    		isFront = true;
		}
		
		if (isFront == true && task_now_dst == 0) {
			$('.card').toggleClass('flipped');
	        	$('.front').hide();
	        	$('.back').show();
	    		$('.thumb').find('.back').css('display', '');
	    		isFront = false;
		}
		
		if (myWordLike[wordptr] == 1)
			$('.fa-heart').append('<style>.fa-heart:before{color:rgba(255,50,0,0.4)}</style>')
		else 
			$('.fa-heart').append('<style>.fa-heart:before{color:rgba(255,255,255,0.7)}</style>')
    })

    $('#show-translate').click(function(){
        if ($('.word-tooltip').is(':hidden'))
            $('.word-tooltip').show();
        else 
            $('.word-tooltip').hide();
    })
});
