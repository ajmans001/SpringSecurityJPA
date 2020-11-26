package com.cgm.ejemploSpring.demoProyectoSpring.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cgm.ejemploSpring.demoProyectoSpring.entities.CatUsuario;

@Repository
public interface UserRepository extends JpaRepository<CatUsuario, Integer> {
  Optional<CatUsuario> findByTelefono(String paramString);
  
  Optional<CatUsuario> findByUsername(String paramString);
  
  @Query("Select u from CatUsuario u where u.id_usuario = ?1")
  CatUsuario findById_usuario(int idUsuario);
  
  Boolean existsByTelefono(String paramString);
  
  @Query("Select u from CatUsuario u where u.id_usuario <> ?2 and u.telefono = ?1")
  CatUsuario existsByTelefonoAndId(String paramString, int paramInt);
  
  @Query("Select u from CatUsuario u where u.id_usuario in ?1")
  List<CatUsuario> getUsuariosByIds(List<Integer> paramList);
  
  Boolean existsByUsername(String paramString);
  
  @Query("Select u from CatUsuario u where u.id_usuario <> ?2 and u.username = ?1")
  CatUsuario existsByUsernameAndId(String paramString, int paramInt);
  
  Boolean existsByCorreo(String paramString);
  
  @Query("Select u from CatUsuario u where u.id_usuario <> ?2 and u.correo = ?1")
  CatUsuario existsByCorreoAndId(String paramString, int paramInt);

}

