package com.wipro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.wipro.dto.PerformanceDto;
import com.wipro.entity.Performance;
import com.wipro.exception.IdNotFound;
import com.wipro.repository.PerformanceRepo;

@ExtendWith(MockitoExtension.class)
public class PerformanceServiceImplTests {

    @Mock
    private PerformanceRepo performanceRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PerformanceServiceImpl performanceService;

    private Performance performance;
    private PerformanceDto performanceDto;

    @BeforeEach
    public void setup() {
        performance = new Performance(1L, "Excellent Performance", "Achieved all targets", 4.5);
        performanceDto = new PerformanceDto(1L, "Excellent Performance", "Achieved all targets", 4.5);
    }

    @Test
    @DisplayName("Junit testing for Create performance in serviceimpl ")
    public void testCreatePerformance() {
        // given
        given(modelMapper.map(performanceDto, Performance.class)).willReturn(performance);
        given(performanceRepo.save(performance)).willReturn(performance);
        given(modelMapper.map(performance, PerformanceDto.class)).willReturn(performanceDto);

        // when
        PerformanceDto savedPerformance = performanceService.createPerformance(performanceDto);

        // then
        assertThat(savedPerformance).isNotNull();
        assertThat(savedPerformance.getId()).isEqualTo(performance.getId());
        then(performanceRepo).should(times(1)).save(performance);
    }

    @Test
    @DisplayName("Junit testing for get all performance in serviceimpl ")
    public void testGetAllPerformances() {
        // given
        Performance performance2 = new Performance(2L, "Good Performance", "Met most of the targets", 3.8);
        List<Performance> performances = Arrays.asList(performance, performance2);
        given(performanceRepo.findAll()).willReturn(performances);
        given(modelMapper.map(performance, PerformanceDto.class)).willReturn(performanceDto);
        given(modelMapper.map(performance2, PerformanceDto.class))
            .willReturn(new PerformanceDto(2L, "Good Performance", "Met most of the targets", 3.8));

        // when
        List<PerformanceDto> performanceDtoList = performanceService.getAllPerformances();

        // then
        assertThat(performanceDtoList.size()).isEqualTo(2);
        then(performanceRepo).should(times(1)).findAll();
    }

    @Test
    @DisplayName("Junit testing for get performance by id in serviceimpl ")
    public void testGetPerformanceById_Success() {
        // given
        given(performanceRepo.findById(1L)).willReturn(Optional.of(performance));
        given(modelMapper.map(performance, PerformanceDto.class)).willReturn(performanceDto);

        // when
        PerformanceDto foundPerformance = performanceService.getPerformanceById(1L);

        // then
        assertThat(foundPerformance).isNotNull();
        assertThat(foundPerformance.getId()).isEqualTo(performance.getId());
    }

    @Test
    @DisplayName("Junit testing for performance not found by id in serviceimpl ")
    public void testGetPerformanceById_IdNotFound() {
        // given
        given(performanceRepo.findById(2L)).willReturn(Optional.empty());

        // when & then
        assertThrows(IdNotFound.class, () -> performanceService.getPerformanceById(2L));
    }

    @Test
    @DisplayName("Junit testing for update performance in serviceimpl ")
    public void testUpdatePerformance_Success() {
        // given
        given(performanceRepo.existsById(1L)).willReturn(true);
        given(modelMapper.map(performanceDto, Performance.class)).willReturn(performance);
        given(performanceRepo.save(performance)).willReturn(performance);
        given(modelMapper.map(performance, PerformanceDto.class)).willReturn(performanceDto);

        // when
        PerformanceDto updatedPerformance = performanceService.updatePerformance(1L, performanceDto);

        // then
        assertThat(updatedPerformance).isNotNull();
        assertThat(updatedPerformance.getId()).isEqualTo(performance.getId());
        then(performanceRepo).should(times(1)).save(performance);
    }

    @Test
    @DisplayName("Junit testing for update performance not found in serviceimpl ")
    public void testUpdatePerformance_IdNotFound() {
        // given
        given(performanceRepo.existsById(2L)).willReturn(false);

        // when & then
        assertThrows(IdNotFound.class, () -> performanceService.updatePerformance(2L, performanceDto));
    }

    @Test
    @DisplayName("Junit testing for delete performance in serviceimpl ")
    public void testDeletePerformance_Success() {
        // given
        given(performanceRepo.existsById(1L)).willReturn(true);

        // when
        String result = performanceService.deletePerformance(1L);

        // then
        assertThat(result).isEqualTo("Performance successfully deleted with ID 1");
        then(performanceRepo).should(times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Junit testing for delete performance not found by id in serviceimpl ")
    public void testDeletePerformance_IdNotFound() {
        // given
        given(performanceRepo.existsById(2L)).willReturn(false);

        // when & then
        assertThrows(IdNotFound.class, () -> performanceService.deletePerformance(2L));
    }
}
