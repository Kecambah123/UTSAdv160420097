<?php
header("Access-Control-Allow-Origin: *");
error_reporting(E_ERROR | E_PARSE);
$c = new mysqli("localhost","u1609257_adv","advNative","u1609257_adv");
if($c->connect_errno){
    echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect'));
}
$resto_id = (int) $_GET["id"];


$sql="SELECT users.name, comments.comment FROM users RIGHT JOIN comments ON users.id=comments.user_id where restaurant_id=$resto_id";
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
?>