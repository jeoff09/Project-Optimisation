<?php
	
	/*
		On créer la connection
	*/
	function firstConnection ($id, $mdp,$IV) {
		if (file_exists("C:\\wamp64\\www\\Web\\".$id) != true) {
			$chemin = "C:\\wamp64\\www\\Web\\";
			//On creer un dossier.
			mkdir ($chemin.$id);
		}

		//On creer un fichier ou on stocke le mdp.
		$chemin = "C:\\wamp64\\www\\Web\\".$id."\\Confg.txt";
		$handle = fopen($chemin, "w+");
		$mdp = chiffrement($mdp, $IV);
		$mdp =	base64url_encode($mdp);
		fwrite($handle, "mdp:".$mdp."\r\n");
		$min = rand(0, 1000);
		$max = rand(100000, 1000000);
		$code= rand($min, $max);
		$code = chiffrement($code, $IV);
		$code =	base64url_encode($code);
		fwrite($handle, "Code:".$code);
		fclose($handle);
		return $code;
	}

	/*
		On créer la connexion
	*/
	function connection ($decryptedTabIdMdp) {
		//On verifie si le dossier existe
		if (file_exists("C:\\wamp64\\www\\Web\\".$decryptedTabIdMdp->identifiant) != false) {
			//Si oui on ouvre le dossier et on recupere le mot de passe.
			$chemin = "C:\\wamp64\\www\\Web\\";
			$handle = fopen($chemin.$decryptedTabIdMdp->identifiant."/Confg.txt", "r+");
			$mdp2 = fgets($handle);
			$mdp2 = explode(":",$mdp2);
			$mdp2 = $mdp2[1];
			$mdp2 = dechiffrementdata($mdp2, $decryptedTabIdMdp->IV);
			fclose($handle);
			//On verifie le mot de passe
			if (strcmp($mdp2,$decryptedTabIdMdp->password)) {
				// Si la comparraison est ok on renvoie vrai
				unlink("C:\\wamp64\\www\\Web\\".$decryptedTabIdMdp->identifiant."\\Confg.txt");
				$code = firstConnection($decryptedTabIdMdp->identifiant, $decryptedTabIdMdp->password, $decryptedTabIdMdp->IV);
				$ok = "vrai\\\\".$code;
			}
			else {
				// Si la comparraison n'est pas correcte on renvoie faux
				$Anwser = 'null';
				$ok = "faux\\\\".$Anwser;
			}
			return $ok;
		}else{

			// Si le dossier n'existe pas on renvoie faux
			$code = firstConnection($decryptedTabIdMdp->identifiant, $decryptedTabIdMdp->password, $decryptedTabIdMdp->IV);
    		return $ok = "vrai\\\\".$code;
   		}
	}

?>