<?php

	$response = array();

	require_once __DIR__ . '/db_connect.php';
	//open a connection
	$db = new DB_CONNECT();

	// Where the file is going to be placed 
	$target_path = "../audio/";

	/* Add the original filename to our target path.  
	Result is "uploads/filename.extension" */
	$target_path = $target_path . basename( $_FILES['file']['name']); 

	$response["part1"] = "part1";

	if(move_uploaded_file($_FILES['file']['tmp_name'], $target_path)) {
		echo "The file ".  basename( $_FILES['uploadedfile']['name']). 
		" has been uploaded";
		chmod ("uploads/".basename( $_FILES['uploadedfile']['name']), 0644);
		$response["success"] = 1;
		$response["message"] ="File successfully uploaded";
		echo json_encode($response);
	} 

	$response["part2"] = "part2";
	else{
		echo "There was an error uploading the file, please try again!";
		echo "filename: " .  basename( $_FILES['uploadedfile']['name']);
		echo "target_path: " .$target_path;
		$response["success"] = 0;
		$response["message"] ="File upload failed";
		echo json_encode($response);
	}

	$response["part3"] = "part3";

	
?>