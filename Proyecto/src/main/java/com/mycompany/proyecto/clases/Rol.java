/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.clases;

import java.util.Set;
import java.util.HashSet;
/**
 *
 * @author Usuario
 */
public class Rol {
    private Long id;
    private String nombre;
    private String descripcion;
    private Set<Permiso> permisos = new HashSet<>();
    
    // Constructor
    public Rol(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Set<Permiso> getPermisos() { return permisos; }
    public void setPermisos(Set<Permiso> permisos) { this.permisos = permisos; }
    
    public void addPermiso(Permiso permiso) {
        this.permisos.add(permiso);
    }
    
    public void removePermiso(Permiso permiso) {
        this.permisos.remove(permiso);
    }
}

