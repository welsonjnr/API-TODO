package com.ubistart.challenge.main;

import com.ubistart.challenge.main.entities.Role;
import com.ubistart.challenge.main.entities.Usuario;
import com.ubistart.challenge.main.entities.dto.NewUsuarioDTO;
import com.ubistart.challenge.main.entities.dto.RoleDTO;
import com.ubistart.challenge.main.repositories.UsuarioRepository;
import com.ubistart.challenge.main.services.RoleService;
import com.ubistart.challenge.main.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.InvalidAttributeValueException;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MainApplicationTests {

	@Test
	void contextLoads() {

	}

	private UsuarioRepository usuarioRepository;
	private UsuarioService usuarioService;
	private RoleService roleService;

	@BeforeEach
	void setUp(){
		usuarioService = new UsuarioService();
		roleService = new RoleService();
	}

	@Test
	public void testCreateUsuario() throws InvalidAttributeValueException {
		List<Role> roles = roleService.findAll();
		Collection<RoleDTO> rolesDTO = null;
		roles.forEach( role -> rolesDTO.add(new RoleDTO(role)));

		NewUsuarioDTO usuarioDTO = new NewUsuarioDTO("Usuario", "usuario@gmail.com", "123456", rolesDTO);
		Usuario usuario = usuarioService.save(usuarioDTO);
		assertThat(usuario).isNotNull();
	}

	@Test
	public void testFindUsuarioById() throws InvalidAttributeValueException {
		Usuario usuario = usuarioService.findById(1L).get();
		assertThat(usuario).isNotNull();
	}

}
