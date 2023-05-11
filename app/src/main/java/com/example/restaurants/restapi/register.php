<?php
header("Access-Control-Allow-Origin: *");
error_reporting(E_ERROR | E_PARSE);
$c = new mysqli("localhost","u1609257_adv","advNative","u1609257_adv");
if($c->connect_errno){
    echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect'));
}
$name = $_POST["name"];
$password = $_POST["password"];

$sql="INSERT INTO users(name,password) VALUES (?,?)";
$stmt = $c->prepare($sql);
$stmt->bind_param("ss", $name, $password);
$stmt->execute();

$sql2 = "SELECT * FROM users WHERE name=? AND password=?";
$stmt2 = $c->prepare($sql2);
$stmt2->bind_param("ss", $name, $password);
$stmt2->execute();
$result = $stmt2->get_result();

if($result->num_rows > 1){
    echo json_encode(array('result' => 'USERNAME_ERROR', 'sql error' => $sql));
}
else{
    if($stmt->affected_rows > 0){
        echo json_encode(array('result' => 'OK', 'sql' => $sql));
    }
    else{
        echo json_encode(array('result' => "ERROR", 'error' => $c->error));
    }
}
$stmt->close();
$stmt2->close();
$c->close();
?>