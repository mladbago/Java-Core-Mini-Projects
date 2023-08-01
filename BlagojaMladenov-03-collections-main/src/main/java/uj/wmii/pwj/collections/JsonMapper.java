package uj.wmii.pwj.collections;

import java.util.List;
import java.util.Map;

public interface JsonMapper {

    String toJson(Map<String, ?> map);

    static JsonMapper defaultInstance() {
        return new JsonFormat();
    }
}
class JsonFormat implements JsonMapper {

    public String rewrite(Object o) {
        String toReturn = "";

        if (o instanceof String)
            toReturn = "\"" + ((String) o).replace("\"", "\\\"") + "\"";
        if (o instanceof Integer)
            toReturn = Integer.toString((Integer) o);
        if (o instanceof Short)
            toReturn = Short.toString((Short) o);
        if (o instanceof Long)
            toReturn = Long.toString((Long) o);
        if (o instanceof Byte)
            toReturn = Byte.toString((Byte) o);
        if (o instanceof Boolean)
            toReturn = Boolean.toString((Boolean) o);
        if (o instanceof Float)
            toReturn = Float.toString((Float) o);
        if (o instanceof Double)
            toReturn = Double.toString((Double) o);
        if (o instanceof List)
            toReturn = listMaker((List<?>) o);
        if (o instanceof Map)
            toReturn = mapMaker((Map<String, ?>) o);

        return toReturn;
    }
    @Override
    public String toJson(Map<String, ?> map) {

        if (map == null || map.isEmpty())
            return "{}";

        return mapMaker(map);
    }

    public String mapMaker(Map<String, ?> map) {

        StringBuilder stringMaker = new StringBuilder();
        stringMaker.append("{");

        stringMaker.append("\n");
        for (Map.Entry<String,?> entry : map.entrySet()) {
            stringMaker.append(rewrite(entry.getKey()) + ": " + rewrite((entry.getValue())) + ",");
        }
        if(map.size() != 0)
            stringMaker.deleteCharAt(stringMaker.length()-1);
        stringMaker.append("}");

        return stringMaker.toString();
    }
    public String listMaker(List<?> list) {
        StringBuilder stringMaker = new StringBuilder();
        stringMaker.append("[");
        for (Object o : list) {
            stringMaker.append(rewrite(o)).append(",");
        }
        if(list.size()!=0)
            stringMaker.deleteCharAt(stringMaker.length()-1);
        stringMaker.append("]");

        return stringMaker.toString();
    }
}
