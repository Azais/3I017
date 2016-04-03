function enregistrement(form){
	var login = form.login.value;
	var password = form.pass.value;
	var prenom = form.prenom.value;
	var nom = form.nom.value;
	var password2 = form.pass2.value;
	var ok = verif_form_enregistrement(login, password, password2, prenom, nom);
	if(ok){
		enregistre(prenom, nom, login, password);
		}
}

function verif_form_enregistrement(login, password, password2, prenom, nom){

	
	if(prenom.length==0){
		func_erreur("Tu n'as pas de prénom");
		return false;
	}

	if(nom.length==0){
		func_erreur("Tu n'as pas de nom");
		return false;
	}

	if(login.length ==0){
		func_erreur("Merci de mettre un identifiant");
		return false;

	}
	if(password.length== 0){
		func_erreur("Mot de passe obligatoire");
		return false;	
	}
	if(login.length>20){
		func_erreur("Votre login est beaucoup trop long");
		return false;
	}

	if(password!=password2){
		func_erreur("Avez vous des problèmes de mémoire ? Les mots de passe ne correspondent pas.");
		return false;
	}

	if(login==password){
		func_erreur("Votre mot de passe est identique à votre login. Respectez-vous.");
		return false;
	}
	return true;
}

function enregistre(prenom, nom, login, password){
	$.ajax({
	type: "GET",
	url: "user/createuser",
	data:"login="+login+"&password="+password+"&nom="+nom+"&prenom="+prenom,
	dataType: "json",
	success: function(rep){
		
	},
	error: function (jqXMTR, textStatus, errorThrown){
			alert(textStatus);			
		}
	});
}



function func_erreur(msg){
	var msg_box ="<div id='msg_erreur_connexion'>"+msg+"</div>";
	var old_msg = $("#msg_erreur_connexion"); // on recupere l'objet HTML
	if(old_msg.length===0){
		$(".erreur_box").prepend(msg_box);	
	}else{
		old_msg.replaceWith(msg_box);
	}
}