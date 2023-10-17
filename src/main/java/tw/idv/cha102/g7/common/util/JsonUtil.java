package tw.idv.cha102.g7.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonUtil {//NOT IN USED by Reynolds
    public static final Gson GSON = new GsonBuilder().create();
    public static final String JSON_MIME_TYPE = "application/json";
    private static final Logger logger = LogManager.getLogger(JsonUtil.class);

    public static <P> P json2Pojo(HttpServletRequest request, Class<P> classOfPojo) {
        try (BufferedReader br = request.getReader()) {
            return GSON.fromJson(br, classOfPojo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <P> void writePojo2Json(HttpServletResponse response, P pojo) {
        response.setContentType(JSON_MIME_TYPE);
        try (PrintWriter pw = response.getWriter()) {
            pw.print(GSON.toJson(pojo));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
