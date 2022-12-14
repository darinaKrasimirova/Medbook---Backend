package bg.medbook.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.medbook.model.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

}
