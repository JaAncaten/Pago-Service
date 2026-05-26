package com.vetnova.pagoservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetnova.pagoservice.model.Pago;
import com.vetnova.pagoservice.repository.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> obtenerPagos() {
        return pagoRepository.findAll();
    }

    public Pago obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public Pago guardarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    public Pago actualizarPago(Long id, Pago pagoActualizado) {
        Optional<Pago> pagoExistente = pagoRepository.findById(id);

        if (pagoExistente.isPresent()) {
            Pago pago = pagoExistente.get();

            pago.setVentaId(pagoActualizado.getVentaId());
            pago.setMetodoPago(pagoActualizado.getMetodoPago());
            pago.setMonto(pagoActualizado.getMonto());
            pago.setEstado(pagoActualizado.getEstado());
            pago.setFechaPago(pagoActualizado.getFechaPago());
            pago.setReferencia(pagoActualizado.getReferencia());

            return pagoRepository.save(pago);
        }

        return null;
    }

    public boolean eliminarPago(Long id) {
        if (pagoRepository.existsById(id)) {
            pagoRepository.deleteById(id);
            return true;
        }

        return false;
    }
}