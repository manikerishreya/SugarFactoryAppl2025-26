package com.project.model;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CaneUtils {

    private static final Map<String, Integer> varietyMaturityMap = new HashMap<>();
    private static final Map<String, Integer> cropTypeMaturityMap = new HashMap<>();

    static {
        // Cane variety maturity (months) for PL
        varietyMaturityMap.put("Co-86032", 12);
        varietyMaturityMap.put("Co-94012", 12);
        varietyMaturityMap.put("Vasant 95", 11);
        varietyMaturityMap.put("Co-265", 13);

        // Crop type maturity months
        cropTypeMaturityMap.put("PL", 0);  // plant crop uses variety maturity
        cropTypeMaturityMap.put("R1", 9);
        cropTypeMaturityMap.put("R2", 9);
        cropTypeMaturityMap.put("R3", 9);
    }

    public static Date calculateCuttingDate(Date plantingDate, String caneVariety, String cropType) {
        if (plantingDate == null) return null;

        int monthsToAdd;
        if ("PL".equalsIgnoreCase(cropType)) {
            monthsToAdd = varietyMaturityMap.getOrDefault(caneVariety, 12);
        } else {
            monthsToAdd = cropTypeMaturityMap.getOrDefault(cropType, 9);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(plantingDate);
        cal.add(Calendar.MONTH, monthsToAdd);

        return cal.getTime();
    }
}
