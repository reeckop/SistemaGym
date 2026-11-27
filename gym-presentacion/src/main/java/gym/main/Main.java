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
import gym.servicios.interfaces.ICursoServicios;
import gym.servicios.interfaces.IInscripcionServicios;
import gym.servicios.interfaces.IProductoServicios;
import gym.servicios.interfaces.IVentaServicios;
import gym.servicios.servicios.CursoServicios;
import gym.servicios.servicios.InscripcionServicios;
import gym.servicios.servicios.ProductoServicios;
import gym.servicios.servicios.VentaServicios;

/**
 * Inicia el sistema de gestión del gimnasio.
 *
 * @author PC GAMER MASTER RACE
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                UnitOfWorkFactory unidadDeTrabajoFactory = UnitOfWorkFactory.obtenerInstancia();
                IEmpleadoServicios empleadoServicios = new EmpleadoServicios(unidadDeTrabajoFactory);
                IClienteServicios clienteServicios = new ClienteServicios(unidadDeTrabajoFactory);
                IMembresiaServicios membresiaServicios = new MembresiaServicios(unidadDeTrabajoFactory);
                IRegistroIngresoServicios registroIngresoServicios = new RegistroIngresoServicios(unidadDeTrabajoFactory);
                IProductoServicios productoServicios = new ProductoServicios(unidadDeTrabajoFactory);
                ICursoServicios cursoServicios = new CursoServicios(unidadDeTrabajoFactory);
                IVentaServicios ventaServicios = new VentaServicios(unidadDeTrabajoFactory);
                ICursoServicios curosServicios = new CursoServicios(unidadDeTrabajoFactory);
                IInscripcionServicios inscripcionServicios = new InscripcionServicios(unidadDeTrabajoFactory);
                EmpleadoDTO empleado = new EmpleadoDTO();

                iniciar_sesion pantalla = new iniciar_sesion(empleadoServicios, membresiaServicios, clienteServicios, registroIngresoServicios, productoServicios, ventaServicios, cursoServicios, inscripcionServicios, empleado);
                pantalla.setVisible(true);
            }
        });
    }
}
