package cz.kocabek.animerecomedationsystem.security.validation;


import java.util.regex.Pattern;

@FunctionalInterface
public interface PasswordConstrain {

    boolean constrain(boolean constrain, Pattern pattern,String password);
}
