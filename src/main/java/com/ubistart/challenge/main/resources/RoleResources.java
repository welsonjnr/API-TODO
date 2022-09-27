package com.ubistart.challenge.main.resources;

import com.ubistart.challenge.main.entities.Role;
import com.ubistart.challenge.main.entities.dto.RoleDTO;
import com.ubistart.challenge.main.entities.dto.RoleToUserDTO;
import com.ubistart.challenge.main.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/role")
public class RoleResources {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDTO> insert(@Valid @RequestBody RoleDTO roleDTO) {
        Role role = roleService.save(roleDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(role.getId()).toUri();
        return ResponseEntity.created(uri).body(new RoleDTO(role));
    }

    @PostMapping(value = "/addtouser")
    public ResponseEntity<RoleDTO> insert(@Valid @RequestBody RoleToUserDTO roleDTO) {
        roleService.addRoleToUser(roleDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<Role>> findAll() {
        return ResponseEntity.ok().body(roleService.findAll());
    }

}
