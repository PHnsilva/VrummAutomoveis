package com.vrumm.service;

import com.vrumm.domain.Cliente;
import com.vrumm.domain.ClienteVinculoAtivoChecker;
import com.vrumm.domain.Cpf;
import com.vrumm.dto.ClienteForm;
import com.vrumm.repository.ClienteRepository;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteVinculoAtivoChecker clienteVinculoAtivoChecker;

    public ClienteService(ClienteRepository clienteRepository,
                          ClienteVinculoAtivoChecker clienteVinculoAtivoChecker) {
        this.clienteRepository = clienteRepository;
        this.clienteVinculoAtivoChecker = clienteVinculoAtivoChecker;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente salvar(ClienteForm form) {
        Cpf cpf = new Cpf(form.getCpf());
        validarCpfDuplicado(cpf, null);

        Cliente cliente = new Cliente();
        cliente.setNome(form.getNome());
        cliente.setCpf(cpf);
        cliente.setRg(form.getRg());
        cliente.setProfissao(form.getProfissao());

        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, ClienteForm form) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Cpf cpf = new Cpf(form.getCpf());
        validarCpfDuplicado(cpf, id);

        cliente.setNome(form.getNome());
        cliente.setCpf(cpf);
        cliente.setRg(form.getRg());
        cliente.setProfissao(form.getProfissao());

        return clienteRepository.update(cliente);
    }

    public void remover(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        boolean possuiVinculosAtivos = clienteVinculoAtivoChecker.possuiVinculosAtivos(id);

        if (!cliente.podeSerRemovido(possuiVinculosAtivos)) {
            throw new IllegalStateException("Cliente não pode ser removido pois possui vínculos ativos");
        }

        clienteRepository.deleteById(cliente.getId());
    }

    private void validarCpfDuplicado(Cpf cpf, Long idAtual) {
        Optional<Cliente> existente = clienteRepository.findByCpf(cpf);
        if (existente.isPresent()) {
            boolean mesmoRegistro = idAtual != null && existente.get().getId().equals(idAtual);
            if (!mesmoRegistro) {
                throw new IllegalArgumentException("Já existe cliente com este CPF");
            }
        }
    }
}