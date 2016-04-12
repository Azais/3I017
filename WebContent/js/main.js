function User(id, login, contact, photo){
	this.id=id;
	this.login=login;
	this.contact=false;
	if(contact!=undefined){
		this.contact=contact;
	}
	if(photo!=undefined){
		this.photo=photo;
	}else{
		this.photo="undefined.jpg";
	}
	env.users[this.id]=this;
}

User.prototype.modifystatus=function(){
								this.contact=!this.contact;
							}
function isNumber(s){
	return (! isNaN(s-0));
}

function Commentaire(id, auteur, texte, date, like, score){
	this.id=id;
	this.auteur=auteur;
	this.texte=texte;
	this.date=date;
	this.score=score;
	if(like==undefined){
		this.likes=false;
	}else{
		this.likes=like;
	}
	
}


Commentaire.prototype.getHtml=function(){
										//alert(JSON.stringify(this));
                                        //alert("id: "+this.id+" auteur:"+this.auteur.id);
										var s="<div class='comment_general'>"
									
										s+="<div class='comment'> ";
										var photoP =this.auteur.photo;
										
										$.get("img/"+photoP)
									    .fail(function() { 
									        photoP = "undefined.jpg";
									    })
										s+="<div class='comment_picture'><img class='comment_img' src='img/"+photoP+"' ></div>";
										s+="<div class='text_comment'>"+this.texte+"</div>";
										s+="</div>";
										s+="<div class='comment_meta'>";
										
										if(env.actif != undefined){
											if(env.actif.login != this.auteur.login){
												s+="<div class='comment_like'><img class='picture_like' src='square_heart.png'></div>";
										
                                
												s+="<div class='comment_friend'  >";
												if(this.follows){
													s+="<img class='picture_friend' src='square_profile.png'></div>";
												}else{
													s+="<img class='picture_friend' src='square_profile_dead.png'></div>";
												}
											}
										}
										s+="<div class='author_comment'>"+this.auteur.login+"</div>";
										s+="<div class='date_comment'>"+this.date+"</div>";
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
	console.log("resultats: "+resultats);
	env.recherche=this;
	this.getHtml = function(){
		var s = "";
		//alert(this.resultats);
		for(var i=0; i<this.resultats.length;i++){
			console.log("i: "+i+" "+this.resultats[i]);
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
		$('#main').empty();
		$('#main').prepend(r.getHtml());
        updateWindowOnClick();
        updateHeartOnClick();
	}else{
		alert("erreur: "+obj.erreur);	
	}
}

RechercheCommentaire.revival=function revival(key, value){
	//alert(key);
	//alert(value);
	if(key.length===0){
        //alert("point 1");
		if(value===undefined){
			var r = RechercheCommentaire(value.resultats, value.recherche, value.contacts_only, value.date);
			return(r);
		} else {
			return(value);
		}
	}else{ 
		if (isNumber(key)/*&& value.text != ''*/) {
			console.log('key: '+key+' value: '+value.text);
			var c = new Commentaire(value.id, value.auteur, value.text, value.postDate, value.likes, value.follows);
			
			return(c);
		}else{
	
			if(key==='auteur') {
	
				var u;		
				if(env.users[value.id] !== undefined){
					u = env.users[value.id];		
				}else {
					console.log("Ajout de :"+value.login+value.picture);
					u = new User(value.id, value.login, value.contact, value.picture);
				}
				return u;
			}else{
			 	if(key==='postDate'){
			 		//alert(value.$date);
					var d = new Date(value.$date);
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

function main (id, login, key, photo){
	env={};
	env.users=[];
	if(!(id==null)){
		
		env.key=key;
		env.actif=new User(id, login, true, photo);
		//gererDivConnection();
		}
	isConnected(env);
}


function search(query){
	//var friends=($('#box_friends').get(0).checked)?1:0;
	var friends=0;
	//var query= $("#requete").val();
	var dataQuery='';
	if(query!==undefined)
		dataQuery='&query='+query;
	console.log(query);
	$.ajax({ 
		type: "GET",
		url: "ListMessage",
		data: (env.key===undefined? "": "key="+env.key)+dataQuery+"&friends="+friends,
		dataType: "json",
		success: RechercheCommentaire.traiteReponseJSON,
		error: function(jqXHR, textStatus, errorThrown){
			alert(textStatus);
			}
		});
}

function ajoutsup_contact(index){
    
	var url;
    var idUser=env.users[env.recherche.resultats[index].auteur.id].id;
    console.log("idUser: "+idUser);
	if(env.users[idUser].contact){
		url="RemoveFriend";
	}else{
		url="AddFriend";
		}
    console.log("url :"+url+" id: "+idUser);
	$.ajax({
		type: "GET",
		url: url,
		data: "key="+env.key+"&id_friend="+idUser,
		dataType: "json",
		success: function(rep){
            if(rep.erreur===undefined){
                console.log("point 7");
                console.log("url "+ url)
                env.users[idUser].contact=((url==="AddFriend")?true:false);
                console.log("point 8: "+env.users[idUser].contact);
                updateWindowFollow();
            }else{
                alert(rep.message);
            }
            
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert(textStatus);
			}
		});
    
}
function likedis_comment(index){
    
	var url;
    var idComment=env.recherche.resultats[index].id;
    //console.log("idComment: "+idComment);
	if(env.recherche.resultats[index].likes){
		url="Dislike";
	}else{
		url="Like";
		}
	$.ajax({
		type: "GET",
		url: url,
		data: "key="+env.key+"&id_comment="+idComment,
		dataType: "json",
		success: function(rep){
            if(rep.erreur===undefined){
                //console.log("point 7");
                console.log("url "+ url)
                env.recherche.resultats[index].likes=((url==="Like")?true:false);
                //console.log("point 8: "+env.users[idUser].contact);
                updateHeartFollow(index)
            }else{
                alert(rep.message);
            }
            
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert(textStatus);
			}
		});
    
}

function func_new_comment(text){
	//alert("text :"+text);
	//alert("env.key :"+env.key);
    
	$.ajax({
		type:"GET",
		url:"user/AddComment",
		data:"key="+env.key+"&text="+text,
		dataType:"text",
		success: traiteReponseNewComment,
		error: function(jqXHR, textStatus, errorThrown){
			alert(textStatus);
			},
		

		
		});
}

function traiteReponseNewComment(rep){
	if(rep.erreur!= undefined){
		alert("Erreur traiteReponse1:"+rep.erreur);
		//disconnect();
	}else{
		alert("Alerte traiteReponse2"+rep);
		search();
	}
}
function disconnect(){
	$.ajax({
		type: "GET",
		url: "/logout",
		data: "key="+env.Key,
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
					
				}
			});
	}

function commenter(form){

	var text = form.comment.value;
	func_new_comment(text);
}


