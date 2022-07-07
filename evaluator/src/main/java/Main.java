import com.evaluator.models.Alarm;
import com.evaluator.utils.HibernateUtil;

import java.sql.Time;

public class Main {
    public static void main(String[] args) {

        var alarm1 = new Alarm("ALARM1", "BTC", "SMA", 12, new Time(System.currentTimeMillis()));
        var alarm2 = new Alarm("ALARM2", "ETHtobtc", "SMA", 10, new Time(System.currentTimeMillis()));
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(alarm1);
        session.flush();
        session.persist(alarm2);
        session.getTransaction().commit();
        var alarm = (Alarm) session.get(Alarm.class, 2);
        System.out.println(alarm.getMarketSymbol());
        try {
            Thread.sleep(100000);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

