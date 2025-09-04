package com.kenti.antezana.sistema_de_gestion_de_reservas.service;

import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Cliente;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Rol;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.User;
import com.kenti.antezana.sistema_de_gestion_de_reservas.repository.UserRepo;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.dto.RegisterReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User findUserByEmail(String email) {
        return  userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    public boolean userExists(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    @Transactional
    public User save(User user) {
        return userRepo.save(user);
    }

    @Transactional
    public User crearUsuario(RegisterReq req){

        Cliente cliente = Cliente.builder()
                .nombre(req.nombre())
                .apellido(req.apellido())
                .email(req.email())
                .telefono(req.telefono())
                .documento(req.documento())
                .tipoDeDocumento(req.tipoDeDocumento())
                .build();

        User user = User.builder()
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .rol(Rol.CLIENTE)
                .cliente(cliente)
                .build();
        return userRepo.save(user);
    }

    public void deleteById(Long id) {
        if (!userRepo.existsById(id)) {
            throw new UsernameNotFoundException("User with id " + id + " not found");
        }
        userRepo.deleteById(id);
    }


//    @Transactional
//    public UserProfile updateProfile(User user, UserProfileReq req) {
//        UserProfile up = mapper.toEntity(req);
//        up.setId(user.getProfile().getId());
//        user.setProfile(up);
//        user = userRepository.save(user);
//        return user.getProfile();
//    }

//    public UserProfile getProfile(User user){
//        return user.getProfile();
//    }

}
