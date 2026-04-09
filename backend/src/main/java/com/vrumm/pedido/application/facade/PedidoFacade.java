package com.vrumm.pedido.application.facade;

import com.vrumm.automovel.domain.model.Automovel;
import com.vrumm.automovel.infrastructure.persistence.AutomovelRepository;
import com.vrumm.cliente.application.facade.ClienteFacade;
import com.vrumm.pedido.application.dto.PedidoForm;
import com.vrumm.pedido.application.dto.PedidoStatusDto;
import com.vrumm.pedido.domain.model.PedidoAluguel;
import com.vrumm.pedido.domain.model.PedidoStatus;
import com.vrumm.pedido.infrastructure.persistence.PedidoRepository;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Singleton
public class PedidoFacade {

    private static final DateTimeFormatter DATA_HORA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final Locale LOCALE_PT_BR = new Locale("pt", "BR");

    private final PedidoRepository pedidoRepository;
    private final AutomovelRepository automovelRepository;
    private final ClienteFacade clienteFacade;

    public PedidoFacade(PedidoRepository pedidoRepository,
                        AutomovelRepository automovelRepository,
                        ClienteFacade clienteFacade) {
        this.pedidoRepository = pedidoRepository;
        this.automovelRepository = automovelRepository;
        this.clienteFacade = clienteFacade;
    }

    public List<Automovel> listarAutomoveisDisponiveis() {
        return automovelRepository.findAll().stream()
                .filter(Automovel::isDisponivel)
                .sorted(Comparator.comparing(Automovel::getMarca).thenComparing(Automovel::getModelo))
                .toList();
    }

    public PedidoAluguel criarPedido(Long clienteId, PedidoForm form) {
        validarClienteExistente(clienteId);
        Automovel automovel = automovelRepository.findById(form.getAutomovelId())
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado"));

        if (!automovel.isDisponivel()) {
            throw new IllegalArgumentException("Automóvel indisponível para novo pedido");
        }

        PedidoAluguel pedido = PedidoAluguel.criarSolicitacao(
                clienteId,
                automovel.getId(),
                form.getObservacao(),
                form.getValorEntrada(),
                form.getPrazoMeses(),
                form.getRendaDeclarada()
        );
        return pedidoRepository.save(pedido);
    }

    public List<PedidoStatusDto> listarPedidosDoCliente(Long clienteId) {
        validarClienteExistente(clienteId);
        return pedidoRepository.findByClienteId(clienteId).stream()
                .sorted(Comparator.comparing(PedidoAluguel::getDataCriacao).reversed())
                .map(this::toStatusDto)
                .toList();
    }

    public Optional<PedidoStatusDto> buscarResumoPedidoDoCliente(Long clienteId, Long pedidoId) {
        validarClienteExistente(clienteId);
        return pedidoRepository.findByIdAndClienteId(pedidoId, clienteId)
                .map(this::toStatusDto);
    }

    public Optional<PedidoStatusDto> buscarResumoPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .map(this::toStatusDto);
    }

    public void confirmarPagamentoDoCliente(Long clienteId, Long pedidoId, BigDecimal valorPago) {
        validarClienteExistente(clienteId);
        PedidoAluguel pedido = pedidoRepository.findByIdAndClienteId(pedidoId, clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.finalizar(valorPago);
        pedidoRepository.update(pedido);
    }

    public void confirmarPagamento(Long pedidoId, BigDecimal valorPago) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.finalizar(valorPago);
        pedidoRepository.update(pedido);
    }

    public List<PedidoStatusDto> listarPedidosParaAdmin(PedidoStatus status) {
        List<PedidoAluguel> pedidos = status == null
                ? StreamSupport.stream(pedidoRepository.findAll().spliterator(), false).toList()
                : pedidoRepository.findByStatus(status);

        return pedidos.stream()
                .sorted(Comparator.comparing(PedidoAluguel::getDataCriacao).reversed())
                .map(this::toStatusDto)
                .toList();
    }

    public void recusarPedido(Long pedidoId) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.recusar();
        pedidoRepository.update(pedido);
    }

    private PedidoStatusDto toStatusDto(PedidoAluguel pedido) {
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
        dto.setAutomovelDescricao(buscarDescricaoAutomovel(pedido.getAutomovelId()));
        dto.setPodeConfirmarPagamento(pedido.podeConfirmarPagamento());
        dto.setPodeAvancarParaPagamento(false);
        dto.setPodeRecusar(pedido.podeSerRecusado());
        dto.setFinalizado(pedido.getStatus() == PedidoStatus.FINALIZADO);

        clienteFacade.buscarPorId(pedido.getClienteId()).ifPresent(cliente -> {
            dto.setClienteNome(cliente.getNome());
            dto.setClienteEmail(cliente.getEmail());
        });
        return dto;
    }

    private String buscarDescricaoAutomovel(Long automovelId) {
        return automovelRepository.findById(automovelId)
                .map(Automovel::getDescricaoExibicao)
                .orElse("Automóvel #" + automovelId);
    }

    private String formatarMoeda(BigDecimal valor) {
        if (valor == null) {
            return "Não informado";
        }
        return NumberFormat.getCurrencyInstance(LOCALE_PT_BR).format(valor);
    }

    private void validarClienteExistente(Long clienteId) {
        if (clienteId == null || clienteFacade.buscarPorId(clienteId).isEmpty()) {
            throw new IllegalArgumentException("Cliente autenticado não encontrado");
        }
    }
}
