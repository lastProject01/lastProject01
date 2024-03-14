package com.project.Enovel.domain.rebate.service;

import com.project.Enovel.domain.rebate.entity.Rebate;
import com.project.Enovel.domain.rebate.repository.RebateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RebateService {
    private final RebateRepository rebateRepository;

    public void deleteRebate(Rebate rebate) {
        this.rebateRepository.delete(rebate);
    }
}
