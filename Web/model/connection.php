<?php
	
	//$link = mysql_connect("localhost", "mysql_user", "mysql_password")
	//or die($error = "Impossible de se connecter : " . mysql_error());
	//mysql_close($link);

	function firstConnection ($id, $mdp) {
		//On creer un dossier.
		mkdir ("/" + $id)

		//On creer un fichier ou on stocke le mdp.
		$handle = fopen("/" + $id + "/Confg.txt", "w+");
		fwrite($handle, $mdp);
		fclose($handle);
	}

	function connection ($id, $mdp) {
		//On verifie si le dossier existe
		if (file_exists("/" + $id)) {

			//Si oui on ouvre le dossier et on recupere le mot de passe.
			$handle = fopen("/" + $id + "/Confg", "w+");
			$mdp2 = fgets ( $handle);

			//On verifie le mot de passe
			if (strcmp ($mdp2, $mdp)) {
				// Si la comparraison est ok on renvoie vrai
				$ok = TRUE;
			}
			else {
				// Si la comparraison n'est pas correcte on renvoie faux
				$ok = FALSE;
			}

			return $ok

		} else {

			// Si le dossier n'existe pas on renvoie faux
			$ok = FALSE;

    		return $ok;
   		}
	}

	function deconnection ($link) {
	}

?>
