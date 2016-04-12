function connexion(form){
	var login = form.login.value;
	var password = form.pass.value;
	var ok = verif_form_connexion(login, password);
	if(ok){
		connecte(login, password);
		}
}

function verif_form_connexion(login, password){
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
	return true;
}

function connecte(login, password){
	//alert("Connexion de "+login);
	$.ajax({
		type: "GET",
		url: "user/login",
		data:"login="+login+"&password="+password,
		dataType: "json",
		success: function(rep){traiteReponseConnexion(rep, login)},
		error: function (jqXMTR, textStatus, errorThrown){
				alert(textStatus);			
			}
	});
}

function traiteReponseConnexion(rep, login){
	if(rep.erreur != undefined){
		func_erreur(rep.erreur);
	}else{
		window.location.href="main.jsp?id="+rep.id+"&login="+login+"&key="+rep.key+"&picture="+rep.picture;
	}
}

function func_erreur(msg){
	var msg_box ="<div id='msg_erreur'>"+msg+"</div>";
	var old_msg = $("#msg_erreur"); // on recupere l'objet HTML
	if(old_msg.length===0){
		$(".erreur_box").prepend(msg_box);	
	}else{
		old_msg.replaceWith(msg_box);
	}
}

