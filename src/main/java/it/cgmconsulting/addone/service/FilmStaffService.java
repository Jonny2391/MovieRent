package it.cgmconsulting.addone.service;

import it.cgmconsulting.addone.repository.FilmStaffRepository;
import it.cgmconsulting.addone.repository.StaffRepository;
import it.cgmconsulting.addone.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmStaffService {
    final private FilmStaffRepository filmStaffRepository;
    final private StaffRepository staffRepository;

    public ResponseEntity<?> getFilmsByActors(Set<Long> actorIds) {

        Set<Long> existentActorIds = staffRepository.getAllByIds(actorIds);

        Pair<String,Boolean>[] pairs =
                (Pair<String, Boolean>[]) actorIds.stream()
                        .map(value ->
                                Pair.of(value + "", existentActorIds.contains(value))).toArray(Pair[]::new);

        String msg = Utils.notFoundString(": Actors ids not found",
                pairs);

        // Se uno degli attori passati non esiste
        if(!msg.equals("")){
            return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(filmStaffRepository.getFilmsByActors(actorIds, actorIds.size()), HttpStatus.OK);
    }
}
