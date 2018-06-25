$(document).ready(function() {
	
	$('.to-log-static').click(function(){
		$('#user').removeClass("active");
		$('#user').hide();
	    $('#log').addClass("active");
	    $('#log').show();
	    $('.log-href').show();
	}) 
	
	$('#header > nav > ul > li:nth-child(4) > a').click(function(e){
		if (username == "") {
			$('#user').css('height', '25rem');
			$('.log-href').hide();
			$('.log-first-static').show();
			$('#myChart').hide();
			$('#user > div.data-total-box').hide();
			
		} else {
			$('#user').css('height', '100%');
			$('.log-first-static').hide();
			$('#myChart').show();
			$('.log-href').hide();
			$('#user > div.data-total-box').show();
			$('#user > div.data-total-box > div.finish-data-box > text').text(label_word_static[0]);
			$('#user > div.data-total-box > div.review-data-box > text').text(label_word_static[1]);
			$('#user > div.data-total-box > div.remain-data-box > text').text(label_word_static[2]);
			$('#user > div.data-total-box > div.important-data-box > text').text(label_word_static[3]);
			
			var ctx = document.getElementById('myChart').getContext('2d');
			var chart = new Chart(ctx, {
			    type: 'line',
			    data: {
			        labels: recite_timelabel,
			        datasets: [{
			            label: "Recite",
			            backgroundColor: 'rgba(135, 197, 230, 0.2)',
			            borderColor: 'rgba(255, 255, 255, 0)',
			            data: recite_data,
			        }, {
			            label: 'Review',
			            backgroundColor: 'rgba(245, 214, 219, 0.2)',
			            borderColor: 'rgba(255, 255, 255, 0)',
			            data: review_data,
			        }]
			    },
			    options,
			});
		}
	})
	
	var options = {
	    	legend: {
	            display: true,
	            position: 'bottom',
	            labels: {
	            		fontColor: "rgb(140,140,140)",
	            }
	    },
	    scales :{
	        xAxes:[{
	            scaleLabel:{
	                display:true,
	                labelString:'days',
	                fontColor:'rgb(140,140,140)'
	            },
	            //网格显示
	            gridLines:{
	                display:false,
	                
	            },
	
	        }],
	        yAxes:[{
	            scaleLabel:{
	                display:true,
	                labelString:'words',
	                	fontColor:'rgb(140,140,140)'
	            },
	            gridLines:{
	                display:true,
	                color: 'rgba(100, 100,100, 0.2)'
	            },
	
	        }],
	
	    },
	
	}
	
})

