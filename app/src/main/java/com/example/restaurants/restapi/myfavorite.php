<?php
header("Access-Control-Allow-Origin: *");
error_reporting(E_ERROR | E_PARSE);

$c = new mysqli("localhost","u1609257_adv","advNative","u1609257_adv");

if($c->connect_errno) {
	echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect DB'));
	die();
}
$user_id= (int) $_GET['user_id'];

$sql = "SELECT restaurants.*, ROUND((sum(ratings.rating)/count(ratings.rating)),1) AS totalrating, count(ratings.restaurant_id) AS countrating FROM ratings INNER JOIN restaurants ON ratings.restaurant_id=restaurants.id INNER JOIN favorites on restaurants.id=favorites.restaurant_id where favorites.user_id=? GROUP BY restaurants.id";
$stmt = $c->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();
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
$stmt->close();
$c->close();
?>