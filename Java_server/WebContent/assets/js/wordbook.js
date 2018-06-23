// Increments the delay on each item.

$('#header > nav > ul > li:nth-child(2) > a').click(function(e){
	$('#myList').empty();
	$.get("http://localhost:8080/Java_server/ImportantWord", 
            {
                name: username,
            },
            function(data,status,request){
            		console.log(data);
            		var listlen = data.wordlist.length;
            		var click_word = new Array();
            		
            		for (var i=0; i<listlen; i++) {
            			$('#myList').append(list_dom_1 + i + list_dom_2);
            			$('#important_word_' + i + ' > div.word-box > strong').text(data.wordlist[i].word);
            			$('#important_word_' + i + ' > div.word-box > div').attr('id', 'right-angle-' + i);
            			$('#important_word_' + i).find('.phonetic').text('[' + data.wordlist[i].pron+ ']');
            			$('#important_word_' + i).find('audio').attr('src', data.wordlist[i].pronlink);
            			$('#important_word_' + i).find('audio').attr('id', 'audio-'+i);
            			for (var j=0; j<data.wordlist[i].definition.length; j++){
            				if (data.wordlist[i].definition[j].form == "") break;
            				$('#important_word_' + i).find('#fold-mean').append(mean_box_1 + data.wordlist[i].definition[j].form +mean_box_2 +  data.wordlist[i].definition[j].meaning  + mean_box_3);			
            			}
            			$('#important_word_' + i).find('.sentence-box-english').text(data.wordlist[i].sample_english);
            			$('#important_word_' + i).find('.sentence-box-chinese').text(data.wordlist[i].sample_chinese);   
            			$('#important_word_' + i).find('.fa-check-circle').attr('id', 'click-word-'+i);
            			click_word[i] = 0;
            		}
            		
            		
            		$('.right-angle').click(function(event){
					event.preventDefault();
					folder_ID = parseInt($(this).attr('id').split('-')[2]);
					$('#important_word_' + folder_ID + ' > div.folder-word').toggleClass('show');
				})
				
				$('.rolldown-list li').each(function () {
					  var delay = (parseInt($(this).attr('id').split('_')[2]) / 4) + 's';
					  $(this).css({
					    webkitAnimationDelay: delay,
					    mozAnimationDelay: delay,
					    animationDelay: delay
					  });
				});
            		
            		$('.fa-check-circle').click(function(){
            			  var word_id = parseInt($(this).attr('id').split('-')[2]);
            			  console.log(word_id);
            			  if ( click_word[word_id] == 0) {
            				  $(this).append("<style>.fa-check-circle:before{color: rgba(240, 255, 240, 1)}</style>")
            			  } else {
            				  $(this).append("<style>.fa-check-circle:before{color: rgba(255, 255, 255, 1)}</style>")
            			  }
            			  click_word[word_id] = 1 - click_word[word_id];
            		})
            		
            		$('.fa-play-circle').click(function(){
            			var audio_id = $(this).find('audio').attr('id')
            	        document.getElementById(audio_id).play();
            	    })
            }
    )
})

var  mean_box_1= '<div class="fold-mean-box">\
  					<div class="icon fa-circle-o"></div>\
  					&nbsp;&nbsp;&nbsp;';
var mean_box_2 = '&nbsp;&nbsp;\
				  <div class="mean-text">';
var mean_box_3 = '</div>\
		</div>';

var list_dom_1 = '<li id="important_word_'
var list_dom_2 = '">\
  	<div class="word-box">\
  		<strong style="font-size: 1.3rem; flex: 95%">Familiar</strong>\
	  	<div class="right-angle">\
	  		<span class="icon fa-angle-down"></span>\
	  	</div>\
	</div>\
	<div class="folder-word" id="folder-word-1">\
		<div id="fold-pron">\
			<div class="icon fa-tags"></div>\
			<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>pron:</strong>&nbsp;&nbsp;&nbsp;</div>\
			<div class="phonetic">\
				[fəˈmɪliə(r)]\
			</div>\
			<div>&nbsp;&nbsp;&nbsp;</div>\
			<div class="icon fa-play-circle" >\
				<audio src="http://res.iciba.com/resource/amp3/1/0/cd/81/cd8193113f5c74760eeb2531430e43c3.mp3" id="audio">\
				</audio>\
			</div>\
		</div>\
  		<div id="fold-mean">\
  		</div>\
  		<div id="fold-sentence">\
  			<div class="sentence-tag">\
	  			<div class="icon fa-tags"></div>\
	  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>例句：</strong>\
	  		</div>\
  			<div class="sentence-box-english">\
  		You take those small, familiar steps, and you try to be honest, not to live as if nothing had changed, but still to go on with your life.\
  		你采取这些小的、熟悉的方式，你试着诚实一点，别好像什么都被变一样的生活，而是继续你的生活。\
  			</div>\
			<div class="sentence-box-chinese">\
		You take those small, familiar steps, and you try to be honest, not to live as if nothing had changed, but still to go on with your life.\
		你采取这些小的、熟悉的方式，你试着诚实一点，别好像什么都被变一样的生活，而是继续你的生活。\
			</div>\
  		</div>\
		<span class="icon fa-check-circle"></span>\
  	</div>\
	</li>';