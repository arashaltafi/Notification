<?php

if(isset($_POST["nId"]))
{

	$regId =$_POST["nId"];
	$nType =$_POST["notification_type"];

	// INCLUDE YOUR FCM FILE
	include_once 'fcm.php';    


// 	$username= array("Arash Altafi", "Ali Rezaei", "Hasan Jafari");
// 	$message= array("test message 1", "test message 2", "test message 3");
// 	$time= array("1664483217784", "1664483217784", "1664483217784");
	
// 	$statictic=array();
//     $record["username"]= $username;
//     $record["message"]= $message;
//     $record["time"]= $time;
    
// 	$statictic=$record;
//     $body = JSON_encode($statictic);
    
    
    $statictic=array();
    for ($x = 0; $x <= 2; $x++) {
        $record["text"]="hello $x";
        $record["user_name"]="arash altafi";
        $record["user_avatar"]="https://arashaltafi.ir/arash.jpg";
        $statictic[]=$record;
    }
    for ($x = 0; $x <= 4; $x++) {
        $record["text"]="hi $x";
        $record["user_name"]="jafar jafari";
        $record["user_avatar"]="https://arashaltafi.ir/arash.jpg";
        $statictic[]=$record;
    }
    $body = JSON_encode($statictic);


	$arrNotification["priority"] = "HIGH";				
	$arrNotification["body"] = $body;
	$arrNotification["title"] = "Message Notification";
	$arrNotification["sound"] = "default";
	$arrNotification["notification_id"] = "3";
	$arrNotification["type"] = 3;
	$arrNotification["image"] = "https://arashaltafi.ir/arash.jpg";
    

	$fcm = new FCM();
	$result = $fcm->send_notification($regId, $arrNotification, $nType);
	print_r($result);
}

?>