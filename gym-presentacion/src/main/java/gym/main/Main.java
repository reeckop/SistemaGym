/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gym.main;

import gym.login.iniciar_sesion;
import gym.servicios.interfaces.IEmpleadoServicios;
import gym.servicios.servicios.EmpleadoServicios;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {

            IEmpleadoServicios empleadoServicios = new EmpleadoServicios();

            iniciar_sesion pantalla = new iniciar_sesion(empleadoServicios);

            pantalla.setVisible(true);

        });
    }
}
