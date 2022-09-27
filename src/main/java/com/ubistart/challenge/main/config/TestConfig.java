package com.ubistart.challenge.main.config;

import com.ubistart.challenge.main.entities.Role;
import com.ubistart.challenge.main.entities.Usuario;
import com.ubistart.challenge.main.entities.dto.NewTarefaDTO;
import com.ubistart.challenge.main.entities.dto.NewUsuarioDTO;
import com.ubistart.challenge.main.entities.dto.RoleDTO;
import com.ubistart.challenge.main.repositories.TarefaRepository;
import com.ubistart.challenge.main.repositories.UsuarioRepository;
import com.ubistart.challenge.main.services.RoleService;
import com.ubistart.challenge.main.services.TarefaService;
import com.ubistart.challenge.main.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        Role role1 = roleService.save(new RoleDTO("USER"));
        Role role2 = roleService.save(new RoleDTO("ADMIN"));

        NewUsuarioDTO usuarioAdmin = new NewUsuarioDTO("UsuarioAdmin", "admin@gmail.com", "123456", Arrays.asList(new RoleDTO(role2)));
        NewUsuarioDTO usuarioAdmin1 = new NewUsuarioDTO("Usuario Admin Master", "adminmaster@gmail.com", "123456", Arrays.asList(new RoleDTO(role1), new RoleDTO(role2)));
        NewUsuarioDTO usuario1 = new NewUsuarioDTO("Usuario1", "usuario1@gmail.com", "123456", Arrays.asList(new RoleDTO(role1), new RoleDTO(role2)));
        NewUsuarioDTO usuario2 = new NewUsuarioDTO("Usuario2", "usuario2@gmail.com", "123456", Arrays.asList(new RoleDTO(role1)));
        NewUsuarioDTO usuario3 = new NewUsuarioDTO("Usuario3", "usuario3@gmail.com", "123456", Arrays.asList(new RoleDTO(role1)));

        usuarioService.save(usuario1);
        usuarioService.save(usuario2);
        usuarioService.save(usuario3);
        usuarioService.save(usuarioAdmin);
        usuarioService.save(usuarioAdmin1);

        NewTarefaDTO tarefa1 = new NewTarefaDTO("Tarefa 1", "Realizar tarefa Tarefa 1", LocalDate.now().minusDays(20L), "admin@gmail.com");
        NewTarefaDTO tarefa2 = new NewTarefaDTO("Tarefa 2", "Realizar tarefa Tarefa 2", LocalDate.now().plusDays(15L), "adminmaster@gmail.com");
        NewTarefaDTO tarefa3 = new NewTarefaDTO("Tarefa 3", "Realizar tarefa Tarefa 3", LocalDate.now(), "usuario1@gmail.com");
        NewTarefaDTO tarefa4 = new NewTarefaDTO("Tarefa 4", "Realizar tarefa Tarefa 4", LocalDate.now().plusDays(25L), "usuario2@gmail.com");
        NewTarefaDTO tarefa5 = new NewTarefaDTO("Tarefa 5", "Realizar tarefa Tarefa 5", LocalDate.now().minusDays(20L), "usuario3@gmail.com");
        NewTarefaDTO tarefa6 = new NewTarefaDTO("Tarefa 10", "Realizar tarefa Tarefa 10", LocalDate.now().plusDays(20L), "adminmaster@gmail.com");
        NewTarefaDTO tarefa10 = new NewTarefaDTO("Tarefa 20", "Realizar tarefa Tarefa mais dificil", LocalDate.now().plusDays(1L), "adminmaster@gmail.com");

        tarefaService.save(tarefa1);
        tarefaService.save(tarefa2);
        tarefaService.save(tarefa3);
        tarefaService.save(tarefa4);
        tarefaService.save(tarefa5);
        tarefaService.save(tarefa6);
        tarefaService.save(tarefa10);

    }

}
