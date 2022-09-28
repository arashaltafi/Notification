<!DOCTYPE html>
<html>
<head>
  <title>PHP Send Push Notification To Android</title>
 <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
 <div class="container">

    <br>
    <br>
	<!-- Send Notification -->
    <div class="row">
      <div class="col-md-9">
        <form action="send-notification.php" method="post" accept-charset="utf-8">

          <div class="form-group">
            <label for="formGroupExampleInput">Notification Type</label>
              <select class="form-control" id="notification_type" name="notification_type" required="">
              <option value="">Select Notification Type</option>
               
                    <option value="Specific">Send Notification Specific</option>
                    <option value="Topic">Send Notification Topic</option>
  
              </select>
          </div>           

          <div class="form-group">
            <label for="formGroupExampleInput">Token / Topic</label>
            <input type="text" name="nId" class="form-control" id="formGroupExampleInput" placeholder="Please enter token or topic" required="">
            
          </div> 

          <div class="form-group">
           <button type="submit" id="send_form" class="btn btn-success">Send Notification</button>
          </div>
        </form>
      </div>
    </div>
	
	<br/>
	<br/>
	<br/>
	<!-- Create Topic -->
	<div class="row">
      <div class="col-md-9">
        <form action="create-topic.php" method="post" accept-charset="utf-8">       

          <div class="form-group">
            <label for="formGroupExampleInput">Token Id</label>
            <input type="text" name="tokenId" class="form-control" id="formGroupExampleInput" placeholder="Please enter topic name" required="">
          </div> 
		  
		  <div class="form-group">
            <label for="formGroupExampleInput">Topic Name</label>
            <input type="text" name="topicId" class="form-control" id="formGroupExampleInput" placeholder="Please enter token name" required="">
          </div> 

          <div class="form-group">
           <button type="submit" id="send_form" class="btn btn-success">Create Topic</button>
          </div>
        </form>
      </div>
    </div>
 
</div>
</body>
</html>