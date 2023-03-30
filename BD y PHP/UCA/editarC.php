<?php

require_once 'conexion.php';

$data = json_decode(file_get_contents("php://input"), true);

$sql = "UPDATE coordinador SET nombres='" . $data["nombres"] . "', apellidos='" . $data["apellidos"] .
    "', fechaNac='" . $data["fechaNac"] . "', titulo='" . $data["titulo"] . "', email='" . $data["email"] . 
    "', facultad='" . $data["facultad"] . "' WHERE coordinador.idC=2";

$resultado = $mysql->query($sql);

if ($resultado == true) {
    echo 'Datos Actualizados';
} else {
    echo 'Error al actualizar datos';
}

?>
