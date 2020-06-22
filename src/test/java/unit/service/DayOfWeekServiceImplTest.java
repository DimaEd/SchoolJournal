package unit.service;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.DayOfWeek;
import com.ednach.repository.DayOfWeekRepository;
import com.ednach.service.impl.DayOfWeekServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DayOfWeekServiceImplTest {
    @InjectMocks
    private DayOfWeekServiceImpl dayOfWeekService;
    @Mock
    private LocalizedMessageSource localizedMessageSource;
    @Mock
    private DayOfWeekRepository dayOfWeekRepository;

    @Test
    void findAll() {
        final List<DayOfWeek> dayOfWeekList = Collections.singletonList(new DayOfWeek());
        when(dayOfWeekRepository.findAll()).thenReturn(dayOfWeekList);
        assertEquals(dayOfWeekService.findAll(), dayOfWeekList);
    }

    @Test
    void findById() {
        final DayOfWeek dayOfWeek = new DayOfWeek();
         dayOfWeek.setId(1L);
        when(dayOfWeekRepository.findById(1L)).thenReturn(Optional.of(dayOfWeek));
        assertEquals(dayOfWeekService.findById(1L), dayOfWeek);
    }

    @Test
    void findByDayOfWeek() {
        final DayOfWeek dayOfWeek = new DayOfWeek();
        dayOfWeek.setDayOfWeek("monday");
        when(dayOfWeekRepository.findByDayOfWeek("monday")).thenReturn(dayOfWeek);
        assertEquals(dayOfWeekService.findByDayOfWeek("monday"), dayOfWeek);
    }
}