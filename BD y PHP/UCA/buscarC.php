<?php


if ($_SERVER["REQUEST_METHOD"] == "GET") {
    require_once 'conexion.php';

    $data = json_decode(file_get_contents("php://input"));
    $idC = $data->idC;


    $sql = "SELECT * FROM coordinador WHERE idC=$idC";
    $resultado = $mysql->query($sql);


    if ($mysql->affected_rows > 0) {

        $json = "{\"data\": ";

        while ($row = $resultado->fetch_assoc()) {

            $json =$json.json_encode($row);
            $json =$json.",";
        }
        $json=substr(trim($json),0,-1);
        $json.$json."}}";
    }
    echo $json;
    
}
