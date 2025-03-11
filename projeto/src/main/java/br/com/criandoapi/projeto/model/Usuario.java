package br.com.criandoapi.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//CHAMADA DO LOMBOK PARA A CONSTRUÇÃO DOS CONSTRUTORES E GETTER SETTER SEM PRECISAR FAZER 
@Data

@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	@NotBlank(message = "O nome é obrigatorio") // // MÉTODO DE VALIDAÇÃO
	@Size(min = 3 , message = "O nome deve ter no mínimo 3 caracteres")
	@Column(name = "nome_completo", length = 200 , nullable = true)
    private String nome;
	
	@NotBlank(message = "O username é obrigatorio")
	@Column(name = "username", length = 100, nullable = false) 
    private String username;
	
	@Email(message = "Insira um email valido")
	@NotBlank(message = "O email é obrigatorio")
	@Column(name = "email", length = 50 , nullable = false)
    private String email;
	
	@NotBlank(message = "A senha é obrigatorio")
	@Column(name = "senha", columnDefinition = "TEXT" , nullable = false)
    private String senha;
	
	@NotBlank(message = "O telefone é obrigatorio")
	@Column(name = "telefone", length = 15 , nullable = false)
    private String telefone;

	
	
    
}