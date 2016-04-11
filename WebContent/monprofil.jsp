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
	<body onload="javascript:main()">
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
						out.println("Erreur, Veuillez vous connecter");
					}
					

				%>
			};
            
			$(go);
		</script>
		<div id="header">
		<div id="top">
			<div id="logo"> <a href="main.jsp"><img id="logo_left" src="LogoTwit.png"></a> </div>
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
				
Modifiez votre profil !

			
			</div>
		<div id="footer"><div class="sub_footer"><h2>Informations</h2>Ce site est en test</div>
						<div class="sub_footer"><h2>Crédits</h2> Icones par <a href="http://www.elegantthemes.com/blog/freebie-of-the-week/beautiful-flat-icons-for-free">Nick Roach</div>
		</div>

			
	</body>
</html>
