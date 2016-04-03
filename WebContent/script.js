$(document).ready(function(){ // attendre que le document soit chargé
    $('.picture_like').on({
		'click': function() {
		var src = ($(this).attr('src') === 'square_heart.png')
		? 'square_heart_dead.png'
		: 'square_heart.png';
		$(this).attr('src', src);
	}	
	});

	$('.picture_friend').on({
		'click': function() {
		var src = ($(this).attr('src') === 'square_profile.png')
		? 'square_profile_dead.png'
		: 'square_profile.png';
		$(this).attr('src', src);
	}	
	});

	$('.picture_cancel').hover(function() {
		var src = ($(this).attr('src') === 'square_stop-icon.png')
		? 'square_stop-icon_dead.png'
		: 'square_stop-icon.png';
		$(this).attr('src', src);
		
	});


/* Maniere 1 pour faire disparaitre du feed
	$('.picture_cancel').click(function() {
		$(this).closest(".comment_general").css("display","none");
		
	});

*/

	$('.picture_cancel').click(function() {
		$(this).closest(".comment_general").hide();
		
	});


/* Fonctions d'ouverture et fermeture des menus*/

	$('.menu_toggle').click(function(){
		$(".toggle_content").toggle();
	});


/*
	$('.closed_toggle').click(function() {
		$(this).children().show();
		$(this).removeClass("closed_toggle");
		$(this).addClass("open_toggle");
		
	});
	
	$('.open_toggle').click(function() {
		$(this).children().hide();
		$(this).removeClass("open_toggle");
		$(this).addClass("closed_toggle");
		
	});

*/


/*Voir les boutons connexion/déconnexion*/

	$('#deco').click(function(){
		$(this).closest("#disconnect").hide();
		$(this).closest('#disconnect').siblings().find('#connect').show();
	});

});

