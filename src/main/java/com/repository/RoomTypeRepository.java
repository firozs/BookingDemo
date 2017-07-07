package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.model.Room;
import com.model.RoomType;

@RepositoryRestResource
public interface RoomTypeRepository extends CrudRepository<RoomType, Long> {
}
