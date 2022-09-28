<?php

class FCM {
    function __construct() {
    }
   /**
    * Sending Push Notification
   */
  public function create_topic($token_id, $topic_id) {
	  $url = 'https://iid.googleapis.com/iid/v1:batchAdd';
	  // Firebase API Key
      $headers = array('FIREBASE_API_KEY','Content-Type:application/json');
      $fields = array('to' => $topic_id,'registration_tokens' => $token_id);
	  // Open connection
      $ch = curl_init();
      // Set the url, number of POST vars, POST data
      curl_setopt($ch, CURLOPT_URL, $url);
      curl_setopt($ch, CURLOPT_POST, true);
      curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
      curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
      // Disabling SSL Certificate support temporarly
      curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
      curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
      $result = curl_exec($ch);
      if ($result === FALSE) {
          die('Curl failed: ' . curl_error($ch));
      }
      curl_close($ch);
  }
  
  
  public function send_notification($registatoin_ids, $notification, $notification_type) {
      $url = 'https://fcm.googleapis.com/fcm/send';
	  
      if($notification_type == "Send Notification Specific"){
            $fields = array(
                'to' => $registatoin_ids,
                'data' => $notification
            );
      } else { //Send Notification Topic
            $fields = array(
                'to' => $registatoin_ids,
                'data' => $notification
            );
      }
	  
      // Firebase API Key
      $headers = array('FIREBASE_API_KEY','Content-Type:application/json');
     
	  // Open connection
      $ch = curl_init();
      // Set the url, number of POST vars, POST data
      curl_setopt($ch, CURLOPT_URL, $url);
      curl_setopt($ch, CURLOPT_POST, true);
      curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
      curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
      // Disabling SSL Certificate support temporarly
      curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
      curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
      $result = curl_exec($ch);
      if ($result === FALSE) {
          die('Curl failed: ' . curl_error($ch));
      }
      curl_close($ch);
  }
	  
}   

?>