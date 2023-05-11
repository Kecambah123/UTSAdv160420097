<?php
header("Access-Control-Allow-Origin: *");
error_reporting(E_ERROR | E_PARSE);
$c = new mysqli("localhost","u1609257_adv","advNative","u1609257_adv");
if($c->connect_errno){
    echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect'));
}
$comment = $_POST["comment"];
$user_id = (int) $_POST["user_id"];
$resto_id = (int) $_POST["resto_id"];

$sql="INSERT INTO comments(comment,user_id,restaurant_id) VALUES (?,?,?)";
$stmt = $c->prepare($sql);
$stmt->bind_param("sii", $comment, $user_id, $resto_id);
$stmt->execute();

if($stmt->affected_rows > 0){
    echo json_encode(array('result' => 'OK', 'sql' => $sql));
}
else{
    echo json_encode(array('result' => "ERROR", 'error' => $c->error));
}
$stmt->close();
$c->close();
?>