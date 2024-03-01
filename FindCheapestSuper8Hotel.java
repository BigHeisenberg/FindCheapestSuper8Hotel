import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Heisenberg
 * @create: 2024-03-01 09:31
 */
public class FindCheapestSuper8Hotel {
    private static Logger log = Logger.getLogger(FindCheapestSuper8Hotel.class);

    private static OkHttpClient httpClient = new OkHttpClient()
            .newBuilder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
//            .proxy(proxy)     //设置代理
            .build();

    /**
     * Get请求方法
     *
     * @param url
     * @return
     */
    public static String super8GetResult(String url) {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Cookie", "ApplicationGatewayAffinity=2ffcedd6130e0eb275479b88a8a4332d13dee9e85629d376f936e98c038facff; ApplicationGatewayAffinityCORS=2ffcedd6130e0eb275479b88a8a4332d13dee9e85629d376f936e98c038facff; Hm_lvt_bc81079fe1974492d2dd6997d0ca2402=1709196124; cityCodeCookie=110100; cityNameCookie=%E5%8C%97%E4%BA%AC%E5%B8%82; ASP.NET_SessionId=ftr10jt0lb0qfcmv5lfzn2ga; ozuid=; homeAdCoK=1; Hm_lpvt_bc81079fe1974492d2dd6997d0ca2402=1709196353; SECKEY_ABVK=+Qwt/Pke9ZsRWwxjY96aGo0TQJxOeZh7tBO4AVMkakA%3D; BMAP_SECKEY=8EX7GksZQeaBQ3q_cKG46qRwZKnr-EjihUbUeFho76pmZykfp3LDVO60HDLeTyEstzGqi7d2cABGJrvpHgKlgwUvZpk3PbNLHjGfkqSZ763x-T2Gm13eL8216eGQpvCKtd025VZ-AbV9jXIjICH28vsFlJz4rS4g4pE6B1GFXumIMbMN9kYsJseICojj5-7uixL5W1azjbgzc0GBW7IrMA")
                .tag(url)
                .get()
                .build();
        String result = null;
        try {
            Call call = httpClient.newCall(request);
            result = call.execute().body().string();
            closeByTag(call, request);//释放当前请求
        } catch (Exception e) {
            log.error("Fail to request，url:" + url, e);
        }
        return result;
    }

    /**
     * Post请求方法
     *
     * @param url
     * @param formBody
     * @return
     */
    public static String super8PostResult(String url, FormBody formBody) {
        Request request = new Request.Builder()
                .header("Cookie", "homeAdCoK=1; SECKEY_ABVK=+Qwt/Pke9ZsRWwxjY96aGm15U8gYmI+1dHWeBG2/XRk%3D; BMAP_SECKEY=8EX7GksZQeaBQ3q_cKG46vU12EEmojJJo0jPSHbbZFluN0KdSFnlrx1RKs8WZUZnG7za6Gb5xSVvJAM8OSaPQwG54w0_T-g3D5JDg41mpZlJuGCkoU2-j_SQoRYO_spM30dlRoSh__lupVqhH0uxKfZtBeyK9F_ck8kqFSrfaTBnKGrQMEOXN41cpUHo7W5oa7C18sGUcpEu1q8qkbqDsA; ApplicationGatewayAffinity=2ffcedd6130e0eb275479b88a8a4332d13dee9e85629d376f936e98c038facff; ApplicationGatewayAffinityCORS=2ffcedd6130e0eb275479b88a8a4332d13dee9e85629d376f936e98c038facff; Hm_lvt_bc81079fe1974492d2dd6997d0ca2402=1709196124; cityCodeCookie=110100; cityNameCookie=%E5%8C%97%E4%BA%AC%E5%B8%82; ASP.NET_SessionId=ftr10jt0lb0qfcmv5lfzn2ga; ozuid=; homeAdCoK=1; SECKEY_ABVK=+Qwt/Pke9ZsRWwxjY96aGo0TQJxOeZh7tBO4AVMkakA%3D; BMAP_SECKEY=8EX7GksZQeaBQ3q_cKG46qRwZKnr-EjihUbUeFho76pmZykfp3LDVO60HDLeTyEstzGqi7d2cABGJrvpHgKlgwUvZpk3PbNLHjGfkqSZ763x-T2Gm13eL8216eGQpvCKtd025VZ-AbV9jXIjICH28vsFlJz4rS4g4pE6B1GFXumIMbMN9kYsJseICojj5-7uixL5W1azjbgzc0GBW7IrMA; Hm_lpvt_bc81079fe1974492d2dd6997d0ca2402=1709198197")
                .url(url)
                .post(formBody)
                .build();
        String result = "";
        try {
            Call call = httpClient.newCall(request);
            result = call.execute().body().string();
            closeByTag(call, request);//释放当前请求
        } catch (IOException e) {
            log.error("Fail to request " + url + " with params " + formBody.toString(), e);
        }
        return result;
    }


    /**
     * 释放当前请求
     *
     * @param call
     * @param request
     */

    public static void closeByTag(Call call, Request request) {
        if (null == request || null == request.tag() || null == call) {
            return;
        }
        Object tag = request.tag();
        synchronized (call) {
            try {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            } catch (Exception e) {
                log.info("关闭当前请求时出现错误\n" + e.getStackTrace().toString());
            }
        }
    }

    public static void main(String[] args) {
        spiderCheapestSuper8Hotel();
    }

    public static void spiderCheapestSuper8Hotel() {
        String cityUrl = "https://www.super8.com.cn/Statics/Data/citydata.js";
        String hotelListUrl = "https://www.super8.com.cn/Hotel/HotelList";
        String getRoomUrl = "https://www.super8.com.cn/Hotel/GetRoom";
        String stime = "2024-03-05";
        String etime = "2024-03-09";
        JSONArray cityArray = getCityData(cityUrl);
        String cityName = null;
        String hotelName = null;
        Integer cheapestPrice = 200;
        for (int i = 0; i < cityArray.size(); i++) {
            JSONObject city = cityArray.getJSONObject(i);
            if ("钓鱼岛".equals(city.getString("name"))) {
                continue;
            }
            String code = city.getString("code");
            //获取酒店列表
            FormBody formBody1 = new FormBody.Builder().add("stime", stime).add("etime", etime).add("roomnum", "1")
                    .add("citycode", code).add("pageindex", "1").add("sorttype", "1").add("region", "0").add("landMrk", "0")
                    .add("newsearch", "0").add("pagesize", "400").build();
            String result1 = super8PostResult(hotelListUrl, formBody1);
            JSONObject hotelListObj = JSONObject.parseObject(result1);
            if (hotelListObj == null) {
                continue;
            }
            JSONObject data1 = hotelListObj.getJSONObject("data");
            if (data1 == null) {
                continue;
            }
            JSONArray arrdata1 = data1.getJSONArray("arrdata");
            if (arrdata1 == null) {
                continue;
            }
            //遍历酒店列表获取报价
            for (int j = 0; j < arrdata1.size(); j++) {
                JSONObject add = arrdata1.getJSONObject(j);
                String HotelID = add.getString("HotelID");
                if (add.getString("HotelName").contains("测试")) {
                    continue;
                }
                //根据酒店ID获取最新报价
                FormBody formBody2 = new FormBody.Builder().add("stime", stime).add("etime", etime).add("roomnum", "1")
                        .add("hotelid", HotelID).build();
                String result2 = super8PostResult(getRoomUrl, formBody2);
                JSONObject roomObj = JSONObject.parseObject(result2);
                if (roomObj == null) {
                    continue;
                }
                JSONObject data2 = hotelListObj.getJSONObject("data");
                if (data2 == null) {
                    continue;
                }
                JSONArray arrdata2 = data2.getJSONArray("arrdata");
                if (arrdata2 == null) {
                    continue;
                }
                Integer minPrice = arrdata2.getJSONObject(0).getInteger("MinPrice");
                for (int k = 0; k < arrdata2.size(); k++) {
                    JSONObject roomDetails = arrdata2.getJSONObject(k);
                    Integer tmpPrice = roomDetails.getInteger("MinPrice");
                    if (tmpPrice < minPrice && tmpPrice > 0) {
                        minPrice = tmpPrice;
                    }
                }
                if (minPrice < cheapestPrice && minPrice > 0) {
                    cheapestPrice = minPrice;
                    cityName = city.getString("name");
                    hotelName = add.getString("HotelName");
                    log.info("cheapest cityName:" + cityName + ",hotelName:" + hotelName + ",cheapestPrice:" + cheapestPrice
                            + ",startTime:" + stime + ",endTime:" + etime);
                }
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(30) * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("best cheapest cityName:" + cityName + ",hotelName:" + hotelName + ",cheapestPrice:" + cheapestPrice
                + ",startTime:" + stime + ",endTime:" + etime);
    }


    public static JSONArray getCityData(String url) {
        String result = super8GetResult(url);
        result = result.substring(result.indexOf("{"), result.length() - 1);
        result = result.substring(0, result.indexOf(";"));
        JSONObject resultObj = null;
        try {
            resultObj = JSONObject.parseObject(result);
        } catch (Exception e) {
            log.error("requstParams is error!" + url + ",result:" + result, e);
        }
        JSONArray array = resultObj.getJSONArray("normalcities");
        if (array == null) {
            return null;
        }
        return array;
    }
}
