package br.com.alura.microservice.transportador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.microservice.transportador.dto.EntregaDTO;
import br.com.alura.microservice.transportador.dto.VoucherDTO;
import br.com.alura.microservice.transportador.model.Entrega;
import br.com.alura.microservice.transportador.repository.EntregaRepository;

public interface EntregaService {

	VoucherDTO reservaEntrega(EntregaDTO pedidoDTO);

}
