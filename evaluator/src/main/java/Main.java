import models.Alarm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import utils.HibernateUtil;

import javax.imageio.spi.ServiceRegistry;
import java.sql.Time;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {


        var session = HibernateUtil.getSessionFactory().openSession();




        Alarm alarm1 = new Alarm("HELLO", "BTC", 15f, new Time(System.currentTimeMillis()));
        Alarm alarm2 = new Alarm("HELLO", "ETH", 15f, new Time(System.currentTimeMillis()));

        session.beginTransaction();
        session.save(alarm1);
        session.flush();
        session.save(alarm2);
        session.getTransaction().commit();
        var alarm = (Alarm) session.get(Alarm.class, 2);
        System.out.println(alarm.getMarketSymbol());

    }
}

