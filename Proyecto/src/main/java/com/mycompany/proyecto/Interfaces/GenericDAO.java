/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.Interfaces;

import java.util.List;
import java.util.Optional;
/**
 *
 * @author Usuario
 */
public interface GenericDAO<T> {
    void guardar(T entidad);
    void actualizar(T entidad);
    void eliminar(T entidad);
    Optional<T> buscarPorId(Long id);
    List<T> listarTodos();
}
