package com.odea;

import org.junit.Test;

/**
 * User: pbergonzi
 * Date: 16/10/12
 * Time: 11:36
 */

public class FechasTest {
   @Test
    public void testFechasSemana() {
        LocalDate now = new LocalDate();
        System.out.println(now.withDayOfWeek(DateTimeConstants.MONDAY));
        System.out.println(now.withDayOfWeek(DateTimeConstants.FRIDAY));
    }
}
