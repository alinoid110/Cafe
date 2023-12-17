package org.example.CoffeeBewerter.service.generalFuncs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.example.CoffeeBewerter.SpecialText.generateStartReviewMessage;
import static org.example.CoffeeBewerter.constants.logsConstants.LogsConstants.START_COMMAND_USED_LOG;

@Slf4j
@Component
public class GeneralFuncs {

    public String startCommandReceived(long chatId, String name) {
        log.info(START_COMMAND_USED_LOG + chatId);
        return generateStartReviewMessage(name);
    }


}
