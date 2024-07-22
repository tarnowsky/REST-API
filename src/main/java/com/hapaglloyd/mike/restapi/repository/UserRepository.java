package com.hapaglloyd.mike.restapi.repository;

import com.hapaglloyd.mike.restapi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
