<?php

if ($_SERVER["REQUEST_METHOD"] == "POST") {

    require_once 'conexion.php';

    $nombres = $_POST['nombres'];
    $apellidos = $_POST['apellidos'];
    $fechaNac = $_POST['fechaNac'];
    $titulo = $_POST['titulo'];
    $email = $_POST['email'];
    $facultad = $_POST['facultad'];


    $stm = $mysql->prepare('INSERT INTO coordinador (nombres,apellidos,fechaNac,titulo,email,facultad)VALUES(?,?,?,?,?,?)');
    $stm->bind_param('ssssss', $nombres, $apellidos, $fechaNac, $titulo, $email, $facultad);
    $stm->execute();
    $stm->close();


    if ($stm == true) {

        echo 'Datos guardados exitosamente';
    } else {
        echo 'Error al guardar los datos';
    }
}
