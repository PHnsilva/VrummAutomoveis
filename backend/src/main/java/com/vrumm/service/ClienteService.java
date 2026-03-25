package com.vrumm.service;

import com.vrumm.domain.Cliente;
import com.vrumm.dto.ClienteForm;
import com.vrumm.repository.ClienteRepository;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente salvar(ClienteForm form) {
        validarCpfDuplicado(form.getCpf(), null);

        Cliente cliente = new Cliente();
        cliente.setNome(form.getNome().trim());
        cliente.setCpf(normalizarCpf(form.getCpf()));
        cliente.setRg(form.getRg().trim());
        cliente.setProfissao(form.getProfissao().trim());

        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, ClienteForm form) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        validarCpfDuplicado(form.getCpf(), id);

        cliente.setNome(form.getNome().trim());
        cliente.setCpf(normalizarCpf(form.getCpf()));
        cliente.setRg(form.getRg().trim());
        cliente.setProfissao(form.getProfissao().trim());

        return clienteRepository.update(cliente);
    }

    public void remover(Long id) {
        clienteRepository.deleteById(id);
    }

    private void validarCpfDuplicado(String cpf, Long idAtual) {
        String cpfNormalizado = normalizarCpf(cpf);

        Optional<Cliente> existente = clienteRepository.findByCpf(cpfNormalizado);
        if (existente.isPresent()) {
            boolean mesmoRegistro = idAtual != null && existente.get().getId().equals(idAtual);
            if (!mesmoRegistro) {
                throw new IllegalArgumentException("Já existe cliente com este CPF");
            }
        }
    }

    private String normalizarCpf(String cpf) {
        return cpf.replaceAll("\\D", "");
    }
}