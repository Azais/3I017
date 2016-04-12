$(document).ready(function(){ // attendre que le document soit chargé
	$( "input:first" ).change(function(){
		search($( "input:first" ).val());
	});
    /*
    $('#main').on('click', '.picture_like', function(){
		var src = ($(this).attr('src') === 'square_heart.png')
		? 'square_heart_dead.png'
		: 'square_heart.png';
		$(this).attr('src', src);
    });
    * /
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
		var src = 'square_stop-icon_dead.png';
		
		$(this).attr('src', src);
		
	});
	
	
/*
	$('#main').on('click', '.comment_picture', function() {
	
		var general = $(this).closest('.comment_general');
		console.log("general :"+general);
		var meta = general.find('comment_meta');
		console.log(meta);
		var login = meta.find('author_comment');
		console.log(login.text());
		userProfile(login.text());
	});
*/

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
	
	$('#profil').click(function(){
		
		if(env.actif == undefined){
			window.location.href="connexion.html";
		}else{
			window.location.href="monprofil.jsp?id="+env.actif.id+"&login="+env.actif.login+"&key="+env.key;

		}
		
	

	});
	
	$('#logo_left').click(function(){
		
		if(env.actif == undefined){
			window.location.href="main.jsp";
		}else{
			window.location.href="main.jsp?id="+env.id+"&login="+env.actif.login+"&key="+env.key;

		}
		
	

	});
});


function updateWindowOnClick(){
	console.log("entré");
    $('.comment_meta').each(function(index, elem){
        console.log(elem);
        var idUser=env.recherche.resultats[index].auteur.id;
        var src = ((env.users[idUser].contact) ? 'square_profile.png': 'square_profile_dead.png');
   
        $('.picture_friend', elem).attr('src', src);
        $(elem).on('click','.picture_friend',function(event){
        	event.stopPropagation();
       
            //var src = (($(this).attr('src'))=== 'square_profile_dead.png')? 'square_profile.png': 'square_profile_dead.png';
  
            //$(this).attr('src', src);
            //console.log("point 4 src: "+src);
            if(env.key!==undefined){
        
                ajoutsup_contact(index);
         
                
            }
        });
    });
};


function updateWindowFollow(){
	console.log("entré");
    $('.comment_meta').each(function(index, elem){
    
        var idUser=env.recherche.resultats[index].auteur.id;
        var src = ((env.users[idUser].contact) ? 'square_profile.png': 'square_profile_dead.png');

        $('.picture_friend', elem).attr('src', src);
    });
};

function updateHeartOnClick(){
	console.log("entré");
    $('.comment_meta').each(function(index, elem){
   
        var idUser=env.recherche.resultats[index].auteur.id;
        var src = ((env.recherche.resultats[index].likes) ? 'square_heart.png': 'square_heart_dead.png');

        $('.picture_like', elem).attr('src', src);
        $(elem).on('click','.picture_like',function(event){
        	event.stopPropagation();

            //var src = (($(this).attr('src'))=== 'square_profile_dead.png')? 'square_profile.png': 'square_profile_dead.png';

            //$(this).attr('src', src);
            //console.log("point 4 src: "+src);
            if(env.key!==undefined){
            
                likedis_comment(index);
        
                
            }
        });
    });
};
function updateHeartFollow(i){

	var src = ((env.recherche.resultats[i].likes) ? 'square_heart.png': 'square_heart_dead.png');
    $('.picture_like',$('.comment_meta')[i]).attr('src', src);
};

function userProfile(login){
	if(env.actif == undefined){
		window.location.href="main.jsp?profil="+login;
	}else{
		window.location.href="main.jsp?id="+env.id+"&login="+env.actif.login+"&key="+env.key+"&profil="+login;

	}
	
}

function accessProfile(){
	console.log("profil");
    $('.comment_meta').each(function(index, elem){
        console.log(elem);
        var idUser=env.recherche.resultats[index].auteur.id;
        console.log(idUser);
   
        $(elem).on('click','.author_comment',function(event){
        	event.stopPropagation();
       
            //var src = (($(this).attr('src'))=== 'square_profile_dead.png')? 'square_profile.png': 'square_profile_dead.png';
  
            //$(this).attr('src', src);
            //console.log("point 4 src: "+src);
            if(env.key!==undefined){
        
                userProfile(idUser);
         
                
            }
        });
    });
};
