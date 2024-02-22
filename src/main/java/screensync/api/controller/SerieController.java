package screensync.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import screensync.api.dto.SerieDTO;
import screensync.api.service.SerieService;

import java.util.List;

@RestController
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping("/series")
    public List<SerieDTO> obterSeries(){
        return service.obterTodasAsSeries();
    }

    @GetMapping("/series/top5")
    public List<SerieDTO> obtertop5Series(){
        return service.obtertop5Series();
    }
}
