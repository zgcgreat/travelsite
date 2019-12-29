package com.itcast.service;

import com.itcast.domain.DutyStarInf;
import com.itcast.domain.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Joy
 */
@Slf4j
@Service
public class DutyStarService {
    public void doBusiness(HttpServletRequest request, HttpServletResponse response) {
        String dutyDt = request.getParameter("dutyDt");
        //"yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        log.info(dutyDt, today);
        ResultInfo info = new ResultInfo();
        List<DutyStarInf> outParam = generateResut(dutyDt, today);
        info.setData(outParam);
        info.setFlag(true);
        //响应数据
        BaseService.writeValue(response, info);


    }

    private List<DutyStarInf> generateResut(String dutyDt, String today) {

        List<String> monList = getMonthBetween(dutyDt, today);
        List<DutyStarInf> outParam = new ArrayList<>();
        for (String month : monList) {
            DutyStarInf dutyStarInf = new DutyStarInf();
            dutyStarInf.setStarChgdate(month);
            int months = calMonths(dutyDt, month);
            dutyStarInf.setStarNum(getStarNum(months));
            if (months > 1 && months % 12 == 0) {
                dutyStarInf.setStarChgRsn("满" + months + "月增加50星");
            }
            outParam.add(dutyStarInf);
            log.info(month);
        }
        return outParam;
    }

    private int getStarNum(int months) {
        int starNum;
        starNum = (months / 12) * 50;
        return starNum;
    }


    /**
     * 计算两个日期之间相差的月份数量
     *
     * @param dutyDt
     * @param today
     * @return
     */
    private int calMonths(String dutyDt, String today) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        try {
            bef.setTime(sdf.parse(dutyDt));
            aft.setTime(sdf.parse(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        System.out.println(Math.abs(month + result));
        return Math.abs(month + result);

    }

    /**
     * 计算两个日期之间的月份
     *
     * @param minDate
     * @param maxDate
     * @return
     */
    private List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<>();
        //格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        try {
            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), min.get(Calendar.DATE));

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), max.get(Calendar.DATE));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //!max.before(min) min.before(max) || min.equals(max)
        while (min.before(max)) {
            result.add(sdf.format(min.getTime()));
            min.add(Calendar.MONTH, 1);
        }

        return result;
    }


}
