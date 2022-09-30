<?php

if(isset($_POST["nId"]))
{

	$regId =$_POST["nId"];
	$nType =$_POST["notification_type"];

	// INCLUDE YOUR FCM FILE
	include_once 'fcm.php';    


	$username= array("Arash Altafi", "Ali Rezaei", "Hasan Jafari");
	$message= array("test message 1", "test message 2", "test message 3");
	$time= array("1664483217784", "1664483217784", "1664483217784");
	
	$statictic=array();
    $record["username"]= $username;
    $record["message"]= $message;
    $record["time"]= $time;
    
	$statictic=$record;
    $body = JSON_encode($statictic);


	$arrNotification["priority"] = "HIGH";				
	$arrNotification["body"] = $body;
	$arrNotification["title"] = "Message Notification";
	$arrNotification["sound"] = "default";
	$arrNotification["notification_id"] = "1";
	$arrNotification["type"] = 1;
	$arrNotification["image"] = "https://arashaltafi.ir/arash.jpg";
    

	$fcm = new FCM();
	$result = $fcm->send_notification($regId, $arrNotification, $nType);
	print_r($result);
}

?>