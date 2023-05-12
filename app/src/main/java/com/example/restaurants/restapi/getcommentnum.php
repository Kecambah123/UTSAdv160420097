<?php
header("Access-Control-Allow-Origin: *");
error_reporting(E_ERROR | E_PARSE);
$c = new mysqli("localhost","u1609257_adv","advNative","u1609257_adv");
if($c->connect_errno){
    echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect'));
}
$resto_id = (int) $_GET["id"];

$sql="SELECT count(comment) as comment_num FROM comments WHERE restaurant_id=$resto_id";
$result = $c->query($sql);
if ($result->num_rows > 0) {
	$row = $result->fetch_assoc();

    echo json_encode($row);
} else {
	echo json_encode(array('message' => 'No data found'));
	die();
}
?>