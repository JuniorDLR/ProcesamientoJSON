<?php

if($_SERVER["REQUEST_METHOD"]=="DELETE"){

    require_once 'conexion.php';
 
    $data = json_decode(file_get_contents("php://input"));
    $idC = $data->idC;

    $sql="DELETE FROM `coordinador` WHERE idC=$idC";
    $resultado = $mysql->query($sql);

    if($resultado == true){

        echo 'Datos eliminado';
     }
     else{

        echo 'Error al eliminar los datos';
     }
}



?>