<?php 
header("Access-Control-Allow-Origin: *");
error_reporting(E_ERROR | E_PARSE);
$c = new mysqli("localhost","u1609257_adv","advNative","u1609257_adv");
if($c->connect_errno){
    echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect'));
}
$name = $_POST["name"];
$password = $_POST["password"];

$sql = "SELECT * FROM users WHERE name=? AND password=?";
$stmt = $c->prepare($sql);
$stmt->bind_param("ss", $name, $password);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $r=mysqli_fetch_assoc($result);
    echo json_encode(array("result"=>"success","user_id"=>$r['id']));
} else {
    echo json_encode(array("result"=>"error","message"=>"sql error: $sql"));
}
$stmt->close();
$c->close();
?>