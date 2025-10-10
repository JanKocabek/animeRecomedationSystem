package cz.kocabek.animerecomedationsystem.account;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@SessionScope
@Data
public class UserSessionData {

    private Integer userId;
}
