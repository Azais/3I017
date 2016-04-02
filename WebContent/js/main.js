function User(id, login, contact){
	this.id=id;
	this.login=login;
	this.contact=false;
	if(contact!=undefined){
		this.contact=contact;
	}
	env.users[this.id]=this;
}

User.prototype.modifystatus=function(){
								this.contact=!this.contact;
							}
function isNumber(s){
	return (! isNaN(s-0));
}
function Commentaire(id, auteur, texte, date, score){
	this.id=id;
	this.auteur=auteur;
	this.texte=texte;
	this.date=date;
	this.score=score;
}


Commentaire.prototype.getHtml=function(){
										//alert(JSON.stringify(this));	
										var s="<div class='comment_general'>"
									
										s+="<div class='comment'> ";
										s+="<div class='text_comment'>"+this.texte+"</div>";
										s+="</div>";
										s+="<div class='comment_meta'>";
										s+="<div class='comment_like'><img class='picture_like' src='square_heart.png'></div>";
										
									
										s+="<div class='comment_friend'><img class='picture_friend' src='square_profile.png'></div>";
										
										s+="<div class='author_comment'>"+this.auteur.login+"</div>";
										s+="<div class='comment_cancel'><img class='picture_cancel' src='square_stop-icon_dead.png'></div>";
										s+="</div>";
										s += "</div>";
										return s;
									}


function RechercheCommentaire(resultats, recherche, contact_only, date){
	this.resultats=resultats;
	this.recherche=recherche;
	this.contact_only=contact_only;
	this.date=date;
	env.recherche=this;
}

function RechercheCommentaire(resultats){
	this.resultats=resultats;
	env.recherche=this;
	this.getHtml = function(){
		var s = "";
		//alert(this.resultats);
		for(var i=0; i<this.resultats.length;i++){
			s+=this.resultats[i].getHtml();
		}
		
		return s;
	}
}

//RechercheCommentaire.prototype.getHtml=

RechercheCommentaire.traiteReponseJSON=function(json){
	var obj=JSON.parse(JSON.stringify(json), RechercheCommentaire.revival);
	//alert("obj "+obj)
	if(obj.erreur===undefined){
		var r = new RechercheCommentaire(obj);
		$('#main').prepend(r.getHtml())
	}else{
		alert("erreur: "+obj.erreur);	
	}
}

RechercheCommentaire.revival=function revival(key, value){
	//alert(key);
	//alert(value);
	if(key.length===0){
		if(value===undefined){
			var r = RechercheCommentaire(value.resultats, value.recherche, value.contacts_only, value.date);
			return(r);
		} else {
			return(value);
		}
	}else{ 
		if (isNumber(key)&& value.texte != '') {
			var c = new Commentaire(value.id, value.auteur, value.text, value.date, value.score);
			return(c);
		}else{
	
			if(key==='auteur') {
	
				var u;		
				if(env.users[value.id] !== undefined){
					u = env.users[value.id];		
				}else {
					u = new User(value.id, value.login, value.contact);
				}
				return u;
			}else{
			 	if(key==='date'){
					var d = new Date(value);
					return d;
				}else{
					return (value);
				}
			}
		} 
	}
}



function isConnected(env){
	if(env.actif==null){
		$('#connect').show();
		$('#disconnect').hide();

	}else{
		$('#connect').hide();
		$('#disconnect').show();
	}
}

function main (id, login, key){
	env={};
	env.users=[];
	if(!(id==null)){
		
		env.key=key;
		env.actif=new User(id, login);
		//gererDivConnection();
		}
	isConnected(env);
}


function search(){
	//var friends=($('#box_friends').get(0).checked)?1:0;
	var friends=0;
	var query='';
	//var query= $("#requete").val();
	$.ajax({ 
		type: "GET",
		url: "ListMessage",
		data: "key="+env.key+"&query="+query+"&friends="+friends,
		dataType: "json",
		success: RechercheCommentaire.traiteReponseJSON,
		error: function(jqXHR, textStatus, errorThrown){
			alert(textStatus);
			}
		});
}
function ajoutsup_contact(id){
	var user=env.users[id];
	var url;
	if(user.contact){
		url="/RemoveFriend";
	}else{
		url="/AddFriend";
		}
	$.ajax({
		type: "GET",
		url: url,
		data: "key:"+env.key+"&id_friend:"+id,
		dataType: "json",
		success: function(res){
			TraiteReponseAjoutSupContact(rep, id);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert(textStatus);
			}
		});
}
function func_new_comment(text){
	alert("text :"+text);
	alert("env.key :"+env.key);
	$.ajax({
		type:"GET",
		url:"user/AddComment",
		data:"key="+env.key+"&text="+text,
		dataType:"text",
		success: traiteReponseNewComment,
		error: function(jqXHR, textStatus, errorThrown){
			alert(textStatus);
			}

		
		});
}

function traiteReponseNewComment(rep){
	if(rep.erreur!= undefined){
		alert("Erreur traiteReponse1:"+rep.erreur);
		//disconnect();
	}else{
		alert("Alerte traiteReponse2"+rep);
	}
}
function disconnect(){
	$.ajax({
		type: "GET",
		url: "/logout",
		data: "key"+env.Key,
		dataType: "json",
		success: function(rep){
					return;
				},
		error: function(jqXHR, textStatus, errorThrown){
			alert(textStatus);
			},
		complete: function(rep){
					env.actif=undefined;
					env.key=undefined;
					//gererDivConnexion();
					search();
				}
			});
	}

function commenter(form){

	var text = form.comment.value;
	func_new_comment(text);
}


