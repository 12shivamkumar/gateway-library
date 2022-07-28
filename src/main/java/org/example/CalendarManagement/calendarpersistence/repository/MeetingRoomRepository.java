package org.example.CalendarManagement.calendarpersistence.repository;

import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRoomRepository extends CrudRepository<MeetingRoom, Integer> {

    @Query(value = "SELECT * FROM meeting_room m WHERE m.room_name = :name AND m.is_open = true" , nativeQuery = true)
    Optional<MeetingRoom> findByName(@Param("name")String name);

    @Query(value = " SELECT * FROM meeting_room m WHERE m.office_id = :office_id AND m.is_open = true",nativeQuery = true)
    List<Integer> findByOffice(@Param("office_id")int office_id);
}
