package com.app.Repository;


import com.app.Model.Container;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerRepository extends JpaRepository<Container, Long> {
}