package com.bws.userservice.rest.service;

import com.bws.userservice.api.client.TokenServiceClient;
import com.bws.userservice.api.request.AddBalanceRequest;
import com.bws.userservice.api.request.BaseRequest;
import com.bws.userservice.api.request.CreateBalanceRequest;
import com.bws.userservice.api.response.AddBalanceResponse;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.api.response.GetBalanceResponse;
import com.bws.userservice.exception.CreateFailedException;
import com.bws.userservice.model.entity.Balance;
import com.bws.userservice.model.entity.User;
import com.bws.userservice.repository.BalanceRepository;
import com.bws.userservice.rest.service.interfaces.IBalanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bws.userservice.model.constants.ErrorCodeConstants.BALANCE_CREATE_FAILED;

@Service
@RequiredArgsConstructor
@Slf4j

public class BalanceServiceImpl implements IBalanceService {

    private final BalanceRepository balanceRepository;

    private final TokenServiceClient tokenServiceClient;

    @Override
    public AddBalanceResponse addBalance(AddBalanceRequest request) {
        String extractedUsername = tokenServiceClient.extractedUsername(request);

        Balance localUserBalance = balanceRepository.findBalanceByUsername(extractedUsername);
        localUserBalance.setAmount(localUserBalance.getAmount() + request.getAmount());
        log.info("Balance added username : " + localUserBalance.getUsername() + " , balance amount : " + localUserBalance.getAmount());
        balanceRepository.save(localUserBalance);
        AddBalanceResponse addBalanceResponse = new AddBalanceResponse();

        addBalanceResponse.setBalance(localUserBalance.getAmount());

        return addBalanceResponse;
    }

    @Override
    public GetBalanceResponse getBalance(BaseRequest request) {
        String extractedUsername = tokenServiceClient.extractedUsername(request);

        Balance localUserBalance = balanceRepository.findBalanceByUsername(extractedUsername);

        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setBalance(localUserBalance.getAmount());

        return getBalanceResponse;
    }

    @Override
    @Transactional
    public BaseResponse createBalance(CreateBalanceRequest request, User user) throws CreateFailedException{
        try {
            Balance newBalance = new Balance();
            newBalance.setMoney_code("TL");
            newBalance.setAmount(0L);
            newBalance.setUser(user);
            newBalance.setUsername(request.getUsername());
            balanceRepository.save(newBalance);

            return new BaseResponse();
        }catch (Exception e){
            log.error("Create balance error: " + e);
            throw new CreateFailedException(BALANCE_CREATE_FAILED);
        }
    }

}
