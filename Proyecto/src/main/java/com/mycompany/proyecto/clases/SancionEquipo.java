/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.clases;

/**
 *
 * @author Usuario
 */
public class SancionEquipo {
    private int idSancion;
    private String idEquipos;

    // Constructor para inserción y lectura
    public SancionEquipo(int idSancion, String idEquipos) {
        this.idSancion = idSancion;
        this.idEquipos = idEquipos;
    }

    public int getIdSancion() {
        return idSancion;
    }

    public void setIdSancion(int idSancion) {
        this.idSancion = idSancion;
    }

    public String getIdEquipos() {
        return idEquipos;
    }

    public void setIdEquipos(String idEquipos) {
        this.idEquipos = idEquipos;
    }
}