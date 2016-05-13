<?php
	
	/*
		Cette fonction permet de vérifié le code donnée par l'utilisateur et le code gardé dans le fichier 
	*/
	function verifCode($decryptedTabIdMdp) {
		if (file_exists("C:\\wamp64\\www\\Web\\".$decryptedTabIdMdp->identifiant) == true) {
			//Si oui on ouvre le dossier et on recupere le mot de passe.
			$handle = fopen("C:\\wamp64\\www\\Web\\".$decryptedTabIdMdp->identifiant."\\Confg.txt", "r+");
			fgets($handle);
			$Code = fgets($handle);
			$Code = explode(":", $Code);
			$Code = $Code[1];
			fclose($handle);
			$Code = dechiffrementdata($Code, $decryptedTabIdMdp->IV);

			//On verifie le mot de passe
			if ($Code == $decryptedTabIdMdp->code) {
				// Si la comparraison est ok on renvoie vrai
				$ok = "vrai\\\\".$Code;
			}
			else {
				// Si la comparraison n'est pas correcte on renvoie faux
				$Anwser = 'null';
				$ok = "faux\\\\".$Anwser;
			}
			return $ok;
		}
		else{
			$Anwser = 'null';
			$ok = "faux\\\\".$Anwser;	
		}
	}

	/*
		Cette fonction permet de vérifié la liste des fichiers à créer. 
	*/
	function verifieList($decryptedTabIdMdp) {
		$maxConteur = count($decryptedTabIdMdp->conteneurFichier);
		$conteur = 0;
		/*
			On regarde le tableau contenu dans le json
		*/
		for ($conteur=0; $conteur < $maxConteur; $conteur++) { 
			$path = $decryptedTabIdMdp->conteneurFichier[$conteur]->path;
			$repertory = explode("/", $path);
			$pathDoc = "C:\\wamp64\\www\\Web\\".$decryptedTabIdMdp->identifiant;
			/*
				Chaque élements du tableau est un fichier qui doit être contenu. 
			*/
			for ($i=0; $i < count($repertory) - 1; $i++) { 
				if (file_exists($pathDoc."\\".$repertory[$i]) == true){
					$pathDoc = $pathDoc."\\".$repertory[$i];
				}else{
					$pathDoc = $pathDoc."\\".$repertory[$i];
					mkdir($pathDoc);
				}
			}
			if (file_exists($pathDoc."\\".$decryptedTabIdMdp->conteneurFichier[$conteur]->name) != true){
				$pathDoc = $pathDoc."\\".$decryptedTabIdMdp->conteneurFichier[$conteur]->name;
				$handle = fopen($pathDoc, "w+");
				fwrite($handle, $decryptedTabIdMdp->conteneurFichier[$conteur]->content);
			}
			else{
				$pathDoc = $pathDoc."\\".$decryptedTabIdMdp->conteneurFichier[$conteur]->name;
				$dateUp = filemtime($pathDoc);
				if ($dateUp != $decryptedTabIdMdp->conteneurFichier[$conteur]->updated_at){
					$handle = fopen($pathDoc, "w+");
					$chaine = $decryptedTabIdMdp->conteneurFichier[$conteur]->content;
					fwrite($handle, $chaine);
				}
				////-- Amélioration renvoyer le fichier si la date de modification est inférieur à celle en base --////
			}			
		}
		return $ok = "vrai";
	}	
?>