package com.ubistart.challenge.main.services;

import com.ubistart.challenge.main.entities.Role;
import com.ubistart.challenge.main.entities.Usuario;
import com.ubistart.challenge.main.entities.dto.RoleDTO;
import com.ubistart.challenge.main.entities.dto.RoleToUserDTO;
import com.ubistart.challenge.main.repositories.RoleRepository;
import com.ubistart.challenge.main.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Transactional
public class RoleService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Role save(RoleDTO roleDTO){
        Role role = new Role(null, roleDTO.getName());
        return roleRepository.save(role);
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public void addRoleToUser(RoleToUserDTO roleToUserDTO){
        Usuario usuario = usuarioRepository.findByEmail(roleToUserDTO.getEmailUsuario()).get();
        Role role = roleRepository.findByName(roleToUserDTO.getRoleName());

        usuario.getRoles().add(role);
        usuarioRepository.save(usuario);
    }
}
