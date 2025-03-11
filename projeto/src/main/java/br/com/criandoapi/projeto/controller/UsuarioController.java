package br.com.criandoapi.projeto.controller;

import java.lang.StackWalker.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.criandoapi.projeto.dao.IUsuario;
import br.com.criandoapi.projeto.dto.UsuarioDto;
import br.com.criandoapi.projeto.model.Usuario;
import br.com.criandoapi.projeto.security.Token;
import br.com.criandoapi.projeto.service.*;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuario dao;
	
	private UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
		
	}
	
	//LISTAR
	 @GetMapping
	    public ResponseEntity<List<Usuario>> listaUsuarios () {
		
		 return ResponseEntity.status(200).body(usuarioService.listarUsuario());
		 
	 }
	 
	 //CRIAR
	@PostMapping
	public ResponseEntity<Usuario> criarUsuario (@Valid @RequestBody Usuario usuario) {
		
		return ResponseEntity.status(201).body(usuarioService.criarUsuario(usuario));
		
		}
	
	//ATUALIZAR
	@PutMapping
	public ResponseEntity<Usuario> editarUsuario (@Valid @RequestBody Usuario usuario) {
		
		return ResponseEntity.status(200).body(usuarioService.editarUsuario(usuario));
		
		}
	
	//DELETAR
	
	@DeleteMapping("/{id}") 
	
	public ResponseEntity<?>excluirUsuario (@PathVariable Integer id){
		usuarioService.excluiUsuario(id);
		return ResponseEntity.status(204).build();
	}
	
	/*
	 * @DeleteMapping("/{id}") public Optional<Usuario>excluirUsuario (@PathVariable
	 * Integer id) { Optional<Usuario> usuario = dao.findById(id); //AQUI ELE
	 * RETORNA O USUARIO QUE FOI EXCLUIDO
	 * 
	 * dao.deleteById(id); return usuario; }
	 */
	
	
	//TESTE DE VERIFICAÇÃO E COMPARAÇÃO DE SENHA NO LOGIN
	@PostMapping("/login")
	public ResponseEntity<Token> logar(@Valid @RequestBody UsuarioDto usuario){
		Token token = usuarioService.gerarToken(usuario);
		if (token != null) {
			return ResponseEntity.ok(token);
		}
		
		return ResponseEntity.status(403).build();
	}
	
	//INTERCEPTAR A MENSAGEM E PASSAR DE UMA FORMA MAIS LEGIVEL
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
		Map<String,String> erros = new HashMap<String, String>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			erros.put(fieldName, errorMessage);
		});
		
		return erros;
	}
}