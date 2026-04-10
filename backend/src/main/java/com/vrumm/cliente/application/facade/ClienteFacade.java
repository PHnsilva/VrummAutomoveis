package com.vrumm.cliente.application.facade;

import com.vrumm.cliente.application.dto.ClienteForm;
import com.vrumm.cliente.application.dto.PerfilForm;
import com.vrumm.cliente.domain.model.Cliente;
import com.vrumm.cliente.domain.model.ClienteVinculoAtivoChecker;
import com.vrumm.cliente.domain.model.Cpf;
import com.vrumm.cliente.domain.model.PerfilAcesso;
import com.vrumm.cliente.infrastructure.persistence.ClienteRepository;
import com.vrumm.shared.exception.DuplicateResourceException;
import com.vrumm.shared.exception.OperationNotAllowedException;
import com.vrumm.shared.exception.ResourceNotFoundException;
import com.vrumm.shared.security.PasswordHasher;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class ClienteFacade {
    private final ClienteRepository clienteRepository;
    private final ClienteVinculoAtivoChecker clienteVinculoAtivoChecker;
    private final PasswordHasher passwordHasher;

    public ClienteFacade(ClienteRepository clienteRepository, ClienteVinculoAtivoChecker clienteVinculoAtivoChecker, PasswordHasher passwordHasher) {
        this.clienteRepository = clienteRepository;
        this.clienteVinculoAtivoChecker = clienteVinculoAtivoChecker;
        this.passwordHasher = passwordHasher;
    }

    public List<Cliente> listarTodos() { return clienteRepository.findAll(); }
    public List<Cliente> listarClientesGerenciaveis() { return clienteRepository.findByPerfil(PerfilAcesso.CLIENTE); }
    public Optional<Cliente> buscarPorId(Long id) { return clienteRepository.findById(id); }
    public Optional<Cliente> buscarClienteGerenciavel(Long id) { return clienteRepository.findById(id).filter(Cliente::isCliente); }

    public Cliente salvar(ClienteForm form) {
        validarEmailObrigatorio(form.getEmail());
        validarSenhaObrigatoria(form.getSenha());
        Cpf cpf = new Cpf(form.getCpf());
        String email = normalizarEmail(form.getEmail());
        validarCpfDuplicado(cpf, null);
        validarEmailDuplicado(email, null);
        Cliente cliente = new Cliente();
        cliente.setNome(form.getNome()); cliente.setEmail(email); cliente.setCpf(cpf); cliente.setRg(form.getRg()); cliente.setProfissao(form.getProfissao()); cliente.setPerfil(PerfilAcesso.CLIENTE); cliente.setSenhaHash(passwordHasher.hash(form.getSenha()));
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, ClienteForm form) {
        Cliente cliente = clienteRepository.findById(id).filter(Cliente::isCliente).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        Cpf cpf = new Cpf(form.getCpf());
        String email = normalizarEmail(form.getEmail());
        validarCpfDuplicado(cpf, id); validarEmailDuplicado(email, id);
        cliente.setNome(form.getNome()); cliente.setEmail(email); cliente.setCpf(cpf); cliente.setRg(form.getRg()); cliente.setProfissao(form.getProfissao());
        if (form.getSenha() != null && !form.getSenha().isBlank()) cliente.setSenhaHash(passwordHasher.hash(form.getSenha()));
        return clienteRepository.update(cliente);
    }


    public Cliente atualizarPerfil(Long id, PerfilForm form) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        Cpf cpf = new Cpf(form.getCpf());
        String email = normalizarEmail(form.getEmail());
        validarCpfDuplicado(cpf, id);
        validarEmailDuplicado(email, id);
        cliente.setNome(form.getNome());
        cliente.setEmail(email);
        cliente.setCpf(cpf);
        cliente.setRg(form.getRg());
        cliente.setProfissao(form.getProfissao());
        if (form.getSenha() != null && !form.getSenha().isBlank()) {
            cliente.setSenhaHash(passwordHasher.hash(form.getSenha()));
        }
        return clienteRepository.update(cliente);
    }

    public Optional<Cliente> autenticar(String email, String senha) {
        if (email == null || senha == null || email.isBlank() || senha.isBlank()) return Optional.empty();
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(normalizarEmail(email));
        if (clienteOpt.isEmpty()) return Optional.empty();
        Cliente cliente = clienteOpt.get();
        if (!passwordHasher.matches(senha, cliente.getSenhaHash())) return Optional.empty();
        return Optional.of(cliente);
    }

    public void remover(Long id) {
        Cliente cliente = clienteRepository.findById(id).filter(Cliente::isCliente).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        boolean possuiVinculosAtivos = clienteVinculoAtivoChecker.possuiVinculosAtivos(id);
        if (!cliente.podeSerRemovido(possuiVinculosAtivos)) throw new OperationNotAllowedException("Cliente não pode ser removido pois possui vínculos ativos");
        clienteRepository.deleteById(cliente.getId());
    }

    private void validarCpfDuplicado(Cpf cpf, Long idAtual) {
        Optional<Cliente> existente = clienteRepository.findByCpf(cpf);
        if (existente.isPresent()) {
            boolean mesmoRegistro = idAtual != null && existente.get().getId().equals(idAtual);
            if (!mesmoRegistro) throw new DuplicateResourceException("Já existe cliente com este CPF");
        }
    }
    private void validarEmailDuplicado(String email, Long idAtual) {
        Optional<Cliente> existente = clienteRepository.findByEmail(email);
        if (existente.isPresent()) {
            boolean mesmoRegistro = idAtual != null && existente.get().getId().equals(idAtual);
            if (!mesmoRegistro) throw new DuplicateResourceException("Já existe cliente com este e-mail");
        }
    }
    private void validarEmailObrigatorio(String email) { if (email == null || email.isBlank()) throw new IllegalArgumentException("E-mail é obrigatório"); }
    private void validarSenhaObrigatoria(String senha) { if (senha == null || senha.isBlank()) throw new IllegalArgumentException("Senha é obrigatória"); }
    private String normalizarEmail(String email) { return email.trim().toLowerCase(); }
}
