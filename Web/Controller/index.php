<?php

include '../model/chiffrer.php';
include '../model/connection.php';
include '../model/data.php';
include '../model/json.php';

$decryptedTabIdMdp = dechiffrement();

$ok = connection($decryptedTabIdMdp);

echo $ok;

?>