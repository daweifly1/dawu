package top.chendawei.util.vo;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapAdapter
        extends XmlAdapter<MapConvertor, Map<String, Object>> {
    public MapConvertor marshal(Map<String, Object> map)
            throws Exception {
        MapConvertor convertor = new MapConvertor();
        for (Entry<String, Object> entry : map.entrySet()) {
            MapConvertor.MapEntry e = new MapConvertor.MapEntry(entry);
            convertor.addEntry(e);
        }
        return convertor;
    }

    public Map<String, Object> unmarshal(MapConvertor map)
            throws Exception {
        Map<String, Object> result = new HashMap();
        for (MapConvertor.MapEntry e : map.getEntries()) {
            result.put(e.getKey(), e.getValue());
        }
        return result;
    }
}
