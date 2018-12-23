package xmu.ghct.crm.dao;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateDao {

    public DateDao(){}

    public Date transferToDateTime(String dataTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss");
        Date date = formatter.parse(dataTime);
        return date;
    }

}
