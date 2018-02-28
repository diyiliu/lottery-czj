import org.junit.Test;

/**
 * Description: TestMain
 * Author: DIYILIU
 * Update: 2018-02-28 22:44
 */
public class TestMain {

    @Test
    public void test(){

        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {

            System.out.println(info.getName());
        }
    }
}
