package wanted.project.wantedpreonboardingbackend.lottery.service;

import wanted.project.wantedpreonboardingbackend.lottery.dto.requestDto.CreateLottoRequestDto;

public interface LottoService {
    CreateLottoRequestDto createLotto(int quantity);
}
