function setProfile(form){

	var password = form.pass.value;
	var newpassword = form.newpass.value;
	var photo = form.photoprofil.value;

	if(newpassword.length > 0){

			setPassword(password, newpassword);
		}
	
	if(photo.length > 0){
		
		setPicture(photo);
		
		
	}
	
}

function fileExists(filename){
	
	$.get("img/"+filename)
    .done(function() { 
        return true;
    })
    
    return false;
	
}

function setPassword(password, newpass){
	//alert("Connexion de "+login);
	$.ajax({
		type: "GET",
		url: "SetPassword",
		data:"key="+env.key+"&oldPass="+password+"&newPass="+newpass,
		dataType: "json",
		success: function(rep){},
		error: function (jqXMTR, textStatus, errorThrown){
				alert(textStatus);			
			}
	});
}

function setPicture(photo){
	//alert("Connexion de "+login);
	$.ajax({
		type: "GET",
		url: "SetPicture",
		data:"key="+env.key+"&newPicture="+photo,
		dataType: "json",
		success: function(rep){},
		error: function (jqXMTR, textStatus, errorThrown){
				alert(textStatus);			
			}
	});
}

function traiteReponseProfile(rep, login){
	if(rep.erreur != undefined){
		func_erreur(rep.erreur);
	}else{
		window.location.href="main.jsp?id="+rep.id+"&login="+login+"&key="+rep.key;
	}
}


function func_erreur(msg){
	var msg_box ="<div id='msg_erreur_profile'>"+msg+"</div>";
	var old_msg = $("#msg_erreur_profile"); // on recupere l'objet HTML
	if(old_msg.length===0){
		$(".erreur_box").prepend(msg_box);	
	}else{
		old_msg.replaceWith(msg_box);
	}
}