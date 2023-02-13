package net.devdariobetances.kokoromachiaautheauthoservice.repo;

import net.devdariobetances.kokoromachiaautheauthoservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByPhone(String phone);

}
