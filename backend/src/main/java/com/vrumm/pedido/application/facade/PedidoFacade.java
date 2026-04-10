package com.vrumm.pedido.application.facade;

import com.vrumm.agente.infrastructure.persistence.BancoRepository;
import com.vrumm.automovel.domain.model.Automovel;
import com.vrumm.automovel.infrastructure.persistence.AutomovelRepository;
import com.vrumm.cliente.application.facade.ClienteFacade;
import com.vrumm.contrato.domain.model.Contrato;
import com.vrumm.contrato.domain.model.ContratoCredito;
import com.vrumm.contrato.infrastructure.persistence.ContratoCreditoRepository;
import com.vrumm.contrato.infrastructure.persistence.ContratoRepository;
import com.vrumm.pedido.application.dto.ContratoCreditoForm;
import com.vrumm.pedido.application.dto.ContratoForm;
import com.vrumm.pedido.application.dto.FinanceiroAvaliacaoForm;
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
    private final ContratoRepository contratoRepository;
    private final ContratoCreditoRepository contratoCreditoRepository;
    private final BancoRepository bancoRepository;

    public PedidoFacade(PedidoRepository pedidoRepository,
                        AutomovelRepository automovelRepository,
                        ClienteFacade clienteFacade,
                        ContratoRepository contratoRepository,
                        ContratoCreditoRepository contratoCreditoRepository,
                        BancoRepository bancoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.automovelRepository = automovelRepository;
        this.clienteFacade = clienteFacade;
        this.contratoRepository = contratoRepository;
        this.contratoCreditoRepository = contratoCreditoRepository;
        this.bancoRepository = bancoRepository;
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
                form.getValorEntradaBigDecimal(),
                form.getPrazoMesesInteger(),
                form.getRendaDeclaradaBigDecimal()
        );
        return pedidoRepository.save(pedido);
    }

    public PedidoAluguel atualizarPedidoDoCliente(Long clienteId, Long pedidoId, PedidoForm form) {
        validarClienteExistente(clienteId);
        PedidoAluguel pedido = pedidoRepository.findByIdAndClienteId(pedidoId, clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        return atualizarPedidoInterno(pedido, form);
    }

    public PedidoAluguel atualizarPedidoComoEmpresa(Long pedidoId, PedidoForm form) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        return atualizarPedidoInterno(pedido, form);
    }

    private PedidoAluguel atualizarPedidoInterno(PedidoAluguel pedido, PedidoForm form) {
        Automovel automovel = automovelRepository.findById(form.getAutomovelId())
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado"));
        if (!automovel.isDisponivel() && !automovel.getId().equals(pedido.getAutomovelId())) {
            throw new IllegalArgumentException("Automóvel indisponível para o pedido");
        }
        pedido.atualizarSolicitacao(automovel.getId(), form.getObservacao(), form.getValorEntradaBigDecimal(), form.getPrazoMesesInteger(), form.getRendaDeclaradaBigDecimal());
        return pedidoRepository.update(pedido);
    }

    public void cancelarPedidoDoCliente(Long clienteId, Long pedidoId) {
        validarClienteExistente(clienteId);
        PedidoAluguel pedido = pedidoRepository.findByIdAndClienteId(pedidoId, clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.cancelar();
        pedidoRepository.update(pedido);
    }

    public void cancelarPedidoComoEmpresa(Long pedidoId) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.cancelar();
        pedidoRepository.update(pedido);
    }

    public List<PedidoStatusDto> listarPedidosDoCliente(Long clienteId) {
        validarClienteExistente(clienteId);
        return pedidoRepository.findByClienteId(clienteId).stream()
                .sorted(Comparator.comparing(PedidoAluguel::getDataCriacao).reversed())
                .map(this::toStatusDto)
                .toList();
    }

    public List<PedidoStatusDto> listarPedidosEmpresa(PedidoStatus status) {
        return listarPedidosGerais(status);
    }

    public List<PedidoStatusDto> listarPedidosBanco(PedidoStatus status) {
        return listarPedidosGerais(status);
    }

    private List<PedidoStatusDto> listarPedidosGerais(PedidoStatus status) {
        List<PedidoAluguel> pedidos = status == null
                ? StreamSupport.stream(pedidoRepository.findAll().spliterator(), false).toList()
                : pedidoRepository.findByStatus(status);
        return pedidos.stream()
                .sorted(Comparator.comparing(PedidoAluguel::getDataCriacao).reversed())
                .map(this::toStatusDto)
                .toList();
    }

    public Optional<PedidoStatusDto> buscarResumoPedidoDoCliente(Long clienteId, Long pedidoId) {
        validarClienteExistente(clienteId);
        return pedidoRepository.findByIdAndClienteId(pedidoId, clienteId).map(this::toStatusDto);
    }

    public Optional<PedidoStatusDto> buscarResumoPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).map(this::toStatusDto);
    }

    public Optional<PedidoForm> buscarFormularioPedidoDoCliente(Long clienteId, Long pedidoId) {
        validarClienteExistente(clienteId);
        return pedidoRepository.findByIdAndClienteId(pedidoId, clienteId).map(this::toForm);
    }

    public Optional<PedidoForm> buscarFormularioPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).map(this::toForm);
    }

    public void confirmarPagamentoDoCliente(Long clienteId, Long pedidoId, BigDecimal valorPago) {
        validarClienteExistente(clienteId);
        PedidoAluguel pedido = pedidoRepository.findByIdAndClienteId(pedidoId, clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.finalizar(valorPago);
        pedidoRepository.update(pedido);
    }

    public void confirmarPagamentoComoBanco(Long pedidoId, BigDecimal valorPago) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.finalizar(valorPago);
        pedidoRepository.update(pedido);
    }

    public void recusarPedidoComoEmpresa(Long pedidoId) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.recusar();
        pedidoRepository.update(pedido);
    }

    public void avaliarFinanceiramente(Long pedidoId, Long bancoId, FinanceiroAvaliacaoForm form) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.registrarParecerFinanceiro(bancoId, Boolean.TRUE.equals(form.getFavoravel()), form.getObservacao());
        pedidoRepository.update(pedido);
    }

    public void salvarContrato(Long pedidoId, ContratoForm form) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        if (pedido.isEncerrado() && pedido.getStatus() != PedidoStatus.FINALIZADO) {
            throw new IllegalStateException("Contrato só pode ser gerenciado em pedidos ativos ou finalizados");
        }
        Contrato contrato = contratoRepository.findByPedidoId(pedidoId).orElseGet(Contrato::new);
        if (contrato.getId() == null) {
            contrato.prepararNovoRegistro(pedidoId, form.getTipoContrato(), form.getTipoProprietario(), form.getObservacao());
            contratoRepository.save(contrato);
        } else {
            contrato.setTipoContrato(form.getTipoContrato());
            contrato.setTipoProprietario(form.getTipoProprietario());
            contrato.setObservacao(form.getObservacao());
            contratoRepository.update(contrato);
        }
    }

    public void salvarContratoCredito(Long pedidoId, Long bancoId, ContratoCreditoForm form) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        if (pedido.getParecerFinanceiroFavoravel() == null || !pedido.getParecerFinanceiroFavoravel()) {
            throw new IllegalStateException("Contrato de crédito só pode ser registrado após parecer financeiro favorável");
        }
        ContratoCredito contratoCredito = contratoCreditoRepository.findByPedidoId(pedidoId).orElseGet(ContratoCredito::new);
        if (contratoCredito.getId() == null) {
            contratoCredito.prepararNovoRegistro(pedidoId, bancoId, form.getNumeroReferencia(), form.getValorCredito(), form.getObservacao());
            contratoCreditoRepository.save(contratoCredito);
        } else {
            contratoCredito.setBancoId(bancoId);
            contratoCredito.setNumeroReferencia(form.getNumeroReferencia());
            contratoCredito.setValorCredito(form.getValorCredito());
            contratoCredito.setObservacao(form.getObservacao());
            contratoCreditoRepository.update(contratoCredito);
        }
    }

    public Optional<ContratoForm> buscarContratoForm(Long pedidoId) {
        return contratoRepository.findByPedidoId(pedidoId).map(contrato -> {
            ContratoForm form = new ContratoForm();
            form.setTipoContrato(contrato.getTipoContrato());
            form.setTipoProprietario(contrato.getTipoProprietario());
            form.setObservacao(contrato.getObservacao());
            return form;
        });
    }

    public Optional<ContratoCreditoForm> buscarContratoCreditoForm(Long pedidoId) {
        return contratoCreditoRepository.findByPedidoId(pedidoId).map(contrato -> {
            ContratoCreditoForm form = new ContratoCreditoForm();
            form.setNumeroReferencia(contrato.getNumeroReferencia());
            form.setValorCredito(contrato.getValorCredito());
            form.setObservacao(contrato.getObservacao());
            return form;
        });
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
        dto.setPodeRecusar(pedido.podeSerRecusado());
        dto.setPodeEditar(pedido.podeEditar());
        dto.setPodeCancelar(pedido.podeCancelar());
        dto.setFinalizado(pedido.getStatus() == PedidoStatus.FINALIZADO);
        dto.setCancelado(pedido.getStatus() == PedidoStatus.CANCELADO);
        dto.setParecerFinanceiroDescricao(formatarParecerFinanceiro(pedido.getParecerFinanceiroFavoravel()));
        dto.setParecerFinanceiroObservacao(pedido.getParecerFinanceiroObservacao());
        dto.setDataParecerFinanceiroFormatada(pedido.getDataParecerFinanceiro() == null ? null : pedido.getDataParecerFinanceiro().format(DATA_HORA_FORMATTER));
        dto.setBancoAvaliadorNome(buscarNomeBanco(pedido.getBancoAvaliadorId()));

        clienteFacade.buscarPorId(pedido.getClienteId()).ifPresent(cliente -> {
            dto.setClienteNome(cliente.getNome());
            dto.setClienteEmail(cliente.getEmail());
        });

        contratoRepository.findByPedidoId(pedido.getId()).ifPresent(contrato -> {
            dto.setPossuiContrato(true);
            dto.setTipoContratoDescricao(contrato.getTipoContrato().getDescricao());
            dto.setTipoProprietarioDescricao(contrato.getTipoProprietario().getDescricao());
            dto.setObservacaoContrato(contrato.getObservacao());
        });

        contratoCreditoRepository.findByPedidoId(pedido.getId()).ifPresent(contratoCredito -> {
            dto.setPossuiContratoCredito(true);
            dto.setNumeroReferenciaCredito(contratoCredito.getNumeroReferencia());
            dto.setValorCreditoFormatado(formatarMoeda(contratoCredito.getValorCredito()));
            dto.setObservacaoCredito(contratoCredito.getObservacao());
            dto.setBancoCreditoNome(buscarNomeBanco(contratoCredito.getBancoId()));
        });
        return dto;
    }

    private PedidoForm toForm(PedidoAluguel pedido) {
        PedidoForm form = new PedidoForm();
        form.setAutomovelId(pedido.getAutomovelId());
        form.setObservacao(pedido.getObservacao());
        form.setValorEntrada(pedido.getValorEntrada() == null ? null : pedido.getValorEntrada().stripTrailingZeros().toPlainString());
        form.setPrazoMeses(pedido.getPrazoMeses() == null ? null : String.valueOf(pedido.getPrazoMeses()));
        form.setRendaDeclarada(pedido.getRendaDeclarada() == null ? null : pedido.getRendaDeclarada().stripTrailingZeros().toPlainString());
        return form;
    }

    private String buscarDescricaoAutomovel(Long automovelId) {
        return automovelRepository.findById(automovelId)
                .map(Automovel::getDescricaoExibicao)
                .orElse("Automóvel #" + automovelId);
    }

    private String buscarNomeBanco(Long bancoId) {
        if (bancoId == null) {
            return null;
        }
        return bancoRepository.findById(bancoId)
                .map(banco -> banco.getNome() + " (" + banco.getCodigo() + ")")
                .orElse("Banco #" + bancoId);
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

    private void validarClienteExistente(Long clienteId) {
        if (clienteId == null || clienteFacade.buscarPorId(clienteId).isEmpty()) {
            throw new IllegalArgumentException("Cliente autenticado não encontrado");
        }
    }
}
