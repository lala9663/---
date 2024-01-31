package wanted.project.wantedpreonboardingbackend.lottery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wanted.project.wantedpreonboardingbackend.lottery.dto.requestDto.CreateLottoRequestDto;
import wanted.project.wantedpreonboardingbackend.lottery.entity.Lotto;
import wanted.project.wantedpreonboardingbackend.lottery.repository.LottoRepository;
import wanted.project.wantedpreonboardingbackend.lottery.service.LottoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class LottoServiceImpl implements LottoService {

    private final LottoRepository lottoRepository;

    @Override
    public CreateLottoRequestDto createLotto(int quantity) {
        int nextRound = lottoRepository.findMaxRound() + 1; // 로직에 따라 추첨 회차 계산

        List<Integer> lottoNumbers = generateLottoNumbers();

        CreateLottoRequestDto createdLotto = new CreateLottoRequestDto();
        createdLotto.setRound(nextRound);
        createdLotto.setBall1(lottoNumbers.get(0));
        createdLotto.setBall2(lottoNumbers.get(1));
        createdLotto.setBall3(lottoNumbers.get(2));
        createdLotto.setBall4(lottoNumbers.get(3));
        createdLotto.setBall5(lottoNumbers.get(4));
        createdLotto.setBall6(lottoNumbers.get(5));
        createdLotto.setPrice(3000);

        // 저장 후 생성된 로또 엔티티 반환
        Lotto savedLotto = lottoRepository.save(createdLotto.toEntity());

        CreateLottoRequestDto lottoDto = new CreateLottoRequestDto();
        lottoDto.setRound(savedLotto.getRound());
        lottoDto.setBall1(savedLotto.getBall1());
        lottoDto.setBall2(savedLotto.getBall2());
        lottoDto.setBall3(savedLotto.getBall3());
        lottoDto.setBall4(savedLotto.getBall4());
        lottoDto.setBall5(savedLotto.getBall5());
        lottoDto.setBall6(savedLotto.getBall6());
        lottoDto.setPrice(savedLotto.getPrice());

        return lottoDto;
    }

    private List<Integer> generateLottoNumbers() {
        List<Integer> lottoNumbers = new ArrayList<>();
        Random random = new Random();

        while (lottoNumbers.size() < 6) {
            int randomNumber = random.nextInt(45) + 1;
            if (!lottoNumbers.contains(randomNumber)) {
                lottoNumbers.add(randomNumber);
            }
        }

        return lottoNumbers;
    }
}
