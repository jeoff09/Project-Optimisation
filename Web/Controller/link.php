<?php

include '../model/chiffrer.php';
include '../model/connection.php';
include '../model/data.php';
include '../model/json.php';

$decryptedTabIdMdp = dechiffrementlist();

$ok = verifCode($decryptedTabIdMdp);

if ($ok != "faux\\\\null") {
	$ok = verifieList($decryptedTabIdMdp);
	echo $ok;
} else {
	echo $ok;
}


?>