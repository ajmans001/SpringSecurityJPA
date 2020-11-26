package com.cgm.ejemploSpring.demoProyectoSpring.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cgm.ejemploSpring.demoProyectoSpring.entities.CatRol;



@Repository
public interface RoleRepository extends JpaRepository<CatRol, Long> {
  Optional<CatRol> findByNombre(String paramString);
  
  @Query("Select r from CatRol r where r.nombre in ?1 and r.status = 1")
  List<CatRol> findInNombre(List<String> paramList);
  
  @Query("Select r from CatRol r where r.status = ?1")
  List<CatRol> findByStatus(int paramInt);
  
  Boolean existsByNombre(String paramString);
  
  @Query("Select r from CatRol r where r.id_rol = ?1")
  CatRol findByIdRol(int paramInt);
  
  @Modifying
  @Transactional
  @Query("Update CatRol r set r.status = ?2 where r.id_rol in ?1")
  void actDesacRol(List<Integer> paramList, int paramInt);
}
