package com.example.user.Controller;

import com.example.user.Entity.Classroom;
import com.example.user.Repository.ClassroomRepository;
import com.example.user.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create")
    public Classroom createClassroom(@RequestBody Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @GetMapping("/getAll")
    public List<Classroom> getAllClassrooms(@RequestHeader("Authorization") String token) {
        validateToken(token);
        return classroomRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Classroom getClassroomById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        validateToken(token);
        return classroomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classroom not found"));
    }

    @PutMapping("/update/{id}")
    public Classroom updateClassroom(@PathVariable int id, @RequestBody Classroom updatedClassroom,
                                     @RequestHeader("Authorization") String token) {
        validateToken(token);
        return classroomRepository.findById(id).map(classroom -> {
            classroom.setDept(updatedClassroom.getDept());
            classroom.setSec(updatedClassroom.getSec());
            return classroomRepository.save(classroom);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classroom not found"));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteClassroom(@PathVariable int id, @RequestHeader("Authorization") String token) {
        validateToken(token);
        classroomRepository.deleteById(id);
        return "Classroom deleted successfully";
    }

    private void validateToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token format");
        }

        String jwtToken = token.substring(7);
        String email = jwtUtil.extractEmail(jwtToken);

        if (email == null || !jwtUtil.validateToken(jwtToken, email)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token validation failed");
        }
    }
}
