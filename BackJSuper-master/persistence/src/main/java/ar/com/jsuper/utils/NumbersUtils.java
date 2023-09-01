/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author rafa
 */
public class NumbersUtils {

    public static BigDecimal roundCutTwoDec(BigDecimal value) {
        return value.setScale(2, RoundingMode.DOWN);
    }

    public static String roundForPrint(BigDecimal value) {
        if (Objects.nonNull(value)) {
            return value.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        }
        return "";
    }

    public static String roundForPrint(Object value) {
        if (Objects.nonNull(value)) {
            if (value instanceof BigDecimal) {
                return roundForPrint((BigDecimal)value);
            }
        }
        return "";
    }


}
