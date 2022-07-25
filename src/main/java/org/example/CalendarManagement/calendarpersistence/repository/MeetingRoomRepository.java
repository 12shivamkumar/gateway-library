package org.example.CalendarManagement.calendarpersistence.repository;

import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingRoomRepository extends CrudRepository<MeetingRoom, Integer> {

    @Query(value = "SELECT * FROM meetingroom m WHERE m.name = :name AND m.is_open = true")
    Optional<MeetingRoom> findByName(@Param("name")String name);
}
