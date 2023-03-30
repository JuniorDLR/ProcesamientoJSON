<?php



$mysql = new mysqli("localhost","root","","myuca");

if($mysql->connect_error){

    echo 'Error al conectarse a la base de datos';
}











?>