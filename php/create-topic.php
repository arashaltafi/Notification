<?php

if(isset($_POST["topicId"])){

	$topicId =$_POST["topicId"];
	$tokenId =$_POST["tokenId"];

	// INCLUDE YOUR FCM FILE
	include_once 'fcm.php';    

	$fcm = new FCM();
	$result = $fcm->create_topic($tokenId, $topicId);
	print_r($result);
}

?>