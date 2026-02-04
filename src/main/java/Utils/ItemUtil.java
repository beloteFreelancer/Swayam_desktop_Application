package Utils;

import com.selrom.db.DataUtil;
import java.sql.ResultSet;
import java.util.*;
import model.Item;

public class ItemUtil {

    public static List<Item> getItemsByItemNos(List<Integer> itemNos) {
        List<Item> items = new ArrayList<>();
        if (itemNos == null || itemNos.isEmpty()) {
            return items;
        }

        // Create frequency map to count duplicates
        Map<Integer, Integer> frequencyMap = new LinkedHashMap<>();
        for (Integer ino : itemNos) {
            frequencyMap.put(ino, frequencyMap.getOrDefault(ino, 0) + 1);
        }

        // Build IN clause from unique keys
        String inClause = frequencyMap.keySet().toString().replace("[", "(").replace("]", ")");
        String query = "SELECT * FROM item WHERE ino IN " + inClause;

        DataUtil dataUtil = new DataUtil();
        try (ResultSet rs = dataUtil.doQuery(query)) {
            Map<Integer, Item> itemMap = new HashMap<>();

            while (rs != null && rs.next()) {
                Item item = new Item();
                item.setIno(rs.getInt("ino"));
                item.setBarcode(rs.getString("barcode"));
                item.setIname(rs.getString("iname"));
                item.setIname1(rs.getString("iname1"));
                item.setMrp(rs.getDouble("mrp"));
                item.setRprice(rs.getDouble("rprice"));
                item.setWprice(rs.getDouble("wprice"));
                item.setBatch(rs.getString("batch"));
                item.setSize(rs.getString("size"));
                item.setMfg(rs.getString("mfg_date"));
                item.setExp(rs.getString("exp_date"));

                itemMap.put(item.getIno(), item);
            }

            // Clone and add items based on original list (with duplicates)
            for (Integer ino : itemNos) {
                Item original = itemMap.get(ino);
                if (original != null) {
                    items.add(cloneItem(original));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    private static Item cloneItem(Item original) {
        Item item = new Item();
        item.setIno(original.getIno());
        item.setBarcode(original.getBarcode());
        item.setIname(original.getIname());
        item.setIname1(original.getIname1());
        item.setMrp(original.getMrp());
        item.setRprice(original.getRprice());
        item.setWprice(original.getWprice());
        item.setBatch(original.getBatch());
        item.setSize(original.getSize());
        item.setMfg(original.getMfg());
        item.setExp(original.getExp());
        return item;
    }
}
