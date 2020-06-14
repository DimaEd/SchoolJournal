package com.ednach.controller;

import com.ednach.dto.request.SinRequestDto;
import com.ednach.dto.responce.SinResponseDto;
import com.ednach.model.Schoolboy;
import com.ednach.model.Sin;
import com.ednach.service.SinService;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Teacher;
import com.ednach.service.SchoolboyService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sins")
public class SinController {

    final private Mapper mapper;
    final private SinService sinService;
    final private LocalizedMessageSource localizedMessageSource;
    final private SchoolboyService schoolboyService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SinResponseDto>> getAll() {
        final List<Sin> sins = sinService.findAll();
        final List<SinResponseDto> sinResponseDtoList = sins.stream()
                .map((sin) -> mapper.map(sin, SinResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(sinResponseDtoList, HttpStatus.OK);
    }

//    @RequestMapping(value = "/sinType/{type}",method = RequestMethod.GET)
//    public ResponseEntity<List<SinResponseDto>> getAllSinByType(@PathVariable String type) {
//        final List<Sin> sins = sinService.findByTypeSin(type);
//        final List<SinResponseDto> sinResponseDtoList = sins.stream()
//                .map((sin) -> mapper.map(sin, SinResponseDto.class))
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(sinResponseDtoList, HttpStatus.OK);
//    }

    @RequestMapping(value = "/schoolboy/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<SinResponseDto>> getAllSinByType(@PathVariable Long id) {
        Schoolboy schoolboy = schoolboyService.findById(id);
        final List<Sin> sins = sinService.findBySchoolboy(schoolboy);
        final List<SinResponseDto> sinResponseDtoList = sins.stream()
                .map((sin) -> mapper.map(sin, SinResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(sinResponseDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SinResponseDto> getOne(@PathVariable Long id) {
        final SinResponseDto sinResponseDto = mapper.map(sinService.findById(id), SinResponseDto.class);
        return new ResponseEntity<>(sinResponseDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SinResponseDto> save(@Valid @RequestBody SinRequestDto sinRequestDto) {
        sinRequestDto.setId(null);
        final SinResponseDto sinResponseDto = mapper.map(sinService.save(getSin(sinRequestDto)), SinResponseDto.class);
        return new ResponseEntity<>(sinResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<SinResponseDto> update(@Valid @RequestBody SinRequestDto sinRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, sinRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.sin.unexpectedId", new Object[]{}));
        }
        final SinResponseDto sinResponseDto = mapper.map(sinService.update(getSin(sinRequestDto)), SinResponseDto.class);
        return new ResponseEntity<>(sinResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        sinService.delete(id);
    }

    private Sin getSin(SinRequestDto sinRequestDto) {
        final Sin sin = mapper.map(sinRequestDto, Sin.class);
        final Teacher teacher = new Teacher() ;
        final Schoolboy schoolboy = new Schoolboy();
        teacher.setId(sinRequestDto.getTeacherId());
        schoolboy.setId(sinRequestDto.getSchoolboyId());
        sin.setTeacher(teacher);
        sin.setSchoolboy(schoolboy);
        return sin;
    }
}
