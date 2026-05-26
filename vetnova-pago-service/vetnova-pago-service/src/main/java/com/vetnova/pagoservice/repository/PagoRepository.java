package com.vetnova.pagoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetnova.pagoservice.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {

}