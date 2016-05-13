<?php

//Je déchiffre le mot de passe.
function Decode_json ($encrypted)
{
	$chain = json_decode($encrypted, $assoc = false);

	return $chain;
}

//Je déchiffre le mot de passe.
function Encode_json ($encrypted)
{
	$chain = json_encode($encrypted);

	return $chain;
}

?>