package com.backend.controllers;


import com.backend.utils.HibernateUtil;
import com.evaluator.models.Alarm;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/", produces = "application/json")
@CrossOrigin(origins = "*")
public class AlarmController {
    @GetMapping("/alarms")
    public ArrayList<Alarm> getAlarms() {
        var session = HibernateUtil.getSessionFactory().openSession();
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(Alarm.class);
        criteria.from(Alarm.class);
        var alarms = session.createQuery(criteria).getResultList();
        return (ArrayList<Alarm>) alarms;
    }
    @GetMapping("/alarms/{id}")
    public Alarm getAlarm(@PathVariable("id") int id) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var alarm = session.get(Alarm.class, id);
        return alarm;
    }
}
