package com.food_ordering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food_ordering.model.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {

}
