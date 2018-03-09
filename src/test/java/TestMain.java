import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.junit.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.util.Calendar;

/**
 * Description: TestMain
 * Author: DIYILIU
 * Update: 2018-02-28 22:44
 */
public class TestMain {

    final User32 user = User32.INSTANCE;

    @Test
    public void test() {

        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {

            System.out.println(info.getName());
        }
    }

    @Test
    public void test1() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 12) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        System.out.println(calendar.getTime());
    }

    @Test
    public void testMove() throws Exception {
        WinDef.HWND hwnd = user.FindWindow(null, "幸运飞艇");
        if (hwnd != null) {
            user.ShowWindow(hwnd, 9);
            User32.INSTANCE.SetForegroundWindow(hwnd);

            WinDef.RECT planRect = new WinDef.RECT();
            User32.INSTANCE.GetWindowRect(hwnd, planRect);

            int width = planRect.right - planRect.left;
            int height = planRect.bottom - planRect.top;

            User32.INSTANCE.MoveWindow(hwnd, 0, 0, width, height, true);

            int x = width - 55;
            int y = 230;

            // 移动鼠标复制
            Robot myRobot = new Robot();
            myRobot.mouseMove(x, y);
            myRobot.setAutoDelay(200);
            myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
            myRobot.setAutoDelay(200);
            myRobot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);

            // 获取剪切板中的内容
            Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable clipTf = sysClip.getContents(null);
            if (clipTf != null) {
                // 检查内容是否是文本类型
                if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    String str = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
                    System.out.println(str);
                }
            }
        }
    }
}
