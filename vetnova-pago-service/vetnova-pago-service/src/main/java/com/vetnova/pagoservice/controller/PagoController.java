package com.vetnova.pagoservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vetnova.pagoservice.model.Pago;
import com.vetnova.pagoservice.service.PagoService;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> obtenerPagos() {
        return pagoService.obtenerPagos();
    }

    @GetMapping("/{id}")
    public Pago obtenerPagoPorId(@PathVariable Long id) {
        return pagoService.obtenerPagoPorId(id);
    }

    @PostMapping
    public Pago guardarPago(@RequestBody Pago pago,
                         @RequestHeader("Authorization") String token) {
        return pagoService.guardarPago(pago, token);
}

    @PutMapping("/{id}")
    public Pago actualizarPago(@PathVariable Long id, @RequestBody Pago pago) {
        return pagoService.actualizarPago(id, pago);
    }

    @DeleteMapping("/{id}")
    public String eliminarPago(@PathVariable Long id) {
        boolean eliminado = pagoService.eliminarPago(id);

        if (eliminado) {
            return "Pago eliminado correctamente";
        }

        return "Pago no encontrado";
    }
}