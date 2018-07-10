package wjw.cron.ex;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import wjw.cron.ex.CronExpressionEx;
import wjw.cron.ex.DateFormatUtil;

/**
 *
 * @author Administrator
 */
public class MainTest {

  public MainTest() {
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of main method, of class Main.
   */
  @Test
  public void testMain() throws Exception {
    CronExpressionEx exp = new CronExpressionEx("0 3/5 3,5,14 1-30 * ?");

    System.out.println("toString: " + exp.toString());
    System.out.println("isValidExpression: " + CronExpressionEx.isValidExpression(exp.getCronExpression()));

    System.out.println("Fields->");
    System.out.println("getSecondsField: " + exp.getSecondsField() + " | getSecondsExp: " + exp.getSecondsExp());
    System.out.println("getMinutesField: " + exp.getMinutesField() + " | getMinutesExp: " + exp.getMinutesExp());
    System.out.println("getHoursField: " + exp.getHoursField() + " | getHoursExp: " + exp.getHoursExp());
    System.out.println("getDaysOfMonthField: " + exp.getDaysOfMonthField() + " | getDaysOfMonthExp: " + exp.getDaysOfMonthExp());
    System.out.println("getMonthsField: " + exp.getMonthsField() + " | getMonthsExp: " + exp.getMonthsExp());
    System.out.println("getDaysOfWeekField: " + exp.getDaysOfWeekField() + " | getDaysOfWeekExp: " + exp.getDaysOfWeekExp());
    System.out.println("<-Fields");

    java.util.Date dd = new java.util.Date();
    for (int i = 1; i < 6; i++) {
      dd = exp.getNextValidTimeAfter(dd);
      System.out.println("getNextValidTimeAfter()" + i + "." + DateFormatUtil.format("yyyy-MM-dd HH:mm:ss", dd));
      dd = new java.util.Date(dd.getTime() + 1000);
    }

  }
}
