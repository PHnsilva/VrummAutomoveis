package com.vrumm.pedido.application.mapper;

import com.vrumm.cliente.application.facade.ClienteFacade;
import com.vrumm.contrato.infrastructure.persistence.ContratoCreditoRepository;
import com.vrumm.contrato.infrastructure.persistence.ContratoRepository;
import com.vrumm.pedido.application.dto.PedidoStatusDto;
import com.vrumm.pedido.application.service.PedidoDomainSupport;
import com.vrumm.pedido.domain.model.PedidoAluguel;
import com.vrumm.pedido.domain.model.PedidoStatus;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Singleton
public class PedidoStatusMapper {

    private static final DateTimeFormatter DATA_HORA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final Locale LOCALE_PT_BR = new Locale("pt", "BR");

    private final PedidoDomainSupport pedidoDomainSupport;
    private final ClienteFacade clienteFacade;
    private final ContratoRepository contratoRepository;
    private final ContratoCreditoRepository contratoCreditoRepository;

    public PedidoStatusMapper(PedidoDomainSupport pedidoDomainSupport,
                              ClienteFacade clienteFacade,
                              ContratoRepository contratoRepository,
                              ContratoCreditoRepository contratoCreditoRepository) {
        this.pedidoDomainSupport = pedidoDomainSupport;
        this.clienteFacade = clienteFacade;
        this.contratoRepository = contratoRepository;
        this.contratoCreditoRepository = contratoCreditoRepository;
    }

    public PedidoStatusDto toDto(PedidoAluguel pedido) {
        PedidoStatusDto dto = criarBase(pedido);
        preencherCliente(dto, pedido);
        preencherContrato(dto, pedido);
        preencherContratoCredito(dto, pedido);
        return dto;
    }

    private PedidoStatusDto criarBase(PedidoAluguel pedido) {
        PedidoStatusDto dto = new PedidoStatusDto();
        dto.setId(pedido.getId());
        dto.setStatusCodigo(pedido.getStatus().name());
        dto.setStatusDescricao(pedido.getStatus().getDescricao());
        dto.setStatusBadgeClass(pedido.getStatus().getBadgeClass());
        dto.setDataCriacaoFormatada(pedido.getDataCriacao().format(DATA_HORA_FORMATTER));
        dto.setObservacao(pedido.getObservacao());
        dto.setValorEntradaFormatado(formatarMoeda(pedido.getValorEntrada()));
        dto.setPrazoMeses(pedido.getPrazoMeses());
        dto.setRendaDeclaradaFormatada(formatarMoeda(pedido.getRendaDeclarada()));
        dto.setValorPagoFormatado(formatarMoeda(pedido.getValorPago()));
        dto.setAutomovelDescricao(pedidoDomainSupport.buscarDescricaoAutomovel(pedido.getAutomovelId()));
        dto.setPodeConfirmarPagamento(pedido.podeConfirmarPagamento());
        dto.setPodeRecusar(pedido.podeSerRecusado());
        dto.setPodeEditar(pedido.podeEditar());
        dto.setPodeCancelar(pedido.podeCancelar());
        dto.setFinalizado(pedido.getStatus() == PedidoStatus.FINALIZADO);
        dto.setCancelado(pedido.getStatus() == PedidoStatus.CANCELADO);
        dto.setParecerFinanceiroDescricao(formatarParecerFinanceiro(pedido.getParecerFinanceiroFavoravel()));
        dto.setParecerFinanceiroObservacao(pedido.getParecerFinanceiroObservacao());
        dto.setDataParecerFinanceiroFormatada(formatarDataHora(pedido.getDataParecerFinanceiro()));
        dto.setBancoAvaliadorNome(pedidoDomainSupport.buscarNomeBanco(pedido.getBancoAvaliadorId()));
        return dto;
    }

    private void preencherCliente(PedidoStatusDto dto, PedidoAluguel pedido) {
        clienteFacade.buscarPorId(pedido.getClienteId()).ifPresent(cliente -> {
            dto.setClienteNome(cliente.getNome());
            dto.setClienteEmail(cliente.getEmail());
        });
    }

    private void preencherContrato(PedidoStatusDto dto, PedidoAluguel pedido) {
        contratoRepository.findByPedidoId(pedido.getId()).ifPresent(contrato -> {
            dto.setPossuiContrato(true);
            dto.setTipoContratoDescricao(contrato.getTipoContrato().getDescricao());
            dto.setTipoProprietarioDescricao(contrato.getTipoProprietario().getDescricao());
            dto.setObservacaoContrato(contrato.getObservacao());
        });
    }

    private void preencherContratoCredito(PedidoStatusDto dto, PedidoAluguel pedido) {
        contratoCreditoRepository.findByPedidoId(pedido.getId()).ifPresent(contratoCredito -> {
            dto.setPossuiContratoCredito(true);
            dto.setNumeroReferenciaCredito(contratoCredito.getNumeroReferencia());
            dto.setValorCreditoFormatado(formatarMoeda(contratoCredito.getValorCredito()));
            dto.setObservacaoCredito(contratoCredito.getObservacao());
            dto.setBancoCreditoNome(pedidoDomainSupport.buscarNomeBanco(contratoCredito.getBancoId()));
        });
    }

    private String formatarMoeda(BigDecimal valor) {
        if (valor == null) {
            return "Não informado";
        }
        return NumberFormat.getCurrencyInstance(LOCALE_PT_BR).format(valor);
    }

    private String formatarParecerFinanceiro(Boolean favoravel) {
        if (favoravel == null) {
            return "Pendente de avaliação financeira";
        }
        return favoravel ? "Parecer financeiro favorável" : "Parecer financeiro desfavorável";
    }

    private String formatarDataHora(LocalDateTime dataHora) {
        return dataHora == null ? null : dataHora.format(DATA_HORA_FORMATTER);
    }
}
