<?php
header("Access-Control-Allow-Origin: *");
error_reporting(E_ERROR | E_PARSE);

$c = new mysqli("localhost","u1609257_adv","advNative","u1609257_adv");

if($c->connect_errno) {
	echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect DB'));
	die();
}
$new_name = $_POST["name"];
$new_pwd = $_POST["password"];
$user_id = (int) $_POST["user_id"];

$sql = "UPDATE users SET name='$new_name', password='$new_pwd' WHERE id='$user_id'";
$result = $c->query($sql);

if($result->num_rows > 0){
    echo json_encode(array('result' => 'OK', 'sql' => $sql));
}
else{
    echo json_encode(array('result' => "ERROR", 'error' => $c->error));
}
$c->close();
?>