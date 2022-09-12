package bg.medbook.model.repository;

import java.time.DayOfWeek;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import bg.medbook.model.entity.Workplace;
import bg.medbook.model.entity.Workschedule;

@Repository
public interface WorkscheduleRepository extends JpaRepository<Workschedule, Integer> {

    public List<Workschedule> findByWorkplaceAndWeekdayIn(Workplace workplace,
            List<DayOfWeek> weekdays);

}
