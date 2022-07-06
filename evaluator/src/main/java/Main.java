import models.Alarm;
import utils.HibernateUtil;

import java.sql.Time;
import java.util.NavigableSet;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {


        var session = HibernateUtil.getSessionFactory().openSession();



        Alarm alarm1 = new Alarm("HELLO", "BTC", "SMA", 15f, new Time(System.currentTimeMillis()));
        Alarm alarm2 = new Alarm("HELLO", "ETH", "SMA", 15f, new Time(System.currentTimeMillis()));

//        session.beginTransaction();
//        session.save(alarm1);
//        session.flush();
//        session.persist(alarm2);
//        session.getTransaction().commit();
//        var alarm = (Alarm) session.get(Alarm.class, 2);
//        System.out.println(alarm.getMarketSymbol());

    }
}

