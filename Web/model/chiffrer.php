<?php

/*
	Permet de remplie une variable afin que la taille de la données soit compatible avec l'ago.
*/
function pkcs5_pad ($text, $blocksize)
{
    $pad = $blocksize - (strlen($text) % $blocksize);
    return $text . str_repeat(chr($pad), $pad);
} 

/*
	Permet d'enlver le remplissage d'une variable afin que la taille de la données soit compatible avec l'ago.
*/
function pkcs5_unpad($text)
{
    $pad = ord($text{strlen($text)-1});
    if ($pad > strlen($text)) return false;
    if (strspn($text, chr($pad), strlen($text) - $pad) != $pad) return false;
    return substr($text, 0, -1 * $pad);
}

/*
	Permet de changer le type de codage.
*/
function base64url_encode($data) {
  return rtrim(strtr(base64_encode($data), '+/', '-_'), '=');
}

/*
	Permet de revenir sur un codage.
*/
function base64url_decode($data) {
  return base64_decode(str_pad(strtr($data, '-_', '+/'), strlen($data) % 4, '=', STR_PAD_RIGHT));
}

// Je déchiffre le mot de passe.
function dechiffrement ()
{
	$json_source = $_GET['inputStream'];

	$json_data = json_decode($json_source);

	$int = count($json_data);

	/* Charge un chiffrement */
	$td = mcrypt_module_open('rijndael-128', '', 'cbc', '');
	$size = mcrypt_get_block_size('rijndael-128', 'cbc');

	/* Crée le VI et détermine la taille de la clé */
	$iv = imap_base64($json_data->IV);

	/* Crée la clé */
	$key = 'a7bc27daf59679de';

	/* Initialise le module de chiffrement pour le déchiffrement */
	mcrypt_generic_init($td, $key, $iv);

	/* Déchiffre les données */

	$identifiant = $json_data->identifiant;

	$identifiant = base64url_decode($identifiant);

	$decryptedMdp = mdecrypt_generic($td, $identifiant);

	$decryptedMdp = pkcs5_unpad($decryptedMdp, $size);

	$json_data->identifiant = $decryptedMdp;


	/* Libère le gestionnaire de déchiffrement, et ferme le module */
	mcrypt_generic_deinit($td);
	mcrypt_module_close($td);

	/* Charge un chiffrement */
	$td = mcrypt_module_open('rijndael-128', '', 'cbc', '');
	$size = mcrypt_get_block_size('rijndael-128', 'cbc');

	/* Crée le VI et détermine la taille de la clé */
	$iv = imap_base64($json_data->IV);

	/* Crée la clé */
	$key = 'a7bc27daf59679de';

	/* Initialise le module de chiffrement pour le déchiffrement */
	mcrypt_generic_init($td, $key, $iv);

	/* Déchiffre les données */

	$password = $json_data->password;

	$password = base64url_decode($password);

	$decryptedMdp = mdecrypt_generic($td, $password);

	$decryptedMdp = pkcs5_unpad($decryptedMdp, $size);

	$json_data->password = $decryptedMdp;

	/* Libère le gestionnaire de déchiffrement, et ferme le module */
	mcrypt_generic_deinit($td);
	mcrypt_module_close($td);

	return $json_data;
}

//Je déchiffre UNE donnée.
function dechiffrementdata ($data, $IV)
{
	/* Charge un chiffrement */
	$td = mcrypt_module_open('rijndael-128', '', 'cbc', '');
	$size = mcrypt_get_block_size('rijndael-128', 'cbc');

	/* Crée le VI et détermine la taille de la clé */
	$iv = imap_base64($IV);

	/* Crée la clé */
	$key = 'a7bc27daf59679de';

	/* Initialise le module de chiffrement pour le déchiffrement */
	mcrypt_generic_init($td, $key, $iv);

	$data = base64url_decode($data);

	/* Déchiffre les données */

	$data = mdecrypt_generic($td, $data);

	$data = pkcs5_unpad($data, $size);

	/* Libère le gestionnaire de déchiffrement, et ferme le module */
	mcrypt_generic_deinit($td);
	mcrypt_module_close($td);

	return $data;
}

//Je déchiffre une liste.
function dechiffrementlist ()
{
	$json_source = $_GET['inputStream'];

	$json_data = json_decode($json_source);

	$maxConteur = count($json_data->conteneurFichier);

	$conteur = 0;

	/* Charge un chiffrement */
	$td = mcrypt_module_open('rijndael-128', '', 'cbc', '');
	$size = mcrypt_get_block_size('rijndael-128', 'cbc');

	/* Crée le VI et détermine la taille de la clé */
	$iv = imap_base64($json_data->IV);

	/* Crée la clé */
	$key = 'a7bc27daf59679de';

	/* Initialise le module de chiffrement pour le déchiffrement */
	mcrypt_generic_init($td, $key, $iv);

	/* Déchiffre les données */

	$identifiant = $json_data->identifiant;

	$identifiant = base64url_decode($identifiant);

	$decryptedMdp = mdecrypt_generic($td, $identifiant);

	$decryptedMdp = pkcs5_unpad($decryptedMdp, $size);

	$json_data->identifiant = $decryptedMdp;

	/* Libère le gestionnaire de déchiffrement, et ferme le module */
	mcrypt_generic_deinit($td);
	mcrypt_module_close($td);

		/* Charge un chiffrement */
	$td = mcrypt_module_open('rijndael-128', '', 'cbc', '');
	$size = mcrypt_get_block_size('rijndael-128', 'cbc');

	/* Crée le VI et détermine la taille de la clé */
	$iv = imap_base64($json_data->IV);

	/* Crée la clé */
	$key = 'a7bc27daf59679de';

	/* Initialise le module de chiffrement pour le déchiffrement */
	mcrypt_generic_init($td, $key, $iv);

	/* Déchiffre les données */

	$code = $json_data->code;

	$code = base64url_decode($code);

	$decryptedMdp = mdecrypt_generic($td, $code);

	$decryptedMdp = pkcs5_unpad($decryptedMdp, $size);

	$json_data->code = $decryptedMdp;

	/* Libère le gestionnaire de déchiffrement, et ferme le module */
	mcrypt_generic_deinit($td);
	mcrypt_module_close($td);

		for ($conteur=0; $conteur < $maxConteur; $conteur++) { 
			/* Charge un chiffrement */
			$td = mcrypt_module_open('rijndael-128', '', 'cbc', '');
			$size = mcrypt_get_block_size('rijndael-128', 'cbc');

			/* Crée le VI et détermine la taille de la clé */
			$iv = imap_base64($json_data->IV);

			/* Crée la clé */
			$key = 'a7bc27daf59679de';

			/* Initialise le module de chiffrement pour le déchiffrement */
			mcrypt_generic_init($td, $key, $iv);

			/* Déchiffre les données */

			$name = $json_data->conteneurFichier[$conteur]->name;

			$name = base64url_decode($name);

			$decryptedMdp = mdecrypt_generic($td, $name);

			$decryptedMdp = pkcs5_unpad($decryptedMdp);

			$json_data->conteneurFichier[$conteur]->name = $decryptedMdp;

			/* Libère le gestionnaire de déchiffrement, et ferme le module */
			mcrypt_generic_deinit($td);
			mcrypt_module_close($td);

			/* Charge un chiffrement */
			$td = mcrypt_module_open('rijndael-128', '', 'cbc', '');
			$size = mcrypt_get_block_size('rijndael-128', 'cbc');

			/* Crée le VI et détermine la taille de la clé */
			$iv = imap_base64($json_data->IV);

			/* Crée la clé */
			$key = 'a7bc27daf59679de';

			/* Initialise le module de chiffrement pour le déchiffrement */
			mcrypt_generic_init($td, $key, $iv);

			/* Déchiffre les données */

			$content = $json_data->conteneurFichier[$conteur]->content;

			$content = base64url_decode($content);

			$decryptedMdp = mdecrypt_generic($td, $content);

			$decryptedMdp = pkcs5_unpad($decryptedMdp);

			$json_data->conteneurFichier[$conteur]->content = $decryptedMdp;

			/* Libère le gestionnaire de déchiffrement, et ferme le module */
			mcrypt_generic_deinit($td);
			mcrypt_module_close($td);

			/* Charge un chiffrement */
			$td = mcrypt_module_open('rijndael-128', '', 'cbc', '');
			$size = mcrypt_get_block_size('rijndael-128', 'cbc');

			/* Crée le VI et détermine la taille de la clé */
			$iv = imap_base64($json_data->IV);

			/* Crée la clé */
			$key = 'a7bc27daf59679de';

			/* Initialise le module de chiffrement pour le déchiffrement */
			mcrypt_generic_init($td, $key, $iv);

			/* Déchiffre les données */

			$path = $json_data->conteneurFichier[$conteur]->path;

			$path = base64url_decode($path);

			$decryptedMdp = mdecrypt_generic($td, $path);

			$decryptedMdp = pkcs5_unpad($decryptedMdp);

			$json_data->conteneurFichier[$conteur]->path = $decryptedMdp;

			/* Libère le gestionnaire de déchiffrement, et ferme le module */
			mcrypt_generic_deinit($td);
			mcrypt_module_close($td);

			/* Charge un chiffrement */
			$td = mcrypt_module_open('rijndael-128', '', 'cbc', '');
			$size = mcrypt_get_block_size('rijndael-128', 'cbc');

			/* Crée le VI et détermine la taille de la clé */
			$iv = imap_base64($json_data->IV);

			/* Crée la clé */
			$key = 'a7bc27daf59679de';

			/* Initialise le module de chiffrement pour le déchiffrement */
			mcrypt_generic_init($td, $key, $iv);

			/* Déchiffre les données */

			$updated_at = $json_data->conteneurFichier[$conteur]->updated_at;

			$updated_at = base64url_decode($updated_at);

			$decryptedMdp = mdecrypt_generic($td, $updated_at);

			$decryptedMdp = pkcs5_unpad($decryptedMdp);

			$json_data->conteneurFichier[$conteur]->updated_at = $decryptedMdp;

			/* Libère le gestionnaire de déchiffrement, et ferme le module */
			mcrypt_generic_deinit($td);
			mcrypt_module_close($td);
		}

	return $json_data;
}

//Je chiffre les donnés.
function chiffrement($decryptedMdp, $IV)
{
	/* Charge un chiffrement */
	$td = mcrypt_module_open('rijndael-128', '', 'cbc', '');
	$size = mcrypt_get_block_size('rijndael-128', 'cbc');

	/* Crée le VI et détermine la taille de la clé */
	$iv = imap_base64($IV);

	/* Crée la clé */
	$key = 'a7bc27daf59679de';

	$decryptedMdp = pkcs5_pad($decryptedMdp, $size);

	/* Initialise le module de chiffrement pour le déchiffrement */
	mcrypt_generic_init($td, $key, $iv);

	/* Chiffre les données */
	$mdpEncrypted = mcrypt_generic($td, $decryptedMdp);

	/* Libère le gestionnaire de chiffrement */
	mcrypt_generic_deinit($td);

	return $mdpEncrypted;
}

?>