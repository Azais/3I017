<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Bienvenue</title>
		<script type="text/javascript" src="js/jquery-2.2.1.js"></script>
		<script type="text/javascript" src="js/connexion.js"></script>
		<script type="text/javascript" src="js/script.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
		<link href="css/main6.css" rel="stylesheet" type="text/css" />
		<link href="css/style2.css" rel="stylesheet" type="text/css" />

	</head>
	<body>
		<%@ page contentType="text/html;charset=UTF-8" language="java" %>
		<script type="text/javascript">
			function go(){
				<%   
					String id=request.getParameter("id");
					String login=request.getParameter("login");
					String key=request.getParameter("key");
					String picture=request.getParameter("picture");
	
					
					if((id!=null)&&(login!=null)&&(key!=null)){
						out.println("main ('"+id+"','"+login+"','"+key+"','"+picture+"');");
						
					}else{
						out.println("main();");
					}
					out.println("search();");

				%>
			};
            
			$(go);
		</script>
		<div id="header">
		<div id="top">
			<div id="logo"> <img id="logo_left" src="LogoTwit.png"> </div>
			<div id="search">
				<form  action="javascript:(function(){return;})()">
				<div class="searchbar">
					<input type="text" name="search" value="	Cherchez-moi"/>
				</div>
				</form>
			</div>
			<div id="connect"><a href="connexion.html"><div class="cadre">Se connecter</div></a> <a href="enregistrement.html"><div class="cadre">Enregistrement</div></a>

			</div>
			<div id="disconnect"><div class="cadre" id="profil">Mon profil</div> <a href="enregistrement.html"><div class="cadre" id="deco">Se déconnecter</div></a></div>
			</div>
		

		<div id="nav-bar">
			<div id="nav-title">			
			<h2>Commentaires</h2>
			</div>
		</div>
		</div>
		<div id="left">
		<h2>Menu</h2>
			<div class="menu_toggle" > Versions
				<ul class="toggle_content" style="display: none">
					<li> <a href="main.html">Version 1</a></li>
					<li> <a href="main2.html">Version 2</a></li>
					<li> <a href="main3.html">Version 3</a></li>
					<li> <a href="main4.html">Version 4</a></li>
					<li> <a href="main5.html">Version 5</a></li>		
				</ul>
			</div>

			
				<div class="writer">
				<form class ="erreur_box" action="javascript:(function(){return;})()" onSubmit="javascript:commenter(this)">
			
						<textarea name="comment" cols='20' rows='5'></textarea>
				
					

				<div class="buttons">
					<input type="submit" value="Ecrire" />
					<input type="reset" value="Annuler" />
				</div>	
			</form>	
				</div>
		</div>
		<div id="main">
			
	
			
			</div>

			
			</div>
		<div id="footer"><div class="sub_footer"><h2>Informations</h2>Ce site est en test</div>
						<div class="sub_footer"><h2>Crédits</h2> Icones par <a href="http://www.elegantthemes.com/blog/freebie-of-the-week/beautiful-flat-icons-for-free">Nick Roach</a></div>
		</div>

			
	</body>
</html>
