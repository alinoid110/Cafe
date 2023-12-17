package org.example.CoffeeBewerter.service.sessionService;

import org.example.CoffeeBewerter.repository.SessionRepository;
import org.example.CoffeeBewerter.model.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.example.CoffeeBewerter.SpecialText.GLOBAL_CONTEXT_DEFAULT;
import static org.example.CoffeeBewerter.constants.logsConstants.LogsConstants.NEW_SESSION_CREATED_LOG;
import static org.example.CoffeeBewerter.constants.regConstants.UserRegConstants.REGISTER_CONTEXT_DEFAULT;

@Slf4j
@Component
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public void CreateSession(long chatId) {
        if (sessionRepository.findById(chatId).isEmpty()) {

            UserSession userSession = new UserSession();
            userSession.setChatId(chatId);
            userSession.setGlobalFunctionContext(GLOBAL_CONTEXT_DEFAULT);
            userSession.setRegisterFunctionContext(REGISTER_CONTEXT_DEFAULT);
            userSession.setEditFunctionContext(REGISTER_CONTEXT_DEFAULT);
            userSession.setCafeRegisterFunctionContext(REGISTER_CONTEXT_DEFAULT);
            userSession.setEditReviewFunctionContext(GLOBAL_CONTEXT_DEFAULT);
            userSession.setReviewRegisterFunctionContext(GLOBAL_CONTEXT_DEFAULT);
            userSession.setNumberSearchReview(0L);
            userSession.setReviewRegisterFunctionId(0L);
            userSession.setNumberEditReview(0L);


            sessionRepository.save(userSession);

            log.info(NEW_SESSION_CREATED_LOG + userSession);
        }
    }

}
