package br.com.criandoapi.projeto.service;


import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.criandoapi.projeto.dao.IUsuario;
import br.com.criandoapi.projeto.dto.UsuarioDto;
import br.com.criandoapi.projeto.model.Usuario;
import br.com.criandoapi.projeto.security.Token;
import br.com.criandoapi.projeto.security.TokenUtil;
import jakarta.validation.Valid;

@Service
public class UsuarioService {

	private IUsuario repository;
	private PasswordEncoder passwordEncoder; // CHAMA A CLASS JAVA PASSWORD
	
	public UsuarioService(IUsuario repository) {
		this.repository = repository;
		this.passwordEncoder = new BCryptPasswordEncoder(); //CHAMA O MÉTODO BCRYPT PARA CRIPTOGRAFAR A SENHA
	}
	
	public List<Usuario> listarUsuario(){
		List<Usuario> lista = repository.findAll();
		return lista;
	}
	
	public Usuario criarUsuario(Usuario usuario) {
		String encoder = this.passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(encoder); //AQUI ELE CRIPTOGRAFA A SENHA DO USUARIO
		Usuario usuarioNovo = repository.save(usuario);
		return usuarioNovo;
	}
	
	public Usuario editarUsuario(Usuario usuario) {
		String encoder = this.passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(encoder); //AQUI ELE CRIPTOGRAFA A SENHA DO USUARIO
		Usuario usuarioNovo = repository.save(usuario);
		return usuarioNovo;
	}
	
	public Boolean excluiUsuario(Integer id) {
		repository.deleteById(id);
		return true;
	}

	public Boolean validarSenha(Usuario usuario) {
		String senha = repository.getById(usuario.getId()).getSenha();
		boolean valid = passwordEncoder.matches(usuario.getSenha(), senha);
		return valid;
	}

	public Token gerarToken(@Valid UsuarioDto usuario) {
		Usuario user = repository.findBynomeOrEmail(usuario.getNome(), usuario.getEmail());
		if (user != null) {
			
			Boolean valid = passwordEncoder.matches(usuario.getSenha(), user.getSenha());
			if (valid) {
				return new Token(TokenUtil.createToken(user));
			}
			
		}
		return null;
	}
}