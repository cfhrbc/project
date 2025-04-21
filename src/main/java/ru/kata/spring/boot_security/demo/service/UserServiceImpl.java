package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.Specifications.UserSpecifications;
import ru.kata.spring.boot_security.demo.configs.JwtTokenProvider;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.UserDto;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public List<UserDto> showAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(int id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        userRepository.deleteById(id);
    }

    public UserDto saveUser(UserDto userDto) {
        var user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var roles = userDto.getRoles().stream()
                .map(roleDto -> roleRepository.findByName(roleDto.getName())
                        .orElse(new Role(roleDto.getName())))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto findUserById(int id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    public List<UserDto> getUsersWithFilters(Map<String, String> filters, String sortBy, String sortOrder) {
        var spec = UserSpecifications.withFilters(filters);

        var sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = "desc".equalsIgnoreCase(sortOrder)
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
        }

        var users = userRepository.findAll(spec, sort);
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
