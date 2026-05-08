package com.fiap.wellme.controller;

import com.fiap.wellme.config.OpenApiConfig;
import com.fiap.wellme.dto.user.*;
import com.fiap.wellme.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Usuários", description = "Cadastro, perfil e dashboard do usuário")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public List<UserResponseDTO> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public UserResponseDTO getById(@PathVariable String id) {
        return userService.getById(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar novo usuário (público — senha mín. 8 chars, e-mail único)")
    public UserResponseDTO register(@Valid @RequestBody UserRegisterDTO dto) {
        return userService.register(dto);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Atualizar perfil: nome, foto, meta de água, notificações")
    public UserResponseDTO update(@PathVariable String id, @Valid @RequestBody UserUpdateDTO dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Remover usuário")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/dashboard")
    @Operation(summary = "Dashboard: XP, nível, fases, badges, água do dia")
    public DashboardDTO getDashboard(@PathVariable String id) {
        return userService.getDashboard(id);
    }
}
