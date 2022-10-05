<?php

if(isset($_POST["nId"]))
{

	$regId =$_POST["nId"];
	$nType =$_POST["notification_type"];

	// INCLUDE YOUR FCM FILE
	include_once 'fcm.php';    

	$bodyNotification= array("test 1", "test 2", "test 3", "test 4");
	$statictic=array();
    $record["body"]= $bodyNotification;
	$statictic=$record;
    $body = JSON_encode($statictic);

	$arrNotification["priority"] = "HIGH";				
	$arrNotification["body"] = $body;
	$arrNotification["title"] = "PHP Push Notification";
	$arrNotification["sound"] = "default";
	$arrNotification["notification_id"] = "2";
	$arrNotification["type"] = 2;
	$arrNotification["image"] = "https://arashaltafi.ir/arash.jpg";

	$fcm = new FCM();
	$result = $fcm->send_notification($regId, $arrNotification, $nType);
	print_r($result);
}

?>