package es.prog2425.taskmanager

import es.prog2425.taskmanager.presentacion.Consola
import es.prog2425.taskmanager.servicios.GestorActividades
import es.prog2425.taskmanager.servicios.GestorMenu

fun main() {
    GestorMenu(Consola(),GestorActividades()).mostrarMenuPrincipal()
}