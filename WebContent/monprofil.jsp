<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Bienvenue</title>
		<script type="text/javascript" src="js/jquery-2.2.1.js"></script>
		<script type="text/javascript" src="js/connexion.js"></script>
		<script type="text/javascript" src="js/script.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
		<script type="text/javascript" src="js/profile.js"></script>
		<link href="css/main5.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />

	</head>
	<body>
		<%@ page contentType="text/html;charset=UTF-8" language="java" %>
		<script type="text/javascript">
				function go(){
				<%   
					String id=request.getParameter("id");
					String login=request.getParameter("login");
					String key=request.getParameter("key");
	
					
					if((id!=null)&&(login!=null)&&(key!=null)){
						out.println("main ('"+id+"','"+login+"','"+key+"');");
						
					}else{
						out.println("main();");
					}
				

				%>
			};
            
			$(go);
		</script>
		<div id="header">
		<div id="top">
			<div id="logo"> <img id="logo_left" src="LogoTwit.png"></div>
			<div id="search">
				<form  action="javascript:(function(){return;})()">
				<div class="searchbar">
					<input type="text" name="search" value="	Cherchez-moi"/>
				</div>
				</form>
			</div>
			<div id="connect"><a href="connexion.html"><div class="cadre">Se connecter</div></a> <a href="enregistrement.html"><div class="cadre">Enregistrement</div></a>

			</div>
			<div id="disconnect"><a href="monprofil.html"><div class="cadre">Mon profil</div></a> <a href="enregistrement.html"><div class="cadre" id="deco">Se déconnecter</div></a></div>
			</div>
		

		<div id="nav-bar">
			<div id="nav-title">			
				<h2>Modifier mes informations</h2>
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
		</div>
		<div id="main">
				
<form class ="erreur_box" action="javascript:(function(){return;})()" onSubmit="javascript:setProfile(this)">
				
				<div class="ids"><span>Ancien mot de passe</span>
					<input type="password" name="pass" />
				</div>

				<div class="ids"><span>Nouveau mot de passe</span>
					<input type="password" name="newpass" />
				</div>
				
				
				
				<div class="ids"><span>Changer votre photo de profil. Vous êtes</span>
					<select name="photoprofil">
						<option value="1.jpg">Dieu</option>
		 				<option value="Ada_Lovelace.jpeg">Ada</option>
		 				<option value="AfghanGirl.jpg">Une jeune afghane</option>
		 				<option value="AmericanGothic.jpg">Un couple étrange</option>
		 				<option value="Astronaute.jpg">Sur la lune</option>
						<option value="Che.jpeg">El Che</option>
						<option value="DameHermine.jpg">Peinte par Léonard de Vinci</option>
						<option value="FilsHomme.jpg">Le Fils de l'Homme</option>
						<option value="JeuneFillePerle.jpg">La Jeune Fille à la Perle</option>
						<option value="LaLaitiere.jpg">En train de faire des yaourts</option>
						<option value="LeCri.jpg">Angoissé</option>
						<option value="LouisXIV.jpg">L'état</option>
						<option value="Napoleon.jpg">Petit</option>
						<option value="Turing.jpg">Adam moderne</option>
						<option value="US_Recession.jpg">En pleine récession</option>
	
					</select>		
				</div>
				<div class="buttons">
					<input type="submit" value="Changer" />
					<input type="reset" value="Annuler" />
				</div>	
			</form>	

			
			</div>
		<div id="footer"><div class="sub_footer"><h2>Informations</h2>Ce site est en test</div>
						<div class="sub_footer"><h2>Crédits</h2> Icones par <a href="http://www.elegantthemes.com/blog/freebie-of-the-week/beautiful-flat-icons-for-free">Nick Roach</div>
		</div>

			
	</body>
</html>
