<?php
header("Access-Control-Allow-Origin: *");
error_reporting(E_ERROR | E_PARSE);

$c = new mysqli("localhost","u1609257_adv","advNative","u1609257_adv");

if($c->connect_errno) {
	echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect DB'));
	die();
}
$user_id = (int) $_POST["user_id"];

$sql = "SELECT name,password FROM users WHERE id=?";
$stmt = $c->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $r=mysqli_fetch_assoc($result);
    echo json_encode(array("result"=>"OK","name"=>$r['name'],"password"=>$r['password']));
} else {
    echo json_encode(array("result"=>"error","message"=>"sql error: $sql"));
}
$stmt->close();
$c->close();
?>