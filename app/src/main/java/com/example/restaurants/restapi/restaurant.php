<?php
header("Access-Control-Allow-Origin: *");
error_reporting(E_ERROR | E_PARSE);

$c = new mysqli("localhost","u1609257_adv","advNative","u1609257_adv");

if($c->connect_errno) {
	echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect DB'));
	die();
}

$sql = "";
if(isset($_GET["id"])){
	$id = $_GET["id"];
	$sql = "SELECT restaurants.*, ROUND((sum(rating)/count(rating)),1) AS totalrating, count(rating) AS countrating FROM restaurants LEFT JOIN ratings ON restaurants.id=ratings.restaurant_id WHERE restaurants.id=$id";
	$result = $c->query($sql);
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();

    	echo json_encode($row);
	} else {
		echo json_encode(array('message' => 'No data found'));
		die();
	}
	
}
else{
	$sql = "SELECT restaurants.*, ROUND((sum(rating)/count(rating)),1) AS totalrating, count(rating) AS countrating FROM restaurants LEFT JOIN ratings ON restaurants.id=ratings.restaurant_id GROUP BY restaurants.id";
	$result = $c->query($sql);
	$array = array();
	if ($result->num_rows > 0) {
		while ($obj = $result -> fetch_object()) {
			$array[] = $obj;
		}
		echo json_encode($array);
	} else {
		echo json_encode(array('message' => 'No data found'));
		die();
	}
}
?>