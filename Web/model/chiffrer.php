<?php

//Je déchiffre le mot de passe.
function dechiffrementMdp ($encrypted)
{

/* Charge un chiffrement */
$td = mcrypt_module_open('rijndael-192', '', 'ofb', '');

/* Crée le VI et détermine la taille de la clé */
$iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($td), MCRYPT_DEV_RANDOM);
$ks = mcrypt_enc_get_key_size($td);

/* Crée la clé */
$key = substr(md5('very secret key'), 0, $ks);

/* Initialise le module de chiffrement pour le déchiffrement */
mcrypt_generic_init($td, $key, $iv);

/* Déchiffre les données */
$decryptedMdp = mdecrypt_generic($td, $encrypted);

/* Libère le gestionnaire de déchiffrement, et ferme le module */
mcrypt_generic_deinit($td);
mcrypt_module_close($td);

return $decryptedMdp;
}

//Je rechiffre le mot de passe mais en rijndael-256.
function chiffrementMdp($decryptedMdp)
{

//$secret = serialize(arrayNom($nom))

/* Charge un chiffrement */
$td = mcrypt_module_open('rijndael-256', '', 'ofb', '');

/* Crée le VI et détermine la taille de la clé */
$iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($td), MCRYPT_DEV_RANDOM);
$ks = mcrypt_enc_get_key_size($td);

/* Crée la clé */
$key = substr(md5('very secret key'), 0, $ks);

/* Intialise le chiffrement */
mcrypt_generic_init($td, $key, $iv);

/* Chiffre les données */
$mdpEncrypted = mcrypt_generic($td, $decryptedMdp);

/* Libère le gestionnaire de chiffrement */
mcrypt_generic_deinit($td);

return $mdpEncrypted;
}

//Je chiffre la liste des documents dont le serveur à besoin.
function chiffrementListe($mdpEncrypted, arrayNom($nom))
{

$secret = serialize(arrayNom($nom))

/* Charge un chiffrement */
$td = mcrypt_module_open('rijndael-256', '', 'ofb', '');

/* Crée le VI et détermine la taille de la clé */
$iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($td), MCRYPT_DEV_RANDOM);
$ks = mcrypt_enc_get_key_size($td);

/* Crée la clé */
$key = substr($mdpEncrypted, 0, $ks);

/* Intialise le chiffrement */
mcrypt_generic_init($td, $key, $iv);

/* Chiffre les données */
$encrypted = mcrypt_generic($td, $decryptedMdp);

/* Libère le gestionnaire de chiffrement */
mcrypt_generic_deinit($td);

return $encrypted;
}

//Je déchiffre la liste des documentus ou les documents que j'ai demandé.
function dechiffrement ($encrypted, $mdpEncrypted)
{

/* Charge un chiffrement */
$td = mcrypt_module_open('rijndael-256', '', 'ofb', '');

/* Crée le VI et détermine la taille de la clé */
$iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($td), MCRYPT_DEV_RANDOM);
$ks = mcrypt_enc_get_key_size($td);

/* Crée la clé */
$key = substr($mdpEncrypted, 0, $ks);

/* Initialise le module de chiffrement pour le déchiffrement */
mcrypt_generic_init($td, $key, $iv);

/* Déchiffre les données */
$decrypted = mdecrypt_generic($td, $encrypted);

/* Libère le gestionnaire de déchiffrement, et ferme le module */
mcrypt_generic_deinit($td);
mcrypt_module_close($td);

$decryptedMdp = unserialize($decrypted);

return $decryptedMdp;
}

?>