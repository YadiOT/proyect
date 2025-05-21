/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.clases;

/**
 *
 * @author Usuario
 */
public class SancionInsumo {
    private int idSancion;
    private int idInsumo;
    private int cantidadAfectada;

    // Constructor para inserción y lectura
    public SancionInsumo(int idSancion, int idInsumo, int cantidadAfectada) {
        this.idSancion = idSancion;
        this.idInsumo = idInsumo;
        this.cantidadAfectada = cantidadAfectada;
    }

    public int getIdSancion() {
        return idSancion;
    }

    public void setIdSancion(int idSancion) {
        this.idSancion = idSancion;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public int getCantidadAfectada() {
        return cantidadAfectada;
    }

    public void setCantidadAfectada(int cantidadAfectada) {
        this.cantidadAfectada = cantidadAfectada;
    }
}
