package com.vetnova.pagoservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vetnova.pagoservice.model.Pago;
import com.vetnova.pagoservice.repository.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Pago> obtenerPagos() {
        return pagoRepository.findAll();
    }

    public Pago obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public Pago guardarPago(Pago pago, String token) {
        Pago pagoGuardado = pagoRepository.save(pago);

        if ("PAGADO".equalsIgnoreCase(pagoGuardado.getEstado())) {
            actualizarVentaComoPagada(pagoGuardado.getVentaId(), token);
        }

        return pagoGuardado;
    }

    private void actualizarVentaComoPagada(Long ventaId, String token) {
        String url = "http://localhost:8089/api/ventas/" + ventaId + "/estado/PAGADA";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
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