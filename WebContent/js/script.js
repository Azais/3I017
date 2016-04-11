$(document).ready(function(){ // attendre que le document soit chargé

    
    $('#main').on('click', '.picture_like', function(){
		var src = ($(this).attr('src') === 'square_heart.png')
		? 'square_heart_dead.png'
		: 'square_heart.png';
		$(this).attr('src', src);
    });
/*
	$('.picture_friend').on({
		'click': function() {
		var src = ($(this).attr('src') === 'square_profile.png')
		? 'square_profile_dead.png'
		: 'square_profile.png';
		$(this).attr('src', src);
	}	
	});*/
	
/*	$('#main').on('click', '.picture_friend', function() {
		var src = ($(this).attr('src') === 'square_profile.png')
		? 'square_profile_dead.png'
		: 'square_profile.png';
		$(this).attr('src', src);
		
	});
*/	

/*
	$('.picture_cancel').hover(function() {
		var src = ($(this).attr('src') === 'square_stop-icon.png')
		? 'square_stop-icon_dead.png'
		: 'square_stop-icon.png';
		$(this).attr('src', src); 
		
	});*/
	
	$('#main').on('mouseover', '.picture_cancel', function() {
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
	
	$('#main').on('click', '.picture_cancel', function() {
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


function updateWindowOnClick(){
	console.log("entré");
    $('.comment_meta').each(function(index, elem){
        console.log(elem);
        var idUser=env.recherche.resultats[index].auteur.id;
        var src = ((env.users[idUser].contact) ? 'square_profile.png': 'square_profile_dead.png');
        console.log("point 1 src: "+src);
        $('.picture_friend', elem).attr('src', src);
        $(elem).on('click','.picture_friend',function(){
        	console.log("point 2");
            //var src = (($(this).attr('src'))=== 'square_profile_dead.png')? 'square_profile.png': 'square_profile_dead.png';
            console.log("point 3");
            //$(this).attr('src', src);
            //console.log("point 4 src: "+src);
            if(env.key!==undefined){
                console.log("point 5");
                ajoutsup_contact(index);
                console.log("point 6");
                
            }
        })
    })
}
function updateWindowFollow(){
	console.log("entré");
    $('.comment_meta').each(function(index, elem){
        console.log(elem);
        var idUser=env.recherche.resultats[index].auteur.id;
        var src = ((env.users[idUser].contact) ? 'square_profile.png': 'square_profile_dead.png');
        console.log("point 1 src: "+src);
        $('.picture_friend', elem).attr('src', src);
    })
}
function updateLikeOnClick(){
	console.log("entré");
    $('.comment_meta').each(function(index, elem){
        console.log(elem);
        var idUser=env.recherche.resultats[index].auteur.id;
        var src = ((env.users[idUser].contact) ? 'square_profile.png': 'square_profile_dead.png');
        console.log("point 1 src: "+src);
        $('.picture_friend', elem).attr('src', src);
        $(elem).on('click','.picture_friend',function(){
        	console.log("point 2");
            //var src = (($(this).attr('src'))=== 'square_profile_dead.png')? 'square_profile.png': 'square_profile_dead.png';
            console.log("point 3");
            //$(this).attr('src', src);
            //console.log("point 4 src: "+src);
            if(env.key!==undefined){
                console.log("point 5");
                ajoutsup_contact(index);
                console.log("point 6");
                
            }
        })
    })
}
                        

                                         
