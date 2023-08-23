package wanted.project.wantedpreonboardingbackend.lottery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.project.wantedpreonboardingbackend.lottery.service.LottoService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LottoController {
    private final LottoService lottoService;


}
