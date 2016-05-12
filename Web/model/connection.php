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

	function connection ($decryptedTabIdMdp) {
		//On verifie si le dossier existe
		if ($decryptedTabIdMdp['first_connection'] == FALSE) {
			if (file_exists("/" + $decryptedTabIdMdp['identifiant'])) {

				//Si oui on ouvre le dossier et on recupere le mot de passe.
				$handle = fopen("/" + $decryptedTabIdMdp['identifiant'] + "/Confg", "w+");
				$mdp2 = fgets ( $handle);

				//On verifie le mot de passe
				if (strcmp ($mdp2, $decryptedTabIdMdp['password'])) {
					// Si la comparraison est ok on renvoie vrai
					$ok = echo(TRUE);
				}
				else {
					// Si la comparraison n'est pas correcte on renvoie faux
					$Anwser = 'Mot de passe inccorect';
					$ok = echo(FALSE, $Anwser);
				}

				return $ok;

			} else {

				// Si le dossier n'existe pas on renvoie faux
				$Anwser = 'Compte non trouvé';
				$ok = echo(FALSE, $Anwser);

	    		return $ok;
	   		}
   		}else{
   			firstConnection($decryptedTabIdMdp['identifiant'], $decryptedTabIdMdp['password']);
   		}
	}

	function deconnection ($link) {
		return $ok = echo(FALSE);
	}

?>