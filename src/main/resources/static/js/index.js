
$(document).ready(function(){


	/* ---- Countdown timer ---- */

	$('#counter').countdown({
		timestamp : (new Date("January 14, 2019 23:59:00")).getTime() + 9*10*60*60*1000
	})


	/* ---- Animations ---- */

	$('#links a').hover(
		function(){ $(this).animate({ left: 3 }, 'fast') },
		function(){ $(this).animate({ left: 0 }, 'fast') }
	)

	$('footer a').hover(
		function(){ $(this).animate({ top: 3 }, 'fast') },
		function(){ $(this).animate({ top: 0 }, 'fast') }
	)
})
