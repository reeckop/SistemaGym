/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gym.main;

import gym.login.iniciar_sesion;
import gym.servicios.dto.EmpleadoDTO;
import gym.servicios.interfaces.IClienteServicios;
import gym.servicios.interfaces.IEmpleadoServicios;
import gym.servicios.interfaces.IMembresiaServicios;
import gym.servicios.interfaces.IRegistroIngresoServicios;
import gym.servicios.servicios.ClienteServicios;
import gym.servicios.servicios.EmpleadoServicios;
import gym.servicios.servicios.MembresiaServicios;
import gym.servicios.servicios.RegistroIngresoServicios;
import gym.persistencia.unitofwork.UnitOfWorkFactory;

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
            UnitOfWorkFactory unidadDeTrabajoFactory = UnitOfWorkFactory.obtenerInstancia();
            IEmpleadoServicios empleadoServicios = new EmpleadoServicios(unidadDeTrabajoFactory);
            IClienteServicios clienteServicios = new ClienteServicios(unidadDeTrabajoFactory);
            IMembresiaServicios membresiaServicios = new MembresiaServicios(unidadDeTrabajoFactory);
            IRegistroIngresoServicios registroIngresoServicios = new RegistroIngresoServicios(unidadDeTrabajoFactory);
            EmpleadoDTO empleado = new EmpleadoDTO();

            iniciar_sesion pantalla = new iniciar_sesion(empleadoServicios, membresiaServicios, clienteServicios, registroIngresoServicios, empleado);

            pantalla.setVisible(true);

        });
    }
}
