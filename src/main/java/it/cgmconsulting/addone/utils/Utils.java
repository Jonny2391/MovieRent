package it.cgmconsulting.addone.utils;

import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    // Parametro pairs -> array di coppie ELEMENTO(String), trovato(boolean)
    // Ritorna null se tutti gli elementi sono trovati
    public static String notFoundString(String notFoundString ,Pair<String,Boolean>... pairs){

        String msg = "";
        Set<String> notFoundElements = new HashSet<>();;

        Arrays.stream(pairs)
                .filter(a -> !a.getSecond())
                .forEach(a -> notFoundElements.add(a.getFirst()));

        if (!notFoundElements.isEmpty()) {
            msg = String.join(" and ", notFoundElements) + " " + notFoundString;
        }

        return msg;

    }
}
