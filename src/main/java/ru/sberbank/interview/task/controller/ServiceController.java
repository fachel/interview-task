package ru.sberbank.interview.task.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.interview.task.controller.dto.res.GetListRes;
import ru.sberbank.interview.task.controller.dto.support.EntityDto;
import ru.sberbank.interview.task.service.Service;
import java.util.List;

@RestController
@RequestMapping("interview")
@RequiredArgsConstructor
@Slf4j
public class ServiceController {

    private final Service service;

    @GetMapping("list")
    public List<EntityDto> getEntities(@RequestHeader("ids") List<Long> ids){
        return service.getEntitiesByIds(ids);
    }

    @GetMapping("list/entities")
    public List<EntityDto> getEntitiesByCodeAndSysname(@RequestParam(value = "code", required = false) Integer code,
                                                       @RequestParam(value = "sysname", required = false) String sysname){
        return service.getEntitiesByCodeAndSysname(code, sysname);
    }

    @GetMapping("list/entities/{sysname}")
    public GetListRes getEntitiesBySysname(@PathVariable(value = "sysname") String sysname){
        return service.getList(sysname);
    }

}
