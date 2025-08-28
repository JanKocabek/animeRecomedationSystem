package cz.kocabek.animerecomedationsystem.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
public class UserSessionData {
    private Integer userId;


}
